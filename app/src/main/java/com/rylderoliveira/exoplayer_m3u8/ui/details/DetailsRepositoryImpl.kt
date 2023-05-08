package com.rylderoliveira.exoplayer_m3u8.ui.details

import com.rylderoliveira.exoplayer_m3u8.data.services.TitleService
import com.rylderoliveira.exoplayer_m3u8.data.models.Title
import retrofit2.Response

class DetailsRepositoryImpl(
    private val service: TitleService
) : DetailsRepository {

    override suspend fun getMovie(): Response<Title?> = service.getMovie()
}
