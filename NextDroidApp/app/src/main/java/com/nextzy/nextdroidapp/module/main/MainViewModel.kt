package com.nextzy.nextdroidapp.module.main

import com.nextzy.nextdroidapp.module.main.adapter.item.PhotoItem
import com.nextzy.nextdroidapp.module.main.adapter.item.PhotoListItem
import com.nextzy.nextdroidapp.repository.PhotoRepository
import com.nextzy.nextwork.engine.FetchLiveData
import com.nextzy.nextwork.engine.TriggerLiveData
import com.nextzy.tabcustomize.base.mvvm.CustomViewModel
import com.nextzy.tabcustomize.base.repository.network.DefaultResource
import io.reactivex.Single

class MainViewModel : CustomViewModel {

    private var repository: PhotoRepository = PhotoRepository.instance

    var photoListItemAll: MutableList<PhotoListItem> = mutableListOf()

    val photoListLiveData: FetchLiveData<DefaultResource<PhotoListItem>>
    val photoListAfterIdLiveData: TriggerLiveData<Int, DefaultResource<PhotoListItem>>
    val photoListBeforeIdLiveData: TriggerLiveData<Int, DefaultResource<PhotoListItem>>

    init {
    }
    constructor(){
        this.photoListLiveData = FetchLiveData<DefaultResource<PhotoListItem>>()
                .createSwitchMap({ forceFetch ->
                                     repository.getPhotoList(forceFetch)
                                 })
        this.photoListAfterIdLiveData = TriggerLiveData<Int, DefaultResource<PhotoListItem>>()
                .createSwitchMap({ id ->
                                     repository.getPhotoListAfterId(id)
                                 })
        this.photoListBeforeIdLiveData = TriggerLiveData<Int, DefaultResource<PhotoListItem>>()
                .createSwitchMap({ id ->
                                     repository.getPhotoListBeforeId(id)
                                 })
    }

    fun getMaxPhotoId(): Single<Int> {
        return repository.getMaxPhotoId()
    }


    fun requestPhotoList(forceFetch: Boolean = false) {
        if (forceFetch) {
            photoListItemAll.clear()
            repository.clearAllDataInDatabase()
        }
        photoListLiveData.trigger(forceFetch)
    }

    fun requestPhotoListBeforeId(id: Int) {
        photoListBeforeIdLiveData.trigger(id)
    }

    fun requestPhotoListAfterId(id: Int) {
        photoListAfterIdLiveData.trigger(id)
    }

    fun getMinimumPhotoId(): Int {
        if (photoListItemAll.size == 0) return 0

        var minId = photoListItemAll[0].get(0).id

        photoListItemAll.forEach { it: PhotoListItem ->
            it.photoItemList.forEach { pictureItem ->
                minId = Math.min(minId, pictureItem.id)
            }
        }

        return minId
    }

    fun getMaximumPhotoId(): Int {
        if (photoListItemAll.size == 0) return 0

        var maxId = photoListItemAll[0].get(0).id

        photoListItemAll.forEach { it: PhotoListItem ->
            it.photoItemList.forEach { pictureItem ->
                maxId = Math.max(maxId, pictureItem.id)
            }
        }

        return maxId
    }

    fun getPhotoAllItem(): List<PhotoItem> {
        val list = ArrayList<PhotoItem>()
        photoListItemAll.forEach { photoListItem ->
            list.addAll(photoListItem.photoItemList)
        }
        return list
    }

    fun getPhotoItem(pos: Int): PhotoItem? {
        var tmpPos = pos
        photoListItemAll.forEach { photoListItem ->
            if (tmpPos >= photoListItem.size()) {
                tmpPos -= photoListItem.size()
            } else {
                return photoListItem[tmpPos]
            }
        }
        return null
    }

    fun getPhotoItemType(pos: Int): Int {
        var tmpPos = pos
        photoListItemAll.forEach { photoListItem ->
            if (tmpPos >= photoListItem.size()) {
                tmpPos -= photoListItem.size()
            } else {
                return photoListItem[tmpPos].type
            }
        }
        return -1
    }

    fun getPhotoItemListSize(): Int {
        var size = 0
        photoListItemAll.forEach { size += it.size() }
        return size
    }

    fun addPhotoItemList(i: Int = -1, data: PhotoListItem?) {
        if (data?.photoItemList?.isNotEmpty() == true) {
            if (i == -1) {
                photoListItemAll.add(data)
            } else {
                photoListItemAll.add(i, data)
            }
        }
    }

    fun saveData() {
        repository.savePhotoListItemAll(photoListItemAll)
    }

}