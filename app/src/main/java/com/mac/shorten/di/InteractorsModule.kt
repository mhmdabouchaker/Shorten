package com.mac.shorten.di

import com.mac.shorten.cache.LinkDao
import com.mac.shorten.cache.model.LinkEntityMapper
import com.mac.shorten.interactors.link.ShortenLink
import com.mac.shorten.network.LinkService
import com.mac.shorten.network.model.LinkDtoMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object InteractorsModule {

    @ViewModelScoped
    @Provides
    fun provideShortenLink(
        linkDao: LinkDao,
        entityMapper: LinkEntityMapper,
        linkService: LinkService,
        linkDtoMapper: LinkDtoMapper
    ): ShortenLink {
        return ShortenLink(
            linkDao = linkDao,
            entityMapper = entityMapper,
            linkService = linkService,
            linkDtoMapper = linkDtoMapper)
    }
}