package com.puzzlebench.clean_marvel_kotlin.data.local
import io.realm.RealmObject

const val EMPTY_STRING = ""
const val DEFAULT_ID = 0

open class CharacterRealm (
        var id: Int? = DEFAULT_ID,
        var name: String? = EMPTY_STRING,
        var description: String? = EMPTY_STRING,
        var thumbnail: ThumbnailRealm? = ThumbnailRealm()
) : RealmObject()
