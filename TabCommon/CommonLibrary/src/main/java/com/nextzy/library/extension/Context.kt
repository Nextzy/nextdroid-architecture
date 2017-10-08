package com.nextzy.library.extension

import android.content.Context
import android.graphics.Point
import android.graphics.Typeface
import android.os.Build
import android.support.annotation.DimenRes
import android.support.v4.content.ContextCompat
import android.text.TextUtils
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.*
import android.widget.Toast
import timber.log.Timber
import java.io.File
import java.util.*

/**
 * Created by「 The Khaeng 」on 03 Oct 2017 :)
 */


private val cachedFontMap = hashMapOf<String, Typeface>()

fun Context.inflate(layoutResId: Int, parent: ViewGroup? = null, attachToRoot: Boolean = true): View =
        LayoutInflater.from(this).inflate(layoutResId, parent, attachToRoot)

fun Context.getFloatDimen(@DimenRes resId: Int): Float {
    val outValue = TypedValue()
    this.resources.getValue(resId, outValue, true)
    return outValue.float
}

fun Context?.toast(message: CharSequence, duration: Int)
        = Toast.makeText(this, message, duration).show()

fun Context.getStringResByName(name: String): String {
    return this.resources.getString(this.resources.getIdentifier(
            name,
            "string",
            this.packageName))
}

fun Context.getColorResByName(name: String): Int {
    return ContextCompat.getColor(this, this.resources.getIdentifier(
            name,
            "color",
            this.packageName))
}

fun Context.findFont(fontPath: String?, defaultFontPath: String? = null): Typeface {

    if (fontPath == null) {
        return Typeface.DEFAULT
    }

    val fontName = File(fontPath).name
    var defaultFontName = ""
    if (!TextUtils.isEmpty(defaultFontPath)) {
        defaultFontName = File(defaultFontPath).name
    }

    return if (cachedFontMap.containsKey(fontName)) {
        cachedFontMap[fontName] ?: Typeface.DEFAULT
    } else {
        try {
            val assets = this.resources.assets

            if (Arrays.asList(*assets.list("")).contains(fontPath)) {
                val typeface = Typeface.createFromAsset(this.assets, fontName)
                cachedFontMap.put(fontName, typeface)
                typeface
            } else if (Arrays.asList(*assets.list("fonts")).contains(fontName)) {
                val typeface = Typeface.createFromAsset(this.assets, String.format("fonts/%s", fontName))
                cachedFontMap.put(fontName, typeface)
                typeface
            } else if (Arrays.asList(*assets.list("iconfonts")).contains(fontName)) {
                val typeface = Typeface.createFromAsset(this.assets, String.format("iconfonts/%s", fontName))
                cachedFontMap.put(fontName, typeface)
                typeface
            } else if (!TextUtils.isEmpty(defaultFontPath) && Arrays.asList(*assets.list("")).contains(defaultFontPath)) {
                val typeface = Typeface.createFromAsset(this.assets, defaultFontPath)
                cachedFontMap.put(defaultFontName, typeface)
                typeface
            } else {
                throw Exception("Font not Found")
            }

        } catch (e: Exception) {
            Timber.e(String.format("Unable to find %s font. Using Typeface.DEFAULT instead.", fontName))
            cachedFontMap.put(fontName, Typeface.DEFAULT)
            Typeface.DEFAULT
        }

    }
}

val Context?.deviceScreenSize: Point
    get() {
        var resolutionX = 0
        var resolutionY = 0
        val wm = this?.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = wm.defaultDisplay
        val dm = DisplayMetrics()
        wm.defaultDisplay.getMetrics(dm)

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            try {
                val mGetRawH = Display::class.java.getMethod("getRawHeight")
                val mGetRawW = Display::class.java.getMethod("getRawWidth")
                resolutionX = mGetRawW.invoke(display) as Int
                resolutionY = mGetRawH.invoke(display) as Int
            } catch (e: Exception) {
                resolutionX = display.width
                resolutionY = display.height
            }

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            val outMetrics = DisplayMetrics()
            display.getRealMetrics(outMetrics)

            resolutionX = outMetrics.widthPixels
            resolutionY = outMetrics.heightPixels
        }

        val point = Point()
        point.x = resolutionX
        point.y = resolutionY
        return point
    }

