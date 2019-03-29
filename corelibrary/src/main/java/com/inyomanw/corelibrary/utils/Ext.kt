package com.inyomanw.corelibrary.utils

import android.view.View

fun View.goneIf(boolean: Boolean) {
    if (boolean) this.visibility = View.GONE else this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.visible(){
    this.visibility = View.VISIBLE
}