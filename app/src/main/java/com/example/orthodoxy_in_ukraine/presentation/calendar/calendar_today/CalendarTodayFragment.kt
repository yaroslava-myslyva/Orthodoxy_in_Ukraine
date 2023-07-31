package com.example.orthodoxy_in_ukraine.presentation.calendar.calendar_today

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.orthodoxy_in_ukraine.R

class CalendarTodayFragment : Fragment() {

    companion object {
        fun newInstance() = CalendarTodayFragment()
    }

    private lateinit var viewModel: CalendarTodayViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_calendar_today, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CalendarTodayViewModel::class.java)
        // TODO: Use the ViewModel
    }

}