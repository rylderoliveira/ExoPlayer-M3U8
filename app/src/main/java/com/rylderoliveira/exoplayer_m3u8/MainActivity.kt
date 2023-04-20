package com.rylderoliveira.exoplayer_m3u8

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.Player.STATE_READY
import androidx.media3.common.TrackSelectionOverride
import androidx.media3.common.TrackSelectionParameters
import androidx.media3.common.Tracks
import androidx.media3.common.VideoSize
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.trackselection.DefaultTrackSelector
import androidx.media3.ui.PlayerView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rylderoliveira.exoplayer_m3u8.databinding.ActivityMainBinding

@UnstableApi
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var settingsAdapter: SettingsAdapter
    private lateinit var textView1: TextView
    private lateinit var textView2: TextView
    private lateinit var player: ExoPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        settingsAdapter = SettingsAdapter()

        val trackSelector = DefaultTrackSelector(this)

        player = ExoPlayer.Builder(this)
            .setTrackSelector(trackSelector)
            .build()

        val settings: ImageButton = binding.playerView.findViewById(R.id.exo_settings)
        val recyclerViewSettings: RecyclerView =
            binding.playerView.findViewById(R.id.exo_settings_listview)
        recyclerViewSettings.adapter = settingsAdapter
        recyclerViewSettings.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        settings.setOnClickListener {
            recyclerViewSettings.isVisible = recyclerViewSettings.isVisible.not()
        }

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

                    settingsAdapter.add(formats)
                }
                super.onPlaybackStateChanged(playbackState)
            }

            override fun onVideoSizeChanged(videoSize: VideoSize) {
                textView1.text = videoSize.height.toString()
                super.onVideoSizeChanged(videoSize)
            }

            override fun onTracksChanged(tracks: Tracks) {
                textView2.text = player.audioFormat?.label
                super.onTracksChanged(tracks)
            }

            override fun onTrackSelectionParametersChanged(parameters: TrackSelectionParameters) {
                Log.i("Rylder", "Mudouuuu")
                super.onTrackSelectionParametersChanged(parameters)
            }
        }

        val button1: ImageButton = binding.playerView.findViewById(R.id.button_1)
        val button2: ImageButton = binding.playerView.findViewById(R.id.button_2)
        val button3: ImageButton = binding.playerView.findViewById(R.id.button_3)
        val button4: ImageButton = binding.playerView.findViewById(R.id.button_4)
        val button5: ImageButton = binding.playerView.findViewById(R.id.button_5)
        textView1 = binding.playerView.findViewById(R.id.text_view_1)
        textView2 = binding.playerView.findViewById(R.id.text_view_2)

        button1.setOnClickListener {
            trackSelector.parameters = trackSelector.buildUponParameters()
                .clearOverridesOfType(C.TRACK_TYPE_VIDEO)
                .addOverride(
                    TrackSelectionOverride(
                        trackSelector.currentMappedTrackInfo?.getTrackGroups(
                            0
                        )?.get(0)!!, 0
                    )
                )
                .build()
        }

        button2.setOnClickListener {
            trackSelector.parameters = trackSelector.buildUponParameters()
                .clearOverridesOfType(C.TRACK_TYPE_VIDEO)
                .addOverride(
                    TrackSelectionOverride(
                        trackSelector.currentMappedTrackInfo?.getTrackGroups(
                            0
                        )?.get(0)!!, 1
                    )
                )
                .build()
        }

        button3.setOnClickListener {
            trackSelector.parameters = trackSelector.buildUponParameters()
                .clearOverridesOfType(C.TRACK_TYPE_VIDEO)
                .addOverride(
                    TrackSelectionOverride(
                        trackSelector.currentMappedTrackInfo?.getTrackGroups(
                            0
                        )?.get(0)!!, 2
                    )
                )
                .build()
        }

        button4.setOnClickListener {
            trackSelector.parameters = trackSelector.buildUponParameters()
                .clearOverridesOfType(C.TRACK_TYPE_AUDIO)
                .addOverride(
                    TrackSelectionOverride(
                        trackSelector.currentMappedTrackInfo?.getTrackGroups(
                            1
                        )?.get(0)!!, 0
                    )
                )
                .build()
        }

        button5.setOnClickListener {
            trackSelector.parameters = trackSelector.buildUponParameters()
                .clearOverridesOfType(C.TRACK_TYPE_AUDIO)
                .addOverride(
                    TrackSelectionOverride(
                        trackSelector.currentMappedTrackInfo?.getTrackGroups(
                            1
                        )?.get(1)!!, 0
                    )
                )
                .build()
        }


        val mediaItem =
            MediaItem.fromUri(Uri.parse("http://207.244.241.91/hls/pantera/h264/pantera_,1080.mp4,576.mp4,480.mp4,sub.srt,forced.srt,.urlset/master.m3u8"))
        binding.playerView.player = player
        binding.playerView.setControllerVisibilityListener(PlayerView.ControllerVisibilityListener {
            if (it == View.VISIBLE) {

            } else {
                recyclerViewSettings.isVisible = false
            }
        })
        player.setMediaItem(mediaItem)
        player.addListener(playerListener)
        player.seekTo(600000)
        player.prepare()
        player.play()
    }

    private fun DefaultTrackSelector.updateSelector() {
        parameters = buildUponParameters()
            .addOverride(
                TrackSelectionOverride(
                    currentMappedTrackInfo?.getTrackGroups(0)?.get(0)!!,
                    0
                )
            )
            .build()
    }
}