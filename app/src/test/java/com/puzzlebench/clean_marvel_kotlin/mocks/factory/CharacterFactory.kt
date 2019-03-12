package com.puzzlebench.clean_marvel_kotlin.mocks.factory

import com.puzzlebench.clean_marvel_kotlin.data.service.response.Comics
import com.puzzlebench.clean_marvel_kotlin.domain.model.Character
import com.puzzlebench.clean_marvel_kotlin.domain.model.Thumbnail
import com.puzzlebench.clean_marvel_kotlin.presentation.mvp.presenter.ZERO

class CharactersFactory {

    companion object Factory {
        private const val BASE_ID = ZERO
        private const val BASE_NAME = "Name"
        private const val BASE_DESCRIPTION = "Description"
        private const val BASE_PATH = "image"
        private const val BASE_EXTENSION = ".png"

        open fun getMockCharacter(): List<Character> {
            return listOf(1..5).map {
                Character(
                        BASE_ID,
                        "$BASE_NAME$it",
                        "$BASE_DESCRIPTION$it",
                        Thumbnail(BASE_ID,"$BASE_PATH$it", BASE_EXTENSION),
                        Comics()
                )
            }
        }
    }
}
