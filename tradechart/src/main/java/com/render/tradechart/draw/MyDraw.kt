package com.render.tradechart.draw

import android.graphics.Paint


interface MyDraw {
    fun drawLine(startX: Float, startY: Float, stopX: Float, stopY: Float, paint: Paint)
    fun drawText(text: String, x: Float, y: Float, paint: Paint)
}