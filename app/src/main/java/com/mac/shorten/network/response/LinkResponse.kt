package com.mac.shorten.domain.model

import com.google.gson.annotations.SerializedName

data class LinkResponse(
    @SerializedName("ok")
    val status : Boolean,

    @SerializedName("result")
    val result: Link
)
