package com.mac.shorten.interactors.link

import com.mac.shorten.cache.AppDatabaseFake
import com.mac.shorten.cache.LinkDaoFake
import com.mac.shorten.cache.model.LinkEntityMapper
import com.mac.shorten.domain.data.DataState
import com.mac.shorten.domain.model.Link
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class DeleteLinkTest {

    private val appDatabase = AppDatabaseFake()

    private lateinit var deleteLink: DeleteLink
    private lateinit var linkDao: LinkDaoFake
    private val entityMapper = LinkEntityMapper()

    @Before
    fun setup(){
        linkDao = LinkDaoFake(appDatabase)

        deleteLink = DeleteLink(
            linkDao = linkDao,
            entityMapper = entityMapper
        )
    }

    @Test
    fun deleteLinkFromCache_GetListOfLinks_Success(): Unit = runBlocking {
        val link1 = Link("evdI2Q", "https://shrtco.de/evdI2Q", "http://helloworld.com")
        // insert fake first link to fill in the cache
        linkDao.insertLink(entityMapper.mapFromDomainModel(link1))

        val link2 = Link("pNBXFS", "https://shrtco.de/pNBXFS", "http://test.com")
        // insert fake second link to fill in the cache
        linkDao.insertLink(entityMapper.mapFromDomainModel(link2))

        // run user case
        val flowItems = deleteLink.run(link1.code).toList()

        // confirm cache is not empty
        assert(linkDao.getAllLinks().isNotEmpty())

        // emission should be the list of links
        val links = flowItems[0].data
        // confirm they are actually Links objects
        assert(links?.get(index = 0) is Link)
    }

    @Test
    fun deleteLinkFromCache_GetListOfLinks_Empty(): Unit = runBlocking {
        val link = Link("evdI2Q", "https://shrtco.de/evdI2Q", "http://helloworld.com")
        // insert fake first link to fill in the cache
        linkDao.insertLink(entityMapper.mapFromDomainModel(link))

        // run user case
        val flowItems = deleteLink.run(link.code).toList()

        // confirm cache is empty
        assert(linkDao.getAllLinks().isEmpty())

        // emission should be empty
        assert(flowItems[0].status == DataState.Status.EMPTY)
    }
}