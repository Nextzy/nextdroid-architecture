package com.nextzy.nextdroidapp.module.photo

import android.view.View
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.nextzy.library.extension.view.show
import com.nextzy.library.glide.GlideApp
import com.nextzy.nextdroidapp.R
import com.nextzy.nextdroidapp.module.main.adapter.item.PhotoItem
import com.nextzy.tabcustomize.base.mvvm.CustomMvvmActivity
import kotlinx.android.synthetic.main.activity_photo.*
import kotlinx.android.synthetic.main.activity_photo.container_aperture as containerAperture
import kotlinx.android.synthetic.main.activity_photo.container_camera as containerCamera
import kotlinx.android.synthetic.main.activity_photo.container_content as containerContent
import kotlinx.android.synthetic.main.activity_photo.container_focus as containerFocus
import kotlinx.android.synthetic.main.activity_photo.container_iso as containerIso
import kotlinx.android.synthetic.main.activity_photo.container_lens as containerLens
import kotlinx.android.synthetic.main.activity_photo.container_shutter_speed as containerShutterSpeed
import kotlinx.android.synthetic.main.activity_photo.ic_back as icBack
import kotlinx.android.synthetic.main.activity_photo.tv_aperture as tvAperture
import kotlinx.android.synthetic.main.activity_photo.tv_camera as tvCamera
import kotlinx.android.synthetic.main.activity_photo.tv_caption as tvCaption
import kotlinx.android.synthetic.main.activity_photo.tv_focus as tvFocus
import kotlinx.android.synthetic.main.activity_photo.tv_iso as tvIso
import kotlinx.android.synthetic.main.activity_photo.tv_lens as tvLens
import kotlinx.android.synthetic.main.activity_photo.tv_no_content as tvNoContent
import kotlinx.android.synthetic.main.activity_photo.tv_shutter_speed as tvShutterSpeed


/**
 * Created by「 The Khaeng 」on 08 Oct 2017 :)
 */

class PhotoActivity : CustomMvvmActivity() {

    companion object {
        const val KEY_PHOTO_ITEM = "key_photo_item"
        const val KEY_HOLDER_ID = "key_holder_id"
    }

    private lateinit var viewModel: PhotoViewModel
    private lateinit var photoItem: PhotoItem

    override
    fun setupViewModel() {
        viewModel = getViewModel(PhotoViewModel::class.java)
    }

    override
    fun setupLayoutView(): Int = R.layout.activity_photo


    override
    fun setupInstance() {
        super.setupInstance()
        photoItem = intent.extras.getParcelable(KEY_PHOTO_ITEM)
    }

    override
    fun setupView() {
        super.setupView()
        icBack.setOnClickListener(onClickListener())
        GlideApp.with(this)
                .load(photoItem.imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(photo)

        tvCaption.text = photoItem.caption

        if (photoItem.camera?.isNotBlank() == true) {
            containerCamera.show(true)
            tvCamera.text = photoItem.camera
        }
        if (photoItem.lens?.isNotBlank() == true) {
            containerLens.show(true)
            tvLens.text = photoItem.lens
        }
        if (photoItem.focalLength?.isNotBlank() == true) {
            containerFocus.show(true)
            tvFocus.text = photoItem.focalLength
        }
        if (photoItem.shutterSpeed?.isNotBlank() == true) {
            containerShutterSpeed.show(true)
            tvShutterSpeed.text = photoItem.shutterSpeed
        }
        if (photoItem.aperture?.isNotBlank() == true) {
            containerAperture.show(true)
            tvAperture.text = photoItem.aperture
        }
        if (photoItem.iso?.isNotBlank() == true) {
            containerIso.show(true)
            tvIso.text = photoItem.iso
        }


        if (photoItem.camera?.isNotBlank() == true
                && photoItem.lens?.isBlank() == true
                && photoItem.focalLength?.isBlank() == true
                && photoItem.shutterSpeed?.isBlank() == true
                && photoItem.aperture?.isBlank() == true
                && photoItem.iso?.isBlank() == true) {
            containerContent.show(false)
            tvNoContent.show(true)
        }
    }


    override
    fun onBackPressed() {
        setResult(RESULT_OK, intent)
        finish()
    }

    private fun onClickListener(): View.OnClickListener
            = View.OnClickListener { v ->
        when (v) {
            icBack -> finish()
        }
    }


}

