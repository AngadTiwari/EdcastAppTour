package com.angad.apptour.utils

import android.content.Context
import android.graphics.*
import android.view.View
import android.widget.ImageView
import android.widget.TextView

class TooltipView(context: Context, val xPos: Float, val yPos: Float, val title: String, val description: String): TextView(context) {

    init {
        //In versions > 3.0 need to define layer Type
        if (android.os.Build.VERSION.SDK_INT >= 11) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        setBackgroundColor(Color.BLUE)
        setPadding(48,24, 48, 24)
        val updatedTitle = "$title\n$description"
        setText("${Helpers.makeTextBold(updatedTitle, 0, title.length)}")
        setTextColor(Color.WHITE)
        x = xPos
        y = yPos + height
        setShadowLayer(120f, x + width, y + height, Color.WHITE)
    }
}