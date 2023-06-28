package com.rylderoliveira.customplayer

import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.Format
import com.google.android.exoplayer2.Tracks
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
        trackSelector.parameters = trackSelector.parameters.buildUpon()
            .clearOverridesOfType(customTrack.group.type)
            .addOverride(TrackSelectionOverride(customTrack.group, customTrack.index))
            .build()
    }

    fun getCustomTracksBy(rendererIndex: Int): List<CustomTrack> {
        val customTracks = mutableListOf<CustomTrack>()
        val trackGroupArray = trackSelector.currentMappedTrackInfo?.getTrackGroups(rendererIndex)
        trackGroupArray?.let {
            for (trackGroupIndex in 0 until trackGroupArray.length) {
                val trackGroup = trackGroupArray[trackGroupIndex]
                for (formatIndex in 0 until trackGroup.length) {
                    val format = trackGroup.getFormat(formatIndex)
                    customTracks.add(
                        CustomTrack(
                            index = formatIndex,
                            name = getTrackName(format),
                            group = trackGroup,
                        )
                    )
                }
            }

        }
        return customTracks
    }

    fun getTrackName(format: Format): String {
        format.language?.let { formatLanguage ->
            return locale.find { it.language == formatLanguage }
                ?.getDisplayLanguage(localeDefault)?.uppercase()
                ?: formatLanguage.uppercase()
        } ?: return format.height.toString()
    }
}
