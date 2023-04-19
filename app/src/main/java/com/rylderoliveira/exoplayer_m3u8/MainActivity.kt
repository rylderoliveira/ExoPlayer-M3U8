package com.rylderoliveira.exoplayer_m3u8

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.core.view.isVisible
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.Tracks
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.trackselection.DefaultTrackSelector
import com.rylderoliveira.exoplayer_m3u8.databinding.ActivityMainBinding

@UnstableApi class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val trackSelector = DefaultTrackSelector(this)

        val player = ExoPlayer.Builder(this)
            .setTrackSelector(trackSelector)
            .build()


//        val settings: ImageButton = binding.playerView.findViewById(R.id.exo_settings)
//        val options: LinearLayout = binding.playerView.findViewById(R.id.linear)
//        settings.setOnClickListener {
//            options.isVisible = options.isVisible.not()
//        }



        val playerListener = object : Player.Listener {
            override fun onTracksChanged(tracks: Tracks) {
                super.onTracksChanged(tracks)
            }
        }

        val mediaItem = MediaItem.fromUri(Uri.parse("http://207.244.241.91/hls/avatar/avatar_,1080.mp4,576.mp4,480.mp4,sub.srt,.urlset/master.m3u8"))
        binding.playerView.player = player
        player.setMediaItem(mediaItem)
        player.addListener(playerListener)
        player.prepare()
        player.play()
    }
}