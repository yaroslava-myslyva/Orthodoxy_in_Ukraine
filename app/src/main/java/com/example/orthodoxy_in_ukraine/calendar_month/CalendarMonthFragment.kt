package com.example.orthodoxy_in_ukraine.calendar_month

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.orthodoxy_in_ukraine.R

class CalendarMonthFragment : Fragment() {

    companion object {
        fun newInstance() = CalendarMonthFragment()
    }

    private lateinit var viewModel: CalendarMonthViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_calendar_month, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CalendarMonthViewModel::class.java)
        // TODO: Use the ViewModel
    }

}