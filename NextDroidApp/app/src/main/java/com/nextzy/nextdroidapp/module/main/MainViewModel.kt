package com.nextzy.nextdroidapp.module.main

import com.nextzy.nextdroidapp.module.main.adapter.item.PhotoListItem
import com.nextzy.nextdroidapp.repository.PictureRepository
import com.nextzy.nextwork.engine.FetchLiveData
import com.nextzy.nextwork.engine.TriggerLiveData
import com.nextzy.tabcustomize.base.mvvm.CustomViewModel
import com.nextzy.tabcustomize.base.repository.network.DefaultResource

class MainViewModel : CustomViewModel() {

    private var repository: PictureRepository = PictureRepository.instance

    var pictureListItem: PhotoListItem? = null

    val pictureList: FetchLiveData<DefaultResource<PhotoListItem>>
    val pictureListAfterId: TriggerLiveData<Int, DefaultResource<PhotoListItem>>
    val pictureListBeforeId: TriggerLiveData<Int, DefaultResource<PhotoListItem>>

    init {
        this.pictureList = FetchLiveData<DefaultResource<PhotoListItem>>()
                .createSwitchMap({ forceFetch ->
                                     repository.getPictureList(forceFetch)
                                 })
        this.pictureListAfterId = TriggerLiveData<Int, DefaultResource<PhotoListItem>>()
                .createSwitchMap({ id ->
                                     repository.getPictureListAfterId(id)
                                 })
        this.pictureListBeforeId = TriggerLiveData<Int, DefaultResource<PhotoListItem>>()
                .createSwitchMap({ id ->
                                     repository.getPictureListBeforeId(id)
                                 })
    }

    fun requestPhotoList(forceFetch: Boolean = false) {
        if (forceFetch) {
            pictureListItem = null
            repository.clearAllDataInDatabase()
        }
        pictureList.trigger(forceFetch)
    }

    fun requestPhotoListBeforeId(id:Int) {
        pictureListBeforeId.trigger(id)
    }

    fun requestPhotoListAfterId(id:Int) {
        pictureListAfterId.trigger(id)
    }

    fun getMinimumPhotoId(): Int {
        if (pictureListItem == null) return 0
        if (pictureListItem?.size() == 0) return 0

        var minId = pictureListItem?.get(0)?.id ?: 0
        pictureListItem?.pictureItemList?.forEach{ pictureItem ->
            minId = Math.min(minId, pictureItem.id)
        }
        return minId
    }

    fun getMaximumPhotoId(): Int{
        if (pictureListItem == null) return 0
        if (pictureListItem?.size() == 0) return 0

        var maxId = pictureListItem?.get(0)?.id ?: 0
        pictureListItem?.pictureItemList?.forEach{ pictureItem ->
            maxId = Math.max(maxId, pictureItem.id)
        }
        return maxId
    }


}