package com.puzzlebench.clean_marvel_kotlin.data.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.MatrixCursor
import android.net.Uri
import android.util.Log
import com.puzzlebench.clean_marvel_kotlin.data.local.GetCharactersLocalImpl
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.exceptions.RealmException

class CharacterProvider : ContentProvider() {

    companion object {
        private const val AUTHORITY = "com.puzzlebench.clean_marvel_kotlin.data.provider.CharacterProvider"
        private const val CHARACTERS_TABLE = "CharacterRealm"

        const val CHARACTERS = 1
        val ID_COLUM = "id"

        val NAME_COLUM = "name"
        val DESCRIPTION_COLUM = "description"
        val THUMBNAIL_PATH_COLUM = "path"
        val THUMBNAIL_EXTENSION_COLUM = "extension"
        val CONTENT_URI: Uri = Uri.parse("content://$AUTHORITY/$CHARACTERS_TABLE")

    }

    val sURIMatcher = UriMatcher(UriMatcher.NO_MATCH)

    init {
        sURIMatcher.addURI(AUTHORITY, CHARACTERS_TABLE, CHARACTERS)
    }

    override fun onCreate(): Boolean {
        try {
            Realm.init(context)
            val config = RealmConfiguration.Builder()
                    .name("realm.character")
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
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun update(uri: Uri?, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun delete(uri: Uri?, selection: String?, selectionArgs: Array<out String>?): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getType(uri: Uri?): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}