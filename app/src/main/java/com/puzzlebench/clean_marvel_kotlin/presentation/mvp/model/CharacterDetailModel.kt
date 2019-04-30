package com.puzzlebench.clean_marvel_kotlin.presentation.mvp.model

import com.puzzlebench.clean_marvel_kotlin.domain.model.Character
import com.puzzlebench.clean_marvel_kotlin.domain.usecase.GetCharacterAditionalInfoServiceUseCase
import io.reactivex.Observable

open class CharacterDetailModel(val getCharacterAditionalInfoServiceUseCase: GetCharacterAditionalInfoServiceUseCase) {
    open fun getCharacterAdditionalInfoServiceUseCase(characterId: Int): Observable<Character> = getCharacterAditionalInfoServiceUseCase.invoke(characterId)
}
