package com.puzzlebench.clean_marvel_kotlin.data.local

import io.realm.RealmObject

open class ThumbnailRealm(
        var path: String? = EMPTY_STRING,
        var extension: String? = EMPTY_STRING
) : RealmObject()