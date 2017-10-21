package com.nextzy.nextwork.engine;

import android.arch.core.util.Function;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.Transformations;
import android.support.annotation.Nullable;

/**
 * Created by「 The Khaeng 」on 16 Oct 2017 :)
 */

public class TriggerLiveData<TRIGGER, RESULT> extends LiveData<RESULT>{

    MutableLiveData<TRIGGER> trickLiveData = new MutableLiveData<>();

    LiveData<RESULT> liveData;


    public TRIGGER getTriggerValue(){
        return trickLiveData.getValue();
    }


    public void reset(){
        trickLiveData.setValue( null );
    }


    public void trigger( TRIGGER trigger ){
        reset();
        if( getTriggerValue() != null && isShouldBeSkip( getTriggerValue(), trigger ) ) return;

        trickLiveData.postValue( trigger );
    }


    public TriggerLiveData<TRIGGER, RESULT> createSwitchMap( Function<TRIGGER, LiveData<RESULT>> func ){
        liveData = Transformations.switchMap(
                trickLiveData,
                trigger -> {
                    if( trigger == null ){
                        return AbsentLiveData.create();
                    }else{
                        return func.apply( trigger );
                    }
                }
        );
        return this;
    }

    @Override
    public void observe( LifecycleOwner owner, Observer<RESULT> observer ){
        if( liveData != null ){
            liveData.observe( owner, observer );
        }
    }

    @Nullable
    @Override
    public RESULT getValue(){
        if( liveData == null ) return null;
        return liveData.getValue();
    }

    protected boolean isShouldBeSkip( TRIGGER newTrigger, TRIGGER oldTrigger ){
        return equals( newTrigger, oldTrigger );
    }

    boolean equals( Object a, Object b ){
        return ( a == b ) || ( a != null && a.equals( b ) );
    }


}
