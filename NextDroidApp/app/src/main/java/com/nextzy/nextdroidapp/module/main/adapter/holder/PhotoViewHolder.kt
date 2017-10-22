package com.nextzy.nextdroidapp.module.main.adapter.holder

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.graphics.drawable.Drawable
import android.support.v7.widget.AppCompatImageView
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
import com.nextzy.library.glide.GlideRequest
import com.nextzy.nextdroidapp.R
import com.nextzy.nextdroidapp.module.main.adapter.item.PhotoItem
import com.thekhaeng.pushdownanim.PushDownAnim
import kotlinx.android.synthetic.main.view_holder_picture.view.*


/**
 * Created by「 The Khaeng 」on 03 Oct 2017 :)
 */

class PhotoViewHolder(parent: ViewGroup)
    : BaseViewHolder<PhotoItem>(parent, R.layout.view_holder_picture) {

    override
    fun onBindView(view: View) {
    }

    override
    fun onBind(item: PhotoItem) {
        val glideRequest = GlideApp.with(context)
                .load(item.imageUrl)
        itemView.title.text = item.caption

        setupImageViewSize(item, glideRequest)

        glideRequest
                .placeholder(R.drawable.img_placeholder)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transition(DrawableTransitionOptions.withCrossFade())
                .listener(requestListener(itemView.image, item))
                .into(itemView.image)

        PushDownAnim
                .setOnTouchPushDownAnim(itemView)
                .setScale(0.98f)


    }

    /* =========================== Private method =============================================== */
    private fun setupImageViewSize(item: PhotoItem, glideRequest: GlideRequest<Drawable>) {
        if (item.isSetImageSizePortrait) {
            if (isPortrait()) {
                glideRequest
                        .override(item.imageWidthPortrait, item.imageHeightPortrait)
                if (itemView.image.layoutParams.height != item.imageHeightPortrait) {
                    itemView.image.layoutParams = itemView.image.layoutParams.apply {
                        height = item.imageHeightPortrait
                    }
                    itemView.image.requestLayout()
                }
            } else {
                glideRequest
                        .override(item.imageWidthLandScape, item.imageHeightLandScape)
                if (itemView.image.layoutParams.height != item.imageHeightLandScape) {
                    itemView.image.layoutParams = itemView.image.layoutParams.apply {
                        height = item.imageHeightLandScape
                    }
                    itemView.image.requestLayout()
                }
            }

        }else{
            itemView.image.layout(0,0,0,0)
        }
    }

    private fun isPortrait(): Boolean {
        return context.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT
    }

    /* =========================== Callback method =============================================== */
    private fun requestListener(imageView: AppCompatImageView, item: PhotoItem): RequestListener<Drawable>
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
                if (width != 0 && targetHeight != 0) {
                    if (imageView.layoutParams.height != targetHeight) {
                        if (isPortrait()) {
                            item.imageWidthPortrait = width
                            item.imageHeightPortrait = targetHeight
                        } else {
                            item.imageWidthLandScape = width
                            item.imageHeightLandScape = targetHeight
                        }
                        imageView.layoutParams.height = targetHeight
                        imageView.requestLayout()
                    }
                }else{
                    itemView.image.layout(0,0,0,0)
                }

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
