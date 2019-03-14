package com.puzzlebench.clean_marvel_kotlin.domain.usecase

import com.puzzlebench.clean_marvel_kotlin.data.service.CharacterServicesImpl
import com.puzzlebench.clean_marvel_kotlin.mocks.factory.CharactersFactory
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class GetCharacterServiceUseCaseTest {

    private lateinit var characterServiceImp: CharacterServicesImpl

    companion object {
        private const val SIZE = 10
    }

    @Before
    fun setUp() {
        val videoItems = CharactersFactory.getMockCharacter(SIZE)
        val observable = Observable.just(videoItems)
        characterServiceImp = mock(CharacterServicesImpl::class.java)
        `when`(characterServiceImp.getCharacters()).thenReturn(observable)
    }

    @Test
    operator fun invoke() {
        val getCharacterServiceUseCase = GetCharacterServiceUseCase(characterServiceImp)
        getCharacterServiceUseCase.invoke()
        verify(characterServiceImp).getCharacters()
    }

}