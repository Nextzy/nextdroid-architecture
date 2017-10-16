package com.nextzy.nextdroidapp.module.main.adapter.item

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by「 The Khaeng 」on 08 Oct 2017 :)
 */


data class PictureItem(
        val id: Int = 0,
        val imageUrl: String? = null,
        val caption: String? = null,
        val profilePicture: String? = null,
        val camera: String? = null,
        val lens: String? = null,
        val focalLength: String? = null,
        val iso: String? = null,
        val shutterSpeed: String? = null,
        val aperture: String? = null)
    : Parcelable {
    constructor(source: Parcel) : this(
            source.readInt(),
            source.readString(),
            source.readString(),
            source.readString(),
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
        writeString(imageUrl)
        writeString(caption)
        writeString(profilePicture)
        writeString(camera)
        writeString(lens)
        writeString(focalLength)
        writeString(iso)
        writeString(shutterSpeed)
        writeString(aperture)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<PictureItem> = object : Parcelable.Creator<PictureItem> {
            override fun createFromParcel(source: Parcel): PictureItem = PictureItem(source)
            override fun newArray(size: Int): Array<PictureItem?> = arrayOfNulls(size)
        }
    }
}
