package com.nextzy.nextdroidapp.repository

import android.arch.lifecycle.LiveData
import com.nextzy.nextdroidapp.module.main.adapter.item.PhotoItem
import com.nextzy.nextdroidapp.module.main.adapter.item.PhotoListItem
import com.nextzy.nextdroidapp.repository.network.PhotoApiManager
import com.nextzy.nextdroidapp.repository.network.model.reponse.PhotoResponse
import com.nextzy.tabcustomize.base.repository.database.realm.CustomDatabase
import com.nextzy.tabcustomize.base.repository.network.DefaultNetworkBoundResource
import com.nextzy.tabcustomize.base.repository.network.DefaultResource
import com.nextzy.tabcustomize.base.repository.network.DefaultResponse
import io.reactivex.Single

/**
 * Created by「 The Khaeng 」on 12 Oct 2017 :)
 */

class PhotoRepository private constructor() {

    private object Holder {
        val INSTANCE = PhotoRepository()
    }

    companion object {
        val instance: PhotoRepository by lazy { Holder.INSTANCE }
    }

    private val serviceManager: PhotoApiManager = PhotoApiManager.instance
    private val database: CustomDatabase = CustomDatabase.instance

    fun getMaxPhotoId(): Single<Int> {
        return database.loadMaxPhotoId()
    }

    fun savePhotoListItemAll(photoListItemAll: MutableList<PhotoListItem>) {
        database
                .saveItemListAsSingle(photoListItemAll)
                .subscribe()
    }


    fun getPhotoList(isForceFetch: Boolean = false): LiveData<DefaultResource<PhotoListItem>>
            = object : DefaultNetworkBoundResource<PhotoListItem, PhotoResponse>() {

        override
        fun saveCallResult(item: PhotoListItem) {
            item.id = CustomDatabase.PHOTO_LIST
            database.saveItemAsSingle(item).subscribe()
        }

        override
        fun shouldFetch(data: PhotoListItem?): Boolean {
            return isForceFetch
                    || data == null
                    || data.shouldFetch()
        }

        override
        fun loadFromDb(): LiveData<PhotoListItem> {
            return database.loadObjectAsLiveData(CustomDatabase.PHOTO_LIST, PhotoListItem::class.java)
        }

        override
        fun createCall(): LiveData<DefaultResponse<PhotoResponse>> {
            return serviceManager.getPhotoList()
        }

        override
        fun convertToResultType(response: PhotoResponse?): PhotoListItem {
            return convertResponseToItem(response)
        }

    }.asLiveData()


    fun getPhotoListAfterId(id: Int): LiveData<DefaultResource<PhotoListItem>>
            = object : DefaultNetworkBoundResource<PhotoListItem, PhotoResponse>() {

        override
        fun saveCallResult(item: PhotoListItem) {
            item.id = id
            database.saveItemAsSingle(item).subscribe()
            database.saveMaxPhotoId(id).subscribe()
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
        fun createCall(): LiveData<DefaultResponse<PhotoResponse>> {
            return serviceManager.getPhotoListAfterId(id)
        }

        override
        fun convertToResultType(response: PhotoResponse?): PhotoListItem {
            return convertResponseToItem(response)
        }

    }.asLiveData()


    fun getPhotoListBeforeId(id: Int): LiveData<DefaultResource<PhotoListItem>>
            = object : DefaultNetworkBoundResource<PhotoListItem, PhotoResponse>() {

        override
        fun saveCallResult(item: PhotoListItem) {
            item.id = id
            database.saveItemAsSingle(item).subscribe()
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
        fun createCall(): LiveData<DefaultResponse<PhotoResponse>> {
            return serviceManager.getPhotoListBeforeId(id)
        }

        override
        fun convertToResultType(response: PhotoResponse?): PhotoListItem {
            return convertResponseToItem(response)
        }

    }.asLiveData()


    private fun convertResponseToItem(response: PhotoResponse?): PhotoListItem {
        val photoList = mutableListOf<PhotoItem>()
        response?.data?.forEach { photo ->
            photoList.add(PhotoItem()
                                    .setId(photo.id)
                                    .setImageUrl(photo.imageUrl)
                                    .setCaption(photo.caption)
                                    .setProfilePicture(photo.profilePicture)
                                    .setCamera(photo.camera)
                                    .setLens(photo.lens)
                                    .setFocalLength(photo.focalLength)
                                    .setIso(photo.iso)
                                    .setShutterSpeed(photo.shutterSpeed)
                                    .setAperture(photo.aperture))
        }
        return PhotoListItem()
                .setPhotoItemList(photoList)
    }

    fun clearAllDataInDatabase() {
        database.clearAllData()
    }


}
