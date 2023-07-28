package com.example.orthodoxy_in_ukraine.calendar

import java.util.Date

data class DateWithEvents(
    val date: Date,
    var isFast: Boolean = false,
    var isSundayOrBigHolyday: Boolean = false,
    var isBigHolyday: Boolean = false,
    val holydays: MutableList<Holyday> = mutableListOf<Holyday>()

) {
}