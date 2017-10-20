package com.nextzy.nextdroidapp.repository

import android.arch.lifecycle.LiveData
import com.nextzy.nextdroidapp.module.main.adapter.item.PhotoItem
import com.nextzy.nextdroidapp.module.main.adapter.item.PhotoListItem
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

    fun getPictureList(isForceFetch: Boolean = false): LiveData<DefaultResource<PhotoListItem>>
            = object : DefaultNetworkBoundResource<PhotoListItem, PictureResponse>() {

        override
        fun saveCallResult(item: PhotoListItem) {
            item.id = CustomDatabase.PICTURE_LIST
            database.saveBaseItemAsSingle(item).subscribe()
        }

        override
        fun shouldFetch(data: PhotoListItem?): Boolean {
            return isForceFetch
                    || data == null
                    || data.shouldFetch()
        }

        override
        fun loadFromDb(): LiveData<PhotoListItem> {
            return database.loadObjectAsLiveData(CustomDatabase.PICTURE_LIST, PhotoListItem::class.java)
        }

        override
        fun createCall(): LiveData<DefaultResponse<PictureResponse>> {
            return serviceManager.getPictureList()
        }

        override
        fun convertToResultType(response: PictureResponse?): PhotoListItem {
            return convertResponseToItem(response)
        }

    }.asLiveData()


    fun getPictureListAfterId(id: Int): LiveData<DefaultResource<PhotoListItem>>
            = object : DefaultNetworkBoundResource<PhotoListItem, PictureResponse>() {

        override
        fun saveCallResult(item: PhotoListItem) {
            item.id = id
            database.saveBaseItemAsSingle(item).subscribe()
        }

        override
        fun shouldFetch(data: PhotoListItem?): Boolean {
            return data == null
                    || data.shouldFetch()
        }

        override
        fun loadFromDb(): LiveData<PhotoListItem> {
            return database.loadObjectAsLiveData(id, PhotoListItem::class.java)
        }

        override
        fun createCall(): LiveData<DefaultResponse<PictureResponse>> {
            return serviceManager.getPictureListAfterId(id)
        }

        override
        fun convertToResultType(response: PictureResponse?): PhotoListItem {
            return convertResponseToItem(response)
        }

    }.asLiveData()


    fun getPictureListBeforeId(id: Int): LiveData<DefaultResource<PhotoListItem>>
            = object : DefaultNetworkBoundResource<PhotoListItem, PictureResponse>() {

        override
        fun saveCallResult(item: PhotoListItem) {
            item.id = id
            database.saveBaseItemAsSingle(item).subscribe()
        }

        override
        fun shouldFetch(data: PhotoListItem?): Boolean {
            return data == null
                    || data.shouldFetch()
        }

        override
        fun loadFromDb(): LiveData<PhotoListItem> {
            return database.loadObjectAsLiveData(id, PhotoListItem::class.java)
        }

        override
        fun createCall(): LiveData<DefaultResponse<PictureResponse>> {
            return serviceManager.getPictureListBeforeId(id)
        }

        override
        fun convertToResultType(response: PictureResponse?): PhotoListItem {
            return convertResponseToItem(response)
        }

    }.asLiveData()


    private fun convertResponseToItem(response: PictureResponse?): PhotoListItem {
        val pictureList = mutableListOf<PhotoItem>()
        response?.data?.forEach { picture ->
            pictureList.add(PhotoItem()
                                    .setId(picture.id)
                                    .setImageUrl(picture.imageUrl)
                                    .setCaption(picture.caption)
                                    .setProfilePicture(picture.profilePicture)
                                    .setCamera(picture.camera)
                                    .setLens(picture.lens)
                                    .setFocalLength(picture.focalLength)
                                    .setIso(picture.iso)
                                    .setShutterSpeed(picture.shutterSpeed)
                                    .setAperture(picture.aperture))
        }
        return PhotoListItem()
                .setPictureItemList(pictureList)
    }

    fun clearAllDataInDatabase() {
        database.clearAllData()
    }

}
