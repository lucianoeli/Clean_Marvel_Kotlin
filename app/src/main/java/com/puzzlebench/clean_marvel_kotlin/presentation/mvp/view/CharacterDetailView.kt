package com.puzzlebench.clean_marvel_kotlin.presentation.mvp.view

import android.util.Log
import com.puzzlebench.clean_marvel_kotlin.R
import com.puzzlebench.clean_marvel_kotlin.domain.model.Character
import com.puzzlebench.clean_marvel_kotlin.domain.model.CharacterAditionalInfo
import com.puzzlebench.clean_marvel_kotlin.presentation.extension.showToast
import com.puzzlebench.clean_marvel_kotlin.presentation.mvp.fragment.CharacterDetailFragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.character_detail.*
import kotlinx.android.synthetic.main.character_detail.view.*
import java.lang.ref.WeakReference

class CharacterDetailView(fragment: CharacterDetailFragment) {
    private val fragmentRef = WeakReference(fragment)

    fun init() {
        val fragment = fragmentRef.get()
        if (fragment != null) {
            //TODO loadiing
        }
    }

    fun showToastNoDetailToShow() {
        val fragment = fragmentRef.get()
        if (fragment != null) {
            val message = fragment.activity.baseContext.resources.getString(R.string.no_description_available)
            fragment.activity.applicationContext.showToast(message)
        }
    }

    fun showToastNetworkError(error: String) {
        fragmentRef.get()?.activity?.applicationContext?.showToast(error)
    }

    fun showCharacterDetail(character: Character) {
        val fragment = fragmentRef.get()
        if (fragment != null) {
            fragment.description.text = character.description
            fragment.name.text = character.name

            val imageUrl = character.thumbnail.path + "." + character.thumbnail.extension
            Log.d("MY MSG", character.thumbnail.path + "." + character.thumbnail.extension)


            Picasso
                    .with(fragment.context)
                    .load(imageUrl)
                    .into(fragment.imageView)

        }

    }
}