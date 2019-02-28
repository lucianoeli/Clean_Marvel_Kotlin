package com.puzzlebench.clean_marvel_kotlin.data.service.response

import com.puzzlebench.clean_marvel_kotlin.domain.model.EMPTY_STRING

class CharacterResponse (
        val id: Int? = null,
        val name: String? = EMPTY_STRING,
        val description: String? = EMPTY_STRING,
        val thumbnail: ThumbnailResponse? = null
)