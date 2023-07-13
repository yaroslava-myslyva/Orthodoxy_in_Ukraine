package com.example.orthodoxy_in_ukraine.calendar

import android.util.Log
import java.util.*
import kotlin.collections.ArrayList
import kotlin.properties.Delegates

class DateFillerWithRollingEvents() { //переходящі свята
    val calendar = GregorianCalendar(TimeZone.getTimeZone("GMT+2:00"))
    private var saveCurrentDay by Delegates.notNull<Int>()
    private var saveCurrentMonth by Delegates.notNull<Int>()
    private var saveCurrentYear by Delegates.notNull<Int>()

    public fun fetchListDatesWithRollingEvents(yearBetweenEasters: DateFillerWithRollingEvents.YearBetweenEasters): ArrayList<DateWithEvents> {
        val listDateWithEvents = arrayListOf<DateWithEvents>()
        calendar.time = yearBetweenEasters.dateOfEasterInFirstYear
        val dateOfEasterInSecondYear = yearBetweenEasters.dateOfEasterInSecondYear
        var currentDayDate = calendar.time

        while (currentDayDate.compareTo(dateOfEasterInSecondYear) != 0) {
            saveCurrentCalendar()

            val dateWithEvents = makeDateWithEvents(calendar, yearBetweenEasters)
            listDateWithEvents.add(dateWithEvents)

            overwritingSavedCalendar()
            calendar.add(Calendar.DATE, 1)
            currentDayDate = calendar.time
        }
        return listDateWithEvents
    }

    private fun makeDateWithEvents(
        calendar: GregorianCalendar,
        years: DateFillerWithRollingEvents.YearBetweenEasters
    ): DateWithEvents {
        val dateWithEvents = DateWithEvents(calendar.time)
        isVelykyyPist(dateWithEvents, calendar, years)
        return dateWithEvents
    }

    private fun isVelykyyPist(
        dateWithEvents: DateWithEvents,
        calendar: GregorianCalendar,
        years: DateFillerWithRollingEvents.YearBetweenEasters
    ) {
        saveCurrentCalendar()
        val lastDay = years.dateOfEasterInSecondYear
        calendar.time = lastDay
        calendar.add(Calendar.DATE, -48)
        val firstDay = calendar.time
        if (dateWithEvents.date >= firstDay && dateWithEvents.date < lastDay) {
            dateWithEvents.isFast = true
        }
        overwritingSavedCalendar()
    }

    private fun saveCurrentCalendar(){
        saveCurrentYear = calendar.get(Calendar.YEAR)
        saveCurrentMonth = calendar.get(Calendar.MONTH)
        saveCurrentDay = calendar.get(Calendar.DATE)
    }

    private fun overwritingSavedCalendar(){
        calendar.set(saveCurrentYear, saveCurrentMonth, saveCurrentDay)
    }

    enum class YearBetweenEasters(
        val dateOfEasterInFirstYear: Date,
        val dateOfEasterInSecondYear: Date
    ) {
        YEAR_2023_2024(easter2023, easter2024)
    }

    companion object {
        val calendar = GregorianCalendar(TimeZone.getTimeZone("GMT+2:00"))
        val easter2023: Date
            get() {
                calendar.set(2023, Calendar.APRIL, 16)
                return calendar.time
            }
        val easter2024: Date
            get() {
                calendar.set(2024, Calendar.MAY, 5)
                return calendar.time
            }
    }
}
