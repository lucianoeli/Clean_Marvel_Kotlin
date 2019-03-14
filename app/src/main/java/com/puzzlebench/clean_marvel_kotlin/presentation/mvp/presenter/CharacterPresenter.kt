package com.puzzlebench.clean_marvel_kotlin.presentation.mvp.presenter
import android.view.View
import com.puzzlebench.clean_marvel_kotlin.R
import com.puzzlebench.clean_marvel_kotlin.domain.usecase.GetCharacterServiceUseCase
import com.puzzlebench.clean_marvel_kotlin.domain.usecase.SaveLocalCharactersUseCase
import com.puzzlebench.clean_marvel_kotlin.presentation.base.Presenter
import com.puzzlebench.clean_marvel_kotlin.presentation.mvp.view.CharacterView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CharacterPresenter(
        view: CharacterView,
        private val getCharacterServiceUseCase: GetCharacterServiceUseCase,
        private val saveLocalCharactersUseCase: SaveLocalCharactersUseCase,
        val subscriptions: CompositeDisposable
) : Presenter<CharacterView>(view) {

    fun init() {
        view.init()
        view.getCharacters(View.OnClickListener { requestGetCharacters() })
    }

    fun requestGetCharacters() {
        view.showLoading()
        val subscription = getCharacterServiceUseCase
                .invoke().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ characters ->
                    if (characters.isEmpty()) {
                        view.showToast(R.string.message_no_items_to_show)
                    } else {
                        view.showCharacters(characters)
                        saveLocalCharactersUseCase.invoke(characters)
                        view.showToast(R.string.toast_msg_data_loaded)
                    }
                    view.hideLoading()
                }, { e ->
                    view.hideLoading()
                    view.showToast(e.message.toString())
                })
        subscriptions.add(subscription)
    }
}
