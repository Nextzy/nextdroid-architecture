package com.nextzy.nextwork.operator;

import android.util.Log;

import com.google.gson.Gson;
import com.nextzy.nextwork.BuildConfig;
import com.nextzy.nextwork.exception.NoInternetConnectionException;
import com.nextzy.nextwork.exception.ServerNotFoundException;
import com.nextzy.nextwork.exception.StatusCodeException;
import com.nextzy.nextwork.reponse.NextworkResponse;

import org.json.JSONException;

import java.io.IOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.SingleTransformer;
import io.reactivex.functions.Function;
import retrofit2.HttpException;

/**
 * Created by「 The Khaeng 」on 02 Oct 2017 :)
 */
public abstract class NextworkErrorJsonHandling<T extends NextworkResponse> implements SingleTransformer<T, T>{
    private final static String TAG = NextworkErrorJsonHandling.class.getSimpleName();

    private Class<T> typeOfResultClass;
    private String serviceName;

    public NextworkErrorJsonHandling( Class<T> typeOfResultClass, String serviceName ){
        this.typeOfResultClass = typeOfResultClass;
        this.serviceName = serviceName;
    }

    @Override
    public Single<T> apply( Single<T> upstream ){
        return upstream
                .onErrorResumeNext( new Function<Throwable, SingleSource<? extends T>>(){
                    @Override
                    public SingleSource<? extends T> apply( Throwable throwable ) throws Exception{
                        return NextworkErrorJsonHandling.this.handleHttpExceptionAndConvertToErrorJson( throwable, typeOfResultClass );
                    }
                } )
                .flatMap( new Function<T, SingleSource<? extends T>>(){
                    @Override
                    public SingleSource<? extends T> apply( T t ) throws Exception{
                        return NextworkErrorJsonHandling.this.checkJsonResponse( t );
                    }
                } );
    }

    /* =========================== Private method =============================================== */
    private Single<T> handleHttpExceptionAndConvertToErrorJson( Throwable throwable, Class<T> type ){
        if( throwable instanceof UnknownHostException || throwable instanceof ConnectException ){
            printLog( throwable.getClass().getName() );
            return Single.error( new NoInternetConnectionException( throwable.getMessage() ) );
        }
        if( !( throwable instanceof HttpException ) ){
            printLog( throwable.getClass().getName() );
            return Single.error( throwable );
        }

        return handleErrorCode( (HttpException) throwable, type );
    }


    private Single<T> handleErrorCode( HttpException httpException, Class<T> type ){

        if( httpException.response() == null || httpException.response().errorBody() == null ){
            printLog( httpException.getClass().getName() );
            return Single.error( new NullPointerException( "Null response() or response().errorBody()" ) );
        }

        if( httpException.response().code() == 404 ){
            // TODO: 8/2/2016 response().errorBody() cached the previous repeated request
            // if we don't do like this then httpException
            // may get response from its cache and successfully convert to Json
            // cannot send out other object, too.

            printLog( httpException.getClass().getName() );
            return Single.error( new ServerNotFoundException( 404 ) );
        }

        List<StatusCodeException> errorCodeList = getStatusCodeException();
        if( errorCodeList != null ){
            for( StatusCodeException statusCodeException : errorCodeList ){
                int errorCode = statusCodeException.getStatusCode();
                if( httpException.response().code() == errorCode ){
                    printLog( httpException.getClass().getName() );
                    return Single.error( statusCodeException );
                }
            }
        }
        return convertResponseErrorBody( httpException, type );
    }

    private Single<T> convertResponseErrorBody( HttpException httpException, Class<T> type ){
        String jsonBody;
        try{
            jsonBody = httpException.response().errorBody().string();
        }catch( IOException e ){
            printLog( e.getClass().getName() );
            return Single.error( e );
        }

        if( jsonBody.isEmpty() ){
            printLog( NullPointerException.class.getName() );
            return Single.error( new NullPointerException( "Empty Error JSON Body" ) );
        }

        if( !isJson( jsonBody ) ){
            printLog( JSONException.class.getName() );
            return Single.error( new JSONException( "Body is not a Error JSON type " + printInValidJson( jsonBody ) ) );
        }

        return Single.just( new Gson().fromJson( jsonBody, type ) );
    }


    private void printLog( String errorName ){
        if( BuildConfig.DEBUG ){
            Log.e( TAG, String.format( "\n%s\nservice:\t %s", errorName, serviceName ) );
        }
    }


    private boolean isJson( String body ){
        if( !body.startsWith( "{" ) ) return false;
        try{
            new Gson().fromJson( body, Object.class );
            return true;
        }catch( com.google.gson.JsonSyntaxException ignored ){
        }
        return false;
    }

    /* =========================== Abstract method ============================================== */
    protected String printInValidJson( String jsonBody ){
        return jsonBody;
    }

    protected abstract Single<T> checkJsonResponse( T t );

    protected abstract List<StatusCodeException> getStatusCodeException();
}
