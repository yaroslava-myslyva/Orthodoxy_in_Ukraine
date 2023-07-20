package com.example.orthodoxy_in_ukraine.calendar.calendar_month

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.orthodoxy_in_ukraine.R
import com.example.orthodoxy_in_ukraine.calendar.DateFillerWithRollingEvents
import com.example.orthodoxy_in_ukraine.calendar.DateWithEvents
import com.example.orthodoxy_in_ukraine.databinding.FragmentCalendarMonthBinding
import java.util.*

class CalendarMonthFragment : Fragment() {

    companion object {
        fun newInstance() = CalendarMonthFragment()
    }

    private lateinit var viewModel: CalendarMonthViewModel
    private lateinit var binding: FragmentCalendarMonthBinding
    private val calendar = GregorianCalendar(TimeZone.getTimeZone("GMT+2:00"))
    private var yearOnDisplay = calendar.get(Calendar.YEAR)
    private var monthOnDisplay = calendar.get(Calendar.MONTH)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCalendarMonthBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CalendarMonthViewModel::class.java)


        buildingCalendarView()

        binding.monthNavigationPrevious.setOnClickListener {
            monthOnDisplay -= 1
            buildingCalendarView()
        }

        binding.monthNavigationNext.setOnClickListener {
            monthOnDisplay += 1
            buildingCalendarView()
        }


    }

    private fun buildingCalendarView() {
        definitionNameOfMonth()
        binding.calendarTable.removeAllViews()
        calendar.set(yearOnDisplay, monthOnDisplay, 1)
        val firstMonthDay = calendar.time
        calendar.set(
            yearOnDisplay,
            monthOnDisplay,
            calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        )
        val lastMonthDay = calendar.time

        val rollingEvents = DateFillerWithRollingEvents()
        val list =
            rollingEvents.fetchListDatesWithRollingEvents(DateFillerWithRollingEvents.YearBetweenEasters.YEAR_2023_2024)
        // непереходящі свята
        val monthList = list.filter { it.date >= firstMonthDay && it.date < lastMonthDay || it.date.toString() == lastMonthDay.toString()}

        Log.d("ttt", "lastMonthDay = $lastMonthDay")
        Log.d("ttt", "monthList = $monthList")

        calendar.set(yearOnDisplay, monthOnDisplay, 1)
        var dayOfWeekFirstDayOfMonth = calendar.get(Calendar.DAY_OF_WEEK) - 1
        if (dayOfWeekFirstDayOfMonth == 0) dayOfWeekFirstDayOfMonth = 7
        val numberOfEmptyCells = dayOfWeekFirstDayOfMonth - 1
        for (i in 1..numberOfEmptyCells) {
            val button = Button(this.requireContext())
            button.gravity = Gravity.CENTER
            val params = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
            params.width = 150
            params.setMargins(2, 2, 2, 2)
            button.layoutParams = params

            binding.calendarTable.addView(button, i - 1)
        }

        val lengthOfCurrentMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

        for (i in 1..lengthOfCurrentMonth) {
            val button = Button(this.requireContext())
            button.gravity = Gravity.CENTER
            val params = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
            params.width = 130
            params.height = 100
            params.gravity = Gravity.CENTER
            params.setMargins(8, 8, 8, 8)

            button.layoutParams = params
            button.text = i.toString()
            button.setBackgroundResource(R.drawable.btn)
            calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), i)
            val currentDay = calendar.time
            Log.d("ttt", "currentDay - $currentDay")
            val currentDayInList = monthList.find { it.date.toString() == currentDay.toString() }
            Log.d("ttt", "currentDayInList - $currentDayInList")
            if (currentDayInList != null) {
                Log.d("ttt", "i'm working")
                settingColorOfDay(button, currentDayInList)
            }

            binding.calendarTable.addView(button, i - 1 + numberOfEmptyCells)
        }

        calendar.set(
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            lengthOfCurrentMonth
        )
        var dayOfWeekLastDayOfMonth = calendar.get(Calendar.DAY_OF_WEEK) - 1
        if (dayOfWeekLastDayOfMonth == 0) dayOfWeekLastDayOfMonth = 7
        val numberOfEmptyCells2 = 7 - dayOfWeekLastDayOfMonth

        for (i in 1..numberOfEmptyCells2) {
            val button = Button(this.requireContext())
            button.gravity = Gravity.CENTER
            val params = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
            params.width = 150
            params.setMargins(2, 2, 2, 2)
            button.layoutParams = params


            binding.calendarTable.addView(button, i - 1 + numberOfEmptyCells + lengthOfCurrentMonth)
        }
    }

    private fun settingColorOfDay(button: Button, dateWithEvents: DateWithEvents) {

        if (dateWithEvents.isFast) {
            if (dateWithEvents.isSundayOrBigHolyday) {

            }
            button.setBackgroundResource(R.drawable.btn)
            return
        }
        if (dateWithEvents.isSundayOrBigHolyday) {

            return
        }
        if (dateWithEvents.isEaster) {

            button.setTextColor(Color.parseColor("#6A0C14"))
            //button.typeface = Typeface.DEFAULT_BOLD
            return
        }

    }


    private fun definitionNameOfMonth() {
        while (monthOnDisplay < 0) {
            yearOnDisplay -= 1
            monthOnDisplay += 12
        }
        while (monthOnDisplay > 11) {
            yearOnDisplay += 1
            monthOnDisplay -= 12
        }
        with(binding.monthName) {
            text = when (monthOnDisplay) {
                Calendar.JANUARY -> JANUARY
                Calendar.FEBRUARY -> FEBRUARY
                Calendar.MARCH -> MARCH
                Calendar.APRIL -> APRIL
                Calendar.MAY -> MAY
                Calendar.JUNE -> JUNE
                Calendar.JULY -> JULY
                Calendar.AUGUST -> AUGUST
                Calendar.SEPTEMBER -> SEPTEMBER
                Calendar.OCTOBER -> OCTOBER
                Calendar.NOVEMBER -> NOVEMBER
                Calendar.DECEMBER -> DECEMBER
                else -> "помилка"
            }
        }
    }
}

const val JANUARY = "Січень"
const val FEBRUARY = "Лютий"
const val MARCH = "Березень"
const val APRIL = "Квітень"
const val MAY = "Травень"
const val JUNE = "Червень"
const val JULY = "Липень"
const val AUGUST = "Серпень"
const val SEPTEMBER = "Вересень"
const val OCTOBER = "Жовтень"
const val NOVEMBER = "Листопад"
const val DECEMBER = "Грудень"
