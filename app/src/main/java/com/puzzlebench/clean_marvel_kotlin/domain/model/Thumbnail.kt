package com.puzzlebench.clean_marvel_kotlin.domain.model

import com.puzzlebench.clean_marvel_kotlin.data.local.DEFAULT_ID
import com.puzzlebench.clean_marvel_kotlin.data.local.EMPTY_STRING

open class Thumbnail(
        val id: Int = DEFAULT_ID,
        val path: String = EMPTY_STRING,
        val extension: String = EMPTY_STRING
)

