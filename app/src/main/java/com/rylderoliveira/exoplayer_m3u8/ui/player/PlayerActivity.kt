package com.rylderoliveira.exoplayer_m3u8.ui.player

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.rylderoliveira.customplayer.CustomPlayer
import com.rylderoliveira.exoplayer_m3u8.databinding.ActivityPlayerBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlayerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlayerBinding
    private val viewModel: PlayerViewModel by viewModel()
    lateinit var player: CustomPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initPlayer()
        onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                binding.customPlayerView.onBackPressed(this@PlayerActivity)
            }
        })
    }

    private fun initPlayer() {
        binding.customPlayerView.customFragmentManager = supportFragmentManager
        player = binding.customPlayerView.customPlayer
//        player.setMediaBy("https://lb01.odeon-service.xyz/data1/videos/filme/dual/decada/2020/505642_pantera_negra_wakanda_para_sempre/h264/576/pantera_negra_wakanda_para_sempre.mp4")
//        player.setMediaBy("https://bitdash-a.akamaihd.net/content/MI201109210084_1/m3u8s/f08e80da-bf1d-4e3d-8899-f0f6155f6efa.m3u8")
        player.setMediaBy("https://i-slave3.playmediaapi.xyz/data3/videos/serie/dual/decada/2020/111141_maid/s1/maid.smil/manifest.mpd")
        player.play()
    }


}