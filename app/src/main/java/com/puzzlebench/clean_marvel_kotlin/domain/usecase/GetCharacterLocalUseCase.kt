package com.puzzlebench.clean_marvel_kotlin.domain.usecase

import com.puzzlebench.clean_marvel_kotlin.data.local.GetCharactersLocalImpl

open class GetCharacterLocalUseCase(private val getCharactersLocalImpl: GetCharactersLocalImpl){
    open operator fun invoke() = getCharactersLocalImpl.getCharacters()
}