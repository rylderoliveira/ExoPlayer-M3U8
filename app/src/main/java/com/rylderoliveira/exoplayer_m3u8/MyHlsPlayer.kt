package com.rylderoliveira.exoplayer_m3u8

interface MyHlsPlayer {
    fun play()
    fun pause()
    fun next()
    fun previous()
    fun seekTo(position: Long)
    fun setPreferredAudio(name: String)
    fun setPreferredSubtitle(name: String)
    fun setPreferredResolution(name: String)
    fun setVideoSpeed(speed: Float)
}
