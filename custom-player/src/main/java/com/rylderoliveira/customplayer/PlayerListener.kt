package com.rylderoliveira.customplayer

import androidx.media3.common.Player
import androidx.media3.common.Player.STATE_READY
import androidx.media3.common.Tracks

class PlayerListener( private val listener: CustomPlayerDash) : Player.Listener {

    override fun onPlaybackStateChanged(playbackState: Int) {
        super.onPlaybackStateChanged(playbackState)
        if (playbackState == STATE_READY) {
            listener.play()
        }
    }

    override fun onTracksChanged(tracks: Tracks) {
        super.onTracksChanged(tracks)
        listener.updateTracks()
        listener.updateButtons()
    }
}
