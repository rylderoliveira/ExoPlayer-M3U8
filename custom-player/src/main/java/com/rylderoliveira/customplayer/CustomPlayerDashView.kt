package com.rylderoliveira.customplayer

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.media3.common.Player
import androidx.media3.common.Player.REPEAT_MODE_ALL
import androidx.media3.common.Player.REPEAT_MODE_OFF
import androidx.media3.common.Player.REPEAT_MODE_ONE
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.PlayerView
import androidx.recyclerview.widget.LinearLayoutManager
import com.rylderoliveira.customplayer.databinding.PlayerControllerBinding
import com.rylderoliveira.customplayer.databinding.ViewCustomPlayerBinding
import com.rylderoliveira.extensions.hide
import com.rylderoliveira.extensions.show


@UnstableApi class CustomPlayerDashView
@JvmOverloads
constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : FrameLayout(context, attributeSet, defStyleAttr), CustomPlayerDash {

    private val inflater = LayoutInflater.from(context)
    private val binding = ViewCustomPlayerBinding.inflate(inflater, this, true)
    private val controller = PlayerControllerBinding.bind(binding.playerView)
    private val videoAdapter: TrackAdapter = TrackAdapter()
    private val audioAdapter: TrackAdapter = TrackAdapter()
    private val textAdapter: TrackAdapter = TrackAdapter()
    private var customPlayer: CustomPlayer = CustomPlayer.Builder(context)
        .setPlayerListener(PlayerListener(this))
        .build()

    init {
        initialize()
        initListeners()
    }

    private fun initListeners() {
        controller.buttonPlay.setOnClickListener {
            play()
        }
        controller.buttonPause.setOnClickListener {
            pause()
        }
        controller.buttonNext.setOnClickListener {
            next()
        }
        controller.buttonSelector.setOnClickListener {
            if (controller.linearLayoutContainerTrackSelector.isVisible) {
                controller.linearLayoutContainerTrackSelector.hide()
            } else {
                controller.linearLayoutContainerTrackSelector.show()
                controller.linearLayoutContainerTrackSelector.requestFocus()
            }
        }
        binding.playerView.setControllerVisibilityListener(PlayerView.ControllerVisibilityListener { visibility ->
            if (visibility == VISIBLE) {
                controller.linearLayoutContainerTrackSelector.hide()
            }
        })
        controller.exoProgress.setOnFocusChangeListener { view, b ->
            if (b) {
                controller.linearLayoutContainerTrackSelector.hide()
            }
        }
        controller.buttonRepeat.setOnClickListener {
            customPlayer.updateRepeatMode()
        }
    }

    override fun clearTracks() {
        with(customPlayer) {
            audioTracks.clear()
            videoTracks.clear()
            subtitleTracks.clear()
        }
    }

    override fun onRepeatModeChanged(repeatMode: Int) {
        when (repeatMode) {
            REPEAT_MODE_OFF -> handleNewRepeatMode(R.drawable.ic_media_repeat_off, R.string.repeat_mode_off)
            REPEAT_MODE_ONE -> handleNewRepeatMode(R.drawable.ic_media_repeat_one, R.string.repeat_mode_one)
            REPEAT_MODE_ALL -> handleNewRepeatMode(R.drawable.ic_media_repeat_all, R.string.repeat_mode_all)
        }
    }

    private fun handleNewRepeatMode(@DrawableRes icResource: Int, @StringRes message: Int) {
        controller.buttonRepeat.setImageResource(icResource)
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun updateTracks() {
        customPlayer.setCustomTracks()
        controller.recyclerViewTrackSelectorVideos.apply {
            adapter = videoAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            with(videoAdapter) {
                items = customPlayer.videoTracks
                listener = { track -> customPlayer.selectTrack(track) }
            }
        }
        controller.recyclerViewTrackSelectorAudios.apply {
            adapter = audioAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            with(audioAdapter) {
                items = customPlayer.audioTracks
                listener = { track -> customPlayer.selectTrack(track) }
            }
        }
        controller.recyclerViewTrackSelectorSubtitles.apply {
            adapter = textAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            with(textAdapter) {
                items = customPlayer.subtitleTracks
                listener = { track -> customPlayer.selectTrack(track) }
            }
        }
    }

    fun onBackPressed(activity: AppCompatActivity) {
        if (controller.linearLayoutContainerTrackSelector.isVisible) {
            controller.linearLayoutContainerTrackSelector.hide()
        } else {
            activity.finish()
        }
    }

    override fun setMediaBy(url: String) {
        customPlayer.setMediaBy(url)
    }

    override fun setMediaBy(urlList: List<String>) {
        customPlayer.setMediaBy(urlList)
    }

    override fun initialize() {
        binding.playerView.player = customPlayer.player
        controller.exoProgress.setKeyTimeIncrement(10000L)
    }

    override fun release() {
        customPlayer.release()
    }

    override fun showController() {
        binding.playerView.showController()
    }

    override fun play() {
        controller.buttonPlay.hide()
        controller.buttonPause.show()
        controller.buttonPause.requestFocus()
        customPlayer.play()
    }

    override fun pause() {
        controller.buttonPause.hide()
        controller.buttonPlay.show()
        controller.buttonPlay.requestFocus()
        customPlayer.pause()
    }

    override fun next() {
        customPlayer.next()
    }

    override fun previous() {
        customPlayer.previous()
    }

    override fun updateButtons() {
        val hasNext = customPlayer.player.hasNextMediaItem()
        controller.buttonNext.isEnabled = hasNext
        controller.buttonNext.isFocusable = hasNext
    }

    override fun restart() {
        customPlayer.restart()
    }

    override fun showLoading() {
        binding.viewAnimationPlayback.show()
    }

    override fun hideLoading() {
        binding.viewAnimationPlayback.hide()
    }

    override fun shouldShowNextEpisode() {

    }
}
