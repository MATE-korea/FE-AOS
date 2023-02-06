package com.mate.carpool.util

import com.mate.carpool.ui.util.date
import com.mate.carpool.ui.util.hour
import com.mate.carpool.ui.util.minute
import com.mate.carpool.ui.util.month
import java.util.*
import kotlin.time.Duration.Companion.minutes

fun Long.formatStartTimeToDTO() = this.run {
    val cal = Calendar.getInstance()
    cal.timeInMillis = this
    String.format(Locale.KOREA,"%02d:%02d",cal.hour,cal.minute)
}

fun Long.formatStartDayMonthToDTO() = this.run {
    val cal = Calendar.getInstance()
    cal.timeInMillis = this
    String.format(Locale.KOREA,"%02d/%02d",cal.month,cal.date)
}

fun Long.formatStartTimeAsDisplay() = this.run {
    val cal = Calendar.getInstance()
    cal.timeInMillis = this
    if(cal.minute != 0)
        String.format(Locale.KOREA,"%d시 %b분",cal.hour, cal.minute )
    else
        String.format(Locale.KOREA,"%d시",cal.hour )
}