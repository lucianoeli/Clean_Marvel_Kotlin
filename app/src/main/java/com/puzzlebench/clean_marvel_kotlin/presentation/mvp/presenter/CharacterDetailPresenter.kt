package com.puzzlebench.clean_marvel_kotlin.presentation.mvp.presenter

import android.util.Log
import com.puzzlebench.clean_marvel_kotlin.domain.usecase.GetCharacterAditionalInfoServiceUseCase
import com.puzzlebench.clean_marvel_kotlin.presentation.base.Presenter
import com.puzzlebench.clean_marvel_kotlin.presentation.mvp.view.CharacterDetailView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CharacterDetailPresenter(
        view: CharacterDetailView,
        private val getCharacterAditionalInfoServiceUseCase: GetCharacterAditionalInfoServiceUseCase,
        val subscriptions: CompositeDisposable,
        val characterId: Int
) : Presenter<CharacterDetailView>(view) {

    fun init() {
        view.init()
       requestCharacterInfo(characterId)
    }

    private fun requestCharacterInfo(characterId: Int) {
        val subscription = getCharacterAditionalInfoServiceUseCase
                .invoke(characterId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ character ->
                    if (character == null) {
                        //view.showToastNoDetailToShow()
                        view.showToastNoDetailToShow()
                        Log.d("RCI","if")
                    } else {
                        view.showCharacterDetail(character[0])
                        Log.d("RCI",character.toString())
                    }
                }, { e ->
                    view.showToastNetworkError(e.message.toString())
                })
        subscriptions.add(subscription)
    }
}