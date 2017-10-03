package com.nextzy.library.base.delegate

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.TextView
import com.nextzy.library.R
import com.nextzy.library.extension.findFont
import com.nextzy.library.extension.view.pxToSp
import java.util.*


/**
 * Created by「 The Khaeng 」on 02 Oct 2017 :)
 */

class CustomFontDelegate
private constructor(
        private val textView: TextView,
        private val attributes: IntArray = intArrayOf(android.R.attr.textStyle)) {

    companion object {
        val DEFAULT_FONT_NAME = "ProductSans-Regular.ttf"
        val BOLD_FONT_NAME = "ProductSans-Bold.ttf"

        fun newInstance(textView: TextView): CustomFontDelegate {
            return CustomFontDelegate(textView)
        }
    }

    internal interface FontInjector {
        val normalTypeFace: Typeface
        val boldTypeFace: Typeface
    }

    fun injectProductSansFont(attrs: AttributeSet, context: Context) {
        injectCustomFont(attrs, object : FontInjector {
            override
            val normalTypeFace: Typeface
                get() = context.findFont("ProductSans-Regular.ttf")

            override
            val boldTypeFace: Typeface
                get() = context.findFont("ProductSans-Bold.ttf")
        })

    }

    private fun injectCustomFont(attrs: AttributeSet?, fontInjector: FontInjector) {
        if (!textView.isInEditMode) {
            val attrTextStyle: Int
            if (attrs != null) {
                val typedArray = textView.context.obtainStyledAttributes(attrs, attributes)
                attrTextStyle = typedArray.getInt(0, 0)
                typedArray.recycle()
            } else {
                attrTextStyle = Typeface.NORMAL
            }


            var oldTypeface: Typeface? = textView.typeface
            val textStyle: Int = Typeface.NORMAL

            oldTypeface =
                    if (oldTypeface != null
                            && oldTypeface.style and Typeface.BOLD == 1
                            || attrTextStyle and Typeface.BOLD == 1) {
                        fontInjector.boldTypeFace
                    } else {
                        fontInjector.normalTypeFace
                    }

            if ("th" == Locale.getDefault().language) {
                textView.setTextSize(
                        TypedValue.COMPLEX_UNIT_SP,
                        pxToSp(textView.textSize) + 1.0f)
            }

            if (textView.context.resources.getBoolean(R.bool.is_tablet)) {
                textView.setTextSize(
                        TypedValue.COMPLEX_UNIT_SP,
                        pxToSp(textView.textSize) + 2.0f)
            }
            textView.setTypeface(oldTypeface, textStyle)
        }
    }


}
