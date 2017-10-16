package com.nextzy.nextdroidapp.repository.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import java.util.*

/**
 * Created by「 The Khaeng 」on 16 Oct 2017 :)
 */
data class Picture(
        @SerializedName("id") val id: Int = 0,
        @SerializedName("link") val link: String? = null,
        @SerializedName("image_url") val imageUrl: String? = null,
        @SerializedName("caption") val caption: String? = null,
        @SerializedName("user_id") val userId: Int = 0,
        @SerializedName("username") val username: String? = null,
        @SerializedName("profile_picture") val profilePicture: String? = null,
        @SerializedName("tags") val tags: List<String> = ArrayList(),
        @SerializedName("created_time") val createdTime: Date? = null,
        @SerializedName("camera") val camera: String? = null,
        @SerializedName("lens") val lens: String? = null,
        @SerializedName("focal_length") val focalLength: String? = null,
        @SerializedName("iso") val iso: String? = null,
        @SerializedName("shutter_speed") val shutterSpeed: String? = null,
        @SerializedName("aperture") val aperture: String? = null)
    : Parcelable {
    constructor(source: Parcel) : this(
            source.readInt(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readInt(),
            source.readString(),
            source.readString(),
            source.createStringArrayList(),
            source.readSerializable() as Date?,
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString()
                                      )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(id)
        writeString(link)
        writeString(imageUrl)
        writeString(caption)
        writeInt(userId)
        writeString(username)
        writeString(profilePicture)
        writeStringList(tags)
        writeSerializable(createdTime)
        writeString(camera)
        writeString(lens)
        writeString(focalLength)
        writeString(iso)
        writeString(shutterSpeed)
        writeString(aperture)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Picture> = object : Parcelable.Creator<Picture> {
            override fun createFromParcel(source: Parcel): Picture = Picture(source)
            override fun newArray(size: Int): Array<Picture?> = arrayOfNulls(size)
        }
    }
}
