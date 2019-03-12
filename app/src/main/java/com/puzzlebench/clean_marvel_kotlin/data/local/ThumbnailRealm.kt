package com.puzzlebench.clean_marvel_kotlin.data.local

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class ThumbnailRealm(
        @PrimaryKey
        var id: Int = DEFAULT_ID,
        var path: String = EMPTY_STRING,
        var extension: String = EMPTY_STRING
) : RealmObject()