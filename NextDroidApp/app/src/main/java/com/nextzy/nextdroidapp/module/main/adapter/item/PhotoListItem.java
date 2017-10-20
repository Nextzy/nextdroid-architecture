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

    private List<PhotoItem> pictureItemList = new ArrayList<>();

    public PhotoListItem(){
        super( PhotoListAdapter.TYPE_PICTURE_LIST );
    }

    public PhotoListItem setPictureItemList( List<PhotoItem> pictureItemList ){
        this.pictureItemList = pictureItemList;
        return this;
    }

    public void addPictureItemList(PhotoListItem pictureListItem){
        this.pictureItemList.addAll(pictureListItem.getPictureItemList());
    }

    public void addPictureItemList(int index, PhotoListItem pictureListItem){
        this.pictureItemList.addAll(index, pictureListItem.getPictureItemList());
    }

    public PhotoItem get( int pos ){
        if( pictureItemList == null || pictureItemList.isEmpty() ) return null;
        return pictureItemList.get( pos );
    }

    public int size(){
        if( pictureItemList == null || pictureItemList.isEmpty() ) return 0;
        return pictureItemList.size();
    }

    public int getType( int position ){
        return pictureItemList.get( position ).getType();
    }

    public List<PhotoItem> getPictureItemList(){
        return pictureItemList;
    }

    public boolean shouldFetch(){
        return pictureItemList.isEmpty();
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

    protected PhotoListItem( Parcel in ){
        super( in );
        this.pictureItemList = in.createTypedArrayList( PhotoItem.CREATOR );
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

        return pictureItemList != null ? pictureItemList.equals( that.pictureItemList ) : that.pictureItemList == null;
    }

    @Override
    public int hashCode(){
        return pictureItemList != null ? pictureItemList.hashCode() : 0;
    }
}
