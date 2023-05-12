package com.rylderoliveira.exoplayer_m3u8.data.repositories.title

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.google.gson.Gson
import com.rylderoliveira.exoplayer_m3u8.data.models.Title
import kotlinx.coroutines.flow.first

class TitleRepositoryImpl(
    private val dataStore: DataStore<Preferences>,
) : TitleRepository {

    private val gson = Gson()

    override suspend fun getTitle(): Title? {
        val stringTitle = dataStore.data.first()[DATA_STORE_TITLE_KEY]
        return gson.fromJson(stringTitle, Title::class.java)
    }

    override suspend fun saveTitle(title: Title?) {
        val jsonTitle = gson.toJson(title)
        dataStore.edit {
            it[DATA_STORE_TITLE_KEY] = jsonTitle
        }
    }

    companion object {
        private val DATA_STORE_TITLE_KEY = stringPreferencesKey("titleKey")
    }
}