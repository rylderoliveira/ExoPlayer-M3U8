package com.rylderoliveira.exoplayer_m3u8.ui.details

import com.rylderoliveira.exoplayer_m3u8.data.models.Title
import retrofit2.Response

interface DetailsRepository {
    suspend fun getMovie(): Response<Title?>
    suspend fun saveMovie(title: Title?)
}
