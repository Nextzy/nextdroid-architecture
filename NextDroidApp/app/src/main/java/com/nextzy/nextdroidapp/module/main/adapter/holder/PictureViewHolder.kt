package com.nextzy.nextdroidapp.module.main.adapter.holder

import android.support.annotation.DrawableRes
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.nextzy.library.base.view.holder.base.BaseViewHolder
import com.nextzy.nextdroidapp.R
import com.nextzy.nextdroidapp.module.main.adapter.item.PictureListItem
import jp.wasabeef.glide.transformations.RoundedCornersTransformation


/**
* Created by「 The Khaeng 」on 03 Oct 2017 :)
*/

class PictureViewHolder(itemView: ViewGroup)
    : BaseViewHolder<PictureListItem>(itemView, R.layout.view_holder_picture) {

    override
    fun onBind(item: PictureListItem) {
    }

    fun setRoundImage(imgView: ImageView,
                 url: String,
                 @DrawableRes placeholderId: Int) {
        Glide.with(context)
                .load(url)
                .centerCrop()
                .placeholder(placeholderId)
                .bitmapTransform(RoundedCornersTransformation(
                        context,
                        getDimension(R.dimen.card_view_corner).toInt(),
                        0 ))
                .into(imgView)
    }

}
