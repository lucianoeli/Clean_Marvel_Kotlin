package com.puzzlebench.clean_marvel_kotlin.domain.usecase

import com.puzzlebench.clean_marvel_kotlin.data.service.CharacterServicesImpl
import com.puzzlebench.clean_marvel_kotlin.domain.model.Character
import io.reactivex.Observable

open class GetCharacterAditionalInfoServiceUseCase(private val characterServicesImpl: CharacterServicesImpl) {
    open operator fun invoke(id: Int): Observable<Character> = characterServicesImpl.getCharacterById(id)
}