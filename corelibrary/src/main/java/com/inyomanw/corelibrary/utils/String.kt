package com.inyomanw.corelibrary.utils

import android.content.Context
import com.inyomanw.corelibrary.R
import org.joda.time.*
import org.joda.time.format.DateTimeFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.absoluteValue

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

fun String?.convertDate(
    inputFormat: String = "yyyy-MM-dd",
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

//if value <0 upcoming else ago
fun Context.calculateTwoDates(firstDate: String = "yyyy-MM-dd"): String {
    val date1 = LocalDateTime.parse(firstDate)
    val str = DateTimeFormat.forPattern("yyyy-MM-dd")
    val secondDate = LocalDate.now().toString(str)
    val dateNow = LocalDateTime.parse(secondDate)

    var value = Days.daysBetween(date1, dateNow).days
    var hours = Hours.hoursBetween(date1, dateNow).hours
    return when {
        value in 7..29 -> Weeks.weeksBetween(date1, dateNow).weeks.toString() +
                getString(R.string.allias_week) + " ago"
        value in 30..365 -> Months.monthsBetween(date1, dateNow).months.toString() +
                getString(R.string.allias_month) + " ago"
        value > 365 -> Years.yearsBetween(date1, dateNow).years.toString() +
                getString(R.string.allias_year) + " ago"
        value in 1..6 -> value.toString() + getString(R.string.allias_day) + " ago"

        value == 0 && hours != 0 -> hours.toString() + getString(R.string.allias_hour) + " ago"
        value == 0 && hours == 0 -> Minutes.minutesBetween(date1, dateNow).minutes.toString() +
                getString(R.string.allias_hour) + " ago"

        value in -7..-29 -> Weeks.weeksBetween(date1, dateNow).weeks.absoluteValue.toString() + " more" +
                getString(R.string.allias_week)
        value in -30..-365 -> Months.monthsBetween(date1, dateNow).months.absoluteValue.toString() + " more" +
                getString(R.string.allias_month)
        value > -365 -> Years.yearsBetween(date1, dateNow).years.absoluteValue.toString() + " more" +
                getString(R.string.allias_year)
        value in -1..-6 -> value.absoluteValue.toString() + " more" +
                getString(R.string.allias_day)

        else -> value.absoluteValue.toString() + getString(R.string.allias_day) + " ago"
    }
}