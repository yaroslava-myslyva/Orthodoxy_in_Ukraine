package com.example.orthodoxy_in_ukraine.presentation.prayer_book

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.orthodoxy_in_ukraine.R

class PrayerBookFragment : Fragment() {

    companion object {
        fun newInstance() = PrayerBookFragment()
    }

    private lateinit var viewModel: PrayerBookViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_prayer_book, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PrayerBookViewModel::class.java)
        // TODO: Use the ViewModel
    }

}