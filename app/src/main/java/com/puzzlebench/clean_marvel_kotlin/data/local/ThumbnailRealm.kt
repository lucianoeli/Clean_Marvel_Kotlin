package com.puzzlebench.clean_marvel_kotlin.data.local

import io.realm.RealmObject

class ThumbnailRealm(
        val path: String? = EMPTY_STRING,
        val extension: String? = EMPTY_STRING
) : RealmObject()