package com.puzzlebench.clean_marvel_kotlin.presentation.mvp.model

import com.puzzlebench.clean_marvel_kotlin.domain.model.Character
import com.puzzlebench.clean_marvel_kotlin.domain.usecase.GetCharacterServiceUseCase
import com.puzzlebench.clean_marvel_kotlin.domain.usecase.SaveLocalCharactersUseCase
import io.reactivex.Observable

open class CharacterModel(val characterServiceUseCase: GetCharacterServiceUseCase, val saveLocalCharactersUseCase: SaveLocalCharactersUseCase) {
    open fun getCharacterServiceUseCase(): Observable<List<Character>> = characterServiceUseCase.invoke()
    open fun saveLocalCharactersUseCase(chars: List<Character>) = saveLocalCharactersUseCase.invoke(chars)
}
