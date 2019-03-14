package com.puzzlebench.clean_marvel_kotlin.presentation.mvp

import com.puzzlebench.clean_marvel_kotlin.R
import com.puzzlebench.clean_marvel_kotlin.domain.model.Character
import com.puzzlebench.clean_marvel_kotlin.mocks.factory.CharactersFactory
import com.puzzlebench.clean_marvel_kotlin.presentation.mvp.model.CharacterModel
import com.puzzlebench.clean_marvel_kotlin.presentation.mvp.presenter.CharacterPresenter
import com.puzzlebench.clean_marvel_kotlin.presentation.mvp.view.CharacterView
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.inOrder
import org.mockito.Mockito.mock
import org.mockito.Mockito.never
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class CharacterPresenterTest {
    @Mock
    private lateinit var model: CharacterModel

    @Mock
    private lateinit var view: CharacterView

    private lateinit var characterPresenter: CharacterPresenter

    companion object {
        private const val SIZE = 10
    }

    @Before
    fun setUp() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler -> Schedulers.trampoline() }
        MockitoAnnotations.initMocks(this)
        val subscriptions = mock(CompositeDisposable::class.java)
        characterPresenter = CharacterPresenter(view, model, subscriptions)


    }

    @Test
    fun reposeWithError() {
        Mockito.`when`(model.getCharacterServiceUseCase()).thenReturn(Observable.error(Exception("")))
        characterPresenter.init()
        val order = Mockito.inOrder(view, model)
        order.verify(view).init()
        order.verify(view).showLoading()
        order.verify(model).getCharacterServiceUseCase()
        verify(model, never()).saveLocalCharactersUseCase(ArgumentMatchers.anyCollection<Character>() as List<Character>)
        verify(view, never()).showToast(R.string.message_no_items_to_show)
        verify(view, never()).showToast(R.string.toast_msg_data_loaded)
        verify(view).showToast(ArgumentMatchers.anyString())
    }

    @Test
    fun reposeWithItemToShow() {
        val itemsCharecters = CharactersFactory.getMockCharacter(SIZE)
        val observable = Observable.just(itemsCharecters)
        Mockito.`when`(model.getCharacterServiceUseCase()).thenReturn(observable)
        characterPresenter.init()
        val order = Mockito.inOrder(view, model)
        order.verify(view).init()
        order.verify(view).showLoading()
        order.verify(model).getCharacterServiceUseCase()
        order.verify(view).showCharacters(itemsCharecters)
        order.verify(model).saveLocalCharactersUseCase(itemsCharecters)
        order.verify(view).showToast(R.string.toast_msg_data_loaded)
        order.verify(view).hideLoading()
    }

    @Test
    fun reposeWithoutItemToShow() {
        val itemsCharecters = emptyList<Character>()
        val observable = Observable.just(itemsCharecters)
        Mockito.`when`(model.characterServiceUseCase.invoke()).thenReturn(observable)
        characterPresenter.requestGetCharacters()
        val order = inOrder(view, model)
        order.verify(view).init()
        order.verify(view).showLoading()
        order.verify(model.characterServiceUseCase.invoke())
        order.verify(view).showToast(R.string.message_no_items_to_show)
        order.verify(view).hideLoading()
        verify(model.saveLocalCharactersUseCase.invoke(itemsCharecters), never())
        verify(view, never()).showCharacters(itemsCharecters)
    }
}
