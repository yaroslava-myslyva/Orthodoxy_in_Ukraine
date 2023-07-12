package com.example.orthodoxy_in_ukraine.calendar_month

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

    private fun buildingCalendarView() {
        definitionNameOfMonth()
        binding.calendarTable.removeAllViews()
        Log.d("ttt", "f - $monthOnDisplay, $yearOnDisplay")
        calendar.set(yearOnDisplay, monthOnDisplay, 1)

        val dayOfWeekFirstDayOfMonth = calendar.get(Calendar.DAY_OF_WEEK) - 1
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

            binding.calendarTable.addView(button, i - 1 + numberOfEmptyCells)
        }

        calendar.set(
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            lengthOfCurrentMonth
        )
        val dayOfWeekLastDayOfMonth = calendar.get(Calendar.DAY_OF_WEEK) - 1
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
