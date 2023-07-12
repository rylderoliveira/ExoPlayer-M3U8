package com.rylderoliveira.customplayer

import android.util.Log
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.Timeline
import androidx.media3.common.text.CueGroup

class PlayerListener(private val listener: CustomPlayerDash) : Player.Listener {

    override fun onPlaybackStateChanged(playbackState: Int) {
        when (playbackState) {
            Player.STATE_BUFFERING -> listener.showLoading()
            Player.STATE_READY -> listener.hideLoading()
            Player.STATE_ENDED -> {}
            Player.STATE_IDLE -> {}
        }
        super.onPlaybackStateChanged(playbackState)
    }

    override fun onIsLoadingChanged(isLoading: Boolean) {
        if (isLoading) listener.shouldShowNextEpisode()
        super.onIsLoadingChanged(isLoading)
    }

    override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
        super.onMediaItemTransition(mediaItem, reason)
        listener.clearTracks()
        listener.clearRunnable()
    }

    override fun onRepeatModeChanged(repeatMode: Int) {
        listener.onRepeatModeChanged(repeatMode)
        super.onRepeatModeChanged(repeatMode)
    }

    override fun onRenderedFirstFrame() {
        super.onRenderedFirstFrame()
        listener.updateButtons()
        listener.updateTracks()
    }
}
