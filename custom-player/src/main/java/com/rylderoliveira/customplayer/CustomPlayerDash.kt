package com.rylderoliveira.customplayer

interface CustomPlayerDash {
    fun initialize()
    fun release()
    fun showController()
    fun play()
    fun pause()
    fun next()
    fun previous()
    fun restart()
    fun updateButtons()
    fun updateTracks()
    fun clearTracks()
    fun setMediaBy(url: String)
    fun setMediaBy(urlList: List<String>)
    fun onRepeatModeChanged(repeatMode: Int)
    fun showLoading()
    fun hideLoading()
    fun shouldShowNextEpisode()
    fun clearRunnable()
    fun showButtonPause()
    fun showButtonPlay()
}