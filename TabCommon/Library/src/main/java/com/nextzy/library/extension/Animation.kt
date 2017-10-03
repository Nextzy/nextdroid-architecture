package com.nextzy.library.extension

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.support.annotation.Px
import android.view.View
import android.view.ViewGroup
import android.view.animation.*
import com.nextzy.library.extension.view.OnGetViewSizeListener
import com.nextzy.library.extension.view.getSize

/**
 * Created by「 The Khaeng 」on 02 Oct 2017 :)
 */
const val DEFAULT_START_DELAY = 200

const val EXTRA_SHORT_ANIM_TIME: Long = 100L
const val SHORT_ANIM_TIME: Long = 200L
const val MEDIUM_ANIM_TIME: Long = 400L
const val LONG_ANIM_TIME: Long = 500L



fun View?.fadeIn(duration: Long = 200L) {
    if (this?.visibility == View.VISIBLE) return
    val fadeIn = AlphaAnimation(0f, 1f)
    fadeIn.interpolator = DecelerateInterpolator()
    fadeIn.duration = duration
    this?.animation = fadeIn
    fadeIn.setAnimationListener(object : Animation.AnimationListener {
        override
        fun onAnimationStart(animation: Animation) {
            if (this@fadeIn?.visibility == View.GONE) {
                this@fadeIn.visibility = View.VISIBLE
            }
        }

        override
        fun onAnimationEnd(animation: Animation) {
        }

        override
        fun onAnimationRepeat(animation: Animation) {

        }
    })
    fadeIn.start()
}

fun View?.fadeOut(duration: Long = 200L) {
    if (this?.visibility == View.GONE) return

    val fadeOut = AlphaAnimation(1f, 0f)
    fadeOut.interpolator = AccelerateInterpolator()
    fadeOut.duration = duration
    this?.animation = fadeOut
    fadeOut.setAnimationListener(object : Animation.AnimationListener {
        override
        fun onAnimationStart(animation: Animation) {

        }

        override
        fun onAnimationEnd(animation: Animation) {
            if (this@fadeOut?.visibility == View.VISIBLE) {
                this@fadeOut.visibility = View.GONE
            }
        }

        override fun onAnimationRepeat(animation: Animation) {

        }
    })
    fadeOut.start()
}


fun View?.popUp(delay: Long = 0L,
                duration: Long = 150,
                interpolator: Interpolator = OvershootInterpolator(),
                animListener: AnimatorListenerAdapter? = null) {
    if (this?.visibility != View.VISIBLE) {
        this.getSize(object : OnGetViewSizeListener {
            override
            fun onSize(width: Int, height: Int) {
                this@popUp?.visibility = View.VISIBLE
                this@popUp?.pivotX = (width / 2).toFloat()
                this@popUp?.pivotY = (height / 2).toFloat()
                this@popUp?.scaleX = 0.0f
                this@popUp?.scaleY = 0.0f
                this@popUp?.animate()
                        ?.scaleX(1f)?.scaleY(1f)
                        ?.setDuration(duration)
                        ?.setInterpolator(interpolator)
                        ?.setStartDelay(delay)
                        ?.setListener(animListener)
            }
        })
    }
}

fun View?.hidePopUp(delay: Long = 0L,
                    duration: Long = 150,
                    interpolator: Interpolator = AccelerateInterpolator(),
                    animListener: AnimatorListenerAdapter? = null) {
    if (this?.visibility == View.VISIBLE
            && (this.tag == null || this.tag == false)) {
        this.tag = true
        this.getSize(object : OnGetViewSizeListener {
            override
            fun onSize(width: Int, height: Int) {
                this@hidePopUp.pivotX = (width / 2).toFloat()
                this@hidePopUp.pivotY = (height / 2).toFloat()
                if (animListener != null) {
                    this@hidePopUp.animate()
                            .scaleX(0.1f).scaleY(0.1f)
                            .setDuration(duration)
                            .setInterpolator(interpolator)
                            .setStartDelay(delay)
                            .setListener(animListener)
                } else {
                    this@hidePopUp.animate()
                            .scaleX(0.1f).scaleY(0.1f)
                            .setDuration(duration)
                            .setInterpolator(interpolator)
                            .setStartDelay(delay)
                            .setListener(
                                    object : AnimatorListenerAdapter() {
                                        override
                                        fun onAnimationEnd(animation: Animator) {
                                            this@hidePopUp.visibility = View.GONE
                                            this@hidePopUp.tag = false
                                        }
                                    })
                }
            }

        })
    }

}

fun View?.translateY(@Px translateY: Int,
                     delay: Long = 0L,
                     duration: Long = 150,
                     interpolator: Interpolator = OvershootInterpolator(),
                     animListener: AnimatorListenerAdapter? = null) {
    this@translateY?.animate()
            ?.translationY(translateY.toFloat())
            ?.setInterpolator(interpolator)
            ?.setDuration(duration)
            ?.setStartDelay(delay)
            ?.setListener(animListener)
}

fun View?.translateX(@Px translateX: Int,
                     delay: Long = 0L,
                     duration: Long = 150,
                     interpolator: Interpolator = OvershootInterpolator(),
                     animListener: AnimatorListenerAdapter? = null) {
    this@translateX?.animate()
            ?.translationX(translateX.toFloat())
            ?.setInterpolator(interpolator)
            ?.setDuration(duration)
            ?.setStartDelay(delay)
            ?.setListener(animListener)
}

fun View?.rotate(degree: Int, dur: Long = 100) {
    if (this?.visibility == View.VISIBLE) {
        this.animate()
                .rotation(degree.toFloat())
                .duration = dur
    }
}


interface OnViewAnimationListener {
    fun onFinish()
}


fun View?.expand(listener: OnViewAnimationListener? = null) {
    if (this != null) {
        this.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val targetHeight = this.measuredHeight
        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        this.layoutParams.height = 1
        this.visibility = View.VISIBLE
        val a = object : Animation() {
            override
            fun applyTransformation(interpolatedTime: Float, t: Transformation) {
                if (interpolatedTime.compareTo(1f) == 0) {
                    this@expand.layoutParams.height =
                            ViewGroup.LayoutParams.WRAP_CONTENT
                    listener?.onFinish()
                } else {
                    this@expand.layoutParams.height =
                            (targetHeight * interpolatedTime).toInt()
                }
                this@expand.requestLayout()
            }

            override
            fun willChangeBounds(): Boolean {
                return true
            }
        }

        // 1dp/ms
        a.duration = (targetHeight / this.context.resources.displayMetrics.density).toInt().toLong()
        this.startAnimation(a)
    }
}

fun View?.collapse() {
    if (this != null) {
        val initialHeight = this.measuredHeight

        val a = object : Animation() {
            override
            fun applyTransformation(interpolatedTime: Float, t: Transformation) {
                if (interpolatedTime.compareTo(1f) == 0) {
                    this@collapse.visibility = View.GONE
                } else {
                    this@collapse.layoutParams.height = initialHeight.minus((initialHeight * interpolatedTime).toInt())
                    this@collapse.requestLayout()
                }
            }

            override
            fun willChangeBounds(): Boolean {
                return true
            }
        }

        // 1dp/ms
        a.duration = (initialHeight.div(this.context.resources.displayMetrics.density)).toInt().toLong()
        this.startAnimation(a)
    }
}