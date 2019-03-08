package com.puzzlebench.clean_marvel_kotlin.data.local

import com.puzzlebench.clean_marvel_kotlin.data.mapper.CharacterMapperLocal
import com.puzzlebench.clean_marvel_kotlin.domain.model.Character
import io.realm.Realm

class SaveCharactersLocalImpl(private val mapper: CharacterMapperLocal = CharacterMapperLocal()) {
    fun saveCharacters(characters: List<Character>) {
        val realm = Realm.getDefaultInstance()
        realm.executeTransaction {
            it.insertOrUpdate(mapper.transformToRealmCharacterList(characters))
        }
    }
}