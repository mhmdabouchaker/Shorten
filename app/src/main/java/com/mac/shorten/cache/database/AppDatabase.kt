package com.mac.shorten.cache.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mac.shorten.cache.LinkDao
import com.mac.shorten.cache.model.LinkEntity

@Database(entities = [LinkEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase(){

    abstract fun linkDao(): LinkDao

    companion object{
        val DATABASE_NAME: String = "link_db"
    }
}