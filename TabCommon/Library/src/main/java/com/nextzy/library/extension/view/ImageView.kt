package com.nextzy.library.extension.view

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import jp.wasabeef.glide.transformations.CropCircleTransformation

/**
 * Created by「 The Khaeng 」on 03 Oct 2017 :)
 */
fun ImageView.setImage(resId: Int,
                        cache: DiskCacheStrategy =  DiskCacheStrategy.NONE){
    Glide.with(this?.context)
            .load(resId)
            .centerCrop()
            .diskCacheStrategy(cache)
            .into(this)
}

fun ImageView.setCircleImage(url: String?){
    Glide.with(this.context)
            .load(url)
            .centerCrop()
            .bitmapTransform(CropCircleTransformation(this.context))
            .into(this)

}