package com.mac.shorten.interactors.linkList

import com.mac.shorten.cache.AppDatabaseFake
import com.mac.shorten.cache.LinkDaoFake
import com.mac.shorten.cache.model.LinkEntityMapper
import com.mac.shorten.domain.data.DataState
import com.mac.shorten.domain.model.Link
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetLinkListTest {

    private val appDatabase = AppDatabaseFake()

    private lateinit var getLinkList: GetLinkList

    private lateinit var linkDao: LinkDaoFake
    private val entityMapper = LinkEntityMapper()

    @Before
    fun setup(){
        linkDao = LinkDaoFake(appDatabaseFake = appDatabase)

        getLinkList = GetLinkList(
            linkDao = linkDao,
            entityMapper = entityMapper)
    }

    @Test
    fun getLinksFromCache_Success(): Unit = runBlocking{
        val link = Link("evdI2Q", "https://shrtco.de/evdI2Q", "http://helloworld.com")

        // insert fake link to fill in the cache
        linkDao.insertLink(entityMapper.mapFromDomainModel(link))

        // run use case
        val flowItems = getLinkList.run().toList()

        // confirm cache is not empty
        assert(linkDao.getAllLinks().isNotEmpty())

        // emission should be the list of links
        val links = flowItems[0].data
        // confirm they are actually Links objects
        assert(links?.get(index = 0) is Link)
    }

    @Test
    fun getLinksFromCache_Empty(): Unit = runBlocking {

        // run use case
        val flowItems = getLinkList.run().toList()

        // confirm cache is empty
        assert(linkDao.getAllLinks().isEmpty())

        // emission should be empty
        assert(flowItems[0].status == DataState.Status.EMPTY)
    }

}