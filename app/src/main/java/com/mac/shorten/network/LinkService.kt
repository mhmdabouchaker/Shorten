package com.mac.shorten.network

import com.mac.shorten.network.response.LinkResponse
import retrofit2.http.POST
import retrofit2.http.Query

interface LinkService {

    @POST("shorten")
    suspend fun shortenLink(@Query("url") url: String)
    : LinkResponse
}