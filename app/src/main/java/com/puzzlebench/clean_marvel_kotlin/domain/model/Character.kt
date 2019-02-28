package com.puzzlebench.clean_marvel_kotlin.domain.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Character(
        @PrimaryKey
        var id: Int? = null,
        var name: String? = EMPTY_STRING,
        var description: String? = EMPTY_STRING,
        var thumbnail: Thumbnail? = null
) : RealmObject()
