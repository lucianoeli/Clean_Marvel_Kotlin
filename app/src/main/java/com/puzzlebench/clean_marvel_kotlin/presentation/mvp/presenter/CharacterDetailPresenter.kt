package com.puzzlebench.clean_marvel_kotlin.presentation.mvp.presenter

import android.util.Log
import com.puzzlebench.clean_marvel_kotlin.domain.usecase.GetCharacterAditionalInfoServiceUseCase
import com.puzzlebench.clean_marvel_kotlin.presentation.base.Presenter
import com.puzzlebench.clean_marvel_kotlin.presentation.mvp.view.CharacterDetailView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

const val ZERO = 0
class CharacterDetailPresenter(
        view: CharacterDetailView,
        private val getCharacterAdditionalInfoServiceUseCase: GetCharacterAditionalInfoServiceUseCase,
        val subscriptions: CompositeDisposable,
        val characterId: Int
) : Presenter<CharacterDetailView>(view) {

    fun init() {
        view.init()
       requestCharacterInfo(characterId)
    }

    private fun requestCharacterInfo(characterId: Int) {
        val subscription = getCharacterAdditionalInfoServiceUseCase
                .invoke(characterId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ character ->
                    if (character == null) {
                        view.showToastNoDetailToShow()
                    } else {
                        view.showCharacterDetail(character)
                    }
                    view.hideLoading()
                }, { e ->
                    view.showToastNetworkError(e.message.toString())
                })
        subscriptions.add(subscription)
    }
}