package com.nextzy.nextdroidapp.module.main.adapter.item;

import android.os.Parcel;

import com.nextzy.library.base.view.holder.base.BaseItem;
import com.nextzy.nextdroidapp.module.main.adapter.PhotoListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by「 The Khaeng 」on 08 Oct 2017 :)
 */


public class PhotoListItem extends BaseItem{

    private List<PhotoItem> photoItemList = new ArrayList<>();

    public PhotoListItem(){
        super( PhotoListAdapter.TYPE_PICTURE_LIST );
    }

    public PhotoListItem setPhotoItemList( List<PhotoItem> photoItemList ){
        this.photoItemList = photoItemList;
        return this;
    }

    public void addPhotoItemList( PhotoListItem photoListItem){
        this.photoItemList.addAll(photoListItem.getPhotoItemList());
    }

    public void addPhotoItemList( int index, PhotoListItem photoListItem){
        this.photoItemList.addAll(index, photoListItem.getPhotoItemList());
    }

    public PhotoItem get( int pos ){
        if( photoItemList == null || photoItemList.isEmpty() ) return null;
        return photoItemList.get( pos );
    }

    public int size(){
        if( photoItemList == null || photoItemList.isEmpty() ) return 0;
        return photoItemList.size();
    }

    public int getType( int position ){
        return photoItemList.get( position ).getType();
    }

    public List<PhotoItem> getPhotoItemList(){
        return photoItemList;
    }

    public boolean shouldFetch(){
        return photoItemList.isEmpty();
    }

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel( Parcel dest, int flags ){
        super.writeToParcel( dest, flags );
        dest.writeTypedList( this.photoItemList );
    }

    protected PhotoListItem( Parcel in ){
        super( in );
        this.photoItemList = in.createTypedArrayList( PhotoItem.CREATOR );
    }

    public static final Creator<PhotoListItem> CREATOR = new Creator<PhotoListItem>(){
        @Override
        public PhotoListItem createFromParcel( Parcel source ){
            return new PhotoListItem( source );
        }

        @Override
        public PhotoListItem[] newArray( int size ){
            return new PhotoListItem[size];
        }
    };

    @Override
    public boolean equals( Object o ){
        if( this == o ) return true;
        if( !( o instanceof PhotoListItem ) ) return false;

        PhotoListItem that = (PhotoListItem) o;

        return photoItemList != null ? photoItemList.equals( that.photoItemList ) : that.photoItemList == null;
    }

    @Override
    public int hashCode(){
        return photoItemList != null ? photoItemList.hashCode() : 0;
    }
}
