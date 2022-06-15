package com.zero.android.common.extensions

import java.text.SimpleDateFormat
import java.util.*

fun Long.toDate() = Date(this)

fun Date.toMessageDateFormat(): String {
    val currentCalendar = Calendar.getInstance()
    val currentYear = currentCalendar.get(Calendar.YEAR)
    val currentWeek = currentCalendar.get(Calendar.WEEK_OF_YEAR)
    val currentDay = currentCalendar.get(Calendar.DAY_OF_MONTH)

    val targetCalendar = Calendar.getInstance()
    targetCalendar.time = this
    val targetYear = targetCalendar.get(Calendar.YEAR)
    val targetWeek = targetCalendar.get(Calendar.WEEK_OF_YEAR)
    val targetDay = targetCalendar.get(Calendar.DAY_OF_MONTH)

    if (!this.after(currentCalendar.time)) {
        return when {
            targetYear < currentYear -> {
                this.format("MM/dd/yyyy")
            }
            else -> {
                if (targetWeek == currentWeek) {
                    if (targetDay == currentDay) {
                        if (this.before(currentCalendar.time)) {
                            "Today"
                        } else {
                            "Just Now"
                        }
                    } else this.format("EEE")
                } else this.format("MM/dd")
            }
        }
    } else
        return ""
}

fun Date.format(format: String, locale: Locale = Locale.ENGLISH): String {
    val formatter = SimpleDateFormat(format, locale)
    return formatter.format(this)
}
