package com.nextzy.nextwork.engine;

import com.nextzy.nextwork.engine.model.NextworkResource;

/**
 * Created by「 The Khaeng 」on 13 Oct 2017 :)
 */

public interface NextworkResourceCreator<T,R extends NextworkResource<T>>{
   R loading( T data);

   R success( T newData );

   R error( Throwable error, T newData );
}
