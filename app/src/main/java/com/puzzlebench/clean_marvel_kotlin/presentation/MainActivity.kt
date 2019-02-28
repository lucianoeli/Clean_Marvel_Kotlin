package com.puzzlebench.clean_marvel_kotlin.presentation

import android.content.Context
import android.os.Bundle
import com.puzzlebench.clean_marvel_kotlin.R
import com.puzzlebench.clean_marvel_kotlin.data.service.CharacterServicesImpl
import com.puzzlebench.clean_marvel_kotlin.domain.usecase.GetCharacterServiceUseCase
import com.puzzlebench.clean_marvel_kotlin.presentation.base.BaseRxActivity
import com.puzzlebench.clean_marvel_kotlin.presentation.mvp.CharacterPresenter
import com.puzzlebench.clean_marvel_kotlin.presentation.mvp.CharacterView
import io.realm.Realm

open class MainActivity : BaseRxActivity() {

    val getCharacterServiceUseCase = GetCharacterServiceUseCase(CharacterServicesImpl())
    val presenter = CharacterPresenter(CharacterView(this), getCharacterServiceUseCase, subscriptions)
    private lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Realm.init(applicationContext)
        realm = Realm.getDefaultInstance()
        presenter.init()
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
}
