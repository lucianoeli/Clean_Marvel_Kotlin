package com.puzzlebench.clean_marvel_kotlin.presentation.mvp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.puzzlebench.clean_marvel_kotlin.R
import com.puzzlebench.clean_marvel_kotlin.data.service.CharacterServicesImpl
import com.puzzlebench.clean_marvel_kotlin.domain.usecase.GetCharacterAditionalInfoServiceUseCase
import com.puzzlebench.clean_marvel_kotlin.presentation.base.BaseRxDialogFragment
import com.puzzlebench.clean_marvel_kotlin.presentation.mvp.presenter.CharacterDetailPresenter
import com.puzzlebench.clean_marvel_kotlin.presentation.mvp.view.CharacterDetailView

private const val ARG_CHARACTER_ID = "characterId"

class CharacterDetailFragment : BaseRxDialogFragment() {

    companion object {
        fun newInstance(characterId: Int) =
                CharacterDetailFragment().apply {
                    arguments = Bundle().apply {
                        putInt(ARG_CHARACTER_ID, characterId)
                    }
                }
    }

    override fun onStart() {
        super.onStart()

        dialog.let {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog.window.setLayout(width, height)
        }

        val getCharacterAditionalInfoServiceUseCase = GetCharacterAditionalInfoServiceUseCase(CharacterServicesImpl())
        val presenter = CharacterDetailPresenter(
                CharacterDetailView(this),
                getCharacterAditionalInfoServiceUseCase,
                subscriptions,
                arguments.getInt(ARG_CHARACTER_ID)
        )
        presenter.init()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.character_detail, container, false)
    }
}
