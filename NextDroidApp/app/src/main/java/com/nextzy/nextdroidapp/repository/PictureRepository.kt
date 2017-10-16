package com.nextzy.nextdroidapp.repository

import android.arch.lifecycle.LiveData
import com.nextzy.nextdroidapp.module.main.adapter.item.PictureItem
import com.nextzy.nextdroidapp.module.main.adapter.item.PictureListItem
import com.nextzy.nextdroidapp.repository.network.PictureApiManager
import com.nextzy.nextdroidapp.repository.network.model.reponse.PictureResponse
import com.nextzy.tabcustomize.base.repository.database.realm.CustomDatabase
import com.nextzy.tabcustomize.base.repository.network.DefaultNetworkBoundResource
import com.nextzy.tabcustomize.base.repository.network.DefaultResource
import com.nextzy.tabcustomize.base.repository.network.DefaultResponse

/**
 * Created by「 The Khaeng 」on 12 Oct 2017 :)
 */

class PictureRepository private constructor() {

    private object Holder {
        val INSTANCE = PictureRepository()
    }

    companion object {
        val instance: PictureRepository by lazy { Holder.INSTANCE }
    }

    private val serviceManager: PictureApiManager = PictureApiManager.instance
    private val database: CustomDatabase = CustomDatabase.instance

    fun getPictureList(isForceFetch: Boolean = false): LiveData<DefaultResource<PictureListItem>>
            = object : DefaultNetworkBoundResource<PictureListItem, PictureResponse>() {

        override
        fun saveCallResult(item: PictureListItem) {
            item.id = CustomDatabase.PICTURE_LIST
            database.saveBaseItemAsSingle(item).subscribe()
        }

        override
        fun shouldFetch(data: PictureListItem?): Boolean {
            return isForceFetch
                    || data?.shouldFetch() == true
        }

        override
        fun loadFromDb(): LiveData<PictureListItem> {
            return database.loadObjectAsLiveData(CustomDatabase.PICTURE_LIST, PictureListItem::class.java)
        }

        override
        fun createCall(): LiveData<DefaultResponse<PictureResponse>> {
            return serviceManager.getPictureList()
        }


        override
        fun convertToResultType(response: PictureResponse?): PictureListItem {
            val pictureList = mutableListOf<PictureItem>()
            response?.data?.forEach { picture ->
                pictureList.add(PictureItem(
                        id = picture.id,
                        imageUrl = picture.imageUrl,
                        caption = picture.caption,
                        profilePicture = picture.profilePicture,
                        camera = picture.camera,
                        lens = picture.lens,
                        focalLength = picture.focalLength,
                        iso = picture.iso,
                        shutterSpeed = picture.shutterSpeed,
                        aperture = picture.aperture))
            }
            return PictureListItem()
                    .setPictureItemList(pictureList)
        }

    }.asLiveData()

}
