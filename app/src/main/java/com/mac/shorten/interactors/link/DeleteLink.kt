package com.mac.shorten.interactors.link

import com.mac.shorten.cache.LinkDao
import com.mac.shorten.cache.model.LinkEntity
import com.mac.shorten.cache.model.LinkEntityMapper
import com.mac.shorten.domain.data.DataState
import com.mac.shorten.domain.model.Link
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DeleteLink(
    private val linkDao: LinkDao,
    private val entityMapper: LinkEntityMapper
) {
    fun run(code: String
    ): Flow<DataState<List<Link>>> = flow {
        try {
            // delete selected row
            deleteLinkFromCache(code)
            // get cached data
            val list = entityMapper.fromEntityList(getAllLinksFromCache())
            // check if list is empty
            if (list.isEmpty()){
                // emit empty
                emit(DataState.empty())
            }else{
                // emit success
                emit(DataState.success(list))
            }

        }catch (e: Exception){
            emit(DataState.error(e.message ?: "Unknown error"))
        }
    }

    private suspend fun deleteLinkFromCache(code: String){
        linkDao.deleteLink(code)
    }
    private suspend fun getAllLinksFromCache(): List<LinkEntity> {
        return linkDao.getAllLinks()
    }
}