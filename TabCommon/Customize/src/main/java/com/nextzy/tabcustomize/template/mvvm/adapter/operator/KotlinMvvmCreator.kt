package com.nextzy.tabcustomize.template.mvvm.adapter.operator


import com.nextzy.library.base.view.holder.base.BaseItem
import com.nextzy.tabcustomize.template.mvvm.adapter.item.KotlinMvvmCustomItem
import java.util.*

/**
* Created by「 The Khaeng 」on 03 Oct 2017 :)
*/

object KotlinMvvmCreator {
    val TYPE_CUSTOM_ITEM = 10

    fun createMockItem(): List<BaseItem> {
        val itemList = ArrayList<BaseItem>()
        itemList.add(KotlinMvvmCustomItem())
        itemList.add(KotlinMvvmCustomItem())
        itemList.add(KotlinMvvmCustomItem())
        itemList.add(KotlinMvvmCustomItem())
        itemList.add(KotlinMvvmCustomItem())
        itemList.add(KotlinMvvmCustomItem())
        return itemList
    }

    private fun createCustomItem(): KotlinMvvmCustomItem {
        return KotlinMvvmCustomItem()
    }


}
