package com.angad.apptour

import android.app.Activity
import android.os.Build
import android.support.v7.widget.AppCompatEditText
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.FrameLayout
import com.angad.apptour.utils.IOnTourCompleted
import com.angad.apptour.utils.OverlayView
import com.angad.apptour.utils.TooltipView


class AppTour(val step: Int): View.OnClickListener, View.OnTouchListener {
    private val tag: String? = context::class.java.simpleName

    companion object {

        private lateinit var context: Activity
        private lateinit var contentArea: ViewGroup
        private lateinit var iOnTourCompleted: IOnTourCompleted
        private var appTourSteps: ArrayList<AppTour> = arrayListOf()
        private var currentStep: Int = 0
        private var totalSteps: Int = 0

        /**
         * initialize the sdk, load all the variable
         */
        fun initializeSdk(context: Activity) {
            this.context = context
            this.iOnTourCompleted = context as IOnTourCompleted
            this.contentArea = context.window.decorView.findViewById(android.R.id.content) as ViewGroup
            
            val button = contentArea.findViewById(R.id.button) as Button 
            val imageView = contentArea.findViewById(R.id.imageView) as ImageView
            val editText = contentArea.findViewById(R.id.editText) as EditText
            
            stepNumber(1).onView(button).title("Button").desciption("tap on button for surprise").show()
            stepNumber(2).onView(imageView).title("Image").desciption("tap on image for surprise").show()
            stepNumber(3).onView(editText).title("Field").desciption("tap on edit for surprise").show()
        }

        fun stepNumber(step: Int): AppTour {
            totalSteps ++
            val appTour = AppTour(step)
            appTourSteps.add(appTour)
            return appTour
        }
    }

    private var index: Int = step - 1
    private lateinit var view: View
    private lateinit var title: String
    private lateinit var desciption: String
    private lateinit var viewlocation: FloatArray
    private var viewRadius: Float? = null
    private lateinit var overlayView: OverlayView
    private lateinit var tooltipView: TooltipView

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
        if(this.index != currentStep)
            return

        if(currentStep == 0) {
            val viewTreeObserver = this.view.viewTreeObserver
            viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                        view.viewTreeObserver.removeGlobalOnLayoutListener(this)
                    } else {
                        view.viewTreeObserver.removeOnGlobalLayoutListener(this)
                    }
                    setupView()
                }
            })
        } else {
            setupView()
        }
    }

    private fun setupView() {
        viewlocation = findViewLocation(view)
        viewRadius = findRadius(view)
        overlayView = renderOverlayView()
        tooltipView = renderTooltipView()
        if(view is AppCompatEditText) {
            view.setOnTouchListener(this)
        } else {
            view.setOnClickListener(this@AppTour)
        }
    }

    private fun renderOverlayView(): OverlayView {
        overlayView = OverlayView(context, viewlocation, viewRadius!!)
        val layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
        val pos = IntArray(2)
        contentArea.getLocationOnScreen(pos)
        layoutParams.setMargins(0, -pos[1], 0, 0)
        contentArea.addView(overlayView, layoutParams)
        return overlayView
    }

    private fun renderTooltipView(): TooltipView {
        tooltipView = TooltipView(context, view.x, view.y, title, desciption)
        val layoutParams = FrameLayout.LayoutParams(540, 196)
        val contentArea = context.window.decorView.findViewById(android.R.id.content) as ViewGroup
        contentArea.addView(tooltipView, layoutParams)
        return tooltipView
    }

    private fun findViewLocation(view: View): FloatArray {
        val viewLocation = IntArray(2)
        view.getLocationInWindow(viewLocation)
        val viewLocationInFloat = FloatArray(2)
        viewLocationInFloat[0] = viewLocation[0].toFloat() + view.width/2f
        viewLocationInFloat[1] = viewLocation[1].toFloat() + view.height/2f
        return viewLocationInFloat
    }

    private fun findRadius(view: View): Float {
        return Math.max(view.width, view.height)/2f
    }

    override fun onClick(view: View?) {
        tapEvent()
    }

    private fun tapEvent() {
        if(totalSteps > 1) {
            currentStep ++
            totalSteps --
            contentArea.removeView(overlayView)
            contentArea.removeView(tooltipView)
            appTourSteps.remove(this)
            appTourSteps.get(0).show()
        } else {
            contentArea.removeView(overlayView)
            contentArea.removeView(tooltipView)
            iOnTourCompleted.onTourCompleted()
        }
    }

    override fun onTouch(view: View?, event: MotionEvent?): Boolean {
        if(event?.action == MotionEvent.ACTION_UP){
            tapEvent()
            return true
        }
        return false
    }
}
