package com.puzzlebench.clean_marvel_kotlin.data.local

import com.puzzlebench.clean_marvel_kotlin.data.mapper.CharacterMapperLocal
import com.puzzlebench.clean_marvel_kotlin.domain.model.Character
import io.realm.Realm

class GetCharactersLocalImpl {
    val mapper = CharacterMapperLocal()
    fun getCharacters(): List<Character> {
        val realm = Realm.getDefaultInstance()
        val characters = realm.where(CharacterRealm::class.java).findAll()
        return mapper.transformToCharacterList(characters)
    }
}