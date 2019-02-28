package com.puzzlebench.clean_marvel_kotlin.data.service.response

import com.puzzlebench.clean_marvel_kotlin.domain.model.EMPTY_STRING

class ThumbnailResponse(
        var path: String? = EMPTY_STRING,
        var extension: String? = EMPTY_STRING
)