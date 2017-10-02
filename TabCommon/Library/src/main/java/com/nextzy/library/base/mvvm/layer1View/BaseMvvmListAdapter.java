package com.nextzy.library.base.mvvm.layer1View;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.nextzy.library.base.mvvm.exception.ViewModelNotNullException;
import com.nextzy.library.base.mvvm.exception.ViewModelNotSetupException;
import com.nextzy.library.base.mvvm.layer2ViewModel.BaseViewModel;
import com.nextzy.library.base.view.holder.base.BaseViewHolder;

import java.lang.ref.WeakReference;


/**
 * Created by「 The Khaeng 」on 20 Sep 2017 :)
 */

public abstract class BaseMvvmListAdapter<VH extends BaseViewHolder, VM extends BaseViewModel>
        extends RecyclerView.Adapter<VH>{

    protected final Context context;
    protected WeakReference<FragmentActivity> activity;
    protected WeakReference<Fragment> fragment;
    protected OnClickHolderItemListener<VH> listener;

    public interface OnClickHolderItemListener<VH>{
        void onClickHolder( VH v, Object item, int position );
    }

    private RecyclerView recyclerView;

    public BaseMvvmListAdapter( FragmentActivity activity ){
        this.context = activity;
        this.activity = new WeakReference<>( activity );
    }

    public BaseMvvmListAdapter( Fragment fragment ){
        this.context = fragment.getContext();
        this.fragment = new WeakReference<>( fragment );
    }


    public void setOnClickHolderItemListener( OnClickHolderItemListener<VH> listener ){
        this.listener = listener;
    }


    @Override
    public void onAttachedToRecyclerView( RecyclerView recyclerView ){
        super.onAttachedToRecyclerView( recyclerView );
        this.recyclerView = recyclerView;
    }

    public RecyclerView getRecyclerView(){
        return recyclerView;
    }

    public VM getViewModelShared(){
        if( setupViewModel() == null ) throw new ViewModelNotSetupException();
        if( fragment != null ){
            return ViewModelProviders.of( fragment.get().getActivity() )
                    .get( setupViewModel() );
        }else if( activity != null ){
            return ViewModelProviders.of( activity.get() )
                    .get( setupViewModel() );
        }
        return null;
    }

    public VM getViewModel(){
        if( setupViewModel() == null ) throw new ViewModelNotSetupException();
        if( fragment != null ){
            return ViewModelProviders.of( fragment.get() )
                    .get( setupViewModel() );
        }else if( activity != null ){
            return ViewModelProviders.of( activity.get() )
                    .get( setupViewModel() );
        }
        return null;
    }

    public VM getViewModel( Class<VM> viewModelClass ){
        if( setupViewModel() == null ) throw new ViewModelNotNullException();
        if( fragment != null ){
            return ViewModelProviders.of( fragment.get() )
                    .get( viewModelClass );
        }else if( activity != null ){
            return ViewModelProviders.of( activity.get() )
                    .get( viewModelClass );
        }
        return null;
    }

    public abstract Class<VM> setupViewModel();


    @Override
    public VH onCreateViewHolder( ViewGroup parent, int viewType ){
        return null;
    }

    @Override
    public void onBindViewHolder( VH holder, int position ){
        if( listener != null ){
            holder.itemView.getRootView().setOnClickListener( setOnClickItem( holder ) );
        }
    }


    @NonNull
    private View.OnClickListener setOnClickItem( final VH holder ){
        return v -> listener.onClickHolder(
                holder,
                holder.getItem(),
                holder.getAdapterPosition()
        );
    }

}
