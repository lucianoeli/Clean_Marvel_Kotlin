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
import io.reactivex.Scheduler
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Before
import org.junit.BeforeClass
import org.junit.ClassRule
import org.junit.Ignore
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.any
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.mock
import org.mockito.Mockito.never
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class CharacterDetailPresenterTest {

    companion object {
        const val CHARACTER_ID = 1
        const val ONE = 1
/*
        @ClassRule
        @JvmField
        val schedulers = RxImmediateSchedulerRule()
        */
    }

    private lateinit var characterDetailPresenter: CharacterDetailPresenter
    @Mock
    private lateinit var model: CharacterDetailModel
    @Mock
    private lateinit var view: CharacterDetailView

    private lateinit var subscriptions: CompositeDisposable
    @Mock
    private lateinit var obs: Observable<Character>
    @Mock private lateinit var char: Character


    @Before
    fun setUp() {
        subscriptions = CompositeDisposable()
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler -> Schedulers.trampoline() }
        MockitoAnnotations.initMocks(this)
        characterDetailPresenter = CharacterDetailPresenter(view, model, subscriptions, CHARACTER_ID)
    }

    @Test
    fun requestCharacterWithInfoCharacterToShow() {
        doReturn(Observable.just(char)).`when`(model).getCharacterAdditionalInfoServiceUseCase(anyInt())
       // Mockito.`when`(model.getCharacterAdditionalInfoServiceUseCase(anyInt()))
        //        .thenReturn(Observable.just(char))
        characterDetailPresenter.init()
        //val order = Mockito.inOrder(view, model)
        verify(view).init()
        verify(model).getCharacterAdditionalInfoServiceUseCase(anyInt())
        verify(view).showCharacterDetail(char)
        verify(view, times(1)).hideLoading()
        verify(view, never()).showToast(R.string.message_no_items_to_show)
    }

    @Test
    fun requestCharacterWithError() {
        doReturn(Observable.error<Character>(Throwable())).`when`(model).getCharacterAdditionalInfoServiceUseCase(anyInt())
        characterDetailPresenter.init()
        verify(view).init()
        verify(view).hideLoading()
        verify(view, times(1)).showToast(ArgumentMatchers.anyString())
        verify(view, never()).showToast(R.string.message_no_items_to_show)
    }
}
