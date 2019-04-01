package com.inyomanw.corelibrary.utils

import android.content.Context
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
}

fun String?.getInitial(maxChar: Int = 2): String {
    var res = ""
    if (!this.isNullOrBlank()) {
        val split = this.split(" ").joinToString("") {
            it.take(1).toUpperCase()
        }
        val length = split.length < maxChar
        res = split.take(
            if (length) split.length else maxChar
        )
    }

    return res
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

fun String?.convertDate(inputFormat: String = "yyyy-MM-dd",
                        outputFormat: String = "dd MMMM yyyy",
                        localeId: Locale = Locale("id", "ID")
): String {
    if (!this.isNullOrEmpty()) {
        val dateFormat = SimpleDateFormat(inputFormat, localeId)
        val requiredFormat = SimpleDateFormat(outputFormat, localeId)
        try {
            val date = dateFormat.parse(this)
            return requiredFormat.format(date)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    return ""
}

fun Context.calculateTwoDates(firstDate: String = "yyyy-MM-dd"): String {
    val date1 = LocalDateTime.parse(firstDate)
    val str = DateTimeFormat.forPattern("yyyy-MM-dd")
    val secondDate = LocalDate.now().toString(str)
    val date2 = LocalDateTime.parse(secondDate)
    var value = Days.daysBetween(date2, date1).days
    return when {
        value >= 7 -> {
            value = Weeks.weeksBetween(date2, date1).weeks
            value.absoluteValue.toString() + getString(R.string.allias_week)
        }
        value >= 30 -> {
            value = Months.monthsBetween(date2, date1).months
            value.absoluteValue.toString() + getString(R.string.allias_month)
        }
        value == 0 -> 1.toString() + getString(R.string.allias_day)
        else -> value.absoluteValue.toString() + getString(R.string.allias_day)

    }
}

fun String.changeDateFormat(oldPattern: String, newPattern: String): String {
    var res = ""
    try {
        val oldFormat = DateTimeFormat.forPattern(oldPattern)
        val oldDateTime = oldFormat.parseDateTime(this).toDateTime(DateTimeZone.UTC)
        val newFormat = DateTimeFormat.forPattern(newPattern)
        val newDateTime = DateTime.parse(newFormat.print(oldDateTime), newFormat).toDateTime(DateTimeZone.UTC)
        res = newDateTime.toString(newPattern)
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return res
}

fun String.changeDateFormatWithoutTimeZone(oldPattern: String, newPattern: String): String {
    var res = ""
    try {
        val oldFormat = DateTimeFormat.forPattern(oldPattern)
        val newFormat = DateTimeFormat.forPattern(newPattern)
        val oldDateTime = oldFormat.parseDateTime(this)
        val newDateTime = DateTime.parse(newFormat.print(oldDateTime), newFormat)
        res = newDateTime.toString(newPattern)
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return res
}