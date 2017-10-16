package com.nextzy.nextdroidapp.module.main.adapter.item;

import android.os.Parcel;

import com.nextzy.library.base.view.holder.base.BaseItem;
import com.nextzy.nextdroidapp.module.main.adapter.operator.PictureCreator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by「 The Khaeng 」on 08 Oct 2017 :)
 */


public class PictureListItem extends BaseItem{

    private List<PictureItem> pictureItemList = new ArrayList<>();

    public PictureListItem(){
        super( PictureCreator.TYPE_PICTURE );
    }

    public PictureListItem setPictureItemList( List<PictureItem> pictureItemList ){
        this.pictureItemList = pictureItemList;
        return this;
    }

    public List<PictureItem> getPictureItemList(){
        return pictureItemList;
    }

    public  boolean shouldFetch(){
        return true;
    }

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel( Parcel dest, int flags ){
        super.writeToParcel( dest, flags );
        dest.writeTypedList( this.pictureItemList );
    }

    protected PictureListItem( Parcel in ){
        super( in );
        this.pictureItemList = in.createTypedArrayList( PictureItem.CREATOR );
    }

    public static final Creator<PictureListItem> CREATOR = new Creator<PictureListItem>(){
        @Override
        public PictureListItem createFromParcel( Parcel source ){
            return new PictureListItem( source );
        }

        @Override
        public PictureListItem[] newArray( int size ){
            return new PictureListItem[size];
        }
    };
}
