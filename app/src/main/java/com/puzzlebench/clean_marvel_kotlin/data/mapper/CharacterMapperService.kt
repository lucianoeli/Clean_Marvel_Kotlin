package com.puzzlebench.clean_marvel_kotlin.data.mapper

import com.puzzlebench.clean_marvel_kotlin.data.service.response.CharacterResponse
import com.puzzlebench.clean_marvel_kotlin.data.service.response.ThumbnailResponse
import com.puzzlebench.clean_marvel_kotlin.domain.model.Character
import com.puzzlebench.clean_marvel_kotlin.domain.model.Thumbnail

open class CharacterMapperService : BaseMapperRepository<CharacterResponse, Character> {

    override fun transform(type: CharacterResponse): Character = Character(
            type.id,
            type.name,
            type.description,
            type.thumbnail?.let { transformToThumbnail(it) },
            type.comics,
            type.series,
            type.stories,
            type.events
    )

    override fun transformToRepository(type: Character): CharacterResponse = CharacterResponse(
            type.id,
            type.name,
            type.description,
            type.thumbnail?.let { transformToThumbnailResponse(it) },
            type.comics,
            type.series,
            type.stories,
            type.events
    )

    fun transformToThumbnail(thumbnailResponse: ThumbnailResponse): Thumbnail = Thumbnail(
            thumbnailResponse.id,
            thumbnailResponse.path,
            thumbnailResponse.extension
    )

    fun transformToThumbnailResponse(thumbnail: Thumbnail): ThumbnailResponse = ThumbnailResponse(
            thumbnail.id,
            thumbnail.path,
            thumbnail.extension
    )

    fun transform(charactersResponse: List<CharacterResponse>): List<Character> = charactersResponse.map { transform(it) }
}