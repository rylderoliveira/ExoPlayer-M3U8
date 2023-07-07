package com.rylderoliveira.customplayer

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentManager
import androidx.media3.common.Player
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
    var customFragmentManager: FragmentManager? = null
    var customPlayer: CustomPlayer = CustomPlayer.Builder(context)
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


        }
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

    }

    override fun showExitDialog(activity: AppCompatActivity) {
//        TODO: Implementar o dialog mais tarde
        release()
        activity.finish()
    }

    override fun keepWatchingDialog() {
        TODO("Not yet implemented")
    }

    override fun initialize() {
        binding.playerView.player = customPlayer.player
    }

    override fun release() {
        customPlayer.release()
    }

    override fun showController() {
        TODO("Not yet implemented")
    }

    override fun hideController() {
        TODO("Not yet implemented")
    }

    override fun showLoading() {
        TODO("Not yet implemented")
    }

    override fun hideLoading() {
        TODO("Not yet implemented")
    }

    override fun showNextEpisodeProgress() {
        TODO("Not yet implemented")
    }

    override fun hideNextEpisodeProgress() {
        TODO("Not yet implemented")
    }

    override fun saveCurrentPosition() {
        TODO("Not yet implemented")
    }

    override fun setPreferredAudio() {
        TODO("Not yet implemented")
    }

    override fun setPreferredVideo() {
        TODO("Not yet implemented")
    }

    override fun setPreferredText() {
        TODO("Not yet implemented")
    }

    override fun setSubtitleStyle() {
        TODO("Not yet implemented")
    }

    override fun loadMediaParams() {
        TODO("Not yet implemented")
    }

    override fun loadAvailableTracks() {
        TODO("Not yet implemented")
    }

    override fun loadPlaylist() {
        TODO("Not yet implemented")
    }

    override fun play() {
        controller.buttonPlay.hide()
        controller.buttonPause.show()
        customPlayer.play()
    }

    override fun pause() {
        controller.buttonPause.hide()
        controller.buttonPlay.show()
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
    }

    override fun fastForward() {
        TODO("Not yet implemented")
    }

    override fun rewind() {
        TODO("Not yet implemented")
    }

    override fun restart() {
        customPlayer.restart()
    }

    override fun seekTo() {
        TODO("Not yet implemented")
    }

    override fun setRepeatMode(repeatMode: Player.RepeatMode) {
        TODO("Not yet implemented")
    }
}