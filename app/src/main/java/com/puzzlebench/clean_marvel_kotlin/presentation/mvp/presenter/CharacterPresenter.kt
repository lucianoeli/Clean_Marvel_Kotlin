package com.puzzlebench.clean_marvel_kotlin.presentation.mvp.presenter
import android.view.View
import com.puzzlebench.clean_marvel_kotlin.domain.usecase.GetCharacterLocalUseCase
import com.puzzlebench.clean_marvel_kotlin.domain.usecase.GetCharacterServiceUseCase
import com.puzzlebench.clean_marvel_kotlin.domain.usecase.SaveLocalCharactersUseCase
import com.puzzlebench.clean_marvel_kotlin.presentation.base.Presenter
import com.puzzlebench.clean_marvel_kotlin.presentation.extension.showToast
import com.puzzlebench.clean_marvel_kotlin.presentation.mvp.view.CharacterView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CharacterPresenter(
        view: CharacterView,
        private val getCharacterServiceUseCase: GetCharacterServiceUseCase,
        private val saveLocalCharactersUseCase: SaveLocalCharactersUseCase,
        private val getCharacterLocalUseCase: GetCharacterLocalUseCase,
        val subscriptions: CompositeDisposable
) : Presenter<CharacterView>(view) {

    fun init() {
        view.init()
        view.getCharacters(View.OnClickListener { requestGetCharacters() })
    }

    private fun requestGetCharacters() {
        view.showLoading()
        val subscription = getCharacterServiceUseCase
                .invoke().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ characters ->
                    if (characters.isEmpty()) {
                        view.showToastNoItemToShow()
                    } else {
                        view.showCharacters(characters)
                        saveLocalCharactersUseCase.invoke(characters)
                        view.activityRef.get()?.applicationContext?.showToast("Data has been locally saved")
                    }
                    view.hideLoading()
                }, { e ->
                    view.hideLoading()
                    view.showToastNetworkError(e.message.toString())
                })
        subscriptions.add(subscription)
    }
}
