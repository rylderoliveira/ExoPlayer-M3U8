package com.rylderoliveira.exoplayer_m3u8.ui.player

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rylderoliveira.customplayer.CustomMedia
import com.rylderoliveira.exoplayer_m3u8.data.models.Title
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class PlayerViewModel(
    private val playerRepository: PlayerRepository,
) : ViewModel() {

    suspend fun loadMedia() {
        delay(1000L)

    }
}
