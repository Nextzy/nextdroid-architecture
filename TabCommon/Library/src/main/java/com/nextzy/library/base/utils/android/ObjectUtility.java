package com.nextzy.library.base.utils.android;

import java.util.Collection;

/**
 * Created by thekhaeng on 5/4/2017 AD.
 */

public class ObjectUtility {

    public static boolean isNotEmptyString(String string){
        return string != null && !"".equals(string.trim());
    }

    public static boolean isEmptyString(String string){
        return !isNotEmptyString(string);
    }

    public static boolean isNotEmptyList(Collection collection){
        return collection != null && collection.size() != 0;
    }

    public static boolean isEmptyList(Collection collection){
        return !isNotEmptyList(collection);
    }
}
