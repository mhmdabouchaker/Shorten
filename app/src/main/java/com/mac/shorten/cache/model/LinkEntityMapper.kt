package com.mac.shorten.cache.model

import com.mac.shorten.domain.model.Link
import com.mac.shorten.domain.util.DomainMapper

class LinkEntityMapper : DomainMapper<LinkEntity, Link>{
    override fun mapToDomainModel(model: LinkEntity): Link {
        return Link(
            model.code,
            model.fullShortLink,
            model.originalLink
        )
    }

    override fun mapFromDomainModel(domainModel: Link): LinkEntity {
        return LinkEntity(
            domainModel.code,
            domainModel.full_short_link,
            domainModel.original_link
        )
    }

    fun fromEntityList(initial: List<LinkEntity>): List<Link>{
        return initial.map { mapToDomainModel(it) }
    }
}