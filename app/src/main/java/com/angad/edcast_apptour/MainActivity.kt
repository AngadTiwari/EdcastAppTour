package com.angad.edcast_apptour

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.Toast
import com.angad.apptour.AppTour
import com.angad.apptour.utils.IOnTourCompleted
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), IOnTourCompleted {

    private var tag: String = MainActivity::class.java.simpleName
    private lateinit var apptour: AppTour

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AppTour.initializeSdk(this)

        AppTour.stepNumber(1).onView(button).title("Button").desciption("tap on button for surprise").show()
        AppTour.stepNumber(2).onView(imageView).title("Image").desciption("tap on image for surprise").show()
        AppTour.stepNumber(3).onView(editText).title("Field").desciption("tap on edit for surprise").show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onTourCompleted() {
        Toast.makeText(this, "App tour is been completed", Toast.LENGTH_LONG).show()
    }
}
