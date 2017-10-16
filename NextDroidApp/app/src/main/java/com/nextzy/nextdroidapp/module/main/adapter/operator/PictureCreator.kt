package com.nextzy.nextdroidapp.module.main.adapter.operator


import com.nextzy.library.base.view.holder.base.BaseItem
import com.nextzy.tabcustomize.template.mvvm.adapter.item.CustomItem
import java.util.*

/**
* Created by「 The Khaeng 」on 03 Oct 2017 :)
*/

object PictureCreator {
    const val TYPE_PICTURE = 10
    const val TYPE_LOADMORE = 11

    fun createMockItem(): List<BaseItem> {
        val itemList = ArrayList<BaseItem>()
        itemList.add(CustomItem())
        itemList.add(CustomItem())
        itemList.add(CustomItem())
        itemList.add(CustomItem())
        itemList.add(CustomItem())
        itemList.add(CustomItem())
        return itemList
    }

    private fun createCustomItem(): CustomItem {
        return CustomItem()
    }


}
