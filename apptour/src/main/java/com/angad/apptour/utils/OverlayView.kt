package com.angad.apptour.utils

import android.content.Context
import android.widget.ImageView
import android.graphics.*
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.view.View

class OverlayView(context: Context, val circleCenter: FloatArray, val radius: Float): ImageView(context) {

    init {
        //In versions > 3.0 need to define layer Type
        if (android.os.Build.VERSION.SDK_INT >= 11) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val paint = Paint(ANTI_ALIAS_FLAG)//Draw Overlay
        paint.setColor(Color.parseColor("#88000000"))
        paint.setStyle(Paint.Style.FILL)
        canvas.drawPaint(paint)

        paint.setXfermode(PorterDuffXfermode(PorterDuff.Mode.CLEAR))//Draw transparent shape
        canvas.drawCircle(circleCenter[0], circleCenter[1], radius, paint)
    }
}