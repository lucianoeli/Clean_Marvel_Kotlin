package com.puzzlebench.clean_marvel_kotlin.presentation.mvp

import android.support.design.widget.FloatingActionButton
import android.widget.Button
import com.puzzlebench.clean_marvel_kotlin.data.local.SaveCharactersLocalImpl
import com.puzzlebench.clean_marvel_kotlin.data.service.CharacterServicesImpl
import com.puzzlebench.clean_marvel_kotlin.domain.model.Character
import com.puzzlebench.clean_marvel_kotlin.domain.usecase.GetCharacterServiceUseCase
import com.puzzlebench.clean_marvel_kotlin.domain.usecase.SaveLocalCharactersUseCase
import com.puzzlebench.clean_marvel_kotlin.mocks.factory.CharactersFactory
import com.puzzlebench.clean_marvel_kotlin.presentation.mvp.presenter.CharacterPresenter
import com.puzzlebench.clean_marvel_kotlin.presentation.mvp.view.CharacterView
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.refreshFloatingButton
import org.junit.Assert
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

//TODO fix on second iteration
// error: However, there was exactly 1 interaction with this mock:
class CharacterPresenterTest {
    private var view = mock(CharacterView::class.java)
    private var characterServiceImp = mock(CharacterServicesImpl::class.java)
    private var saveCharactersLocalImpl = mock(SaveCharactersLocalImpl::class.java)
    private lateinit var getCharacterServiceUseCase: GetCharacterServiceUseCase
    private lateinit var saveLocalCharactersUseCase: SaveLocalCharactersUseCase
    private lateinit var characterPresenter: CharacterPresenter

    private lateinit var mButton: FloatingActionButton

    @Before
    fun setUp() {

        RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler -> Schedulers.trampoline() }
        getCharacterServiceUseCase = GetCharacterServiceUseCase(characterServiceImp)
        saveLocalCharactersUseCase = SaveLocalCharactersUseCase(saveCharactersLocalImpl)
        val subscriptions = mock(CompositeDisposable::class.java)
        characterPresenter = CharacterPresenter(view, getCharacterServiceUseCase, saveLocalCharactersUseCase, subscriptions)

        view.init()
        mButton = view.activityRef.get()?.refreshFloatingButton



    }

    @Ignore
    fun reposeWithError() {
        val itemsCharecters = CharactersFactory.getMockCharacter()
        val observable = Observable.just(itemsCharecters)
        Mockito.`when`(getCharacterServiceUseCase.invoke()).thenReturn(observable)
        Mockito.`when`(getCharacterServiceUseCase.invoke()).thenReturn(Observable.error(Exception("")))

        verify(view).init()
        verify(characterServiceImp).getCharacters()
        verify(view).hideLoading()
        verify(view).showToastNetworkError("")

    }

    @Test
    fun reposeWithItemToShow() {
        val itemsCharecters = CharactersFactory.getMockCharacter()
        val observable = Observable.just(itemsCharecters)
        Mockito.`when`(getCharacterServiceUseCase.invoke()).thenReturn(observable)
        characterPresenter.init()
        view.activityRef.get()?.runOnUiThread(Runnable { mButton.requestFocus() })
        mButton.performClick()
        verify(view).init()
        verify(characterServiceImp).getCharacters()
        verify(view).showCharacters(itemsCharecters)
    }

    @Test
    fun reposeWithoutItemToShow() {
        val itemsCharecters = emptyList<Character>()
        val observable = Observable.just(itemsCharecters)
        Mockito.`when`(getCharacterServiceUseCase.invoke()).thenReturn(observable)
        characterPresenter.init()
        verify(view).init()
        verify(characterServiceImp).getCharacters()
    }

}