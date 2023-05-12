package com.rylderoliveira.exoplayer_m3u8.ui.player

import android.net.Uri
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.Player.STATE_READY
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.video.VideoSize
import com.rylderoliveira.exoplayer_m3u8.R
import com.rylderoliveira.exoplayer_m3u8.databinding.ActivityPlayerBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlayerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlayerBinding
    private lateinit var player: ExoPlayer
    private lateinit var textViewTitleName: TextView
    private lateinit var textViewMediaName: TextView
    private val viewModel: PlayerViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
        viewModel.title.observe(this) { title ->
            textViewTitleName.text = title?.title
            if (title?.episodes?.isNotEmpty() == true) {
                textViewMediaName.text = title.episodes.first()?.episodeName
            }
            val mediaItem =
                MediaItem.fromUri(Uri.parse(title?.mediaUrl ?: title?.episodes?.first()?.mediaUrl))
            binding.playerView.player = player
            player.setMediaItem(mediaItem)
//            player.addListener(playerListener)
            player.prepare()
            player.play()
        }

        val trackSelector = DefaultTrackSelector(this)

        player = ExoPlayer.Builder(this)
            .setTrackSelector(trackSelector)
            .build()

        val playerListener = object : Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                if (playbackState == STATE_READY) {
                    val trackGroups =
                        trackSelector.currentMappedTrackInfo?.getTrackGroups(0)?.get(0)
                    val formats = mutableListOf<String?>()
                    if (trackGroups != null) {
                        for (i in 0 until trackGroups.length) {
                            val track = trackGroups.getFormat(i)
                            if (track.frameRate != -1f) {
                                formats.add(track.height.toString())
                            }
                        }
                    }
                }
                super.onPlaybackStateChanged(playbackState)
            }

            override fun onVideoSizeChanged(videoSize: VideoSize) {
//                textView1.text = videoSize.height.toString()
                super.onVideoSizeChanged(videoSize)
            }
        }
//
//        val button1: ImageButton = binding.playerView.findViewById(R.id.button_1)
//        val button2: ImageButton = binding.playerView.findViewById(R.id.button_2)
//        val button3: ImageButton = binding.playerView.findViewById(R.id.button_3)
//        val button4: ImageButton = binding.playerView.findViewById(R.id.button_4)
//        val button5: ImageButton = binding.playerView.findViewById(R.id.button_5)
//        textView1 = binding.playerView.findViewById(R.id.text_view_1)
//        textView2 = binding.playerView.findViewById(R.id.text_view_2)
//
//        button1.setOnClickListener {
//            trackSelector.parameters = trackSelector.buildUponParameters()
//                .clearOverridesOfType(C.TRACK_TYPE_VIDEO)
//                .addOverride(
//                    TrackSelectionOverride(
//                        trackSelector.currentMappedTrackInfo?.getTrackGroups(
//                            0
//                        )?.get(0)!!, 0
//                    )
//                )
//                .build()
//        }
//
//        button2.setOnClickListener {
//            trackSelector.parameters = trackSelector.buildUponParameters()
//                .clearOverridesOfType(C.TRACK_TYPE_VIDEO)
//                .addOverride(
//                    TrackSelectionOverride(
//                        trackSelector.currentMappedTrackInfo?.getTrackGroups(
//                            0
//                        )?.get(0)!!, 1
//                    )
//                )
//                .build()
//        }
//
//        button3.setOnClickListener {
//            trackSelector.parameters = trackSelector.buildUponParameters()
//                .clearOverridesOfType(C.TRACK_TYPE_VIDEO)
//                .addOverride(
//                    TrackSelectionOverride(
//                        trackSelector.currentMappedTrackInfo?.getTrackGroups(
//                            0
//                        )?.get(0)!!, 2
//                    )
//                )
//                .build()
//        }
//
//        button4.setOnClickListener {
//            trackSelector.parameters = trackSelector.buildUponParameters()
//                .clearOverridesOfType(C.TRACK_TYPE_AUDIO)
//                .addOverride(
//                    TrackSelectionOverride(
//                        trackSelector.currentMappedTrackInfo?.getTrackGroups(
//                            1
//                        )?.get(0)!!, 0
//                    )
//                )
//                .build()
//        }
//
//        button5.setOnClickListener {
//            trackSelector.parameters = trackSelector.buildUponParameters()
//                .clearOverridesOfType(C.TRACK_TYPE_AUDIO)
//                .addOverride(
//                    TrackSelectionOverride(
//                        trackSelector.currentMappedTrackInfo?.getTrackGroups(
//                            1
//                        )?.get(1)!!, 0
//                    )
//                )
//                .build()
//        }


    }

    private fun initViews() {
        binding.playerView.apply {
            textViewTitleName = findViewById(R.id.text_view_player_title_name)
            textViewMediaName = findViewById(R.id.text_view_player_media_name)
        }
    }
}