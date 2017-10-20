package com.nextzy.nextdroidapp.module.main.adapter.item;

import android.os.Parcel;

import com.nextzy.library.base.view.holder.base.BaseItem;
import com.nextzy.nextdroidapp.module.main.adapter.PhotoListAdapter;

/**
 * Created by「 The Khaeng 」on 17 Oct 2017 :)
 */

public class PhotoItem extends BaseItem{

    private int imageWidth = 0;
    private int imageHeight = 0;
    private String imageUrl = null;
    private String caption = null;
    private String profilePicture = null;
    private String camera = null;
    private String lens = null;
    private String focalLength = null;
    private String iso = null;
    private String shutterSpeed = null;
    private String aperture = null;

    public PhotoItem(){
        super( PhotoListAdapter.TYPE_PICTURE );
    }

    @Override
    public boolean isItemTheSame( Object baseItem ){
        if( baseItem instanceof PhotoItem ){
            return getId() == ( (PhotoItem) baseItem ).getId();
        }
        return false;
    }

    @Override
    public boolean isContentTheSame( Object baseItem ){
        return equals( baseItem );
    }


    public PhotoItem setImageWidth( int imageWidth ){
        this.imageWidth = imageWidth;
        return this;
    }

    public PhotoItem setImageHeight( int imageHeight ){
        this.imageHeight = imageHeight;
        return this;
    }

    public PhotoItem setId( int id ){
        super.setId( id );
        return this;
    }

    public PhotoItem setImageUrl( String imageUrl ){
        this.imageUrl = imageUrl;
        return this;
    }

    public PhotoItem setCaption( String caption ){
        this.caption = caption;
        return this;
    }

    public PhotoItem setProfilePicture( String profilePicture ){
        this.profilePicture = profilePicture;
        return this;
    }

    public PhotoItem setCamera( String camera ){
        this.camera = camera;
        return this;
    }

    public PhotoItem setLens( String lens ){
        this.lens = lens;
        return this;
    }

    public PhotoItem setFocalLength( String focalLength ){
        this.focalLength = focalLength;
        return this;
    }

    public PhotoItem setIso( String iso ){
        this.iso = iso;
        return this;
    }

    public PhotoItem setShutterSpeed( String shutterSpeed ){
        this.shutterSpeed = shutterSpeed;
        return this;
    }

    public PhotoItem setAperture( String aperture ){
        this.aperture = aperture;
        return this;
    }

    public boolean isSetImageSize(){
       return imageWidth != 0 && imageHeight != 0;
    }

    public int getImageWidth(){
        return imageWidth;
    }

    public int getImageHeight(){
        return imageHeight;
    }

    public String getImageUrl(){
        return imageUrl;
    }

    public String getCaption(){
        return caption;
    }

    public String getProfilePicture(){
        return profilePicture;
    }

    public String getCamera(){
        return camera;
    }

    public String getLens(){
        return lens;
    }

    public String getFocalLength(){
        return focalLength;
    }

    public String getIso(){
        return iso;
    }

    public String getShutterSpeed(){
        return shutterSpeed;
    }

    public String getAperture(){
        return aperture;
    }

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel( Parcel dest, int flags ){
        super.writeToParcel( dest, flags );
        dest.writeInt( this.imageWidth );
        dest.writeInt( this.imageHeight );
        dest.writeString( this.imageUrl );
        dest.writeString( this.caption );
        dest.writeString( this.profilePicture );
        dest.writeString( this.camera );
        dest.writeString( this.lens );
        dest.writeString( this.focalLength );
        dest.writeString( this.iso );
        dest.writeString( this.shutterSpeed );
        dest.writeString( this.aperture );
    }

    protected PhotoItem( Parcel in ){
        super( in );
        this.imageWidth = in.readInt();
        this.imageHeight = in.readInt();
        this.imageUrl = in.readString();
        this.caption = in.readString();
        this.profilePicture = in.readString();
        this.camera = in.readString();
        this.lens = in.readString();
        this.focalLength = in.readString();
        this.iso = in.readString();
        this.shutterSpeed = in.readString();
        this.aperture = in.readString();
    }

    public static final Creator<PhotoItem> CREATOR = new Creator<PhotoItem>(){
        @Override
        public PhotoItem createFromParcel( Parcel source ){
            return new PhotoItem( source );
        }

        @Override
        public PhotoItem[] newArray( int size ){
            return new PhotoItem[size];
        }
    };

    @Override
    public boolean equals( Object o ){
        if( this == o ) return true;
        if( !( o instanceof PhotoItem ) ) return false;

        PhotoItem photoItem = (PhotoItem) o;

        if( imageWidth != photoItem.imageWidth ) return false;
        if( imageHeight != photoItem.imageHeight ) return false;
        if( imageUrl != null ? !imageUrl.equals( photoItem.imageUrl ) : photoItem.imageUrl != null )
            return false;
        if( caption != null ? !caption.equals( photoItem.caption ) : photoItem.caption != null )
            return false;
        if( profilePicture != null ? !profilePicture.equals( photoItem.profilePicture ) : photoItem.profilePicture != null )
            return false;
        if( camera != null ? !camera.equals( photoItem.camera ) : photoItem.camera != null )
            return false;
        if( lens != null ? !lens.equals( photoItem.lens ) : photoItem.lens != null ) return false;
        if( focalLength != null ? !focalLength.equals( photoItem.focalLength ) : photoItem.focalLength != null )
            return false;
        if( iso != null ? !iso.equals( photoItem.iso ) : photoItem.iso != null ) return false;
        if( shutterSpeed != null ? !shutterSpeed.equals( photoItem.shutterSpeed ) : photoItem.shutterSpeed != null )
            return false;
        return aperture != null ? aperture.equals( photoItem.aperture ) : photoItem.aperture == null;
    }

    @Override
    public int hashCode(){
        int result = imageWidth;
        result = 31 * result + imageHeight;
        result = 31 * result + ( imageUrl != null ? imageUrl.hashCode() : 0 );
        result = 31 * result + ( caption != null ? caption.hashCode() : 0 );
        result = 31 * result + ( profilePicture != null ? profilePicture.hashCode() : 0 );
        result = 31 * result + ( camera != null ? camera.hashCode() : 0 );
        result = 31 * result + ( lens != null ? lens.hashCode() : 0 );
        result = 31 * result + ( focalLength != null ? focalLength.hashCode() : 0 );
        result = 31 * result + ( iso != null ? iso.hashCode() : 0 );
        result = 31 * result + ( shutterSpeed != null ? shutterSpeed.hashCode() : 0 );
        result = 31 * result + ( aperture != null ? aperture.hashCode() : 0 );
        return result;
    }
}
