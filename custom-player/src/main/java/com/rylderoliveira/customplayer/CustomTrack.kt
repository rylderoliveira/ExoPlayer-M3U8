package com.rylderoliveira.customplayer

import com.google.android.exoplayer2.source.TrackGroup

data class CustomTrack(
    val index: Int,
    val name: String,
    val group: TrackGroup?,
)