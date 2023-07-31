package com.example.orthodoxy_in_ukraine.app

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class OrthodoxyApplication : Application() {
    companion object {
        val applicationScope = CoroutineScope(SupervisorJob())
    }
}