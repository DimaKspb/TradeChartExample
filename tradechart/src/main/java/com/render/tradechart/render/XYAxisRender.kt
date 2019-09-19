package com.render.tradechart.render

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.render.tradechart.model.BaseRender
import com.render.tradechart.component.Chart
import com.render.tradechart.component.ChartConfig

class XYAxisRender(private val chart: Chart) :
    BaseRender {

    private val myPaintLine = Paint().apply {
        color = Color.RED
        style = Paint.Style.STROKE
        strokeWidth = 2f
    }

    override fun draw(canvas: Canvas) {
        //horizontal
        canvas.drawLine(0f, chart.chartHeight, chart.width.toFloat() - ChartConfig.offsetRight, chart.chartHeight, myPaintLine)
        //vertical
        canvas.drawLine(chart.width.toFloat() - ChartConfig.offsetRight, chart.chartHeight, chart.width.toFloat() - ChartConfig.offsetRight, 0f, myPaintLine)
    }
}