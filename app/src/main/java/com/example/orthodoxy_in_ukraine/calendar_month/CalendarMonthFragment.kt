package com.example.orthodoxy_in_ukraine.calendar_month

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridLayout
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.orthodoxy_in_ukraine.databinding.FragmentCalendarMonthBinding
import java.time.YearMonth
import java.util.*

class CalendarMonthFragment : Fragment() {

    companion object {
        fun newInstance() = CalendarMonthFragment()
    }

    private lateinit var viewModel: CalendarMonthViewModel
    private lateinit var binding: FragmentCalendarMonthBinding

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
        val calendar = GregorianCalendar(TimeZone.getTimeZone("UTC"))

        val lengthOfCurrentMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

       for(i in 1..lengthOfCurrentMonth){
           Log.d("ttt", "I work $i")
           val b = Button(this.requireContext())
           b.gravity = Gravity.CENTER
           val params = LinearLayout.LayoutParams(
               LinearLayout.LayoutParams.WRAP_CONTENT,
               LinearLayout.LayoutParams.WRAP_CONTENT
           )
           params.width = 150
           params.setMargins(2, 2, 2, 2)
           b.setPadding(5, 5, 5, 5)
           b.layoutParams = params
           b.text = i.toString()

           binding.calendarTable
               .addView(b, i-1)
       }


        Log.d("ttt", "calendar.get(Calendar.YEAR) - ${calendar.get(Calendar.YEAR)}")
        Log.d("ttt", "calendar.get(Calendar.MONTH) - ${calendar.get(Calendar.MONTH)}")

    }

}