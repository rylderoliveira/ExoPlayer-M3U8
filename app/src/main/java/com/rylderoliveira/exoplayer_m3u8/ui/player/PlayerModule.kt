package com.rylderoliveira.exoplayer_m3u8.ui.player

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object PlayerModule {
    val module = module {
        factory<PlayerRepository> { PlayerRepositoryImpl(titleRepository = get()) }
        viewModel { PlayerViewModel(playerRepository = get()) }
    }
}
