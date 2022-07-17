package com.mac.shorten.interactors.link

import com.mac.shorten.cache.AppDatabaseFake
import com.mac.shorten.cache.LinkDaoFake
import com.mac.shorten.cache.model.LinkEntityMapper
import org.junit.Before

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


}