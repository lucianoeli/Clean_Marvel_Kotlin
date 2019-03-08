package com.puzzlebench.clean_marvel_kotlin.data.local

import com.puzzlebench.clean_marvel_kotlin.data.mapper.CharacterMapperLocal
import com.puzzlebench.clean_marvel_kotlin.domain.model.Character
import io.realm.Realm

class SaveCharactersLocalImpl(private val mapper: CharacterMapperLocal = CharacterMapperLocal()) {
    fun saveCharacters(characters: List<Character>) {
        val realm = Realm.getDefaultInstance()
        val realmList = mapper.transformToRealmCharacterList(characters)
        realm.beginTransaction()
        realm.copyToRealmOrUpdate(realmList)
        realm.commitTransaction()
    }
}