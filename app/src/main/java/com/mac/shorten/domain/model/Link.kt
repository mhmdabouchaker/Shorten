package com.mac.shorten.domain.model

data class Link(
    val code: String,
    var full_short_link: String,
    var original_link: String
)
