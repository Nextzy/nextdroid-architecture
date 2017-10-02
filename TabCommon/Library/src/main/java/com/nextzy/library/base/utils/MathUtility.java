package com.nextzy.library.base.utils;

/**
 * Borrowed from github.com/romannurik/muzei
 */
public class MathUtility {

    private MathUtility() { }

    public static float constrain(float min, float max, float v) {
        return Math.max(min, Math.min(max, v));
    }
}
