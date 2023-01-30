package com.mate.carpool.data.model.item

enum class DayStatus {
    Morning,
    Afternoon,
}

fun DayStatus.getDayStatus() =
    when(this) {
        DayStatus.Morning -> "오전"
        DayStatus.Afternoon -> "오후"
    }