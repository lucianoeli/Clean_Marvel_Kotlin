package com.puzzlebench.clean_marvel_kotlin.data.service.response

import com.puzzlebench.clean_marvel_kotlin.data.local.DEFAULT_ID
import com.puzzlebench.clean_marvel_kotlin.data.local.EMPTY_STRING
import com.puzzlebench.clean_marvel_kotlin.presentation.mvp.presenter.ZERO

class Comics (
        val id: Int = DEFAULT_ID,
        val available: Int = ZERO,
        val collectionURI: String = EMPTY_STRING
)
