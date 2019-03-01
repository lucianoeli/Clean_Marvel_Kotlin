package com.puzzlebench.clean_marvel_kotlin.data.mapper

import com.puzzlebench.clean_marvel_kotlin.data.service.response.CharacterResponse
import com.puzzlebench.clean_marvel_kotlin.data.service.response.ThumbnailResponse
import com.puzzlebench.clean_marvel_kotlin.domain.model.Character

import com.puzzlebench.clean_marvel_kotlin.domain.model.Thumbnail

open class CharacterMapperService : BaseMapperRepository<CharacterResponse, Character> {

    override fun transform(characterResponse: CharacterResponse): Character = Character(
            characterResponse.id,
            characterResponse.name,
            characterResponse.description,
            transformToThumbnail(characterResponse.thumbnail),
            characterResponse.comics,
            characterResponse.series,
            characterResponse.stories,
            characterResponse.events
    )

    override fun transformToResponse(type: Character): CharacterResponse = CharacterResponse(
            type.id,
            type.name,
            type.description,
            transformToThumbnailResponse(type.thumbnail),
            type.comics,
            type.series,
            type.stories,
            type.events
    )

    fun transformToThumbnail(thumbnailResponse: ThumbnailResponse): Thumbnail = Thumbnail(
            thumbnailResponse.path,
            thumbnailResponse.extension
    )

    fun transformToThumbnailResponse(thumbnail: Thumbnail): ThumbnailResponse = ThumbnailResponse(
            thumbnail.path,
            thumbnail.extension
    )

    fun transform(charactersResponse: List<CharacterResponse>): List<Character> =
            charactersResponse.map { transform(it) }


}