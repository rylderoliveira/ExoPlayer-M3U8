package com.rylderoliveira.exoplayer_m3u8.ui.player

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import com.rylderoliveira.customplayer.CustomPlayer
import com.rylderoliveira.exoplayer_m3u8.databinding.ActivityPlayerBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

@UnstableApi class PlayerActivity : Activity() {

    private lateinit var binding: ActivityPlayerBinding
//    private val viewModel: PlayerViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initPlayer()
//        onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
//            override fun handleOnBackPressed() {
//                binding.customViewDash.onBackPressed(this@PlayerActivity)
//            }
//        })
    }

    private fun initPlayer() {
//        player.setMediaItem(MediaItem.fromUri("http://i-slave1.odeonvod.com/data1/videos/filme/dual/decada/2020/505642_pantera_negra_wakanda_para_sempre/h264/576/pantera_negra_wakanda_para_sempre.mp4"))
//        player.setMediaItem(MediaItem.fromUri("https://i-slave1.odeonvod.com/data1/videos/filme/dual/decada/2020/505642_pantera_negra_wakanda_para_sempre/wakanda.smil/manifest.mpd"))
//        player.setMediaItem(MediaItem.fromUri("https://bitdash-a.akamaihd.net/content/MI201109210084_1/m3u8s/f08e80da-bf1d-4e3d-8899-f0f6155f6efa.m3u8"))
//        player.setMediaItem(MediaItem.fromUri("https://i-slave3.playmediaapi.xyz/data3/videos/serie/dual/decada/2020/111141_maid/s1/maid.smil/manifest.mpd"))
//        player.setMediaItem(MediaItem.fromUri("https://i-slave3.playmediaapi.xyz/data3/videos/serie/dual/decada/2020/93288_starstruck/s1/h264/1080/e5/starstruck.mp4"))
//        player.setMediaItem(MediaItem.fromUri("http://154.53.54.134/maid.mp4"))
//        player.setMediaItem(MediaItem.fromUri("http://lb03.odeon-service.xyz/data1/videos/filme/dual/decada/2020/897561_freddiemercurythefinalact/h264/576/freddiemercurythefinalact.mp4"))
//        binding.customViewDash.customPlayer.setMediaBy("https://bitdash-a.akamaihd.net/content/MI201109210084_1/m3u8s/f08e80da-bf1d-4e3d-8899-f0f6155f6efa.m3u8")
        binding.customViewDash.customPlayer.setMediaBy("https://i-slave1.odeonvod.com/data1/videos/filme/dual/decada/2020/505642_pantera_negra_wakanda_para_sempre/source.smil/manifest.mpd")
        binding.customViewDash.customPlayer.play()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.customViewDash.customPlayer.release()
    }
}