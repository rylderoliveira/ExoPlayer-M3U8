package com.rylderoliveira.customplayer

import androidx.media3.common.TrackGroup

data class CustomTrack(
    val index: Int,
    val name: String,
    val group: TrackGroup?,
    val type: Int,
)