package com.angad.apptour

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import android.widget.ImageButton
import android.widget.Toast
import android.widget.RelativeLayout



class AppTour {

    companion object {

        lateinit var context: Context

        /**
         * initialize the sdk, load all the variable
         */
        fun initializeSdk(context: Context): AppTour {
            this.context = context
            return AppTour()
        }
    }

    private val tag: String? = context::class.java.simpleName
    private lateinit var view: View
    private lateinit var title: String
    private lateinit var desciption: String
    private lateinit var viewlocation: IntArray

    public fun onView(view: View): AppTour {
        this.view = view
        return this
    }

    public fun title(title: String): AppTour {
        this.title = title
        return this
    }

    public fun desciption(desciption: String): AppTour {
        this.desciption = desciption
        return this
    }

    public fun show() {
        val viewTreeObserver = view.viewTreeObserver
        viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                    view.viewTreeObserver.removeGlobalOnLayoutListener(this)
                } else {
                    view.viewTreeObserver.removeOnGlobalLayoutListener(this)
                }
                viewlocation = findViewLocation()
                Toast.makeText(context, "title: $title, desciption: $desciption, location: ${viewlocation[0]}:${viewlocation[1]}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun findViewLocation(): IntArray {
        val viewLocation = IntArray(2)
        this.view.getLocationOnScreen(viewLocation)
        viewLocation[0] += this.view.width/2
        viewLocation[1] += this.view.height/2
        return viewLocation
    }
}