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

        fun getMockCharacter(size: Int): List<Character> {
            val characters = mutableListOf<Character>()
            var char: Character
            for (i in 1..size) {
                char = Character(
                        i,
                        "$BASE_NAME $i",
                        "$BASE_DESCRIPTION$i",
                        Thumbnail(BASE_ID, "$BASE_PATH$i", "$BASE_EXTENSION$i"),
                        Comics()
                )
                characters.add(char)
            }
            return characters
        }
    }
}
