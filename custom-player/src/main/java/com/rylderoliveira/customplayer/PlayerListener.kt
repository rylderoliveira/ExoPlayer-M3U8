package com.rylderoliveira.customplayer

import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.Tracks

class PlayerListener( private val listener: CustomPlayerDash) : Player.Listener {

    override fun onTracksChanged(tracks: Tracks) {
        super.onTracksChanged(tracks)
    }

    override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
        super.onMediaItemTransition(mediaItem, reason)
        listener.clearTracks()
    }


    override fun onPlaybackStateChanged(playbackState: Int) {
        super.onPlaybackStateChanged(playbackState)
    }

    override fun onPlayWhenReadyChanged(playWhenReady: Boolean, reason: Int) {
        super.onPlayWhenReadyChanged(playWhenReady, reason)
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
