package com.angad.edcast_apptour

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import com.angad.apptour.AppTour
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AppTour.initializeSdk(this)
                .onView(button)
                .title("click")
                .desciption("tap on button for surprise")
                .show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }
}
