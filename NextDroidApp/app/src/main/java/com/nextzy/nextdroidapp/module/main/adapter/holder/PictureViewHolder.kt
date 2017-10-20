package com.nextzy.nextdroidapp.module.main.adapter.holder

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.DrawableImageViewTarget
import com.bumptech.glide.request.target.Target
import com.nextzy.library.base.view.holder.base.BaseViewHolder
import com.nextzy.library.glide.GlideApp
import com.nextzy.nextdroidapp.R
import com.nextzy.nextdroidapp.module.main.adapter.item.PhotoItem
import com.thekhaeng.pushdownanim.PushDownAnim
import kotlinx.android.synthetic.main.view_holder_picture.view.*


/**
 * Created by「 The Khaeng 」on 03 Oct 2017 :)
 */

class PictureViewHolder(parent: ViewGroup)
    : BaseViewHolder<PhotoItem>(parent, R.layout.view_holder_picture) {

    override
    fun onBindView(view: View) {
    }

    override
    fun onBind(item: PhotoItem) {
        val glideRequest = GlideApp.with(context)
                .load(item.imageUrl)
        itemView.title.text = item.caption

        if (item.isSetImageSize) {
            if (itemView.image.layoutParams.height != item.imageHeight) {
                itemView.image.layoutParams = itemView.image.layoutParams.apply {
                    height = item.imageHeight
                }
            }
            glideRequest
                    .override(item.imageWidth, item.imageHeight)
        }

        glideRequest
                .placeholder(R.drawable.img_placeholder)
                .dontTransform()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .transition(DrawableTransitionOptions.withCrossFade())
                .listener(requestListener(item))
                .into(itemView.image)

        PushDownAnim
                .setOnTouchPushDownAnim(itemView)
                .setScale(0.98f)


    }

    private fun requestListener(item: PhotoItem): RequestListener<Drawable>
            = object : RequestListener<Drawable> {
        @SuppressLint("SetTextI18n")
        override
        fun onResourceReady(resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?, isFirstResource: Boolean): Boolean {

            itemView.title.visibility = View.VISIBLE

            resource?.let {
                val glideTarget = target as DrawableImageViewTarget
                val iv = glideTarget.view
                val width = iv.measuredWidth

                val targetHeight = width * resource.intrinsicHeight / resource.intrinsicWidth

                item.imageWidth = width
                item.imageHeight = targetHeight

                itemView.size.visibility = View.VISIBLE
                itemView.size.text = width.toString() + " x " + targetHeight
            }
            return false
        }

        override
        fun onLoadFailed(e: GlideException?,
                         model: Any?,
                         target: Target<Drawable>?,
                         isFirstResource: Boolean): Boolean {
            return false
        }
    }


}
