package com.puzzlebench.clean_marvel_kotlin.domain.model

import com.puzzlebench.clean_marvel_kotlin.data.service.response.Comics
import com.puzzlebench.clean_marvel_kotlin.data.service.response.Events
import com.puzzlebench.clean_marvel_kotlin.data.service.response.Series
import com.puzzlebench.clean_marvel_kotlin.data.service.response.Stories

open class Character(
        val id: Int?,
        val name: String?,
        val description: String?,
        val thumbnail: Thumbnail?,
        val comics: Comics?,
        val series: Series?,
        val stories: Stories?,
        val events: Events?
)
