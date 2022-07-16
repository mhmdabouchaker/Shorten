package com.mac.shorten.network.model

import com.google.gson.annotations.SerializedName

data class LinkDto(
    @SerializedName("code")
    val code: String,

    @SerializedName("full_short_link")
    var fullShortLink: String,

    @SerializedName("original_link")
    var originalLink: String
)
