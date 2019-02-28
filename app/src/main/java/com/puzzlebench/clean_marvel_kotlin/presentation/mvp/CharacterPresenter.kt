package com.puzzlebench.clean_marvel_kotlin.presentation.mvp

import android.view.View
import com.puzzlebench.clean_marvel_kotlin.domain.usecase.GetCharacterServiceUseCase
import com.puzzlebench.clean_marvel_kotlin.presentation.base.Presenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CharacterPresenter(
        view: CharacterView,
        private val getChatacterServiceUseCase: GetCharacterServiceUseCase,
        val subscriptions: CompositeDisposable
) : Presenter<CharacterView>(view) {

    fun init() {
        view.init()
        view.getCharacters(View.OnClickListener { requestGetCharacters() })
    }

    private fun requestGetCharacters() {
        view.showLoading()
        val subscription = getChatacterServiceUseCase.invoke().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({ characters ->
            if (characters.isEmpty()) {
                view.showToastNoItemToShow()
            } else {
                view.showCharacters(characters)
            }
            view.hideLoading()

        }, { e ->
            view.hideLoading()
            view.showToastNetworkError(e.message.toString())
        })
        subscriptions.add(subscription)
    }
}
