package com.nextzy.nextdroidapp.module.main.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.view.ViewGroup
import com.nextzy.library.base.mvvm.exception.TypeNotMatchInAdapterException
import com.nextzy.library.base.view.holder.base.BaseViewHolder
import com.nextzy.nextdroidapp.module.main.MainViewModel
import com.nextzy.nextdroidapp.module.main.adapter.holder.LoadMoreHolder
import com.nextzy.nextdroidapp.module.main.adapter.holder.PictureViewHolder
import com.nextzy.nextdroidapp.module.main.adapter.item.PictureListItem
import com.nextzy.nextdroidapp.module.main.adapter.operator.PictureCreator
import com.nextzy.tabcustomize.base.adapter.CustomMvvmAdapter




/**
 * Created by「 The Khaeng 」on 18 Sep 2017 :)
 */

class PictureListAdapter
    : CustomMvvmAdapter<BaseViewHolder<*>> {

    companion object {
        private val TAG = PictureListAdapter::class.java.simpleName
    }

    private var loadMoreListener: OnLoadMoreListener? = null

    interface OnLoadMoreListener {
        fun onLoadMore()
    }


    private var viewModel: MainViewModel? = null

    constructor(activity: FragmentActivity) : super(activity) {
        viewModel = getSharedViewModel(MainViewModel::class.java)
    }

    constructor(fragment: Fragment) : super(fragment) {
        viewModel = getSharedViewModel(MainViewModel::class.java)
    }


    fun setOnLoadMoreListener(listener: OnLoadMoreListener) {
        this.loadMoreListener = listener
    }

    override
    fun getItemCount(): Int = 0
//            itemList?.size ?: 0

    override
    fun getItemViewType(position: Int): Int = 0
//     itemList?.get( position )?.type ?: -1


    override
    fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): BaseViewHolder<*> {
        super.onCreateViewHolder(viewGroup, viewType)
        if (viewType == PictureCreator.TYPE_PICTURE) {
            return PictureViewHolder(viewGroup)
        } else if (viewType == PictureCreator.TYPE_LOADMORE) {
            return LoadMoreHolder(viewGroup)
        }
        throw TypeNotMatchInAdapterException(TAG)
    }


    override
    fun onBindViewHolder(vh: BaseViewHolder<*>, pos: Int) {
        super.onBindViewHolder(vh, pos)
        val i = null
//        val i = itemList?.get(pos)
        if (getItemViewType(pos) == PictureCreator.TYPE_PICTURE) {
            val viewHolder = vh as PictureViewHolder
            val item = i as PictureListItem
            viewHolder.onBind(item)
        } else if (getItemViewType(pos) == PictureCreator.TYPE_LOADMORE) {
            loadMoreListener?.onLoadMore()
        }
    }

}
