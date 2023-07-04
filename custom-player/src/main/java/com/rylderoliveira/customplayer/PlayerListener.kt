package com.rylderoliveira.customplayer

import android.util.Log
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.PlaybackParameters
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.Player.STATE_READY
import com.google.android.exoplayer2.Tracks

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

    override fun onEvents(player: Player, events: Player.Events) {
        super.onEvents(player, events)
    }
}
