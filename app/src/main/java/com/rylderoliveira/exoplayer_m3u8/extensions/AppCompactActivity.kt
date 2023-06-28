package com.rylderoliveira.exoplayer_m3u8.extensions

import androidx.appcompat.app.AppCompatActivity
import com.rylderoliveira.customplayer.CustomTrack
import com.rylderoliveira.customplayer.TrackSelectorDialog

fun AppCompatActivity.showTrackSelectorDialog(tracks: List<com.rylderoliveira.customplayer.CustomTrack>) {
    com.rylderoliveira.customplayer.TrackSelectorDialog().apply {
        setTracks(tracks)
        show(supportFragmentManager, null)
    }
}