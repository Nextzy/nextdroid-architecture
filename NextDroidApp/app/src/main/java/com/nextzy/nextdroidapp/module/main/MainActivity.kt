package com.nextzy.nextdroidapp.module.main

import android.arch.lifecycle.Observer
import android.content.res.Configuration
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
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
import com.thekhaeng.pushdownanim.PushDownAnim
import io.reactivex.functions.Action
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.ic_info as icInfo
import kotlinx.android.synthetic.main.activity_main.ic_reload as icReload
import kotlinx.android.synthetic.main.activity_main.rv_picture as rvPicture
import kotlinx.android.synthetic.main.activity_main.swipe_layout as swipeLayout


class MainActivity : CustomMvvmActivity() {

    companion object {
        private const val SPAN_TWO = 2
        private const val SPAN_THREE = 3
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var pictureAdapter: PhotoListAdapter
    private lateinit var manager: StaggeredGridLayoutManager

    override
    fun setupViewModel() {
        viewModel = getViewModel(MainViewModel::class.java)
        viewModel.photoListAfterIdLiveData.observe(this, observerPictureListAfterList())
        viewModel.photoListLiveData.observe(this, observerPictureList())
        viewModel.photoListBeforeIdLiveData.observe(this, observerPictureListBeforeList())
    }

    override
    fun setupLayoutView(): Int = R.layout.activity_main


    override
    fun setupInstance() {
        super.setupInstance()
        pictureAdapter = PhotoListAdapter(this).apply {
            setOnClickHolderItemListener(onClickHolderItemListener())
        }
    }

    override
    fun setupView() {
        super.setupView()
        setSupportActionBar(toolbar)

        PushDownAnim
                .setOnTouchPushDownAnim(fab)
                .setOnClickListener(onClickListener())
                .setScale(0.95f)
        icReload.setOnClickListener(onClickListener())
        icInfo.setOnClickListener(onClickListener())
        manager = StaggeredGridLayoutManager(getSpan(), StaggeredGridLayoutManager.VERTICAL).apply {
            gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS
        }
        swipeLayout.setOnRefreshListener(onRefreshListener())


        rvPicture.apply {
            adapter = pictureAdapter
            layoutManager = manager
            addOnScrollListener(EndlessScrollListener.create(onLoadMore()))
        }
    }


    override
    fun initialize() {
        super.initialize()
        viewModel
                .getMaxPhotoId()
                .subscribe({ id: Int ->
                               if (id == -1) {
                                   refreshData()
                               } else {
                                   viewModel.requestPhotoListAfterId(id)
                               }
                           })
    }

    override
    fun onRestoreView(savedInstanceState: Bundle) {
        super.onRestoreView(savedInstanceState)
        if (viewModel.photoListItemAll.isEmpty()) {
            initialize()
        } else {
            pictureAdapter.notifyPhotoDataSetChanged()
            manager.onScrollStateChanged(RecyclerView.SCROLL_STATE_IDLE)
        }
    }

    override
    fun onDestroy() {
        super.onDestroy()
        viewModel.saveData()
    }

    private fun showRefreshing() {
        swipeLayout.isRefreshing = true
    }

    private fun hideRefreshing() {
        delay(Action { swipeLayout.isRefreshing = false }, 1500)
    }

    private fun refreshData() {
        if (viewModel.photoListItemAll.isEmpty()) {
            loadData()
        } else {
            loadNewData()
        }
    }

    private fun loadData() {
        viewModel.requestPhotoList()
    }

    private fun loadMoreData() {
        val minId = viewModel.getMinimumPhotoId()
        viewModel.requestPhotoListBeforeId(minId)
    }

    private fun loadNewData() {
        val maxId = viewModel.getMaximumPhotoId()
        if (maxId <= 0) return
        viewModel.requestPhotoListAfterId(maxId)
    }

    private fun onClickListener(): View.OnClickListener
            = View.OnClickListener { v ->
        when (v) {
            fab -> {
                rvPicture.smoothScrollToPosition(0)
            }
            icReload -> {
                showRefreshing()
                viewModel.requestPhotoList(true)
                pictureAdapter.notifyPhotoDataSetChanged()
            }
            icInfo -> {
                toast("fab")
            }
        }
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

    private fun getSpan(): Int {
        return when {
            resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE -> SPAN_THREE
            resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT -> SPAN_TWO
            else -> SPAN_TWO
        }
    }


    private fun observerPictureList(): Observer<DefaultResource<PhotoListItem>>
            = Observer { resource: DefaultResource<PhotoListItem>? ->
        when {
            resource?.status == NetworkStatus.SUCCESS -> {
                resource.data?.let {
                    viewModel.addPhotoItemList(data = resource.data)
                    pictureAdapter.notifyPhotoDataSetChanged()
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
        when {
            resource?.status == NetworkStatus.SUCCESS -> {
                if (resource.data != null) {
                    viewModel.addPhotoItemList(data = resource.data)
                    pictureAdapter.notifyPhotoDataSetChanged()
                    manager.onScrollStateChanged(RecyclerView.SCROLL_STATE_IDLE)
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
                    viewModel.addPhotoItemList(0, data = resource.data)
                    pictureAdapter.notifyPhotoDataSetChanged()
                    manager.onScrollStateChanged(RecyclerView.SCROLL_STATE_IDLE)
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
