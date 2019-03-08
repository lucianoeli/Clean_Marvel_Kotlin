package com.puzzlebench.clean_marvel_kotlin.presentation.mvp.view

import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.puzzlebench.clean_marvel_kotlin.R
import com.puzzlebench.clean_marvel_kotlin.data.provider.ContentLoader
import com.puzzlebench.clean_marvel_kotlin.domain.model.Character
import com.puzzlebench.clean_marvel_kotlin.presentation.MainActivity
import com.puzzlebench.clean_marvel_kotlin.presentation.adapter.CharacterAdapter
import com.puzzlebench.clean_marvel_kotlin.presentation.extension.showToast
import com.puzzlebench.clean_marvel_kotlin.presentation.mvp.fragment.CharacterDetailFragment
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.ref.WeakReference

class CharacterView(activity: MainActivity) : ContentLoader.UpdateCharacterInterface {

    val activityRef = WeakReference(activity)
    private val SPAN_COUNT = 1
    var adapter = CharacterAdapter { character ->
        val fragment = character.id.let { CharacterDetailFragment.newInstance(it) }
        fragment.show(activity.supportFragmentManager, "CharacterDetailDialog")
    }

    fun init() {
        val activity = activityRef.get()
        activity?.let {
            it.recycleView.layoutManager = GridLayoutManager(it, SPAN_COUNT)
            it.loaderManager.initLoader(1,null, ContentLoader(it,this))
            it.recycleView.adapter = adapter
        }
    }

    fun showToastNoItemToShow() {
        val activity = activityRef.get()
        if (activity != null) {
            val message = activity.baseContext.resources.getString(R.string.message_no_items_to_show)
            activity.applicationContext.showToast(message)
        }
    }

    fun showToastNetworkError(error: String) {
        activityRef.get()?.applicationContext?.showToast(error)
    }

    fun hideLoading() {
        activityRef.get()?.progressBar?.visibility = View.GONE
    }

    fun showCharacters(characters: List<Character>) {
        adapter.data = characters
    }

    fun showLoading() {
        activityRef.get()?.progressBar?.visibility = View.VISIBLE
    }

    fun getCharacters(listener: View.OnClickListener){
        activityRef.get()?.refreshFloatingButton?.setOnClickListener(listener)
    }


    override fun updateCharacters(characters: List<Character>) {
       showCharacters(characters)
    }
}
