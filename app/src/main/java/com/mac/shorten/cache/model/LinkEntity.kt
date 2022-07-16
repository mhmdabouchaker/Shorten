package com.mac.shorten.cache.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "links")
data class LinkEntity (

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "code")
    var code: String,

    @ColumnInfo(name = "full_short_link")
    var fullShortLink: String,

    @ColumnInfo(name = "original_link")
    var originalLink: String

)