package com.nextzy.library.base.utils.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import th.co.thekhaeng.waterlibrary.R;

/**
 * Created by TheKhaeng.
 */
public class LoadingDialog extends DialogFragment{

    public static LoadingDialog newInstance() {
        return new LoadingDialog();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().setCanceledOnTouchOutside(false);
        setCancelable(false);
        return inflater.inflate( R.layout.dialog_loading, container);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindView(view);
        setupView();
    }

    private void bindView(View view) {
    }

    private void setupView() {
    }

    public static class Builder {

        public Builder() {
        }

        public LoadingDialog build() {
            return LoadingDialog.newInstance();
        }
    }
}
