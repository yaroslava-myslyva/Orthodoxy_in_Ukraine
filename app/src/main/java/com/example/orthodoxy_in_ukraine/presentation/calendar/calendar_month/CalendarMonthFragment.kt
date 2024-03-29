package com.example.orthodoxy_in_ukraine.presentation.calendar.calendar_month

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import com.example.orthodoxy_in_ukraine.R
import com.example.orthodoxy_in_ukraine.app.OrthodoxyApplication
import com.example.orthodoxy_in_ukraine.app.OrthodoxyApplication.Companion.applicationScope
import com.example.orthodoxy_in_ukraine.data.db.EventsDataBase
import com.example.orthodoxy_in_ukraine.data.db.model.EventEntity
import com.example.orthodoxy_in_ukraine.presentation.calendar.DateFillerWithRollingEvents
import com.example.orthodoxy_in_ukraine.presentation.calendar.DateWithEvents
import com.example.orthodoxy_in_ukraine.databinding.FragmentCalendarMonthBinding
import com.example.orthodoxy_in_ukraine.presentation.calendar.Holyday
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
        val dataBase =
            activity?.let { EventsDataBase.getDataBase(it, applicationScope) }
        val dao = dataBase?.IEventsDao
        var listE = listOf<EventEntity>()
        applicationScope.launch(Dispatchers.IO) {
            if (dao != null) {
                listE =  dao.fetchEvents() ?: listOf()
            }
            Log.d("tttg", "listE = $listE")
            val listOfHolydays = mutableListOf<Holyday>()
            for (plantEntity in listE) {
                listOfHolydays.add(plantEntity.mapToHolyday())

            }
            withContext(Dispatchers.Main) {
                buildingCalendarView(listOfHolydays)
                binding.monthNavigationPrevious.setOnClickListener {
                    monthOnDisplay -= 1
                    buildingCalendarView(listOfHolydays)
                }

                binding.monthNavigationNext.setOnClickListener {
                    monthOnDisplay += 1
                    buildingCalendarView(listOfHolydays)
                }
            }

        }

    }

    private fun buildingCalendarView(listOfHolydays: List<Holyday>) {
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
        rollingEvents.setListOfHolydays(listOfHolydays)
        val list =
            rollingEvents.fetchListDatesWithRollingEvents(DateFillerWithRollingEvents.YearBetweenEasters.YEAR_2023_2024)
        // непереходящі свята
        val monthList =
            list.filter { it.date >= firstMonthDay && it.date < lastMonthDay || it.date.toString() == lastMonthDay.toString() }


        val params = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        params.width = 130
        params.height = 100
        params.gravity = Gravity.CENTER
        params.setMargins(8, 8, 8, 8)

        // порожні 1
        calendar.set(yearOnDisplay, monthOnDisplay, 1)
        var dayOfWeekFirstDayOfMonth = calendar.get(Calendar.DAY_OF_WEEK) - 1
        if (dayOfWeekFirstDayOfMonth == 0) dayOfWeekFirstDayOfMonth = 7
        val numberOfEmptyCells = dayOfWeekFirstDayOfMonth - 1
        for (i in 1..numberOfEmptyCells) {
            val button = Button(this.requireContext())
            button.gravity = Gravity.CENTER
            button.layoutParams = params
            button.setBackgroundResource(R.drawable.button_empty)

            binding.calendarTable.addView(button, i - 1)
        }

        // заповнені
        val lengthOfCurrentMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

        for (i in 1..lengthOfCurrentMonth) {
            val button = Button(this.requireContext())
            button.gravity = Gravity.CENTER
            button.layoutParams = params
            button.text = i.toString()
            button.setBackgroundResource(R.drawable.button_ordinary)
            calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), i)
            val currentDay = calendar.time

            val currentDayInList = monthList.find { it.date.toString() == currentDay.toString() }

            if (currentDayInList != null) {

                settingColorOfDay(button, currentDayInList)
            }

            binding.calendarTable.addView(button, i - 1 + numberOfEmptyCells)
        }

        // порожні 2
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
            button.layoutParams = params
            button.setBackgroundResource(R.drawable.button_empty)

            binding.calendarTable.addView(button, i - 1 + numberOfEmptyCells + lengthOfCurrentMonth)
        }
    }

    private fun settingColorOfDay(button: Button, dateWithEvents: DateWithEvents) {

        if (dateWithEvents.isSundayOrBigHolyday) {
            button.setTextColor(Color.parseColor("#AF0211"))
            button.typeface = Typeface.DEFAULT_BOLD
            if (dateWithEvents.isFast) {
                button.setBackgroundResource(R.drawable.button_fast)
            }
            if (dateWithEvents.isBigHolyday) {
                button.setBackgroundResource(R.drawable.button_big_holyday)
            }
        }
        if (dateWithEvents.isFast) {
            button.setBackgroundResource(R.drawable.button_fast)
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
