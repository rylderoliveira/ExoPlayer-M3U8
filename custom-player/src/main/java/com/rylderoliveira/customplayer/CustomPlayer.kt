package com.rylderoliveira.customplayer

import android.content.Context
import android.net.Uri
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.Player.REPEAT_MODE_ALL
import androidx.media3.common.Player.REPEAT_MODE_OFF
import androidx.media3.common.Player.REPEAT_MODE_ONE
import androidx.media3.common.Tracks
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.LoadControl
import androidx.media3.exoplayer.SeekParameters
import androidx.media3.exoplayer.analytics.AnalyticsListener
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import androidx.media3.exoplayer.trackselection.DefaultTrackSelector
import androidx.media3.exoplayer.trackselection.MappingTrackSelector
import androidx.media3.exoplayer.util.EventLogger
import okhttp3.OkHttp

@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
class CustomPlayer(
    private val context: Context,
) {

    private val tag = "CustomPlayer"
    private lateinit var trackExtractor: CustomExtractor
    private lateinit var playerListener: Player.Listener
    lateinit var player: ExoPlayer
    private var isSeries: Boolean = false

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
        val defaultMediaSourceFactory = DefaultMediaSourceFactory(defaultDataSourceFactory)
        player = ExoPlayer.Builder(context)
            .setMediaSourceFactory(defaultMediaSourceFactory)
            .build().apply {
//                pauseAtEndOfMediaItems = true
                playWhenReady = true
                repeatMode = REPEAT_MODE_OFF
                addAnalyticsListener(EventLogger(tag))
                addListener(playerListener)
                prepare()
            }
        trackExtractor = CustomExtractor(player.trackSelector as DefaultTrackSelector)
    }

    fun setMediaBy(url: String) {
        player.setMediaItem(
            MediaItem.Builder()
            .setUri(Uri.parse(url))
            .build()
        )

    }

    fun setMediaBy(urlList: List<String>) {
        isSeries = urlList.size > 1
        player.setMediaItems(urlList.map {
            MediaItem.Builder()
                .setUri(Uri.parse(it))
                .build()
        })
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

    fun updateRepeatMode() {
        player.repeatMode = when (player.repeatMode) {
            REPEAT_MODE_OFF -> REPEAT_MODE_ONE
            REPEAT_MODE_ONE -> if (isSeries) REPEAT_MODE_ALL else REPEAT_MODE_OFF
            REPEAT_MODE_ALL -> REPEAT_MODE_OFF
            else -> REPEAT_MODE_OFF
        }
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
