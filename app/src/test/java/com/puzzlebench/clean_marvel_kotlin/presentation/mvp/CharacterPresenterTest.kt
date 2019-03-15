package com.puzzlebench.clean_marvel_kotlin.presentation.mvp

import com.nhaarman.mockitokotlin2.verify
import com.puzzlebench.clean_marvel_kotlin.R
import com.puzzlebench.clean_marvel_kotlin.domain.model.Character
import com.puzzlebench.clean_marvel_kotlin.mocks.factory.CharactersFactory
import com.puzzlebench.clean_marvel_kotlin.presentation.mvp.model.CharacterModel
import com.puzzlebench.clean_marvel_kotlin.presentation.mvp.presenter.CharacterPresenter
import com.puzzlebench.clean_marvel_kotlin.presentation.mvp.view.CharacterView
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.inOrder
import org.mockito.Mockito.never
import org.mockito.MockitoAnnotations
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit

class CharacterPresenterTest {
    private lateinit var characterPresenter: CharacterPresenter
    @Mock private lateinit var view: CharacterView
    @Mock private lateinit var model: CharacterModel
    companion object {
        @BeforeClass
        @JvmStatic
        fun setUpClass() {
            val immediate = object : Scheduler() {
                internal var noDelay = 0

                override fun scheduleDirect(run: Runnable, delay: Long, unit: TimeUnit): Disposable {
                    return super.scheduleDirect(run, noDelay.toLong(), unit) // Prevents StackOverflowErrors when scheduling with a delay
                }

                override fun createWorker(): Scheduler.Worker {
                    return ExecutorScheduler.ExecutorWorker(Executor { it.run() })
                }
            }
            // These avoid ExceptionInInitializerError when testing methods that contains RxJava Schedulers
            RxJavaPlugins.setInitIoSchedulerHandler { scheduler -> immediate }
            RxJavaPlugins.setInitComputationSchedulerHandler { scheduler -> immediate }
            RxJavaPlugins.setInitNewThreadSchedulerHandler { scheduler -> immediate }
            RxJavaPlugins.setInitSingleSchedulerHandler { scheduler -> immediate }
            RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler -> immediate }
        }
        private const val SIZE = 10
    }

    @Before
    fun setUp() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler -> Schedulers.trampoline() }
        MockitoAnnotations.initMocks(this)
        characterPresenter = CharacterPresenter(view, model)
    }

    @Test
    fun reposeWithItemToShow() {
        val itemsCharecters = CharactersFactory.getMockCharacter(SIZE)
        val observable = Observable.just(itemsCharecters)
        doReturn(observable).`when`(model).getCharacterServiceUseCase()
        characterPresenter.init()
        characterPresenter.requestGetCharacters()
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
    fun reposeWithError() {
        doReturn(Observable.error<Character>(Throwable())).`when`(model).getCharacterServiceUseCase()
        characterPresenter.init()
        characterPresenter.requestGetCharacters()
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
    fun reposeWithoutItemToShow() {
        val itemsCharecters = emptyList<Character>()
        val observable = Observable.just(itemsCharecters)
        doReturn(observable).`when`(model).getCharacterServiceUseCase()
        characterPresenter.init()
        characterPresenter.requestGetCharacters()
        val order = inOrder(view, model)
        verify(view).init()
        verify(view).showLoading()
        verify(model).getCharacterServiceUseCase()
        verify(view).showToast(R.string.message_no_items_to_show)
        verify(view).hideLoading()
        verify(model, never()).saveLocalCharactersUseCase(itemsCharecters)
        verify(view, never()).showCharacters(itemsCharecters)
    }
}
