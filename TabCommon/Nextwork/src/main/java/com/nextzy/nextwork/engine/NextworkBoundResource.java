
package com.nextzy.nextwork.engine;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;

import com.nextzy.nextwork.engine.model.NextworkResource;
import com.nextzy.nextwork.engine.model.NextworkResponse;
import com.nextzy.nextwork.operator.NLog;


/**
 * A generic class that can provide a resource backed by both the sqlite database and the network.
 * <p>
 * You can read more about it in the <a href="https://developer.android.com/arch">Architecture
 * Guide</a>.
 *
 * @param <ResultType>
 * @param <RequestType>
 */
public abstract class NextworkBoundResource<ResultType, RequestType, ResponseApiType extends NextworkResponse<RequestType>, ResourceType extends NextworkResource<ResultType>>{
    private final static String TAG = NextworkBoundResource.class.getSimpleName();
    private final AppExecutors appExecutors;
    private final NextworkResourceCreator<ResultType, ResourceType> creator;

    private final MediatorLiveData<ResourceType> result = new MediatorLiveData<>();

    @MainThread
    public NextworkBoundResource( AppExecutors appExecutors,
                                  NextworkResourceCreator<ResultType, ResourceType> creator ){
        this.appExecutors = appExecutors;
        this.creator = creator;
        result.setValue( creator.<ResultType>loading( null ) );
        LiveData<ResultType> dbSource = loadFromDb();
        NLog.i( TAG, "Load from database: " + dbSource );
        result.addSource( dbSource, data -> {
            result.removeSource( dbSource );
            boolean shouldFetch = shouldFetch( data );
            NLog.i( TAG, "ShouldFetch: " + shouldFetch );
            if( shouldFetch ){
                fetchFromNetwork( dbSource );
            }else{
                result.addSource( dbSource, newData -> result.setValue( creator.success( newData ) ) );
            }
        } );
    }

    private void fetchFromNetwork( final LiveData<ResultType> dbSource ){
        LiveData<ResponseApiType> apiResponse = createCall();
        NLog.i( TAG, "CreateCall: " + apiResponse );
        // we re-attach dbSource as a new source, it will dispatch its latest value quickly
        result.addSource( dbSource, newData -> result.setValue( creator.loading( newData ) ) );
        result.addSource( apiResponse, response -> {
            result.removeSource( apiResponse );
            result.removeSource( dbSource );
            //noinspection ConstantConditions
            if( response.isSuccessful() ){
                appExecutors.diskIO().execute( () -> {
                    ResultType data = convertToResultType( processResponse( response ) );
                    saveCallResult( data );
                    NLog.i( TAG, "Save call result: " + data );
                    appExecutors.mainThread().execute( () -> {
                                // we specially request a new live data,
                                // otherwise we will get immediately last cached value,
                                // which may not be updated with latest results received from network.
                                LiveData<ResultType> dataSource = loadFromDb();
                                result.addSource( dataSource,
                                        newData -> result.setValue( creator.success( newData ) ) );
                                NLog.i( TAG, "Load from database: " + dataSource );
                            }
                    );
                } );
            }else{
                onFetchFailed();
                result.addSource( dbSource,
                        newData -> result.setValue( creator.error( response.error, newData ) ) );
            }
        } );
    }

    protected void onFetchFailed(){
        NLog.e( TAG, "Load from database: onFetchFailed()" );
    }

    public LiveData<ResourceType> asLiveData(){
        return result;
    }

    @WorkerThread
    protected RequestType processResponse( ResponseApiType response ){
        return response.body;
    }

    @WorkerThread
    protected abstract void saveCallResult( @NonNull ResultType item );

    @MainThread
    protected abstract boolean shouldFetch( @Nullable ResultType data );

    @NonNull
    @MainThread
    protected abstract LiveData<ResultType> loadFromDb();

    @NonNull
    @MainThread
    protected abstract LiveData<ResponseApiType> createCall();


    public abstract ResultType convertToResultType( RequestType requestType );

}
