package com.mac.shorten.ui.util.extensions

import android.util.Patterns

fun isUrlEmpty(url: String): Boolean{
    return url.isEmpty()
}

fun isUrlNotValid(url: String): Boolean{
    return !Patterns.WEB_URL.matcher(url).matches()
}