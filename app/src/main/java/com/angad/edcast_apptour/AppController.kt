package com.angad.edcast_apptour

import android.app.Application
import com.angad.apptour.AppTour

class AppController: Application() {

    override fun onCreate() {
        super.onCreate()

        AppTour.initializeSdk(context = this)
    }
}