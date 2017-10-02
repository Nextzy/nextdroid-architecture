package com.nextzy.library.base.utils.recyclerView;

import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.nextzy.library.base.view.holder.base.BaseItem;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by thekhaeng on 5/7/2017 AD.
 */

public abstract class SnapItemListListener extends RecyclerView.OnScrollListener {
    // The minimum amount of items to have below your current scroll position
    // before loading more.
    private int visibleThreshold = 5;

    private RecyclerView.LayoutManager mLayoutManager;
    private List<BaseItem> baseItemList;
    private long delaySnap;
    private boolean isDelay = false;

    public SnapItemListListener(List<BaseItem> baseItemList) {
        this(baseItemList, 0);
    }

    public SnapItemListListener(List<BaseItem> baseItemList, long delaySnap) {
        this.baseItemList = baseItemList;
        this.delaySnap = delaySnap;
    }

    public int getLastVisibleItem(int[] lastVisibleItemPositions) {
        int maxSize = 0;
        for (int i = 0; i < lastVisibleItemPositions.length; i++) {
            if (i == 0) {
                maxSize = lastVisibleItemPositions[i];
            } else if (lastVisibleItemPositions[i] > maxSize) {
                maxSize = lastVisibleItemPositions[i];
            }
        }
        return maxSize;
    }

    private int getFirstVisibleItem(int[] firstVisibleItemPositions) {
        int minSize = 0;
        for (int i = 0; i < firstVisibleItemPositions.length; i++) {
            if (i == 0) {
                minSize = firstVisibleItemPositions[i];
            } else if (firstVisibleItemPositions[i] < minSize) {
                minSize = firstVisibleItemPositions[i];
            }
        }
        return minSize;
    }

    // This happens many times a second during a scroll, so be wary of the code you place here.
    // We are given a few useful parameters to help us work out if we need to load some more data,
    // but first we check if we are waiting for the previous load to finish.
    @Override
    public void onScrolled( RecyclerView view, int dx, int dy) {
        if (isDelay) return;

        if (delaySnap == 0) {
            isDelay = false;
            snapItemList(view);
        } else {
            final Handler handler = new Handler();
            isDelay = true;
            handler.postDelayed(() -> {
                isDelay = false;
                snapItemList(view);
            }, delaySnap);
        }
    }

    private void snapItemList(RecyclerView view) {
        if (mLayoutManager == null) {
            mLayoutManager = view.getLayoutManager();
            if (mLayoutManager instanceof GridLayoutManager ) {
                visibleThreshold = visibleThreshold * ((GridLayoutManager) mLayoutManager).getSpanCount();
            } else if (mLayoutManager instanceof StaggeredGridLayoutManager ) {
                visibleThreshold = visibleThreshold * ((StaggeredGridLayoutManager) mLayoutManager).getSpanCount();
            }
        }

        int firstVisibleItemPosition = 0;
        int lastVisibleItemPosition = 0;
        int totalItemCount = mLayoutManager.getItemCount();

        if (mLayoutManager instanceof StaggeredGridLayoutManager ) {
            int[] firstVisibleItemPositions = ((StaggeredGridLayoutManager) mLayoutManager).findFirstVisibleItemPositions(null);
            int[] lastVisibleItemPositions = ((StaggeredGridLayoutManager) mLayoutManager).findLastVisibleItemPositions(null);
            // get maximum element within the list
            firstVisibleItemPosition = getFirstVisibleItem(firstVisibleItemPositions);
            lastVisibleItemPosition = getLastVisibleItem(lastVisibleItemPositions);
        } else if (mLayoutManager instanceof GridLayoutManager ) {
            firstVisibleItemPosition = ((GridLayoutManager) mLayoutManager).findFirstVisibleItemPosition();
            lastVisibleItemPosition = ((GridLayoutManager) mLayoutManager).findLastVisibleItemPosition();
        } else if (mLayoutManager instanceof LinearLayoutManager ) {
            firstVisibleItemPosition = ((LinearLayoutManager) mLayoutManager).findFirstVisibleItemPosition();
            lastVisibleItemPosition = ((LinearLayoutManager) mLayoutManager).findLastVisibleItemPosition();
        }

        if (firstVisibleItemPosition - 1 >= 0) {
            firstVisibleItemPosition--;
        }

        List<BaseItem> snapList = new ArrayList<>();

        for (int i = firstVisibleItemPosition; i < lastVisibleItemPosition + 1; i++) {
            snapList.add(baseItemList.get(i));
        }
        onSnapItemList(snapList, firstVisibleItemPosition, lastVisibleItemPosition);
    }

    public abstract void onSnapItemList(List<BaseItem> snapList, int firstVisibleItemPosition, int lastVisibleItemPosition);

}
