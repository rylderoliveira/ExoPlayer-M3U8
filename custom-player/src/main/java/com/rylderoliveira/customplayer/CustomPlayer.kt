package com.rylderoliveira.customplayer

import android.content.Context
import android.net.Uri
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.Tracks
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import androidx.media3.exoplayer.trackselection.DefaultTrackSelector
import okhttp3.OkHttp

@UnstableApi
class CustomPlayer(
    private val context: Context,
) {

    private lateinit var trackExtractor: CustomExtractor
    private lateinit var playerListener: Player.Listener
    lateinit var player: ExoPlayer

    private val autoVideoTrack = CustomTrack(
        index = -1,
        name = "auto".uppercase(),
        group = null,
        type = C.TRACK_TYPE_VIDEO,
    )
    private val subtitleOff = CustomTrack(
        index = -1,
        name = "desligado".uppercase(),
        group = null,
        type = C.TRACK_TYPE_TEXT,
    )
    val audioTracks: MutableList<CustomTrack> = mutableListOf()
    val videoTracks: MutableList<CustomTrack> = mutableListOf()
    val subtitleTracks: MutableList<CustomTrack> = mutableListOf()

    private fun initialize() {
        val userAgent = "okhttp/${OkHttp.VERSION}"
        val defaultDataSourceFactory = DefaultHttpDataSource.Factory()
            .setUserAgent(userAgent)
            .setConnectTimeoutMs(DefaultHttpDataSource.DEFAULT_CONNECT_TIMEOUT_MILLIS)
            .setReadTimeoutMs(DefaultHttpDataSource.DEFAULT_READ_TIMEOUT_MILLIS)
            .setAllowCrossProtocolRedirects(true)

        player = ExoPlayer.Builder(context)
            .setMediaSourceFactory(DefaultMediaSourceFactory(defaultDataSourceFactory))
            .build()

        trackExtractor = CustomExtractor(player.trackSelector as DefaultTrackSelector)
    }

    fun setMediaBy(url: String) {
        player.setMediaItem(MediaItem.fromUri(Uri.parse(url)))
        player.addListener(playerListener)
        player.prepare()
    }

    fun setCustomTracks() {
        val groups = player.currentTracks.groups
        if (subtitleTracks.isEmpty() && audioTracks.isEmpty() && videoTracks.isEmpty()) {
            for (group in groups) {
                when (group.type) {
                    C.TRACK_TYPE_TEXT -> subtitleTracks.populate(group)
                    C.TRACK_TYPE_VIDEO -> videoTracks.populate(group)
                    C.TRACK_TYPE_AUDIO -> audioTracks.populate(group)
                }
            }
            if (videoTracks.contains(autoVideoTrack).not()) {
                videoTracks.add(autoVideoTrack)
            }
            if (subtitleTracks.contains(subtitleOff).not()) {
                subtitleTracks.add(subtitleOff)
            }
        }
    }

    private fun MutableList<CustomTrack>.populate(group: Tracks.Group) {
        for (index in 0 until group.mediaTrackGroup.length) {
            val track = CustomTrack(
                index = index,
                name = trackExtractor.getTrackName(group.mediaTrackGroup.getFormat(index)),
                group = group.mediaTrackGroup,
                type = group.type
            )
            add(track)
        }
    }

    fun play() = player.play()
    fun pause() = player.pause()
    fun next() = player.seekToNextMediaItem()
    fun previous() = player.seekToPreviousMediaItem()
    fun release() = player.release()
    fun selectTrack(customTrack: CustomTrack) {
        if (customTrack.index == -1) {
            trackExtractor.clearOverrides(customTrack.type)
        } else {
            trackExtractor.selectCustomTrack(customTrack)
        }
    }

    fun restart() {
        player.seekTo(0L)
    }


    class Builder(private val context: Context) {

        private lateinit var _playerListener: Player.Listener
        private lateinit var _extractor: CustomExtractor

        fun setPlayerListener(playerListener: Player.Listener): Builder {
            _playerListener = playerListener
            return this
        }

        fun setExtractor(extractor: CustomExtractor): Builder {
            _extractor = extractor
            return this
        }

        fun build(): CustomPlayer {
            return CustomPlayer(context = context).apply {
                playerListener = _playerListener
                initialize()
            }
        }
    }
}
