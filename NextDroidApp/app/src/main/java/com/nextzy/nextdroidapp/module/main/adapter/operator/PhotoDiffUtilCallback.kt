package com.nextzy.nextdroidapp.module.main.adapter.operator

import android.support.v7.util.DiffUtil
import com.nextzy.library.base.view.holder.base.BaseItem

/**
 * Created by「 The Khaeng 」on 18 Oct 2017 :)
 */
class PhotoDiffUtilCallback(private val oldItemList: List<BaseItem>?,
                            private val newItemList: List<BaseItem>?)
    : DiffUtil.Callback() {

    override
    fun getOldListSize(): Int {
        return oldItemList?.size ?: 0
    }

    override
    fun getNewListSize(): Int {
        return newItemList?.size ?: 0
    }

    override
    fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val newItem = newItemList?.get(newItemPosition)
        val oldItem = oldItemList?.get(oldItemPosition)
        return newItem?.isItemTheSame(oldItem) ?: false
    }


    override
    fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val newItem = newItemList?.get(newItemPosition)
        val oldItem = oldItemList?.get(oldItemPosition)
        return newItem?.isContentTheSame(oldItem) ?: false
    }
}