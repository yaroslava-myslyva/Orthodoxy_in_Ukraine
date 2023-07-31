package com.example.orthodoxy_in_ukraine.presentation.calendar

import android.content.Context
import android.util.Log
import com.example.orthodoxy_in_ukraine.app.OrthodoxyApplication.Companion.applicationScope
import com.example.orthodoxy_in_ukraine.data.db.EventsDataBase
import com.example.orthodoxy_in_ukraine.data.db.model.EventEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.collections.ArrayList
import kotlin.properties.Delegates

class DateFillerWithRollingEvents() { //переходящі свята
    private lateinit var listOfHolydays: List<Holyday>
    val calendar = GregorianCalendar(TimeZone.getTimeZone("GMT+2:00"))
    private var saveCurrentDay by Delegates.notNull<Int>()
    private var saveCurrentMonth by Delegates.notNull<Int>()
    private var saveCurrentYear by Delegates.notNull<Int>()

    public fun fetchListDatesWithRollingEvents(yearBetweenEasters: YearBetweenEasters): ArrayList<DateWithEvents> {
        val listDateWithEventsForYear = arrayListOf<DateWithEvents>()
        calendar.time = yearBetweenEasters.dateOfEasterInFirstYear
        val dateOfEasterInSecondYear = yearBetweenEasters.dateOfEasterInSecondYear
        var currentDayDate = calendar.time
        var counter = 0

        while (currentDayDate.compareTo(dateOfEasterInSecondYear) != 0) {
            saveCurrentCalendar()


            val dateWithEvents = makeDateWithEvents(calendar, yearBetweenEasters, counter)
            listDateWithEventsForYear.add(dateWithEvents)

            overwritingSavedCalendar()
            calendar.add(Calendar.DATE, 1)
            currentDayDate = calendar.time
            counter++
        }
        return listDateWithEventsForYear
    }

    private fun makeDateWithEvents(
        calendar: GregorianCalendar,
        years: YearBetweenEasters,
        counter: Int
    ): DateWithEvents {
        val dateWithEvents = DateWithEvents(calendar.time)

        if (counter < 57) {
            if (counter == 0) dateWithEvents.isBigHolyday = true

            if (counter in 8..49) {
                if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY || calendar.get(
                        Calendar.DAY_OF_WEEK
                    ) == Calendar.FRIDAY
                ) {
                    dateWithEvents.isFast = true
                }
            }
            if (counter == 39) {
                dateWithEvents.isSundayOrBigHolyday = true
                dateWithEvents.isBigHolyday = true
            }
            if (counter == 49) {
                dateWithEvents.isSundayOrBigHolyday = true
                dateWithEvents.isBigHolyday = true
            }
        } else {

            isVelykyyPist(dateWithEvents, calendar, years)
        }

        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            dateWithEvents.isSundayOrBigHolyday = true
        }

        fillingCurrentDateWithNontransitionalHoliday(dateWithEvents)
        // за інтом
        // 1 сплошная
        // 2 звичайні дні
        // 3 Трійця
        // 4 сплошная
        // не за інтом
        // до Хрещення
        // 5 піст
        // 6 петра і павла
        // 7 звичайні
        // 8 успеньський
        // 9 звичайні
        // 10 різдвяний
        // 11 сплошная
        // після Хрещення
        // 12 звичайні
        // від наступної Пасхи
        // 13 сплошная
        // 14 звичайні
        // 15 сплошная
        // 16 Великий піст


        return dateWithEvents
    }

    private fun fillingCurrentDateWithNontransitionalHoliday(dateWithEvents: DateWithEvents) {
        saveCurrentCalendar()
Log.d("ttt", "list - $listOfHolydays")
        listOfHolydays.forEach {
            calendar.set(2023, it.month - 1, it.date)
            if (dateWithEvents.date == calendar.time) {
                Log.d("ttt", "listOfHolydays є співпадіння ${dateWithEvents.date}")
                if(it.isBigHolyday == 1){
                    dateWithEvents.isSundayOrBigHolyday = true
                    dateWithEvents.isBigHolyday = true
                }
            }
        }

        overwritingSavedCalendar()
    }

    private fun isVelykyyPist(
        dateWithEvents: DateWithEvents,
        calendar: GregorianCalendar,
        years: YearBetweenEasters
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

    private fun saveCurrentCalendar() {
        saveCurrentYear = calendar.get(Calendar.YEAR)
        saveCurrentMonth = calendar.get(Calendar.MONTH)
        saveCurrentDay = calendar.get(Calendar.DATE)
    }

    private fun overwritingSavedCalendar() {
        calendar.set(saveCurrentYear, saveCurrentMonth, saveCurrentDay)
    }

    fun setListOfHolydays(list: List<Holyday>) {
        listOfHolydays = list
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
