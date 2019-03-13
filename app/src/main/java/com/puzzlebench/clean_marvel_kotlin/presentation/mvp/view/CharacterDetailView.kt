package com.puzzlebench.clean_marvel_kotlin.presentation.mvp.view

import android.support.annotation.StringRes
import android.view.View
import com.puzzlebench.clean_marvel_kotlin.R
import com.puzzlebench.clean_marvel_kotlin.domain.model.Character
import com.puzzlebench.clean_marvel_kotlin.presentation.extension.showToast
import com.puzzlebench.clean_marvel_kotlin.presentation.mvp.fragment.CharacterDetailFragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.character_detail.description
import kotlinx.android.synthetic.main.character_detail.imageView
import kotlinx.android.synthetic.main.character_detail.name
import kotlinx.android.synthetic.main.character_detail.progressBarDetailCard
import java.lang.ref.WeakReference

class CharacterDetailView(fragment: CharacterDetailFragment) {
    private val fragmentRef = WeakReference(fragment)

    fun init() {
        val fragment = fragmentRef.get()
        fragment?: showLoading()
    }

    fun showToast(@StringRes stringId: Int) {
        val fragment = fragmentRef.get()
        fragment?.let{
            val message = fragment.activity.resources.getString(stringId)
            fragment.activity.applicationContext.showToast(message)
        }
    }

    fun showToast(msg: String) {
        fragmentRef.get()?.activity?.applicationContext?.showToast(msg)
    }

    fun showCharacterDetail(character: Character) {
        val fragment = fragmentRef.get()
        fragment.let{
            fragment?.description?.text = character.description
            fragment?.name?.text = character.name
            val imageUrl = "${character.thumbnail?.path}.${character.thumbnail?.extension}"
            Picasso
                    .with(fragment?.context)
                    .load(imageUrl)
                    .into(fragment?.imageView)
        }
    }

    fun showLoading() {
        fragmentRef.get()?.progressBarDetailCard?.visibility = View.VISIBLE
    }

    fun hideLoading() {
        fragmentRef.get()?.progressBarDetailCard?.visibility = View.GONE
    }
}
