package com.puzzlebench.clean_marvel_kotlin.domain.model

import com.puzzlebench.clean_marvel_kotlin.data.local.DEFAULT_ID
import com.puzzlebench.clean_marvel_kotlin.data.local.EMPTY_STRING
import com.puzzlebench.clean_marvel_kotlin.data.service.response.Comics
import com.puzzlebench.clean_marvel_kotlin.data.service.response.Events
import com.puzzlebench.clean_marvel_kotlin.data.service.response.Series
import com.puzzlebench.clean_marvel_kotlin.data.service.response.Stories

open class Character(
        val id: Int = DEFAULT_ID,
        val name: String = EMPTY_STRING,
        val description: String = EMPTY_STRING,
        val thumbnail: Thumbnail? = Thumbnail(),
        val comics: Comics? = Comics(),
        val series: Series? = Series(),
        val stories: Stories? = Stories(),
        val events: Events? = Events()
)
