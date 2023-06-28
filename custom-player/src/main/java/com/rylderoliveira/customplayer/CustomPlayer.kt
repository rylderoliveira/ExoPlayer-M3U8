package com.rylderoliveira.customplayer

import android.content.Context
import android.net.Uri
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.Player.STATE_READY
import com.google.android.exoplayer2.Tracks
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.MappingTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelectionParameters
import com.google.android.exoplayer2.trackselection.TrackSelector
import java.util.Locale

class CustomPlayer(
    private val context: Context,
) {

    private lateinit var trackSelector: TrackSelector
    private lateinit var trackExtractor: CustomExtractor
    lateinit var player: ExoPlayer

    val audioTracks: MutableList<CustomTrack> = mutableListOf()
    val videoTracks: MutableList<CustomTrack> = mutableListOf()
    val subtitleTracks: MutableList<CustomTrack> = mutableListOf()

    private fun initialize() {
        player = ExoPlayer.Builder(context)
            .setTrackSelector(trackSelector)
            .build()
    }

    fun setMediaBy(url: String) {
        player.setMediaItem(MediaItem.fromUri(Uri.parse(url)))
        player.prepare()
        player.addListener(object : Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                super.onPlaybackStateChanged(playbackState)
                if (playbackState == STATE_READY) {
                    setCustomTracks()
                }
            }

            override fun onTracksChanged(tracks: Tracks) {
                super.onTracksChanged(tracks)
//                setCustomTracks()
            }
        })
    }

    private fun setCustomTracks() {
        val groups = player.currentTracks.groups
        for (group in groups) {
            for (i in 0 until group.length) {

            }
        }
    }

    fun play() = player.play()
    fun pause() = player.pause()
    fun next() = player.seekToNextMediaItem()
    fun previous() = player.seekToPreviousMediaItem()
    fun release() = player.release()
    fun selectTrack(customTrack: CustomTrack) = trackExtractor.selectCustomTrack(customTrack)


    class Builder(private val context: Context) {

        private var _trackSelector: MappingTrackSelector = DefaultTrackSelector(context)
        private var _trackExtractor: CustomExtractor = CustomExtractor(_trackSelector)

        fun setTrackSelector(trackSelector: MappingTrackSelector): Builder {
            _trackSelector = trackSelector
            return this
        }

        fun setTrackExtractor(trackExtractor: CustomExtractor): Builder {
            _trackExtractor = trackExtractor
            return this
        }

        fun build(): CustomPlayer {
            return CustomPlayer(context = context).apply {
                trackSelector = _trackSelector
                trackExtractor = _trackExtractor
                initialize()
            }
        }
    }

    companion object {
        const val RENDERER_VIDEO_INDEX = 0
        const val RENDERER_AUDIO_INDEX = 1
        const val RENDERER_TEXT_INDEX = 2
    }
}
