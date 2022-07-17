package com.mac.shorten.cache

import com.mac.shorten.cache.model.LinkEntity

class LinkDaoFake(
    private val appDatabaseFake: AppDatabaseFake
) : LinkDao {
    override suspend fun insertLink(link: LinkEntity) {
        appDatabaseFake.links.add(link)
    }

    override suspend fun getAllLinks(): List<LinkEntity> {
        return appDatabaseFake.links
    }

    override suspend fun deleteLink(code: String) {
        appDatabaseFake.links.removeIf { it.code == code }
    }
}