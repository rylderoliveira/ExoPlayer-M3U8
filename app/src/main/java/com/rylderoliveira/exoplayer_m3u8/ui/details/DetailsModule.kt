package com.rylderoliveira.exoplayer_m3u8.ui.details

import com.rylderoliveira.exoplayer_m3u8.data.services.TitleService
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DetailsModule {
    val module = module {
        factory { OkHttpClient.Builder().build() }
        factory<Retrofit> {
            Retrofit.Builder()
                .baseUrl("https://1086c8ac-c764-4ab5-a63a-c04a199c5207.mock.pstmn.io")
                .addConverterFactory(GsonConverterFactory.create())
                .client(get())
                .build()
        }
        factory<TitleService> { get<Retrofit>().create(TitleService::class.java) }
        factory<DetailsRepository> { DetailsRepositoryImpl(service = get()) }
        viewModel { DetailsViewModel(repository = get()) }
    }
}
