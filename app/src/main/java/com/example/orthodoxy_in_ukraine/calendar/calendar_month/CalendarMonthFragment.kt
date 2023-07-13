package com.example.orthodoxy_in_ukraine.calendar.calendar_month

import android.graphics.Color
import android.os.Bundle
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

        val rollingEvents = DateFillerWithRollingEvents()
        val list =
            rollingEvents.fetchListDatesWithRollingEvents(DateFillerWithRollingEvents.YearBetweenEasters.YEAR_2023_2024)
        // непереходящі свята
    }

    private fun buildingCalendarView() {
        definitionNameOfMonth()
        binding.calendarTable.removeAllViews()
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
            button.setPadding(5, 5, 5, 5)
            button.layoutParams = params

            binding.calendarTable.addView(button, i - 1)
        }

        val lengthOfCurrentMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

        for (i in 1..lengthOfCurrentMonth) {
            val button = Button(this.requireContext())
            button.gravity = Gravity.CENTER
            val params = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
            params.width = 150
            params.setMargins(2, 2, 2, 2)
            button.setPadding(5, 5, 5, 5)
            button.layoutParams = params
            button.text = i.toString()

            when (yearOnDisplay) {
                2023 -> settingColorOfDay2023(button)
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
            button.setPadding(5, 5, 5, 5)
            button.layoutParams = params


            binding.calendarTable.addView(button, i - 1 + numberOfEmptyCells + lengthOfCurrentMonth)
        }
    }

    private fun settingColorOfDay2023(button: Button) {
        val date = Integer.parseInt(button.text as String)
        if (isFast(date)) {
            if (isSundayOrBigHolyday(date)) {

            }
            return
        }
        if (isSundayOrBigHolyday(date)) {

            return
        }
        if (isEaster(date)) {
            button.setBackgroundResource(R.color.light_red)
            button.setTextColor(Color.parseColor("#6A0C14"))
            //button.typeface = Typeface.DEFAULT_BOLD
            return
        }

    }

    private fun isEaster(date: Int): Boolean {
        if (date == 16 && monthOnDisplay == Calendar.APRIL && yearOnDisplay == 2023) {
            return true
        }
        return false
    }

    private fun isFast(date: Int): Boolean {
        return false
    }

    private fun isSundayOrBigHolyday(date: Int): Boolean {
        return false
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
