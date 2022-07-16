package com.mac.shorten.interactors.link

import android.text.TextUtils
import android.util.Patterns
import com.mac.shorten.cache.LinkDao
import com.mac.shorten.cache.model.LinkEntity
import com.mac.shorten.cache.model.LinkEntityMapper
import com.mac.shorten.domain.data.DataState
import com.mac.shorten.domain.model.Link
import com.mac.shorten.network.LinkService
import com.mac.shorten.network.model.LinkDtoMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ShortenLink(
    private val linkDao: LinkDao,
    private val entityMapper: LinkEntityMapper,
    private val linkService: LinkService,
    private val linkDtoMapper: LinkDtoMapper
) {
    fun run(
        url: String
    ): Flow<DataState<List<Link>>> = flow {
        try {
            emit(DataState.loading())

            val isUrlValid = Patterns.WEB_URL.matcher(url).matches()
            if (!isUrlValid) {
                // emit error
                emit(DataState.error("Please add a valid link"))
            } else if (TextUtils.isEmpty(url)) {
                // emit error
                emit(DataState.error("Please add a link here"))
            } else {
                try {
                    // Convert: NetworkLinkEntity -> Link -> LinkCacheEntity
                    val networkShortenLink = shortenLinkFromNetwork(url)
                    // insert into cache
                    linkDao.insertLink(
                        // map domain -> entity
                        entityMapper.mapFromDomainModel(networkShortenLink)
                    )
                } catch (e: Exception) {
                    // there was a network issue
                    e.printStackTrace()
                }

                val list = entityMapper.fromEntityList(getAllLinksFromCache())
                // check if list is not Empty
                if (list.isNotEmpty()) {
                    // emit success
                    emit(DataState.success(list))
                } else {
                    // emit list is empty
                    emit(DataState.empty())
                }
            }

        } catch (e: Exception) {
            emit(DataState.error(e.message ?: "Unknown error"))
        }
    }


    private suspend fun shortenLinkFromNetwork(url: String): Link {
        return linkDtoMapper.mapToDomainModel(
            linkService.shortenLink(url).result
        )
    }

    private suspend fun getAllLinksFromCache(): List<LinkEntity> {
        return linkDao.getAllLinks()
    }
}