package com.inyomanw.corelibrary.utils

import android.app.Activity
import android.content.Context
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.inyomanw.corelibrary.R
import org.joda.time.*
import org.joda.time.format.DateTimeFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.absoluteValue

fun ImageView.onLoad(context: Context, url :String){
    Glide.with(context)
        .load(url)
        .centerCrop()
        .placeholder(R.drawable.ic_android_black_24dp)
        .into(this)
}

fun View.goneIf(boolean: Boolean) {
    if (boolean) this.visibility = View.GONE else this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.visible(){
    this.visibility = View.VISIBLE
    this.isEnabled = true
}

fun View.invisible(){
    this.visibility = View.INVISIBLE
    this.isEnabled = false
}

fun View.isVisible() : Boolean {
    return this.visibility == View.VISIBLE
}



fun Context.showShortToast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

fun Context.showLongToast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_LONG).show()
}

fun EditText.textToString() : String {
    return this.text.toString()
}

inline fun <reified T : Any> clazz() = T::class.java





fun Activity.showDialog(message : String, cancelable : Boolean = false,
                        positiveButton : String, positiveAction: () -> Unit = {},
                        negativeButton : String, negativeAction : () -> Unit = {}) {
    val dialogBuilder = AlertDialog.Builder(this).apply {
        setMessage(message)
        setCancelable(cancelable)
        setPositiveButton(positiveButton) { dialog, _ ->
            positiveAction()
            dialog.dismiss()
        }
        setNegativeButton(negativeButton) { dialog, which ->
            negativeAction()
            dialog.dismiss()
        }
    }
}