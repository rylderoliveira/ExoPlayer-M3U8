package com.rylderoliveira.extensions

import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.transition.Slide
import androidx.transition.TransitionManager

fun LinearLayout.open(parent: ViewGroup) {
    val mSlide = Slide()
    mSlide.slideEdge = Gravity.END
    TransitionManager.beginDelayedTransition(parent, mSlide)
    visibility = View.VISIBLE
}

fun LinearLayout.close(parent: ViewGroup) {
    val mSlide = Slide()
    mSlide.slideEdge = Gravity.END
    TransitionManager.beginDelayedTransition(parent, mSlide)
    visibility = View.GONE
}