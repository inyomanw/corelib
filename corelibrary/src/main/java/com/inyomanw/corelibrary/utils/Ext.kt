package com.inyomanw.corelibrary.utils

import android.view.View

fun View.goneIf(boolean: Boolean) {
    if (boolean) this.visibility = View.GONE else this.visibility = View.VISIBLE
}