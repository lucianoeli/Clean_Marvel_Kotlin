package com.puzzlebench.clean_marvel_kotlin.data.local

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

const val EMPTY_STRING = ""
const val DEFAULT_ID = 0

open class CharacterRealm(
        @PrimaryKey
        var id: Int = DEFAULT_ID,
        var name: String = EMPTY_STRING,
        var description: String = EMPTY_STRING,
        var thumbnail: ThumbnailRealm? = ThumbnailRealm(id),
        var comics: ComicsRealm? = ComicsRealm(id),
        var series: SeriesRealm? = SeriesRealm(id),
        var stories: StoriesRealm? = StoriesRealm(id),
        var events: EventsRealm? = EventsRealm(id)
) : RealmObject()
