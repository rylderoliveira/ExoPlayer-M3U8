package com.rylderoliveira.exoplayer_m3u8.ui.player

import android.os.Bundle
import android.view.KeyEvent
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.media3.common.util.UnstableApi
import com.rylderoliveira.exoplayer_m3u8.databinding.ActivityPlayerBinding

@UnstableApi
class PlayerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlayerBinding
    private val viewModel: PlayerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initPlayer()
        onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                binding.customViewDash.onBackPressed(this@PlayerActivity)
            }
        })
    }

    private fun initPlayer() {
        val urls = listOf<String>(
            "https://i-slave1.odeonvod.com/data1/videos/filme/dual/decada/2020/505642_pantera_negra_wakanda_para_sempre/source.smil/manifest.mpd",
            "https://bitdash-a.akamaihd.net/content/MI201109210084_1/m3u8s/f08e80da-bf1d-4e3d-8899-f0f6155f6efa.m3u8",
        )
//        binding.customViewDash.setMediaBy("https://i-slave1.odeonvod.com/data1/videos/filme/dual/decada/2020/505642_pantera_negra_wakanda_para_sempre/source.smil/manifest.mpd")
        binding.customViewDash.setMediaBy(urls)
        binding.customViewDash.play()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.customViewDash.release()
    }

    override fun dispatchKeyEvent(event: KeyEvent?): Boolean {
        if (binding.customViewDash.isRunningNextEpisode) {
            if (event?.keyCode == KeyEvent.KEYCODE_BACK) return super.dispatchKeyEvent(event)
            return true
        }
        binding.customViewDash.showController()
        return super.dispatchKeyEvent(event)
    }
}