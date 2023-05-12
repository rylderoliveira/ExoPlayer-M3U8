package com.rylderoliveira.exoplayer_m3u8.ui.details

import com.rylderoliveira.exoplayer_m3u8.data.services.TitleService
import com.rylderoliveira.exoplayer_m3u8.data.models.Title
import com.rylderoliveira.exoplayer_m3u8.data.repositories.title.TitleRepository
import retrofit2.Response

class DetailsRepositoryImpl(
    private val service: TitleService,
    private val titleRepository: TitleRepository,
) : DetailsRepository {

    override suspend fun getMovie(): Response<Title?> = service.getMovie()

    override suspend fun saveMovie(title: Title?) {
        titleRepository.saveTitle(title)
    }
}
