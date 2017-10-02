package com.nextzy.library.base.utils;

import android.graphics.Point;

import java.util.regex.Pattern;

/**
 * Created by thekhaeng on 3/12/2017 AD.
 */

public class FileUtility{
    private static Pattern REGEX_BOTTLE_IMG_NAME = Pattern.compile( "^.+_[0-9]+x[0-9]+dp" );

    public static float calculateRatioFromFileName( String name ){
        if( REGEX_BOTTLE_IMG_NAME.matcher( name ).matches() ){
            String[] firstSplit = name.split( "_" );
            String[] strSplit = firstSplit[firstSplit.length-1].split( "x" );
            Point point = new Point();
            point.x = Integer.parseInt( strSplit[0].replaceAll( "[^0-9]", "" ).trim() );
            point.y = Integer.parseInt( strSplit[1].replaceAll( "[^0-9]", "" ).trim() );

            float ratio = 0f;
            if( point.x > point.y ){
                ratio = (float) point.x / point.y;
            }else{
                ratio = (float) point.y / point.x;
            }
            return ( (int) ( ratio * 100 ) ) / 100f;
        }
        return 0.0f;

    }
}
