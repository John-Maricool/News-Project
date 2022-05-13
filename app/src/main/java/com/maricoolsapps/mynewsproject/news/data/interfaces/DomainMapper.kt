package com.maricoolsapps.mynewsproject.news.data.interfaces

interface DomainMapper <T, DomainModel>{

    fun mapToDomainModel(model: T): DomainModel

   // fun mapFromDomainModel(domainModel: DomainModel): T
}