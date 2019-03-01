package com.puzzlebench.clean_marvel_kotlin.data.mapper

import com.puzzlebench.clean_marvel_kotlin.data.local.CharacterRealm
import com.puzzlebench.clean_marvel_kotlin.data.local.ThumbnailRealm
import com.puzzlebench.clean_marvel_kotlin.domain.model.Character
import com.puzzlebench.clean_marvel_kotlin.domain.model.Thumbnail

class CharacterMapperLocal : BaseMapperRepository<CharacterRealm, Character> {

    override fun transform(type: CharacterRealm): Character = Character(
            type.id,
            type.name,
            type.description,
            transformToThumbnail(type.thumbnail)
    )

    override fun transformToRepository(type: Character): CharacterRealm = CharacterRealm(
            type.id,
            type.name,
            type.description,
            transformToThumbnailRealm(type.thumbnail)
    )

    fun transformToThumbnailRealm(thumbnail: Thumbnail?): ThumbnailRealm = ThumbnailRealm(
            thumbnail?.path,
            thumbnail?.extension
    )

    fun transformToThumbnail(thumbnailRealm: ThumbnailRealm?) = Thumbnail(
            thumbnailRealm?.path,
            thumbnailRealm?.extension
    )

    fun transformToRealmCharacterList(listCharacters: List<Character>) = listCharacters.map { transformToRepository(it) }

    fun transformToCharacterList(listRealmCharacters: List<CharacterRealm>) = listRealmCharacters.map { transform(it) }
}
