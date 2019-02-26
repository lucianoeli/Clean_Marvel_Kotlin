package com.puzzlebench.clean_marvel_kotlin.domain.usecase

import com.puzzlebench.clean_marvel_kotlin.data.service.CharacterFullInfoServiceImpl
import com.puzzlebench.clean_marvel_kotlin.domain.model.CharacterFullInfo
import io.reactivex.Observable

open class GetCharacterFullInfoServiceUseCase(private val characterFullInfoServiceImpl: CharacterFullInfoServiceImpl) {
    open operator fun invoke(): Observable<CharacterFullInfo> = characterFullInfoServiceImpl.getCharacterById()
}