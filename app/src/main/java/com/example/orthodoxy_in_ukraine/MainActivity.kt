package com.example.orthodoxy_in_ukraine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.orthodoxy_in_ukraine.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    //https://medium.com/meetu-engineering/create-your-custom-calendar-view-10ff41f39bfe

    private lateinit var binding :ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}