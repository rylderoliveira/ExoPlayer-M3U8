package com.rylderoliveira.exoplayer_m3u8.data.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Episode(

    @SerializedName("id")
    val id: Int?,

    @SerializedName("episode_name")
    val episodeName: String?,

    @SerializedName("media_url")
    val mediaUrl: String?,
) : Parcelable
