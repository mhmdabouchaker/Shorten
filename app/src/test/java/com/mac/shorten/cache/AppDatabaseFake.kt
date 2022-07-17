package com.mac.shorten.cache

import com.mac.shorten.cache.model.LinkEntity

class AppDatabaseFake {

    // fake for link table in local db
    val links = mutableListOf<LinkEntity>()
}