package com.puzzlebench.clean_marvel_kotlin.data.provider

import android.app.LoaderManager
import android.content.CursorLoader
import android.content.Loader
import android.database.Cursor
import android.os.Bundle
import com.puzzlebench.clean_marvel_kotlin.data.provider.CharacterProvider.Companion.CONTENT_URI
import com.puzzlebench.clean_marvel_kotlin.data.provider.CharacterProvider.Companion.DESCRIPTION_COLUM
import com.puzzlebench.clean_marvel_kotlin.data.provider.CharacterProvider.Companion.ID_COLUM
import com.puzzlebench.clean_marvel_kotlin.data.provider.CharacterProvider.Companion.NAME_COLUM
import com.puzzlebench.clean_marvel_kotlin.data.provider.CharacterProvider.Companion.THUMBNAIL_EXTENSION_COLUM
import com.puzzlebench.clean_marvel_kotlin.data.provider.CharacterProvider.Companion.THUMBNAIL_PATH_COLUM
import com.puzzlebench.clean_marvel_kotlin.domain.model.Character
import com.puzzlebench.clean_marvel_kotlin.domain.model.Thumbnail
import com.puzzlebench.clean_marvel_kotlin.presentation.MainActivity
import com.puzzlebench.clean_marvel_kotlin.presentation.mvp.view.CharacterView

class ContentLoader(val context: MainActivity, val view: CharacterView) : LoaderManager.LoaderCallbacks<Cursor> {

    private val upateInterface: UpdateCharacterInterface = view

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        val mProjection = arrayOf<String>(ID_COLUM, NAME_COLUM, DESCRIPTION_COLUM)
        return CursorLoader(context, CONTENT_URI, null, null, null, null) as Loader<Cursor>
    }

    override fun onLoadFinished(loader: Loader<Cursor>?, data: Cursor?) {
        val characters: MutableList<Character> = mutableListOf()

        data?.let {
            it.moveToFirst()
            while (!it.isAfterLast)
            {
                characters.add(
                        Character(
                                it.getInt(it.getColumnIndex(ID_COLUM)),
                                it.getString(it.getColumnIndex(NAME_COLUM)),
                                it.getString(it.getColumnIndex(DESCRIPTION_COLUM)),
                                Thumbnail(it.getInt(it.getColumnIndex(ID_COLUM)),
                                        it.getString(it.getColumnIndex(THUMBNAIL_PATH_COLUM)),
                                        it.getString(it.getColumnIndex(THUMBNAIL_EXTENSION_COLUM))),
                                null,
                                null,
                                null,
                                null
                        )
                )
                it.moveToNext()
            }
        }
        upateInterface.updateCharacters(characters)
    }

    override fun onLoaderReset(loader: Loader<Cursor>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    interface UpdateCharacterInterface{
        fun updateCharacters(characters: List<Character>)
    }
}