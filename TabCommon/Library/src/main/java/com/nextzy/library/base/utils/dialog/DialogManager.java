package com.nextzy.library.base.utils.dialog;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;

/**
 * Created by thekhaeng on 2/13/2017 AD.
 */

public class DialogManager{
    private final static String TAG = DialogManager.class.getSimpleName();

    private static DialogManager aisDialog;

    public static DialogManager getInstance() {
        if (aisDialog == null) {
            aisDialog = new DialogManager();
        }
        return aisDialog;
    }

    private DialogFragment dialog;

    public void showLoadingDialog(FragmentManager fragmentManager) {
        dismissDialog();
        dialog = new LoadingDialog.Builder()
                .build();
        dialog.show(fragmentManager, TAG);
    }
    public void dismissDialog() {
        if (dialog != null && dialog.isAdded()) {
            dialog.dismiss();
            dialog = null;
        }
    }
}
