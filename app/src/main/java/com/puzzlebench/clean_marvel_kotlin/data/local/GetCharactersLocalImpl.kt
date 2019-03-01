package com.puzzlebench.clean_marvel_kotlin.data.local

import com.puzzlebench.clean_marvel_kotlin.data.mapper.CharacterMapperLocal
import com.puzzlebench.clean_marvel_kotlin.domain.model.Character
import io.realm.Realm

class GetCharactersLocalImpl {
    val mapper = CharacterMapperLocal()
    fun getCharacters(): List<Character> {
        // Get a Realm instance for this thread
        val realm = Realm.getDefaultInstance()

        // Query Realm for all dogs younger than 2 years old
        val characters = realm.where(CharacterRealm::class.java).findAll()
        return mapper.transformToCharacterList(characters)
    }
}