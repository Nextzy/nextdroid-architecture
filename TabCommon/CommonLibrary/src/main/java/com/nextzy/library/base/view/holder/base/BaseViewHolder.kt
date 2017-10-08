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

import com.bumptech.glide.Glide
import com.bumptech.glide.load.Transformation

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
        bindView(itemView)
    }

    open fun bindView(view: View) {}


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
        Glide.with(context)
                .load(url)
                .centerCrop()
                .placeholder(placeholderId)
                .into(imgView)
    }

    fun setCircleImage(imgView: ImageView,
                       url: String,
                       @DrawableRes placeholderId: Int) {
        Glide.with(context)
                .load(url)
                .centerCrop()
                .placeholder(placeholderId)
                .bitmapTransform(CropCircleTransformation(context))
                .into(imgView)
    }

    fun setImage(imgView: ImageView,
                 transformation: Transformation<Bitmap>,
                 url: String,
                 @DrawableRes placeholderId: Int) {
        Glide.with(context)
                .load(url)
                .centerCrop()
                .placeholder(placeholderId)
                .bitmapTransform(transformation)
                .into(imgView)
    }


    class BaseItemHolderInfo(val payload: String) : RecyclerView.ItemAnimator.ItemHolderInfo()
}
