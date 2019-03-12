package com.puzzlebench.clean_marvel_kotlin.data.local

import android.content.ContentValues
import com.puzzlebench.clean_marvel_kotlin.data.mapper.CharacterMapperLocal
import com.puzzlebench.clean_marvel_kotlin.data.provider.CharacterProvider
import com.puzzlebench.clean_marvel_kotlin.data.provider.CharacterProvider.Companion.DESCRIPTION_COLUM
import com.puzzlebench.clean_marvel_kotlin.data.provider.CharacterProvider.Companion.ID_COLUM
import com.puzzlebench.clean_marvel_kotlin.data.provider.CharacterProvider.Companion.NAME_COLUM
import com.puzzlebench.clean_marvel_kotlin.data.provider.CharacterProvider.Companion.THUMBNAIL_EXTENSION_COLUM
import com.puzzlebench.clean_marvel_kotlin.data.provider.CharacterProvider.Companion.THUMBNAIL_PATH_COLUM
import com.puzzlebench.clean_marvel_kotlin.domain.model.Character

class SaveCharactersLocalImpl(
        private val mapper: CharacterMapperLocal = CharacterMapperLocal(),
        private val characterProvider: CharacterProvider = CharacterProvider()) {

    fun saveCharacters(characters: List<Character>) {
        for (character in characters){
            val cv = ContentValues()
            cv.put(ID_COLUM, character.id)
            cv.put(NAME_COLUM, character.name)
            cv.put(DESCRIPTION_COLUM, character.description)
            cv.put(THUMBNAIL_PATH_COLUM, character.thumbnail?.path)
            cv.put(THUMBNAIL_EXTENSION_COLUM, character.thumbnail?.extension)
            characterProvider.insert(CharacterProvider.CONTENT_URI, cv)
        }
    }
}