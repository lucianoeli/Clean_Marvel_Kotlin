package com.puzzlebench.clean_marvel_kotlin.presentation.mvp.view

import android.support.annotation.StringRes
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.puzzlebench.clean_marvel_kotlin.R
import com.puzzlebench.clean_marvel_kotlin.data.provider.CharacterLoader
import com.puzzlebench.clean_marvel_kotlin.data.provider.CharacterProvider
import com.puzzlebench.clean_marvel_kotlin.domain.model.Character
import com.puzzlebench.clean_marvel_kotlin.presentation.MainActivity
import com.puzzlebench.clean_marvel_kotlin.presentation.adapter.CharacterAdapter
import com.puzzlebench.clean_marvel_kotlin.presentation.extension.showToast
import com.puzzlebench.clean_marvel_kotlin.presentation.mvp.fragment.CharacterDetailFragment
import kotlinx.android.synthetic.main.activity_main.progressBar
import kotlinx.android.synthetic.main.activity_main.recycleView
import kotlinx.android.synthetic.main.activity_main.refreshFloatingButton
import java.lang.ref.WeakReference

open class CharacterView(activity: MainActivity) : CharacterLoader.UpdateCharacterInterface {

    val activityRef = WeakReference(activity)
    private val SPAN_COUNT = 1
    var adapter = CharacterAdapter { character ->
        val fragment = character.id.let { CharacterDetailFragment.newInstance(it) }
        fragment.show(activity.supportFragmentManager, activity.getString(R.string.title_character_detail_fragment))
    }

    open fun init() {
        val activity = activityRef.get()
        activity?.let {
            it.recycleView.layoutManager = GridLayoutManager(it, SPAN_COUNT)
            showLoading()
            it.loaderManager.initLoader(CharacterProvider.CHARACTERS, null, CharacterLoader(it, this))
            it.recycleView.adapter = adapter
        }
    }

    open fun showToast(msg: String) {
        activityRef.get()?.applicationContext?.showToast(msg)
    }

    open fun showToast(@StringRes stringId: Int) {
        val activity = activityRef.get()
        if (activity != null) {
            val message = activity.baseContext.resources.getString(stringId)
            activity.applicationContext.showToast(message)
        }
    }

    open fun hideLoading() {
        activityRef.get()?.progressBar?.visibility = View.GONE
    }

    open fun showCharacters(characters: List<Character>) {
        adapter.data = characters
    }

    open fun showLoading() {
        activityRef.get()?.progressBar?.visibility = View.VISIBLE
    }

    open fun getCharacters(listener: View.OnClickListener) {
        activityRef.get()?.refreshFloatingButton?.setOnClickListener(listener)
    }

    override fun updateCharacters(characters: List<Character>) {
        val message = activityRef.get()?.baseContext?.resources?.getString(R.string.toast_msg_data_loaded_from_local_repo)
        showCharacters(characters)
        message?.let { activityRef.get()?.applicationContext?.showToast(it) }
        hideLoading()
    }
}
