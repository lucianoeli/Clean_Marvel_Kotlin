package com.puzzlebench.clean_marvel_kotlin.presentation.mvp.model

import com.puzzlebench.clean_marvel_kotlin.domain.model.Character
import com.puzzlebench.clean_marvel_kotlin.domain.usecase.GetCharacterAditionalInfoServiceUseCase
import io.reactivex.Observable

class CharacterDetailModel(val getCharacterAditionalInfoServiceUseCase: GetCharacterAditionalInfoServiceUseCase) {
    fun getCharacterAdditionalInfoServiceUseCase(characterId: Int): Observable<Character> = getCharacterAditionalInfoServiceUseCase.invoke(characterId)
}
