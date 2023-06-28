package com.rylderoliveira.customplayer

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.children
import androidx.fragment.app.FragmentManager
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.trackselection.MappingTrackSelector
import com.rylderoliveira.customplayer.databinding.CustomPlayerControllersBinding
import com.rylderoliveira.customplayer.databinding.ViewCustomPlayerBinding

class CustomPlayerView : FrameLayout {

    private val inflater = LayoutInflater.from(context)
    private val binding = ViewCustomPlayerBinding.inflate(inflater, this)
    private val controllerView: ConstraintLayout =
        binding.playerView.findViewById(R.id.constraint_layout_custom_controller)
    private val mergeBinding = CustomPlayerControllersBinding.bind(controllerView)
    var customFragmentManager: FragmentManager? = null
    var customPlayer: CustomPlayer = CustomPlayer.Builder(context).build()


    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init(attrs, defStyle)
    }

    init {
        initPlayer()
        initListeners()
    }

    private fun initPlayer() {
        binding.playerView.player = customPlayer.player
    }

    private fun initListeners() {
        mergeBinding.apply {
            buttonSelectAudio.setOnClickListener {
                showTrackSelectorDialog(customPlayer.audioTracks)
            }
            buttonSelectVideo.setOnClickListener {
                showTrackSelectorDialog(customPlayer.videoTracks)
            }
            buttonSelectSubtitle.setOnClickListener {
                showTrackSelectorDialog(customPlayer.subtitleTracks)
            }
            buttonSelectTest.setOnClickListener {

            }
        }
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.CustomPlayerView, defStyle, 0)
        a.recycle()
    }

    private fun showTrackSelectorDialog(tracks: List<CustomTrack>) {
        TrackSelectorDialog().apply {
            setTracks(tracks)
            customFragmentManager?.let { show(it, null) } ?: run {
                Log.e(
                    this.tag,
                    "The TrackSelectorDialog needs to have a FragmentManager to be displayed."
                )
            }
        }
    }
}