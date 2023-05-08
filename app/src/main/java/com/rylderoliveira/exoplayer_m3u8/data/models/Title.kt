package com.rylderoliveira.exoplayer_m3u8.data.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Title(

    @SerializedName("id")
    val id: Int?,

    @SerializedName("title")
    val title: String?,

    @SerializedName("media_url")
    val mediaUrl: String?,

    @SerializedName("episodes")
    val episodes: List<Episode?>,
) : Parcelable
