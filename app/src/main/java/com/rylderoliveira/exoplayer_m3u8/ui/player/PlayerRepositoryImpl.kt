package com.rylderoliveira.exoplayer_m3u8.ui.player

import com.rylderoliveira.exoplayer_m3u8.data.models.Title
import com.rylderoliveira.exoplayer_m3u8.data.repositories.title.TitleRepository

class PlayerRepositoryImpl(
    private val titleRepository: TitleRepository
) : PlayerRepository {
    override suspend fun getTitle(): Title? = titleRepository.getTitle()
}
