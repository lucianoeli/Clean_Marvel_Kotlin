package com.puzzlebench.clean_marvel_kotlin.domain.model

import io.realm.RealmObject
const val EMPTY_STRING = ""
open class Thumbnail(
        var path: String? = EMPTY_STRING,
        var extension: String? = EMPTY_STRING
) : RealmObject()

