package com.nextzy.tabcustomize.base.repository.network;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.SingleTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by「 The Khaeng 」on 12 Oct 2017 :)
 */

@SuppressWarnings( "unchecked" )
public class ConvertToDefaultResponse< FROM extends Response, TO extends DefaultResponse>
        implements SingleTransformer<FROM, TO>{

    @Override
    public SingleSource<TO> apply( Single<FROM> upstream ){
        return upstream
                .subscribeOn( Schedulers.io() )
                .map( new Function<FROM, TO>(){
                    @Override
                    public TO apply( FROM from ) throws Exception{
                        return (TO) new DefaultResponse( from );
                    }
                } )
                .onErrorResumeNext( new Function<Throwable, SingleSource<TO>>(){
                    @Override
                    public SingleSource<TO> apply( Throwable throwable ) throws Exception{
                        return (SingleSource<TO>) Single.just(new DefaultResponse(throwable) );
                    }
                } )
                .observeOn( AndroidSchedulers.mainThread() );
    }
}


