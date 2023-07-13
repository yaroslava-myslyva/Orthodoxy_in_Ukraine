package com.example.orthodoxy_in_ukraine.calendar

import android.util.Log
import java.util.*
import kotlin.collections.ArrayList

class EventDateByYearFiller(){

    fun fetchListDatesWithEvents(yearBetweenEasters: EventDateByYearFiller.YearBetweenEasters) :ArrayList<DateWithEvents>{
        val listDateWithEvents = arrayListOf<DateWithEvents>()
        val currentDayCalendar = GregorianCalendar(TimeZone.getTimeZone("GMT+2:00"))
        currentDayCalendar.time = yearBetweenEasters.dateOfEasterInFirstYear
        val dateOfEasterInSecondYear =  yearBetweenEasters.dateOfEasterInSecondYear
        var currentDayDate = currentDayCalendar.time

        while (currentDayDate.compareTo(dateOfEasterInSecondYear) != 0){
            val dateWithEvents = DateWithEvents(currentDayDate)
            listDateWithEvents.add(dateWithEvents)
            currentDayCalendar.add(Calendar.DATE, 1)
            currentDayDate = currentDayCalendar.time
        }
        return listDateWithEvents
    }



    enum class YearBetweenEasters(val dateOfEasterInFirstYear: Date, val dateOfEasterInSecondYear: Date) {
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
