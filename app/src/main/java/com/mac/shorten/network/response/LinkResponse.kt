package com.mac.shorten.network.response

import com.google.gson.annotations.SerializedName
import com.mac.shorten.domain.model.Link
import com.mac.shorten.network.model.LinkDto

data class LinkResponse(
    @SerializedName("ok")
    val status : Boolean,

    @SerializedName("result")
    val result: LinkDto
)
