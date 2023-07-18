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

    override fun onPlayWhenReadyChanged(playWhenReady: Boolean, reason: Int) {
        super.onPlayWhenReadyChanged(playWhenReady, reason)
        if (reason == Player.PLAY_WHEN_READY_CHANGE_REASON_END_OF_MEDIA_ITEM) {
            listener.next()
        }
    }

    override fun onIsPlayingChanged(isPlaying: Boolean) {
        super.onIsPlayingChanged(isPlaying)
        if (isPlaying) listener.showButtonPause() else listener.showButtonPlay()
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
