package com.puzzlebench.clean_marvel_kotlin.data.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.MatrixCursor
import android.net.Uri
import android.util.Log
import com.puzzlebench.clean_marvel_kotlin.data.local.CharacterRealm
import com.puzzlebench.clean_marvel_kotlin.data.local.EMPTY_STRING
import com.puzzlebench.clean_marvel_kotlin.data.local.GetCharactersLocalImpl
import com.puzzlebench.clean_marvel_kotlin.data.local.ThumbnailRealm
import com.puzzlebench.clean_marvel_kotlin.data.mapper.CharacterMapperLocal
import com.puzzlebench.clean_marvel_kotlin.presentation.mvp.presenter.ZERO
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmModel
import io.realm.exceptions.RealmException

class CharacterProvider : ContentProvider() {

    companion object {
        private const val AUTHORITY = "com.puzzlebench.clean_marvel_kotlin.data.provider.CharacterProvider"
        private const val CHARACTERS_TABLE = "CharacterRealm"
        private const val REALM_NAME = "realm.character"
        const val CHARACTERS = 1
        val ID_COLUM = "id"
        val NAME_COLUM = "name"
        val DESCRIPTION_COLUM = "description"
        val THUMBNAIL_PATH_COLUM = "path"
        val THUMBNAIL_EXTENSION_COLUM = "extension"
        val CONTENT_URI: Uri = Uri.parse("content://$AUTHORITY/$CHARACTERS_TABLE")
    }

    val sURIMatcher = UriMatcher(UriMatcher.NO_MATCH)
    private val mapper: CharacterMapperLocal = CharacterMapperLocal()

    init {
        sURIMatcher.addURI(AUTHORITY, CHARACTERS_TABLE, CHARACTERS)
    }

    override fun onCreate(): Boolean {
        try {
            Realm.init(context)
            val config = RealmConfiguration.Builder()
                    .name(REALM_NAME)
                    .deleteRealmIfMigrationNeeded()
                    .build()
            Realm.setDefaultConfiguration(config)
            return true
        } catch (realmConfigError: RealmException) {
            Log.e("REALM ERROR", realmConfigError.toString())
            return false
        }
    }

    override fun query(uri: Uri?, projection: Array<out String>?, selection: String?, selectionArgs: Array<out String>?, sortOrder: String?): Cursor {
        val cursor = MatrixCursor(
                arrayOf(ID_COLUM,
                        NAME_COLUM,
                        DESCRIPTION_COLUM,
                        THUMBNAIL_PATH_COLUM,
                        THUMBNAIL_EXTENSION_COLUM
                )
        )
        val characters = GetCharactersLocalImpl().getCharacters()
        for (char in characters) {
            cursor.addRow(
                    arrayOf(char.id,
                            char.name,
                            char.description,
                            char.thumbnail?.path,
                            char.thumbnail?.extension
                    ))
        }
        cursor.setNotificationUri(context.contentResolver, CONTENT_URI)
        return cursor
    }

    override fun insert(uri: Uri?, values: ContentValues?): Uri {

        val characterRealm = values?.let {
            CharacterRealm(
                    it.getAsInteger(ID_COLUM),
                    it.getAsString(NAME_COLUM),
                    it.getAsString(DESCRIPTION_COLUM),
                    ThumbnailRealm(it.getAsInteger(ID_COLUM),
                            it.getAsString(THUMBNAIL_PATH_COLUM),
                            it.getAsString(THUMBNAIL_EXTENSION_COLUM)
                    )
            )
        }

        val realm = Realm.getDefaultInstance()
        realm.executeTransaction {
            it.insertOrUpdate(characterRealm as RealmModel)
        }

        return Uri.parse("$CHARACTERS_TABLE/${characterRealm?.id}")

    }

    override fun update(uri: Uri?, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int {
        /*Do nothing*/
        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun delete(uri: Uri?, selection: String?, selectionArgs: Array<out String>?): Int {
        /*Do nothing*/
        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun getType(uri: Uri?): String {
        /*Do nothing*/
        throw UnsupportedOperationException("Not yet implemented")
    }

}