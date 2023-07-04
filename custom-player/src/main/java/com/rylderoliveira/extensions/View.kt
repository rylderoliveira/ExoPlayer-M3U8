package com.rylderoliveira.extensions

import android.view.View

fun View.hide() {
    visibility = View.GONE
}

fun View.show() {
    visibility = View.VISIBLE
}