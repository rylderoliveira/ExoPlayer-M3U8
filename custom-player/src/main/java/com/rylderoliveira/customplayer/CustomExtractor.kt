package com.rylderoliveira.customplayer

import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.Format
import com.google.android.exoplayer2.Tracks
import com.google.android.exoplayer2.source.TrackGroup
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.MappingTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelectionOverride
import com.google.common.collect.ImmutableList
import java.text.Normalizer.Form
import java.util.Locale

class CustomExtractor(
    private val trackSelector: MappingTrackSelector
) {

    private val localeDefault = Locale.getDefault()
    private val locale = Locale.getAvailableLocales()

    fun selectCustomTrack(customTrack: CustomTrack) {
        if (customTrack.group != null) {
            trackSelector.parameters = trackSelector.parameters.buildUpon()
                .clearOverridesOfType(customTrack.type)
                .addOverride(TrackSelectionOverride(customTrack.group, customTrack.index))
                .build()
        }
    }

    fun clearOverrides(type: Int) {
        trackSelector.parameters = trackSelector.parameters.buildUpon()
            .clearOverridesOfType(type)
            .build()
    }

    fun getTrackName(format: Format): String {
        format.language?.let { formatLanguage ->
            return locale.find { it.language == formatLanguage }
                ?.getDisplayLanguage(localeDefault)?.uppercase()
                ?: formatLanguage.uppercase()
        } ?: return format.height.toString()
    }
}
