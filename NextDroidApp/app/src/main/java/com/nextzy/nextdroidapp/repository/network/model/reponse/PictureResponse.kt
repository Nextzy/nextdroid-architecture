package com.nextzy.nextdroidapp.repository.network.model.reponse

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.nextzy.library.base.mvvm.layer3Repository.network.model.BaseResult
import com.nextzy.nextdroidapp.repository.model.Picture

/**
 * Created by Nonthawit on 3/13/2016.
 */
data class PictureResponse(
        @SerializedName("success")
        var isSuccess: Boolean = false,
        @SerializedName("data")
        var data: List<Picture>? = null)
    : BaseResult(), Parcelable {

    constructor(source: Parcel) : this(
            1 == source.readInt(),
            source.createTypedArrayList(Picture.CREATOR)
                                      )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt((if (isSuccess) 1 else 0))
        writeTypedList(data)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<PictureResponse> = object : Parcelable.Creator<PictureResponse> {
            override fun createFromParcel(source: Parcel): PictureResponse = PictureResponse(source)
            override fun newArray(size: Int): Array<PictureResponse?> = arrayOfNulls(size)
        }
    }
}
