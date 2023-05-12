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
        val title = playerRepository.getTitle()
        _title.postValue(title)
    }
}
