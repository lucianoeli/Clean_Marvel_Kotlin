package com.puzzlebench.clean_marvel_kotlin.presentation.mvp

import com.puzzlebench.clean_marvel_kotlin.R
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
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.InOrder
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.any
import org.mockito.Mockito.inOrder
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

    companion object {
        private const val SIZE = 10
    }

    @Before
    fun setUp() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler -> Schedulers.trampoline() }
        getCharacterServiceUseCase = GetCharacterServiceUseCase(characterServiceImp)
        saveLocalCharactersUseCase = SaveLocalCharactersUseCase(saveCharactersLocalImpl)
        val subscriptions = mock(CompositeDisposable::class.java)
        characterPresenter = CharacterPresenter(view, getCharacterServiceUseCase, saveLocalCharactersUseCase, subscriptions)
        characterPresenter.init()
    }

    @Test
    fun reposeWithError() {
        Mockito.`when`(getCharacterServiceUseCase.invoke()).thenReturn(Observable.error(Exception("")))
        characterPresenter.requestGetCharacters()
        val order = Mockito.inOrder(view, characterServiceImp)
        order.verify(view).init()
        order.verify(view).showLoading()
        order.verify(characterServiceImp).getCharacters()
        verify(saveCharactersLocalImpl, never()).saveCharacters(ArgumentMatchers.anyCollection<Character>() as List<Character>)
        verify(view, never()).showToast(R.string.message_no_items_to_show)
        verify(view, never()).showToast(R.string.toast_msg_data_loaded)
        verify(view).showToast(ArgumentMatchers.anyString())
    }

    @Test
    fun reposeWithItemToShow() {
        val itemsCharecters = CharactersFactory.getMockCharacter(SIZE)
        val observable = Observable.just(itemsCharecters)
        Mockito.`when`(getCharacterServiceUseCase.invoke()).thenReturn(observable)
        characterPresenter.requestGetCharacters()
        val order = Mockito.inOrder(view, characterServiceImp, saveCharactersLocalImpl)
        order.verify(view).init()
        order.verify(view).showLoading()
        order.verify(characterServiceImp).getCharacters()
        order.verify(view).showCharacters(itemsCharecters)
        order.verify(saveCharactersLocalImpl).saveCharacters(itemsCharecters)
        order.verify(view).showToast(R.string.toast_msg_data_loaded)
        order.verify(view).hideLoading()
    }

    @Test
    fun reposeWithoutItemToShow() {
        val itemsCharecters = emptyList<Character>()
        val observable = Observable.just(itemsCharecters)
        Mockito.`when`(getCharacterServiceUseCase.invoke()).thenReturn(observable)
        characterPresenter.requestGetCharacters()
        val order = inOrder(view, characterServiceImp)
        order.verify(view).init()
        order.verify(view).showLoading()
        order.verify(characterServiceImp).getCharacters()
        order.verify(view).showToast(R.string.message_no_items_to_show)
        order.verify(view).hideLoading()
        verify(saveCharactersLocalImpl, never()).saveCharacters(itemsCharecters)
        verify(view, never()).showCharacters(itemsCharecters)
    }
}