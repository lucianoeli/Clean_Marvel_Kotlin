package com.puzzlebench.clean_marvel_kotlin.presentation.mvp

import com.puzzlebench.clean_marvel_kotlin.R
import com.puzzlebench.clean_marvel_kotlin.data.service.CharacterServicesImpl
import com.puzzlebench.clean_marvel_kotlin.domain.model.Character
import com.puzzlebench.clean_marvel_kotlin.domain.usecase.GetCharacterAditionalInfoServiceUseCase
import com.puzzlebench.clean_marvel_kotlin.mocks.factory.CharactersFactory
import com.puzzlebench.clean_marvel_kotlin.presentation.mvp.model.CharacterDetailModel
import com.puzzlebench.clean_marvel_kotlin.presentation.mvp.presenter.CharacterDetailPresenter
import com.puzzlebench.clean_marvel_kotlin.presentation.mvp.view.CharacterDetailView
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.any
import org.mockito.Mockito.mock
import org.mockito.Mockito.never
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class CharacterDetailPresenterTest {

    companion object {
        const val CHARACTER_ID = 12211
        const val ONE = 1
    }

    private lateinit var characterDetailPresenter: CharacterDetailPresenter
    @Mock
    private lateinit var model: CharacterDetailModel

    @Mock
    private lateinit var view: CharacterDetailView
    @Mock
    private lateinit var subscriptions: CompositeDisposable
    @Mock
    private lateinit var obs: Observable<Character>

    @Before
    fun setUp() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler -> Schedulers.trampoline() }
        MockitoAnnotations.initMocks(this)
        characterDetailPresenter = CharacterDetailPresenter(view, model, subscriptions, CHARACTER_ID)
    }

    @Test
    fun requestCharacterWithInfoCharacterToShow() {
        val character = CharactersFactory.getMockCharacter(ONE).first()
        val observable = Observable.just(character)
        Mockito.`when`(model.getCharacterAdditionalInfoServiceUseCase(CHARACTER_ID)).thenReturn(observable)
        characterDetailPresenter.init()
        val order = Mockito.inOrder(view, model)
        order.verify(view).init()
        order.verify(model).getCharacterAdditionalInfoServiceUseCase(CHARACTER_ID)
        order.verify(view).showCharacterDetail(character)
        order.verify(view, times(1)).hideLoading()
        verify(view, never()).showToast(R.string.message_no_items_to_show)
    }

    @Test
    fun requestCharacterWithError() {
        Mockito.`when`(model.getCharacterAdditionalInfoServiceUseCase(CHARACTER_ID)).thenReturn(obs)
        characterDetailPresenter.init()
        verify(view).init()
        verify(view).hideLoading()
        verify(view, times(1)).showToast(ArgumentMatchers.anyString())
        verify(view, never()).showToast(R.string.message_no_items_to_show)
    }
}
