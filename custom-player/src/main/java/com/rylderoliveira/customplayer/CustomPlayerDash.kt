package com.rylderoliveira.customplayer

import androidx.appcompat.app.AppCompatActivity
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player.RepeatMode
import com.google.android.exoplayer2.trackselection.MappingTrackSelector

interface CustomPlayerDash {
    fun initialize()
    fun release()
    fun showController()
    fun hideController()
    fun showLoading()
    fun hideLoading()
    fun showNextEpisodeProgress()
    fun hideNextEpisodeProgress()
    fun showExitDialog(activity: AppCompatActivity)
    fun keepWatchingDialog()
    fun saveCurrentPosition()
    fun setPreferredAudio()
    fun setPreferredVideo()
    fun setPreferredText()
    fun setSubtitleStyle()
    fun loadMediaParams()
    fun loadAvailableTracks()
    fun loadPlaylist()
    fun play()
    fun pause()
    fun next()
    fun previous()
    fun fastForward()
    fun rewind()
    fun restart()
    fun seekTo()
    fun setRepeatMode(repeatMode: RepeatMode)
    fun updateButtons()
    fun updateTracks()
}