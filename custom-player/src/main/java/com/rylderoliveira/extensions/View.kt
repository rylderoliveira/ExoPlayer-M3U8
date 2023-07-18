package com.rylderoliveira.extensions

import android.animation.ObjectAnimator
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.transition.Slide
import androidx.transition.TransitionManager

fun View.hide() {
    visibility = View.GONE
}

fun View.show() {
    visibility = View.VISIBLE
    requestFocus()
}

fun View.open(parent: ViewGroup) {
    ObjectAnimator.ofFloat(this, View.TRANSLATION_X, 0f).apply {
        duration = 1000
        start()
        doOnStart { visibility = View.VISIBLE }
    }
}

fun View.close(parent: ViewGroup) {
    ObjectAnimator.ofFloat(this, View.TRANSLATION_X, width.toFloat()).apply {
        duration = 1000
        start()
        doOnEnd { visibility = View.GONE }
    }
}