package com.puzzlebench.clean_marvel_kotlin.data.mapper

import com.puzzlebench.clean_marvel_kotlin.data.service.response.CharacterResponse
import com.puzzlebench.clean_marvel_kotlin.data.service.response.Comics
import com.puzzlebench.clean_marvel_kotlin.data.service.response.Events
import com.puzzlebench.clean_marvel_kotlin.data.service.response.Series
import com.puzzlebench.clean_marvel_kotlin.data.service.response.Stories
import com.puzzlebench.clean_marvel_kotlin.data.service.response.ThumbnailResponse
import com.puzzlebench.clean_marvel_kotlin.domain.model.Character
import com.puzzlebench.clean_marvel_kotlin.domain.model.Thumbnail
import junit.framework.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations

class CharacterMapperServiceTest {

    private lateinit var mapper: CharacterMapperService
    private lateinit var mockComic: Comics
    private lateinit var mockSeries: Series
    private lateinit var mockStories: Stories
    private lateinit var mockEvents: Events

    private val ID = 12342
    private val NAME = "sport"
    private val DESCRIPTION = "some description"
    private val PATH = "http:/some.com/"
    private val EXTENSION = ".PNG"
    private val AVAILABLE = 21
    private val COLLECTIONURI = "URISAMPLE"
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
        val mockThumbnail = Thumbnail(PATH, EXTENSION)
        val mockCharacter = Character(NAME, DESCRIPTION, mockThumbnail)
        val result = mapper.transformToRepository(mockCharacter)
        assertBufferooDataEquality(result, mockCharacter)

    }

    private fun assertBufferooDataEquality(characterResponse: CharacterResponse,
                                           character: Character) {
        Assert.assertEquals(characterResponse.name, character.name)
        Assert.assertEquals(characterResponse.description, character.description)
        Assert.assertEquals(characterResponse.thumbnail.path, character.thumbnail.path)
        Assert.assertEquals(characterResponse.thumbnail.extension, character.thumbnail.extension)

    }

}