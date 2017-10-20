package com.nextzy.library.glide

import android.content.Context
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions



/**
 * Created by「 The Khaeng 」on 18 Oct 2017 :)
 */

@GlideModule
class BaseGlideModule : AppGlideModule(){
    override
    fun applyOptions(context: Context?, builder: GlideBuilder?) {
        super.applyOptions(context, builder)
        builder?.setDefaultRequestOptions(
                RequestOptions()
                        .format(DecodeFormat.PREFER_ARGB_8888) )
    }
}
