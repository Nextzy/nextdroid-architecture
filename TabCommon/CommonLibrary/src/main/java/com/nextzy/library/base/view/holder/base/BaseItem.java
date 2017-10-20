package com.nextzy.library.base.view.holder.base;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by「 The Khaeng 」on 08 Oct 2017 :)
 */

public abstract class BaseItem implements Parcelable{
    private int id;
    private int type;

    public BaseItem( int type ){
        this(0,type);
    }

    public BaseItem( int id, int type ){
        this.id = id;
        this.type = type;
    }

    public int getId(){
        return id;
    }

    public BaseItem setId( int id ){
        this.id = id;
        return this;
    }

    public int getType(){
        return type;
    }

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel( Parcel dest, int flags ){
        dest.writeInt( this.id );
        dest.writeInt( this.type );
    }

    public BaseItem(){
    }

    protected BaseItem( Parcel in ){
        this.id = in.readInt();
        this.type = in.readInt();
    }

    public boolean isItemTheSame( Object baseItem){
        return equals( baseItem );
    }

    public boolean isContentTheSame( Object baseItem){
        return equals( baseItem );
    }

}
