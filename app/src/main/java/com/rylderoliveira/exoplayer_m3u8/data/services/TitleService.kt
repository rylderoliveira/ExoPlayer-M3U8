package com.rylderoliveira.exoplayer_m3u8.data.services

import com.rylderoliveira.exoplayer_m3u8.data.models.Title
import retrofit2.Response
import retrofit2.http.GET

interface TitleService {
    @GET("1")
    suspend fun getMovie(): Response<Title?>
}
