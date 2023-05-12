package com.rylderoliveira.exoplayer_m3u8.ui.player

import com.rylderoliveira.exoplayer_m3u8.data.models.Title

interface PlayerRepository {
    suspend fun getTitle() : Title?
}
