package com.puzzlebench.clean_marvel_kotlin.data.mapper

import com.puzzlebench.clean_marvel_kotlin.data.local.CharacterRealm
import com.puzzlebench.clean_marvel_kotlin.data.local.ComicsRealm
import com.puzzlebench.clean_marvel_kotlin.data.local.EventsRealm
import com.puzzlebench.clean_marvel_kotlin.data.local.SeriesRealm
import com.puzzlebench.clean_marvel_kotlin.data.local.StoriesRealm
import com.puzzlebench.clean_marvel_kotlin.data.local.ThumbnailRealm
import com.puzzlebench.clean_marvel_kotlin.data.service.response.Comics
import com.puzzlebench.clean_marvel_kotlin.data.service.response.Events
import com.puzzlebench.clean_marvel_kotlin.data.service.response.Series
import com.puzzlebench.clean_marvel_kotlin.data.service.response.Stories
import com.puzzlebench.clean_marvel_kotlin.domain.model.Character
import com.puzzlebench.clean_marvel_kotlin.domain.model.Thumbnail

class CharacterMapperLocal : BaseMapperRepository<CharacterRealm, Character> {

    override fun transform(type: CharacterRealm): Character = Character(
            type.id,
            type.name,
            type.description,
            transformToThumbnail(type.thumbnail),
            transformToComics(type.comics),
            transformToSeries(type.series),
            transformToStories(type.stories),
            transformToEvents(type.events)
    )

    override fun transformToRepository(type: Character): CharacterRealm = CharacterRealm(
            type.id,
            type.name,
            type.description,
            transformToThumbnailRealm(type.thumbnail),
            transformToComicsRealm(type.comics),
            transformToSeriesRealm(type.series),
            transformToStorieslRealm(type.stories),
            transformToEventsRealm(type.events)
    )

    fun transformToThumbnailRealm(thumbnail: Thumbnail?): ThumbnailRealm = ThumbnailRealm(
            thumbnail?.path,
            thumbnail?.extension
    )

    fun transformToStorieslRealm(stories: Stories?): StoriesRealm = StoriesRealm(
            stories?.available,
            stories?.collectionURI
    )

    fun transformToComicsRealm(comics: Comics?): ComicsRealm = ComicsRealm(
            comics?.available,
            comics?.collectionURI
    )

    fun transformToEventsRealm(events: Events?): EventsRealm = EventsRealm(
            events?.available,
            events?.collectionURI
    )

    fun transformToSeriesRealm(series: Series?): SeriesRealm = SeriesRealm(
            series?.available,
            series?.collectionURI
    )


    fun transformToThumbnail(thumbnailRealm: ThumbnailRealm?) = Thumbnail(
            thumbnailRealm?.path,
            thumbnailRealm?.extension
    )

    fun transformToSeries(seriesRealm: SeriesRealm?) = Series(
            seriesRealm?.available,
            seriesRealm?.collectionURI
    )

    fun transformToStories(storiesRealm: StoriesRealm?) = Stories(
            storiesRealm?.available,
            storiesRealm?.collectionURI
    )

    fun transformToEvents(eventsRealm: EventsRealm?) = Events(
            eventsRealm?.available,
            eventsRealm?.collectionURI
    )

    fun transformToComics(comicsRealm: ComicsRealm?) = Comics(
            comicsRealm?.available,
            comicsRealm?.collectionURI
    )

    fun transformToRealmCharacterList(listCharacters: List<Character>) = listCharacters.map { transformToRepository(it) }

    fun transformToCharacterList(listRealmCharacters: List<CharacterRealm>) = listRealmCharacters.map { transform(it) }
}
