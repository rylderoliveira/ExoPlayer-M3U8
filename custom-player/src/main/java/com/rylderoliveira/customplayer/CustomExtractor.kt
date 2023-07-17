package com.rylderoliveira.customplayer

import androidx.media3.common.Format
import androidx.media3.common.TrackSelectionOverride
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.trackselection.MappingTrackSelector
import java.util.Locale

@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
class CustomExtractor(
    private val trackSelector: MappingTrackSelector
) {

    private val localeDefault = Locale.getDefault()
    private val locale = Locale.getAvailableLocales()


    fun  selectCustomTrack(customTrack: CustomTrack) {
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
