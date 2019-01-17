package com.dima.tradechart.render

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.dima.tradechart.component.BaseRender
import com.dima.tradechart.component.BaseSeries
import com.dima.tradechart.component.BasesQuote
import com.dima.tradechart.component.Chart

class XYAxisRender(private val chart: Chart) : BaseRender<BaseSeries<BasesQuote>>() {

    private val myPaintLine = Paint().apply {
        color = Color.RED
        style = Paint.Style.STROKE
        strokeWidth = 1f
    }

    override fun draw(canvas: Canvas?, series: BaseSeries<BasesQuote>?) {
        //horizontal
        canvas?.drawLine(0f, chart.offsetRight, chart.width.toFloat() - chart.offsetRight, chart.offsetRight, myPaintLine)
        //vertical
        canvas?.drawLine(
                chart.width.toFloat() - chart.offsetRight,
                chart.offsetRight,
                chart.width.toFloat() - chart.offsetRight,
                chart.height.toFloat(), myPaintLine
        )
    }
}