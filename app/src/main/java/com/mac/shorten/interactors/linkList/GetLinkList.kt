package com.mac.shorten.interactors.linkList

import com.mac.shorten.cache.LinkDao
import com.mac.shorten.cache.model.LinkEntity
import com.mac.shorten.cache.model.LinkEntityMapper
import com.mac.shorten.domain.data.DataState
import com.mac.shorten.domain.model.Link
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetLinkList(
    private val linkDao: LinkDao,
    private val entityMapper: LinkEntityMapper,
) {
    fun run(): Flow<DataState<List<Link>>> = flow {

        try {
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
            e.printStackTrace()
            emit(DataState.error(e.message ?: "Unknown error"))
        }
    }


    private suspend fun getAllLinksFromCache(): List<LinkEntity> {
        return linkDao.getAllLinks()
    }
}