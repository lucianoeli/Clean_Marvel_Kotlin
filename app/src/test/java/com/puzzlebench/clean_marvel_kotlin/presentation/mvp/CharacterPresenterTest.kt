package com.puzzlebench.clean_marvel_kotlin.presentation.mvp

import com.puzzlebench.clean_marvel_kotlin.data.local.SaveCharactersLocalImpl
import com.puzzlebench.clean_marvel_kotlin.data.service.CharacterServicesImpl
import com.puzzlebench.clean_marvel_kotlin.domain.model.Character
import com.puzzlebench.clean_marvel_kotlin.domain.usecase.GetCharacterServiceUseCase
import com.puzzlebench.clean_marvel_kotlin.domain.usecase.SaveLocalCharactersUseCase
import com.puzzlebench.clean_marvel_kotlin.mocks.factory.CharactersFactory
import com.puzzlebench.clean_marvel_kotlin.presentation.mvp.presenter.CharacterPresenter
import com.puzzlebench.clean_marvel_kotlin.presentation.mvp.view.CharacterView
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.never
import org.mockito.Mockito.verify

class CharacterPresenterTest {
    @Mock
    private var view = mock(CharacterView::class.java)
    private var characterServiceImp = mock(CharacterServicesImpl::class.java)
    private var saveCharactersLocalImpl = mock(SaveCharactersLocalImpl::class.java)
    private lateinit var getCharacterServiceUseCase: GetCharacterServiceUseCase
    private lateinit var saveLocalCharactersUseCase: SaveLocalCharactersUseCase
    private lateinit var characterPresenter: CharacterPresenter

    @Before
    fun setUp() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler -> Schedulers.trampoline() }
        getCharacterServiceUseCase = GetCharacterServiceUseCase(characterServiceImp)
        saveLocalCharactersUseCase = SaveLocalCharactersUseCase(saveCharactersLocalImpl)
        val subscriptions = mock(CompositeDisposable::class.java)
        characterPresenter = CharacterPresenter(view, getCharacterServiceUseCase, saveLocalCharactersUseCase, subscriptions)
    }

    @Test
    fun reposeWithError() {
       // val itemsCharecters = CharactersFactory.getMockCharacter()
        val e = Throwable("")
        Mockito.`when`(getCharacterServiceUseCase.invoke()).thenReturn(Observable.error(e))
        characterPresenter.init()
        characterPresenter.requestGetCharacters()
        verify(view).init()
        verify(view).showLoading()
        verify(characterServiceImp).getCharacters()
        //verify(view).hideLoading()
        verify(view).showToastNetworkError(e.message.toString())

    }

    @Test
    fun reposeWithItemToShow() {
        val itemsCharecters = CharactersFactory.getMockCharacter()
        val observable = Observable.just(itemsCharecters)
        Mockito.`when`(getCharacterServiceUseCase.invoke()).thenReturn(observable)
        characterPresenter.init()
        characterPresenter.requestGetCharacters()
        verify(view).init()
        verify(view).showLoading()
        verify(characterServiceImp).getCharacters()
        verify(saveCharactersLocalImpl).saveCharacters(itemsCharecters)
        verify(view).hideLoading()
        verify(view).showCharacters(itemsCharecters)
    }

    @Test
    fun reposeWithoutItemToShow() {
        val itemsCharecters = emptyList<Character>()
        val observable = Observable.just(itemsCharecters)
        Mockito.`when`(getCharacterServiceUseCase.invoke()).thenReturn(observable)
        characterPresenter.init()
        characterPresenter.requestGetCharacters()
        verify(view).init()
        verify(view).showLoading()
        verify(characterServiceImp).getCharacters()
        verify(saveCharactersLocalImpl, never()).saveCharacters(itemsCharecters)
        verify(view).hideLoading()
        verify(view, never()).showCharacters(itemsCharecters)
    }

}