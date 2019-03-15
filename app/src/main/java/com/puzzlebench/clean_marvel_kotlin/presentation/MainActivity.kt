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
import com.puzzlebench.clean_marvel_kotlin.presentation.mvp.model.CharacterModel
import com.puzzlebench.clean_marvel_kotlin.presentation.mvp.presenter.CharacterPresenter
import com.puzzlebench.clean_marvel_kotlin.presentation.mvp.view.CharacterView
import io.realm.Realm

open class MainActivity : BaseRxActivity() {

    lateinit var realm: Realm
    val presenter = CharacterPresenter(
            CharacterView(this),
            CharacterModel(GetCharacterServiceUseCase(CharacterServicesImpl()), SaveLocalCharactersUseCase(SaveCharactersLocalImpl()))
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter.init()
    }
}
