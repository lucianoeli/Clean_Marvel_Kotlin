package com.puzzlebench.clean_marvel_kotlin.data.mapper

import com.puzzlebench.clean_marvel_kotlin.data.service.response.CharacterResponse
import com.puzzlebench.clean_marvel_kotlin.data.service.response.Comics
import com.puzzlebench.clean_marvel_kotlin.data.service.response.Events
import com.puzzlebench.clean_marvel_kotlin.data.service.response.Series
import com.puzzlebench.clean_marvel_kotlin.data.service.response.Stories
import com.puzzlebench.clean_marvel_kotlin.data.service.response.ThumbnailResponse
import com.puzzlebench.clean_marvel_kotlin.domain.model.Character
import com.puzzlebench.clean_marvel_kotlin.domain.model.Thumbnail
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations

class CharacterMapperServiceTest {

    private lateinit var mapper: CharacterMapperService

    companion object {
        private const val COLLECTIONURI = "URISAMPLE"
        private const val ID = 12342
        private const val NAME = "sport"
        private const val DESCRIPTION = "some description"
        private const val PATH = "http:/some.com/"
        private const val EXTENSION = ".PNG"
        private const val AVAILABLE = 21
    }


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        mapper = CharacterMapperService()
    }

    @Test
    fun transform() {
        val mockThumbnailResponse = ThumbnailResponse(ID, PATH, EXTENSION)
        val mockComic = Comics(ID, AVAILABLE, COLLECTIONURI)
        val mockSeries = Series(ID, AVAILABLE, COLLECTIONURI)
        val mockStories = Stories(ID, AVAILABLE, COLLECTIONURI)
        val mockEvents = Events(ID, AVAILABLE, COLLECTIONURI)

        val mockCharacterResponse = CharacterResponse(ID, NAME, DESCRIPTION, mockThumbnailResponse, mockComic, mockSeries, mockStories, mockEvents)
        val result = mapper.transform(mockCharacterResponse)
        assertBufferooDataEquality(mockCharacterResponse, result)
    }

    @Test
    fun transformToRepository() {
        val mockThumbnail = Thumbnail(ID, PATH, EXTENSION)
        val mockComic = Comics(ID, AVAILABLE, COLLECTIONURI)
        val mockSeries = Series(ID, AVAILABLE, COLLECTIONURI)
        val mockStories = Stories(ID, AVAILABLE, COLLECTIONURI)
        val mockEvents = Events(ID, AVAILABLE, COLLECTIONURI)

        val mockCharacter = Character(ID, NAME, DESCRIPTION, mockThumbnail, mockComic, mockSeries, mockStories, mockEvents)
        val result = mapper.transformToRepository(mockCharacter)
        assertBufferooDataEquality(result, mockCharacter)
    }

    private fun assertBufferooDataEquality(characterResponse: CharacterResponse, character: Character) {
        assertEquals(characterResponse.id, character.id)
        assertEquals(characterResponse.name, character.name)
        assertEquals(characterResponse.description, character.description)
        assertEquals(characterResponse.thumbnail?.path, character.thumbnail?.path)
        assertEquals(characterResponse.thumbnail?.extension, character.thumbnail?.extension)
        assertEquals(characterResponse.comics?.available, character.comics?.available)
        assertEquals(characterResponse.comics?.collectionURI, character.comics?.collectionURI)
        assertEquals(characterResponse.stories?.available, character.stories?.available)
        assertEquals(characterResponse.stories?.collectionURI, character.stories?.collectionURI)
        assertEquals(characterResponse.events?.available, character.events?.available)
        assertEquals(characterResponse.events?.collectionURI, character.events?.collectionURI)
        assertEquals(characterResponse.series?.available, character.series?.available)
        assertEquals(characterResponse.series?.collectionURI, character.series?.collectionURI)
    }



}