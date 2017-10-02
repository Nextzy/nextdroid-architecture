package com.nextzy.library.base.mvvm.layer1View;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;

import com.nextzy.library.base.mvvm.layer2ViewModel.BaseListAdapterViewModel;
import com.nextzy.library.base.view.holder.base.BaseViewHolder;

import java.util.List;


/**
 * Created by「 The Khaeng 」on 20 Aug 2017 :)
 */

public abstract class BaseMvvmIndividualListAdapter<VH extends BaseViewHolder, VM extends BaseListAdapterViewModel>
        extends BaseMvvmListAdapter<VH,VM>{


    private RecyclerView recyclerView;

    public BaseMvvmIndividualListAdapter( FragmentActivity activity ){
        super(activity);
    }

    public BaseMvvmIndividualListAdapter( Fragment fragment ){
        super(fragment);
    }

    public abstract boolean setSharedViewModel();

    /* =================================== Read Item =============================================*/
    public List<Object> getItemList(){
        return getPrivateViewModel().getItemList();
    }

    public Object getItem( int pos ){
        return getPrivateViewModel().getItem( pos );
    }

    public boolean hasItems(){
        return !getPrivateViewModel().isEmpty();
    }

    /* =================================== Update Item ===========================================*/
    public void setItemList( List<Object> itemList ){
        setItemList( itemList, true );
    }

    public void setItemList( List<Object> itemList, boolean isNotify ){
        getPrivateViewModel().setItemList(itemList);
        if( isNotify ){
            notifyDataSetChanged();
        }
    }

    public void addItem( Object item ){
        addItem( item, true );
    }

    public void addItem( Object item, boolean isNotify ){
        getPrivateViewModel().addItem( item );
        if( isNotify ){
            notifyItemInserted( getItemCount() - 1 );
        }
    }

    public void addItem( int index, Object item ){
        addItem( index, item, true );
    }

    public void addItem( int index, Object item, boolean isNotify ){
        getPrivateViewModel().addItem( index, item );
        if( isNotify ){
            notifyItemInserted( index );
        }
    }

    public void addAllItems( List<Object> items ){
        addAllItems( items, true );
    }

    public void addAllItems( List<Object> items, boolean isNotify ){
        getPrivateViewModel().addAllItems( items );
        if( isNotify ){
            notifyItemRangeInserted( getItemCount() - 1, items.size() );
        }
    }

    public void addAllItems( int index, List<Object> items ){
        addAllItems( index, items, true );
    }

    public void addAllItems( int index, List<Object> items, boolean isNotify ){
        getPrivateViewModel().addAllItems( index, items );
        if( isNotify ){
            notifyItemRangeInserted( index, items.size() );
        }
    }

    public void updateItem( int index, Object item ){
        updateItem( index, item, true );
    }

    public void updateItem( int index, Object item, boolean isNotify ){
        getPrivateViewModel().updateItem( index, item );
        if( isNotify ){
            notifyItemChanged( index );
        }
    }

    /* =================================== Delete Item ===========================================*/
    public void removeAllItem(){
        removeAllItem( true );
    }

    public void removeAllItem( boolean isNotify ){
        getPrivateViewModel().removeAllItem();
        if( isNotify ){
            notifyDataSetChanged();
        }
    }

    public void removeItemAt( int index ){
        removeItemAt( index, true );
    }

    public void removeItemAt( int index, boolean isNotify ){
        getPrivateViewModel().removeItemAt(index);
        if( isNotify ){
            notifyItemRemoved( index );
        }
    }

    public int removeItem( Object baseItem ){
        return removeItem( baseItem, true );
    }

    public int removeItem( Object baseItem, boolean isNotify ){
        int removeIndex = -1;
        for( int i = 0; i < getItemCount(); i++ ){
            if( getItem( i ).equals( baseItem ) ){
                removeIndex = i;
                break;
            }
        }
        if( removeIndex != -1 ){
            getPrivateViewModel().removeItem( baseItem );
        }
        if( isNotify && removeIndex != -1 ){
            notifyItemRemoved( removeIndex );
        }
        return removeIndex;
    }

    @Override
    public void onBindViewHolder( VH holder, int position ){
        super.onBindViewHolder( holder,position );
        holder.setItem( getItem( holder.getAdapterPosition() ) );
    }

    private VM getPrivateViewModel(){
        if( setSharedViewModel() ){
            return getViewModelShared();
        }else{
            return getViewModel();
        }
    }

}
