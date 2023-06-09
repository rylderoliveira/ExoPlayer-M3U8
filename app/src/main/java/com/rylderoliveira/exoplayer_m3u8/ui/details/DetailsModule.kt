package com.rylderoliveira.exoplayer_m3u8.ui.details

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.rylderoliveira.exoplayer_m3u8.data.services.TitleService
import com.rylderoliveira.exoplayer_m3u8.data.repositories.title.TitleRepository
import com.rylderoliveira.exoplayer_m3u8.data.repositories.title.TitleRepositoryImpl
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DetailsModule {
    val module = module {
//        val dataStore: DataStore<Preferences> = preferencesDataStore("preferences").getValue(and)
        single<DataStore<Preferences>> { preferencesDataStore("preferences").getValue(androidContext(), DataStore<Preferences>::javaClass) }
        factory { OkHttpClient.Builder().build() }
        factory<Retrofit> {
            Retrofit.Builder()
                .baseUrl("https://1086c8ac-c764-4ab5-a63a-c04a199c5207.mock.pstmn.io")
                .addConverterFactory(GsonConverterFactory.create())
                .client(get())
                .build()
        }
        factory<TitleService> { get<Retrofit>().create(TitleService::class.java) }
        factory<TitleRepository> { TitleRepositoryImpl(dataStore = get()) }
        factory<DetailsRepository> { DetailsRepositoryImpl(service = get(), titleRepository = get()) }
        viewModel { DetailsViewModel(repository = get()) }
    }
}
