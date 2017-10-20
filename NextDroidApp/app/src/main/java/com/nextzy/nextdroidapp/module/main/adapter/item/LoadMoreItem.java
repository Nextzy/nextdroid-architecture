package com.nextzy.nextdroidapp.module.main.adapter.item;


import android.os.Parcel;

import com.nextzy.library.base.view.holder.base.BaseItem;
import com.nextzy.nextdroidapp.module.main.adapter.PhotoListAdapter;

/**
 * Created by「 The Khaeng 」on 08 Oct 2017 :)
 */

public class LoadMoreItem extends BaseItem{

    public LoadMoreItem(){
        super( PhotoListAdapter.TYPE_LOADMORE );
    }

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel( Parcel dest, int flags ){
        super.writeToParcel( dest, flags );
    }

    protected LoadMoreItem( Parcel in ){
        super( in );
    }

    public static final Creator<LoadMoreItem> CREATOR = new Creator<LoadMoreItem>(){
        @Override
        public LoadMoreItem createFromParcel( Parcel source ){
            return new LoadMoreItem( source );
        }

        @Override
        public LoadMoreItem[] newArray( int size ){
            return new LoadMoreItem[size];
        }
    };
}
