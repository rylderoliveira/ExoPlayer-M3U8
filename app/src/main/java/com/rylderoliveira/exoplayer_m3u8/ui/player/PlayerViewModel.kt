package com.rylderoliveira.exoplayer_m3u8.ui.player

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rylderoliveira.exoplayer_m3u8.data.models.Title
import kotlinx.coroutines.launch

class PlayerViewModel(
    private val playerRepository: PlayerRepository,
) : ViewModel() {

    private val _title = MutableLiveData<Title?>()
    val title: LiveData<Title?> = _title

    init {
        viewModelScope.launch {
            loadTitle()
        }
    }

    private suspend fun loadTitle() {
        val title = Title(
            id = 0,
            title = "Rock Balboa",
            mediaUrl = "https://i-slave1.odeonvod.com/data1/videos/filme/dual/decada/2010/522938_rambo___ate_o_fim/h264/576/rambo___ate_o_fim.mp4",
            episodes = listOf()
        )
        _title.postValue(title)
    }
}
