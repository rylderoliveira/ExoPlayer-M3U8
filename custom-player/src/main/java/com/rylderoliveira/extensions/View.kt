package com.rylderoliveira.extensions

import android.animation.ObjectAnimator
import android.view.View
import androidx.core.animation.doOnEnd
import androidx.core.view.doOnAttach

fun View.hide() {
    visibility = View.GONE
}

fun View.show() {
    visibility = View.VISIBLE
    requestFocus()
}

fun View.open() {
    ObjectAnimator.ofFloat(this, View.TRANSLATION_X, 0f).apply {
        duration = 700
        start()
        doOnAttach { visibility = View.VISIBLE }
    }
}

fun View.close() {
    ObjectAnimator.ofFloat(this, View.TRANSLATION_X, width.toFloat()).apply {
        duration = 700
        start()
        doOnEnd { visibility = View.GONE }
    }
}