package com.mac.shorten.network.model

import com.mac.shorten.domain.model.Link
import com.mac.shorten.domain.util.DomainMapper

class LinkDtoMapper : DomainMapper<LinkDto, Link> {
    override fun mapToDomainModel(model: LinkDto): Link {
        return Link(
            model.code,
            model.fullShortLink,
            model.originalLink
        )
    }

    override fun mapFromDomainModel(domainModel: Link): LinkDto {
        return LinkDto(
            domainModel.code,
            domainModel.full_short_link,
            domainModel.original_link
        )
    }
}