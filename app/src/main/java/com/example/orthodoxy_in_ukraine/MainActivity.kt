package com.example.orthodoxy_in_ukraine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.*
import com.example.orthodoxy_in_ukraine.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    //https://medium.com/meetu-engineering/create-your-custom-calendar-view-10ff41f39bfe

    //Существуют такие кейсы, когда в программе должны храниться какие-либо первоначальные значения,
    // и чтобы не плодить заполнение в коде, можно создать файлик БД .db и заполнить room из данного файла.
    // Допустим у нас имеется файл созданный в программе DB Browser for SQLite
    // и чтобы его подключить необходимо сперва его поместить в специальную директорию “assets”.
    // Ниже на рисунке указано как создать данную директорию.

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment? ?: return
        val navController = host.navController
        val navView: BottomNavigationView = binding.bottomNav
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.fragment_calendar_today,
                R.id.fragment_calendar_month,
                R.id.fragment_prayer_book,
                R.id.fragment_quotes
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

    }
}