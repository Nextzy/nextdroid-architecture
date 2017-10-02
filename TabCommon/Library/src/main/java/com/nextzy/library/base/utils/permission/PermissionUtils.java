package com.nextzy.library.base.utils.permission;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thekhaeng on 2/11/2017 AD.
 */

public class PermissionUtils{

    public static boolean isGrantPermission( Context context, String permission ){
        int res = context.checkCallingOrSelfPermission( permission );
        return res == PackageManager.PERMISSION_GRANTED;
    }

    public static void requestPermission( Activity activity, final PermissionCallback callback, String... permissions ){
        try{
            Dexter.withActivity( activity )
                    .withPermissions( permissions )
                    .withListener( new MultiplePermissionsListener(){
                        @Override
                        public void onPermissionsChecked( MultiplePermissionsReport report ){
                            if( callback != null ){
                                List<PermissionResult.Permission> permissionList = new ArrayList<>();
                                permissionList.addAll( getGrantedPermissionList( report.getGrantedPermissionResponses() ) );
                                permissionList.addAll( getDeniedPermissionList( report.getDeniedPermissionResponses() ) );
                                PermissionResult permissionResult = new PermissionResult( permissionList );
                                callback.onPermissionResult( permissionResult );
                            }
                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown( List<com.karumi.dexter.listener.PermissionRequest> permissions,
                                                                        PermissionToken token ){
                            token.continuePermissionRequest();
                        }
                    } ).check();
        }catch( NullPointerException exception ){
            throw new NullPointerException( "Application class should be extend from NextzyApplication class before use NextzyPermission class." );
        }
    }

    private static List<PermissionResult.Permission> getGrantedPermissionList( List<PermissionGrantedResponse> permissionGrantedResponseList ){
        List<PermissionResult.Permission> permissionList = new ArrayList<>();
        if( permissionGrantedResponseList != null && permissionGrantedResponseList.size() > 0 ){
            for( PermissionGrantedResponse permissionGrantedResponse : permissionGrantedResponseList ){
                String permissionName = permissionGrantedResponse.getPermissionName();
                PermissionResult.Permission permission = new PermissionResult.Permission( permissionName, true );
                permissionList.add( permission );
            }
        }
        return permissionList;
    }

    private static List<PermissionResult.Permission> getDeniedPermissionList( List<PermissionDeniedResponse> permissionDeniedResponseList ){
        List<PermissionResult.Permission> permissionList = new ArrayList<>();
        if( permissionDeniedResponseList != null && permissionDeniedResponseList.size() > 0 ){
            for( PermissionDeniedResponse permissionDeniedResponse : permissionDeniedResponseList ){
                String permissionName = permissionDeniedResponse.getPermissionName();
                PermissionResult.Permission permission = new PermissionResult.Permission( permissionName, false );
                permissionList.add( permission );
            }
        }
        return permissionList;
    }

    public interface PermissionCallback{
        void onPermissionResult( PermissionResult permissionResult );
    }
}
