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
            type.thumbnail?.let { transformToThumbnail(it) },
            type.comics?.let { transformToComics(it) },
            type.series?.let { transformToSeries(it) },
            type.stories?.let { transformToStories(it) },
            type.events?.let { transformToEvents(it) }
    )

    override fun transformToRepository(type: Character): CharacterRealm = CharacterRealm(
            type.id,
            type.name,
            type.description,
            type.thumbnail?.let { transformToThumbnailRealm(it) },
            type.comics?.let { transformToComicsRealm(it) },
            type.series?.let { transformToSeriesRealm(it) },
            type.stories?.let { transformToStorieslRealm(it) },
            type.events?.let { transformToEventsRealm(it) }
    )

    fun transformToThumbnailRealm(thumbnail: Thumbnail): ThumbnailRealm = ThumbnailRealm(
            thumbnail.id,
            thumbnail.path,
            thumbnail.extension
    )

    fun transformToStorieslRealm(stories: Stories): StoriesRealm = StoriesRealm(
            stories.id,
            stories.available,
            stories.collectionURI
    )

    fun transformToComicsRealm(comics: Comics): ComicsRealm = ComicsRealm(
            comics.id,
            comics.available,
            comics.collectionURI
    )

    fun transformToEventsRealm(events: Events): EventsRealm = EventsRealm(
            events.id,
            events.available,
            events.collectionURI
    )

    fun transformToSeriesRealm(series: Series): SeriesRealm = SeriesRealm(
            series.id,
            series.available,
            series.collectionURI
    )


    fun transformToThumbnail(thumbnailRealm: ThumbnailRealm) = Thumbnail(
            thumbnailRealm.id,
            thumbnailRealm.path,
            thumbnailRealm.extension
    )

    fun transformToSeries(seriesRealm: SeriesRealm) = Series(
            seriesRealm.id,
            seriesRealm.available,
            seriesRealm.collectionURI
    )

    fun transformToStories(storiesRealm: StoriesRealm) = Stories(
            storiesRealm.id,
            storiesRealm.available,
            storiesRealm.collectionURI
    )

    fun transformToEvents(eventsRealm: EventsRealm) = Events(
            eventsRealm.id,
            eventsRealm.available,
            eventsRealm.collectionURI
    )

    fun transformToComics(comicsRealm: ComicsRealm) = Comics(
            comicsRealm.id,
            comicsRealm.available,
            comicsRealm.collectionURI
    )

    fun transformToRealmCharacterList(listCharacters: List<Character>) = listCharacters.map { transformToRepository(it) }

    fun transformToCharacterList(listRealmCharacters: List<CharacterRealm>) = listRealmCharacters.map { transform(it) }
}
