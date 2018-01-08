package com.nextzy.nextwork.engine;

import android.util.Log;

import com.google.gson.GsonBuilder;
import com.nextzy.nextwork.BuildConfig;
import com.nextzy.nextwork.cookie.NextworkWebkitCookieJar;
import com.nextzy.nextwork.interceptor.DefaultHttpLoggerInterceptor;
import com.nextzy.nextwork.interceptor.NextworkInterceptor;

import java.io.IOException;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Authenticator;
import okhttp3.Cache;
import okhttp3.CertificatePinner;
import okhttp3.CookieJar;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

/**
 * Created by「 The Khaeng 」on 02 Oct 2017 :)
 */

public abstract class NextworkApiCreator<T>{
    private static final String TAG = NextworkApiCreator.class.getSimpleName();
    private static final int NO_OVERRIDE = -1;

    private Class<T> apiClass;
    private T mockApi;

    public NextworkApiCreator( Class<T> apiClass ){
        this.apiClass = apiClass;
    }

    protected boolean isLogger(){
        return BuildConfig.DEBUG;
    }

    protected boolean isUseCookie(){
        return false;
    }

    public void setMockApi( T mockApi ){
        this.mockApi = mockApi;
    }

    public T createApi(){
        return createApi( apiClass );
    }

    public T createApi( String baseUrl ){
        return createApi( apiClass, baseUrl );
    }

    public T createApi( Class<T> apiClass ){
        return createApi( apiClass, getBaseUrl() );
    }

    public T createApi( Class<T> apiClass, String baseUrl ){
        if( mockApi != null ) return mockApi;
        return getBaseRetrofitBuilder()
                .baseUrl( baseUrl )
                .addCallAdapterFactory( getAdapterFactory() )
                .build()
                .create( apiClass );
    }

    public T createXmlApi(){
        return createXmlApi( apiClass );
    }

    public T createXmlApi( String baseUrl ){
        return createXmlApi( apiClass, baseUrl );
    }

    public T createXmlApi( Class<T> apiClass ){
        return createXmlApi( apiClass, getBaseUrl() );
    }

    public T createXmlApi( Class<T> apiClass, String baseUrl ){
        if( mockApi != null ) return mockApi;
        return getXmlRetrofitBuilder()
                .baseUrl( baseUrl )
                .addCallAdapterFactory( RxJava2CallAdapterFactory.create() )
                .build()
                .create( apiClass );
    }


    /**
     * return "null" for not use Converter in retrofit.
     */
    protected Converter.Factory addConverter(){
        return GsonConverterFactory.create( new GsonBuilder().setPrettyPrinting().create() );
    }

    protected Interceptor getOfflineCacheInterceptor(){
        return null;
    }

    protected Cache getCache(){
        return null;
    }

    protected List<Interceptor> getAnotherInterceptor(){
        return new ArrayList<>();
    }

    protected Authenticator getDefaultProxyAuthenticator(){
        return null;
    }

    protected Proxy getDefaultProxy(){
        return null;
    }

    protected NextworkInterceptor getDefaultInterceptor(){
        return new NextworkInterceptor();
    }

    protected CookieJar getDefaultCookieJar(){
        if( isUseCookie() && !NextworkWebkitCookieJar.getInstance().isInitialized() ){
            Log.e( TAG, "********************************************************************************************************************************" );
            Log.e( TAG, "To use cookie, you need to call NextworkWebkitCookieJar.getInstance().init(getApplicationContext()); in application class." );
            Log.e( TAG, "********************************************************************************************************************************" );
        }
        if( isUseCookie() && NextworkWebkitCookieJar.getInstance().isInitialized() ){
            return NextworkWebkitCookieJar.getInstance();
        }
        return CookieJar.NO_COOKIES;
    }

    protected long getOverrideClientTimeout(){
        return NO_OVERRIDE;
    }

    protected CertificatePinner getDefaultCertificatePinner(){
        return new CertificatePinner.Builder().build();
    }

    protected HttpLoggingInterceptor getDefaultHttpLoggingInterceptor( boolean showLog ){
        return DefaultHttpLoggerInterceptor.getInterceptor( showLog );
    }

    protected Request.Builder getRequestInterceptor( Request.Builder requestBuilder ){
        return null;
    }

    /* =========================== Private method =============================================== */
    private Retrofit.Builder getBaseRetrofitBuilder(){
        if( addConverter() == null ){
            return new Retrofit.Builder()
                    .client( getOkHttpClient() );
        }
        return new Retrofit.Builder()
                .addConverterFactory( addConverter() )
                .client( getOkHttpClient() );
    }

    private Retrofit.Builder getXmlRetrofitBuilder(){
        return new Retrofit.Builder()
                .addConverterFactory( SimpleXmlConverterFactory.create() )
                .client( getOkHttpClient() );
    }

    private Interceptor getOnTopInterceptor(){
        //per client interceptor
        //requestBuilder.addHeader("key","value")
        return new Interceptor(){
            @Override
            public Response intercept( Chain chain ) throws IOException{
                Request.Builder newRequestBuilder = getRequestInterceptor( chain.request().newBuilder() );
                if( newRequestBuilder == null ){
                    return chain.proceed( chain.request() );
                }
                return chain.proceed( newRequestBuilder.build() );
            }
        };
    }

    private OkHttpClient getOkHttpClient(){
        OkHttpClient.Builder builder = getClient().newBuilder();
        if( getDefaultProxyAuthenticator() != null ){
            builder.proxyAuthenticator( getDefaultProxyAuthenticator() );
        }
        if( getDefaultProxy() != null ){
            builder.proxy( getDefaultProxy() );
        }
        if( getOfflineCacheInterceptor() != null ){
            builder.addInterceptor( getOfflineCacheInterceptor() );
        }
        if( getCache() != null ){
            builder.cache( getCache() );
        }

        if( getAnotherInterceptor() != null && getAnotherInterceptor().size() > 0 ){
            for( Interceptor interceptor : getAnotherInterceptor() ){
                builder.addInterceptor( interceptor );
            }
        }

        if( getOverrideClientTimeout() != NO_OVERRIDE ){
            builder
                    .readTimeout( getOverrideClientTimeout(), TimeUnit.MILLISECONDS )
                    .writeTimeout( getOverrideClientTimeout(), TimeUnit.MILLISECONDS )
                    .connectTimeout( getOverrideClientTimeout(), TimeUnit.MILLISECONDS );
        }

        return builder
                .addInterceptor( getDefaultInterceptor() )
                .addInterceptor( getOnTopInterceptor() )
                .addNetworkInterceptor( getDefaultHttpLoggingInterceptor( isLogger() ) )
                .certificatePinner( getDefaultCertificatePinner() )
                .cookieJar( getDefaultCookieJar() )
                .build();
    }

    /* =========================== Abstract method ============================================== */
    //every network service class must inherit this class and set the class type, too
    public abstract String getBaseUrl();

    public abstract OkHttpClient getClient();

    public CallAdapter.Factory getAdapterFactory(){
        return RxJava2CallAdapterFactory.create();
    }
}
