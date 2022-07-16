package com.mac.shorten.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mac.shorten.cache.model.LinkEntity

@Dao
interface LinkDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLink(link: LinkEntity)

    @Query("SELECT * FROM links")
    suspend fun getAllLinks(): List<LinkEntity>

    @Query("DELETE FROM links WHERE code = :code")
    suspend fun deleteLink(code: String)

}