package com.puzzlebench.clean_marvel_kotlin.domain.model

import io.realm.RealmObject

open class Character(
        val id: Int,
        val name: String,
        val description: String,
        val thumbnail: Thumbnail
)
