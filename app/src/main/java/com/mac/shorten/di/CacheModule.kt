package com.mac.shorten.di

import androidx.room.Room
import com.mac.shorten.BaseApplication
import com.mac.shorten.cache.LinkDao
import com.mac.shorten.cache.database.AppDatabase
import com.mac.shorten.cache.model.LinkEntityMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CacheModule {

    @Singleton
    @Provides
    fun provideDb(app: BaseApplication): AppDatabase{
        return Room
            .databaseBuilder(app, AppDatabase::class.java,AppDatabase.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideLinkDao(db: AppDatabase): LinkDao{
        return db.linkDao()
    }

    @Singleton
    @Provides
    fun provideCacheLinkMapper(): LinkEntityMapper{
        return LinkEntityMapper()
    }
}