package com.puzzlebench.clean_marvel_kotlin.presentation.mvp

import com.nhaarman.mockitokotlin2.verify
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
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Before
import org.junit.BeforeClass
import org.junit.ClassRule
import org.junit.Ignore
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.any
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.mock
import org.mockito.Mockito.never
import org.mockito.Mockito.times

import org.mockito.MockitoAnnotations
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit

class CharacterDetailPresenterTest {

    companion object {
        const val CHARACTER_ID = 1
        const val ONE = 1

        @BeforeClass @JvmStatic
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
        MockitoAnnotations.initMocks(this)
        characterDetailPresenter = CharacterDetailPresenter(view, model, subscriptions, CHARACTER_ID)
    }

    @Test
    fun requestCharacterWithError() {
        doReturn(Observable.error<Character>(Throwable())).`when`(model).getCharacterAdditionalInfoServiceUseCase(anyInt())
        characterDetailPresenter.init()
        verify(view).init()
        verify(view).hideLoading()
        verify(view, times(1)).showToast(ArgumentMatchers.anyString())
        verify(view, never()).showToast(R.string.message_no_items_to_show)
        verify(view, never()).showCharacterDetail(com.nhaarman.mockitokotlin2.any())
    }


    @Test
    fun requestCharacterWithInfoCharacterToShow() {
        doReturn(Observable.just(char)).`when`(model).getCharacterAdditionalInfoServiceUseCase(anyInt())
        characterDetailPresenter.init()
        val order = Mockito.inOrder(view, model)
        order.verify(view).init()
        order.verify(model).getCharacterAdditionalInfoServiceUseCase(anyInt())
        order.verify(view).showCharacterDetail(char)
        order.verify(view, times(1)).hideLoading()
        verify(view, never()).showToast(R.string.message_no_items_to_show)
    }

}
