package com.dima.tradechart.render

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.dima.tradechart.component.BaseRender
import com.dima.tradechart.component.BaseSeries
import com.dima.tradechart.component.BasesQuote
import com.dima.tradechart.component.Chart

class XYAxisRender(private val chart: Chart) : BaseRender<BaseSeries<BasesQuote>>() {
    private val paddingForGrid = 50f

    private val myPaintLine = Paint().apply {
        color = Color.RED
        style = Paint.Style.STROKE
        strokeWidth = 1f
    }

    override fun draw(canvas: Canvas?, series: BaseSeries<BasesQuote>?) {
        //horizontal
        canvas?.drawLine(0f, paddingForGrid, chart.width.toFloat() - paddingForGrid, paddingForGrid, myPaintLine)
        //vertical
        canvas?.drawLine(chart.width.toFloat() - paddingForGrid, paddingForGrid, chart.width.toFloat() - paddingForGrid, chart.height.toFloat(), myPaintLine)
    }
}