package com.nextzy.library.base.utils.android;

import android.support.annotation.NonNull;

/**
 * Created by thekhaeng on 5/6/2017 AD.
 */

public class ShareUtility {

    private static ShareUtility instance;

    public static ShareUtility getInstance(){
        if( instance == null ){
            instance = new ShareUtility();
        }
        return instance;
    }

//    public void share(Activity activity, Images images){
//        File file = null;
//        try {
//            file = Glide.with(activity)
//                    .load("https://lh4.googleusercontent.com/-dqDPYup6e6w/VLlh3vI0zlI/AAAAAAAACuE/kbQ1Gxy6Kv0/s600/Screen+Shot+2015-01-16+at+11.08.44+AM.png")
//                    .downloadOnly((int) 100, (int) 100)
//                    .get();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
//
//
//        if (file == null) { return; }
//        // glide cache uses an unfriendly & extension-less name,
//        // massage it based on the original
//        String fileName = images.best();
//        fileName = fileName.substring(fileName.lastIndexOf('/') + 1);
//        File renamed = new File(file.getParent(), fileName);
//        file.renameTo(renamed);
//        Uri uri = FileProvider.getUriForFile(activity, BuildConfig.FILES_AUTHORITY, renamed);
//        ShareCompat.IntentBuilder.from(activity)
//                .setText("test")
//                .setType(getImageMimeType(fileName))
//                .setSubject("test")
//                .setStream(uri)
//                .startChooser();
//    }

    private String getImageMimeType(@NonNull String fileName) {
        if (fileName.endsWith(".png")) {
            return "image/png";
        } else if (fileName.endsWith(".gif")) {
            return "image/gif";
        }
        return "image/jpeg";
    }
}
