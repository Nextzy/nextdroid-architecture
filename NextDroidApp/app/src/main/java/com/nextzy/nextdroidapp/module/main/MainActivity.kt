package com.nextzy.nextdroidapp.module.main

import android.arch.lifecycle.Observer
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import com.nextzy.library.base.mvvm.layer1View.BaseMvvmListAdapter
import com.nextzy.library.decorator.EndlessScrollListener
import com.nextzy.library.extension.toast
import com.nextzy.library.extension.view.delay
import com.nextzy.nextdroidapp.R
import com.nextzy.nextdroidapp.module.main.adapter.PhotoListAdapter
import com.nextzy.nextdroidapp.module.main.adapter.item.PhotoListItem
import com.nextzy.nextwork.engine.model.NetworkStatus
import com.nextzy.tabcustomize.base.mvvm.CustomMvvmActivity
import com.nextzy.tabcustomize.base.repository.network.DefaultResource
import io.reactivex.functions.Action
import kotlinx.android.synthetic.main.activity_main.rv_picture as rvPicture
import kotlinx.android.synthetic.main.activity_main.swipe_layout as swipeLayout


class MainActivity : CustomMvvmActivity() {

    companion object {
        private const val SPAN = 2
    }


    private lateinit var viewModel: MainViewModel
    private lateinit var pictureAdapter: PhotoListAdapter

    private var isLoadingMore: Boolean = false


    override
    fun setupViewModel() {
        viewModel = getViewModel(MainViewModel::class.java)
        viewModel.pictureList.observe(this, observerPictureList())
        viewModel.pictureListBeforeId.observe(this, observerPictureListBeforeList())
        viewModel.pictureListAfterId.observe(this, observerPictureListAfterList())
        viewModel.requestPhotoList()
        refreshData()
    }


    override
    fun setupLayoutView(): Int = R.layout.activity_main


    override
    fun setupInstance() {
        pictureAdapter = PhotoListAdapter(this).apply {
            setOnClickHolderItemListener(onClickHolderItemListener())
        }
    }


    override
    fun setupView() {
        val manager = StaggeredGridLayoutManager(SPAN, StaggeredGridLayoutManager.VERTICAL).apply {
                        gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
        }
        swipeLayout.setOnRefreshListener(onRefreshListener())


        rvPicture.apply {
            adapter = pictureAdapter
            layoutManager = manager
            addOnScrollListener(EndlessScrollListener.create(onLoadMore()))
        }
    }

    private fun showRefreshing() {
        swipeLayout.isRefreshing = true
    }

    private fun hideRefreshing() {
        delay(Action { swipeLayout.isRefreshing = false }, 1500)
    }

    private fun refreshData() {
        if (viewModel.pictureListItem == null
                || viewModel.pictureListItem?.size() == 0) {
            loadData()
        } else {
            loadNewData()
        }
    }

    private fun loadData() {
        viewModel.requestPhotoList()
    }

    private fun loadMoreData() {
        if (isLoadingMore)
            return
        isLoadingMore = true
        val minId = viewModel.getMinimumPhotoId()
        viewModel.requestPhotoListBeforeId(minId)
    }

    private fun loadNewData() {
        val maxId = viewModel.getMaximumPhotoId()
        viewModel.requestPhotoListBeforeId(maxId)
    }


    private fun onClickHolderItemListener(): BaseMvvmListAdapter.OnClickHolderItemListener<RecyclerView.ViewHolder>
            = object : BaseMvvmListAdapter.OnClickHolderItemListener<RecyclerView.ViewHolder> {
        override
        fun onClickHolder(v: RecyclerView.ViewHolder, position: Int) {
            // do nothing
        }

    }

    private fun onRefreshListener(): SwipeRefreshLayout.OnRefreshListener
            = SwipeRefreshLayout.OnRefreshListener {
        refreshData()
    }


    private fun onLoadMore(): EndlessScrollListener.RefreshList
            = object : EndlessScrollListener.RefreshList {
        override
        fun onRefresh(pageNumber: Int): Boolean {
            toast(getString(R.string.load_more))
            loadMoreData()
            return true
        }
    }


    private fun observerPictureList(): Observer<DefaultResource<PhotoListItem>>
            = Observer { resource: DefaultResource<PhotoListItem>? ->
        when {
            resource?.status == NetworkStatus.SUCCESS -> {
                if (resource.data != null) {
                    viewModel.pictureListItem = resource.data
                    pictureAdapter.notifyPictureDataSetChanged()
                }
                hideRefreshing()
            }
            resource?.status == NetworkStatus.ERROR -> {
                resource.message?.let { showSnackbarError(it) }
                hideRefreshing()
            }
            resource?.status == NetworkStatus.LOADING -> {
                // do nothing
            }
        }
    }

    private fun observerPictureListBeforeList(): Observer<DefaultResource<PhotoListItem>>
            = Observer { resource: DefaultResource<PhotoListItem>? ->
        isLoadingMore = false
        when {
            resource?.status == NetworkStatus.SUCCESS -> {
                if (resource.data != null) {
                    viewModel.pictureListItem?.addPictureItemList(resource.data)
                    pictureAdapter.notifyPictureDataSetChanged()
                }
                hideRefreshing()
            }
            resource?.status == NetworkStatus.ERROR -> {
                hideRefreshing()
                resource.message?.let { showSnackbarError(it) }
            }
            resource?.status == NetworkStatus.LOADING -> {
                // do nothing
            }
        }
    }


    private fun observerPictureListAfterList(): Observer<DefaultResource<PhotoListItem>>
            = Observer { resource: DefaultResource<PhotoListItem>? ->
        when {
            resource?.status == NetworkStatus.SUCCESS -> {
                if (resource.data != null) {
                    viewModel.pictureListItem?.addPictureItemList(0, resource.data)
                    pictureAdapter.notifyPictureDataSetChanged()
                }
                hideRefreshing()
            }
            resource?.status == NetworkStatus.ERROR -> {
                hideRefreshing()
                resource.message?.let { showSnackbarError(it) }
            }
            resource?.status == NetworkStatus.LOADING -> {
                // do nothing
            }
        }
    }

}
