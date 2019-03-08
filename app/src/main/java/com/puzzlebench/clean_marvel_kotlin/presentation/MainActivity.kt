package com.puzzlebench.clean_marvel_kotlin.presentation

import android.os.Bundle
import com.puzzlebench.clean_marvel_kotlin.R
import com.puzzlebench.clean_marvel_kotlin.data.local.GetCharactersLocalImpl
import com.puzzlebench.clean_marvel_kotlin.data.local.SaveCharactersLocalImpl
import com.puzzlebench.clean_marvel_kotlin.data.service.CharacterServicesImpl
import com.puzzlebench.clean_marvel_kotlin.domain.usecase.GetCharacterLocalUseCase
import com.puzzlebench.clean_marvel_kotlin.domain.usecase.GetCharacterServiceUseCase
import com.puzzlebench.clean_marvel_kotlin.domain.usecase.SaveLocalCharactersUseCase
import com.puzzlebench.clean_marvel_kotlin.presentation.base.BaseRxActivity
import com.puzzlebench.clean_marvel_kotlin.presentation.mvp.presenter.CharacterPresenter
import io.realm.Realm
import com.puzzlebench.clean_marvel_kotlin.presentation.mvp.view.CharacterView

open class MainActivity : BaseRxActivity() {

    val getCharacterServiceUseCase = GetCharacterServiceUseCase(CharacterServicesImpl())
    val saveLocalCharactersUseCase = SaveLocalCharactersUseCase(SaveCharactersLocalImpl())
    val getCharacterLocalUseCase = GetCharacterLocalUseCase(GetCharactersLocalImpl())
    val presenter = CharacterPresenter(
            CharacterView(this),
            getCharacterServiceUseCase,
            saveLocalCharactersUseCase,
            getCharacterLocalUseCase,
            subscriptions
    )
    lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter.init()
    }

}
