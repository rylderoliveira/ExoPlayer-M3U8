package com.rylderoliveira.customplayer

import androidx.media3.common.MediaItem
import androidx.media3.common.Player

class PlayerListener( private val listener: CustomPlayerDash) : Player.Listener {

    override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
        super.onMediaItemTransition(mediaItem, reason)
        listener.clearTracks()
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
