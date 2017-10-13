package com.nextzy.nextwork.engine.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
public abstract class NextworkResource<T> {

    @NonNull
    private final @Status int status;

    @Nullable
    private final String message;

    @Nullable
    private final T data;

    public NextworkResource( @NonNull @Status int status, @Nullable T data, @Nullable String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    @NonNull
    public int getStatus(){
        return status;
    }

    @Nullable
    public String getMessage(){
        return message;
    }

    @Nullable
    public T getData(){
        return data;
    }

    @Override
    public String toString() {
        return "NextworkResource{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    @Override
    public boolean equals( Object o ){
        if( this == o ) return true;
        if( !( o instanceof NextworkResource ) ) return false;

        NextworkResource<?> resource = (NextworkResource<?>) o;

        if( status != resource.status ) return false;
        if( message != null ? !message.equals( resource.message ) : resource.message != null )
            return false;
        return data != null ? data.equals( resource.data ) : resource.data == null;
    }

    @Override
    public int hashCode(){
        int result = status;
        result = 31 * result + ( message != null ? message.hashCode() : 0 );
        result = 31 * result + ( data != null ? data.hashCode() : 0 );
        return result;
    }
}
