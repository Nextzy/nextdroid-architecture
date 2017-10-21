package com.nextzy.nextdroidapp.module.main.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v7.util.DiffUtil
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.ViewGroup
import com.bumptech.glide.util.ViewPreloadSizeProvider
import com.nextzy.library.base.mvvm.exception.TypeNotMatchInAdapterException
import com.nextzy.library.base.view.holder.base.BaseViewHolder
import com.nextzy.nextdroidapp.module.main.MainViewModel
import com.nextzy.nextdroidapp.module.main.adapter.holder.LoadMoreHolder
import com.nextzy.nextdroidapp.module.main.adapter.holder.PhotoViewHolder
import com.nextzy.nextdroidapp.module.main.adapter.item.PhotoItem
import com.nextzy.nextdroidapp.module.main.adapter.operator.PhotoDiffUtilCallback
import com.nextzy.tabcustomize.base.adapter.CustomMvvmAdapter
import kotlinx.android.synthetic.main.view_holder_picture.view.*


/**
 * Created by「 The Khaeng 」on 18 Sep 2017 :)
 */

class PhotoListAdapter
    : CustomMvvmAdapter<BaseViewHolder<*>>{


    companion object {
        const val TYPE_PICTURE_LIST = 10
        const val TYPE_LOADMORE = 11
        const val TYPE_PICTURE = 12


        private val TAG = PhotoListAdapter::class.java.simpleName
    }

    private var oldDataList: List<PhotoItem>? = null
    val preloadSizeProvider = ViewPreloadSizeProvider<PhotoItem>()

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

    override
    fun getItemCount(): Int = viewModel?.getPhotoItemListSize()?.plus(1) ?: 1

    override
    fun getItemViewType(position: Int): Int {
        if (position >= viewModel?.getPhotoItemListSize() ?: 0) {
            return TYPE_LOADMORE
        }
        return viewModel?.getPhotoItemType(position) ?: -1
    }

    override
    fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): BaseViewHolder<*> {
        super.onCreateViewHolder(viewGroup, viewType)
        if (viewType == TYPE_PICTURE) {
            return PhotoViewHolder(viewGroup)
        } else if (viewType == TYPE_LOADMORE) {
            return LoadMoreHolder(viewGroup)
        }
        throw TypeNotMatchInAdapterException(TAG, viewType)
    }

    override
    fun onBindViewHolder(vh: BaseViewHolder<*>, pos: Int) {
        super.onBindViewHolder(vh, pos)
        if (getItemViewType(pos) == TYPE_PICTURE) {
            val item = viewModel?.getPhotoItem(pos)
            val viewHolder = vh as PhotoViewHolder
            item?.let { viewHolder.onBind(it) }
            preloadSizeProvider.setView(viewHolder.itemView.image)
        } else if (getItemViewType(pos) == TYPE_LOADMORE) {
            val viewHolder = vh as LoadMoreHolder
            val layoutParams = viewHolder.itemView.layoutParams as StaggeredGridLayoutManager.LayoutParams
            layoutParams.isFullSpan = true
        }
    }

    fun notifyPhotoDataSetChanged() {
        val newDataList = viewModel?.getPhotoAllItem()
        val diffResult = DiffUtil.calculateDiff(PhotoDiffUtilCallback(
                oldDataList,
                newDataList))
        diffResult.dispatchUpdatesTo(this)
        oldDataList = mutableListOf<PhotoItem>().apply {
            viewModel?.getPhotoAllItem()?.forEach {it: PhotoItem -> add(it) }
        }
    }

}
