package com.puzzlebench.clean_marvel_kotlin.data.local

import com.puzzlebench.clean_marvel_kotlin.presentation.mvp.presenter.ZERO
import io.realm.RealmObject

open class ComicsRealm (
        var available: Int? = ZERO,
        var collectionURI: String? = EMPTY_STRING
) : RealmObject()