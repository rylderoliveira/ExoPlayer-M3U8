package com.rylderoliveira.customplayer

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.StyledPlayerView.ControllerVisibilityListener
import com.rylderoliveira.customplayer.databinding.CustomPlayerControllerOption1Binding
import com.rylderoliveira.customplayer.databinding.ViewCustomPlayerBinding
import com.rylderoliveira.extensions.hide
import com.rylderoliveira.extensions.show


class CustomPlayerDashView
@JvmOverloads
constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : FrameLayout(context, attributeSet, defStyleAttr), CustomPlayerDash {

    private val trackSelector = DefaultTrackSelector(context)
    private val trackExtractor = CustomExtractor(trackSelector)
    private val playerListener = PlayerListener(this)
    private val inflater = LayoutInflater.from(context)
    private val binding = ViewCustomPlayerBinding.inflate(inflater, this, true)
    private val controller = CustomPlayerControllerOption1Binding.bind(binding.playerView)
    private val videoAdapter: TrackAdapter = TrackAdapter()
    private val audioAdapter: TrackAdapter = TrackAdapter()
    private val textAdapter: TrackAdapter = TrackAdapter()
    var customFragmentManager: FragmentManager? = null
    var customPlayer: CustomPlayer = CustomPlayer.Builder(context)
        .setTrackSelector(trackSelector)
        .setTrackExtractor(trackExtractor)
        .setPlayerListener(playerListener)
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
            if (controller.slidingPaneLayoutTrackSelector.isOpen) {
                controller.slidingPaneLayoutTrackSelector.closePane()
                controller.slidingPaneLayoutTrackSelector.requestFocus()
            } else {
                controller.slidingPaneLayoutTrackSelector.openPane()
            }
        }
        binding.playerView.setControllerVisibilityListener(ControllerVisibilityListener { visibility ->
            if (visibility == GONE) {
                controller.slidingPaneLayoutTrackSelector.openPane()
            }
        })
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
        if (controller.slidingPaneLayoutTrackSelector.isOpen.not()) {
            controller.slidingPaneLayoutTrackSelector.openPane()
        } else {
            showExitDialog(activity)
        }
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
        controller.slidingPaneLayoutTrackSelector.open()
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