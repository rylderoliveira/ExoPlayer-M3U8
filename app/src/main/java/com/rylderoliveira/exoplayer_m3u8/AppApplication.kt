package com.rylderoliveira.exoplayer_m3u8

import android.app.Application
import com.rylderoliveira.exoplayer_m3u8.ui.details.DetailsModule
import com.rylderoliveira.exoplayer_m3u8.ui.player.PlayerModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class AppApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        val appModules = listOf(
            DetailsModule.module,
            PlayerModule.module,
        )

        startKoin {
            androidLogger()
            androidContext(this@AppApplication)
            modules(appModules)
        }
    }
}
