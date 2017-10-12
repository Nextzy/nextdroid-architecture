
package com.nextzy.nextwork.engine;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;

import com.nextzy.nextwork.engine.model.NextworkResponse;
import com.nextzy.nextwork.engine.model.Resource;


/**
 * A generic class that can provide a resource backed by both the sqlite database and the network.
 * <p>
 * You can read more about it in the <a href="https://developer.android.com/arch">Architecture
 * Guide</a>.
 *
 * @param <ResultType>
 * @param <RequestType>
 */
public abstract class NextworkBoundResource<ResultType, RequestType>{
    private final AppExecutors appExecutors;

    private final MediatorLiveData<Resource<ResultType>> result = new MediatorLiveData<>();

    @MainThread
    public NextworkBoundResource( AppExecutors appExecutors ){
        this.appExecutors = appExecutors;
        result.setValue( Resource.<ResultType>loading( null ) );
        LiveData<ResultType> dbSource = loadFromDb();
        result.addSource( dbSource, data -> {
            result.removeSource( dbSource );
            if( shouldFetch( data ) ){
                fetchFromNetwork( dbSource );
            }else{
                result.addSource( dbSource, newData -> result.setValue( Resource.success( newData ) ) );
            }
        } );
    }

    private void fetchFromNetwork( final LiveData<ResultType> dbSource ){
        LiveData<NextworkResponse<RequestType>> apiResponse = createCall();
        // we re-attach dbSource as a new source, it will dispatch its latest value quickly
        result.addSource( dbSource, newData -> result.setValue( Resource.loading( newData ) ) );
        result.addSource( apiResponse, response -> {
            result.removeSource( apiResponse );
            result.removeSource( dbSource );
            //noinspection ConstantConditions
            if( response.isSuccessful() ){
                appExecutors.diskIO().execute( () -> {
                    saveCallResult( convertToResultType( processResponse( response ) ) );
                    appExecutors.mainThread().execute( () ->
                            // we specially request a new live data,
                            // otherwise we will get immediately last cached value,
                            // which may not be updated with latest results received from network.
                            result.addSource( loadFromDb(),
                                    newData -> result.setValue( Resource.success( newData ) ) )
                    );
                } );
            }else{
                onFetchFailed();
                result.addSource( dbSource,
                        newData -> result.setValue( Resource.error( response.errorMessage, newData ) ) );
            }
        } );
    }

    protected void onFetchFailed(){
    }

    public LiveData<Resource<ResultType>> asLiveData(){
        return result;
    }

    @WorkerThread
    protected RequestType processResponse( NextworkResponse<RequestType> response ){
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
    protected abstract LiveData<NextworkResponse<RequestType>> createCall();


    public abstract ResultType convertToResultType( RequestType requestType );

}
