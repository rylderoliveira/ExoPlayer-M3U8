package com.rylderoliveira.customplayer

import androidx.appcompat.app.AppCompatActivity
import androidx.media3.common.Player

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
    fun setRepeatMode(repeatMode: Player.RepeatMode)
    fun updateButtons()
    fun updateTracks()
}