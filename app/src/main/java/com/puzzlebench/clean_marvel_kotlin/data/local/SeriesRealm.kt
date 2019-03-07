package com.puzzlebench.clean_marvel_kotlin.data.local

import com.puzzlebench.clean_marvel_kotlin.presentation.mvp.presenter.ZERO
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class SeriesRealm(
        @PrimaryKey
        var id: Int = ZERO,
        var available: Int? = ZERO,
        var collectionURI: String? = EMPTY_STRING
) : RealmObject()