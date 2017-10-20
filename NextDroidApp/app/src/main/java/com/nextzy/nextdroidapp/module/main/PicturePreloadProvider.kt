package com.nextzy.nextdroidapp.module.main

import android.content.Context
import com.bumptech.glide.ListPreloader
import com.bumptech.glide.RequestBuilder
import com.nextzy.library.glide.GlideApp
import com.nextzy.nextdroidapp.module.main.adapter.item.PhotoItem

/**
 * Created by「 The Khaeng 」on 19 Oct 2017 :)
 */

class PicturePreloadProvider private constructor(
        private val context: Context,
        var pictureItemList: List<PhotoItem>? = null) : ListPreloader.PreloadModelProvider<String> {

    companion object {
        fun create(context: Context): PicturePreloadProvider = PicturePreloadProvider(context)
    }

    override
    fun getPreloadItems(position: Int): List<String> {
        val urlList = arrayListOf<String>()

        pictureItemList?.let {
            (0..position)
                    .takeWhile { i -> i < it.size }
                    .mapTo(urlList) { i: Int -> it[i].imageUrl }
        }
        return urlList
    }

    override
    fun getPreloadRequestBuilder(url: String): RequestBuilder<*>? {
        return GlideApp.with(context)
                .load(url)
    }

}
