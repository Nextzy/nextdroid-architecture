package com.nextzy.tabcustomize.template.mvvm.adapter.item;

import android.os.Parcel;
import android.os.Parcelable;

import com.nextzy.library.base.view.holder.base.BaseItem;
import com.nextzy.tabcustomize.template.mvvm.adapter.operator.KotlinMvvmCreator;


/**
 * Created by「 The Khaeng 」on 08 Oct 2017 :)
 */

public class CustomItem extends BaseItem{

    public CustomItem(){
        super( KotlinMvvmCreator.TYPE_CUSTOM_ITEM );
    }

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel( Parcel dest, int flags ){
        super.writeToParcel( dest, flags );
    }

    protected CustomItem( Parcel in ){
        super( in );
    }

    public static final Parcelable.Creator<CustomItem> CREATOR = new Parcelable.Creator<CustomItem>(){
        @Override
        public CustomItem createFromParcel( Parcel source ){
            return new CustomItem( source );
        }

        @Override
        public CustomItem[] newArray( int size ){
            return new CustomItem[size];
        }
    };
}
