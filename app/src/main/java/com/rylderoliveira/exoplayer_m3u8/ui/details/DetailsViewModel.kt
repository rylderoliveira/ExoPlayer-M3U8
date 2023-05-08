package com.rylderoliveira.exoplayer_m3u8.ui.details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rylderoliveira.exoplayer_m3u8.data.models.Title
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val repository: DetailsRepository
) : ViewModel() {

    private val _title = MutableLiveData<Title?>()
    val title: LiveData<Title?> = _title

    fun loadMovie() {
        viewModelScope.launch {
            val response = repository.getMovie()
            try {
                // start loading
                if (response.isSuccessful) {
                    _title.postValue(response.body())
                } else {
                    // do nothing
                }
            } catch (e: Exception) {
                Log.e("DetailsViewModel", "Expetion: ${e.message}")
            } finally {
                // end loading
            }
        }
    }
}
