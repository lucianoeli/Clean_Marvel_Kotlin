package com.puzzlebench.clean_marvel_kotlin.domain.usecase
import com.puzzlebench.clean_marvel_kotlin.data.local.SaveCharactersLocalImpl
import com.puzzlebench.clean_marvel_kotlin.domain.model.Character

open class SaveLocalCharactersUseCase (private val saveCharactersLocalImpl: SaveCharactersLocalImpl) {
    open operator fun invoke(characters: List<Character>) = saveCharactersLocalImpl.saveCharacters(characters)
}