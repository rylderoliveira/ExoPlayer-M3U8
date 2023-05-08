package com.rylderoliveira.exoplayer_m3u8.ui.details

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.rylderoliveira.exoplayer_m3u8.data.models.Title
import com.rylderoliveira.exoplayer_m3u8.databinding.ActivityDetailsBinding
import com.rylderoliveira.exoplayer_m3u8.ui.player.PlayerActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding
    private val viewModel: DetailsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initListeners()

        val a = Observer<Title?> {
            val intent = Intent(this, PlayerActivity::class.java)
            startActivity(intent)
        }
        viewModel.title.observe(this, a)
    }

    private fun initListeners() {
        binding.button.setOnClickListener {
            viewModel.loadMovie()
        }
    }
}
