package com.rylderoliveira.exoplayer_m3u8.data.repositories.title

import com.rylderoliveira.exoplayer_m3u8.data.models.Title

interface TitleRepository {
    suspend fun getTitle(): Title?
    suspend fun saveTitle(title: Title?)
}
