package com.puzzlebench.clean_marvel_kotlin.presentation.mvp


import com.puzzlebench.clean_marvel_kotlin.R
import com.puzzlebench.clean_marvel_kotlin.data.service.CharacterServicesImpl
import com.puzzlebench.clean_marvel_kotlin.domain.usecase.GetCharacterAditionalInfoServiceUseCase
import com.puzzlebench.clean_marvel_kotlin.mocks.factory.CharactersFactory
import com.puzzlebench.clean_marvel_kotlin.presentation.mvp.presenter.CharacterDetailPresenter
import com.puzzlebench.clean_marvel_kotlin.presentation.mvp.view.CharacterDetailView
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.mockito.Mockito.anyString
import org.mockito.Mockito.mock
import org.mockito.Mockito.never
import org.mockito.Mockito.verify

class CharacterDetailPresenterTest {

    companion object {
        const val CHARACTER_ID = 12211
        const val ONE = 1
    }

    private var view = mock(CharacterDetailView::class.java)
    private val subscriptions = mock(CompositeDisposable::class.java)
    private var characterServiceImplementation = mock(CharacterServicesImpl::class.java)
    private lateinit var getCharacterAditionalInfoServiceUseCase: GetCharacterAditionalInfoServiceUseCase
    private lateinit var characterDetailPresenter: CharacterDetailPresenter
    @Before
    fun setUp(){
        getCharacterAditionalInfoServiceUseCase = GetCharacterAditionalInfoServiceUseCase(characterServiceImplementation)
        characterDetailPresenter = CharacterDetailPresenter(view,getCharacterAditionalInfoServiceUseCase,subscriptions, CHARACTER_ID)
    }

    @Test
    fun requestCharacterWithInfoCharacterToShow(){
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler -> Schedulers.trampoline() }
        val character = CharactersFactory.getMockCharacter(ONE).first()
        val observable = Observable.just(character)
        Mockito.`when`(getCharacterAditionalInfoServiceUseCase.invoke(CHARACTER_ID)).thenReturn(observable)
        characterDetailPresenter.init()
        val order = Mockito.inOrder(view,characterServiceImplementation)
        order.verify(view).init()
        order.verify(characterServiceImplementation).getCharacterById(CHARACTER_ID)
        order.verify(view).showCharacterDetail(character)
        order.verify(view).hideLoading()
        verify(view, never()).showToast(R.string.message_no_items_to_show)
    }

    @Test
    fun requestCharacterWithNoInfoToShow(){

    }
}
