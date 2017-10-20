package com.nextzy.library.base.view.holder.base

import android.content.Context
import android.graphics.Bitmap
import android.support.annotation.DimenRes
import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.load.Transformation
import com.nextzy.library.glide.GlideApp
import jp.wasabeef.glide.transformations.CropCircleTransformation

/**
 * Created by「 The Khaeng 」on 23 Aug 2017 :)
 */

abstract class BaseViewHolder<I>(parent: ViewGroup, layout: Int)
    : RecyclerView.ViewHolder(LayoutInflater
                                      .from(parent.context)
                                      .inflate(layout, parent, false)) {

    val recyclerView: RecyclerView = parent as RecyclerView

    val layoutManager: RecyclerView.LayoutManager = recyclerView.layoutManager

    val context: Context = itemView.context

    init {
        onBindView(itemView)
    }

    open fun onBindView(view: View) {}


    abstract fun onBind(item: I)

    fun getDimension(@DimenRes resId: Int): Float {
        return context.resources.getDimension(resId)
    }

    fun getString(@StringRes resId: Int): String {
        return context.resources.getString(resId)
    }


    fun setImage(imgView: ImageView,
                 url: String,
                 @DrawableRes placeholderId: Int) {
        GlideApp.with(context)
                .load(url)
                .placeholder(placeholderId)
                .centerCrop()
                .into(imgView)
    }

    fun setCircleImage(imgView: ImageView,
                       url: String,
                       @DrawableRes placeholderId: Int) {
        GlideApp.with(context)
                .load(url)
                .centerCrop()
                .placeholder(placeholderId)
                .transform(CropCircleTransformation(context))
                .into(imgView)
    }

    fun setImage(imgView: ImageView,
                 transformation: Transformation<Bitmap>,
                 url: String,
                 @DrawableRes placeholderId: Int) {
        GlideApp.with(context)
                .load(url)
                .centerCrop()
                .placeholder(placeholderId)
                .transform(transformation)
                .into(imgView)
    }

}
