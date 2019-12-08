package com.dima.tradechart.render

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.dima.tradechart.model.BaseRender
import com.dima.tradechart.model.BaseSeries
import com.dima.tradechart.model.BaseQuote
import com.dima.tradechart.component.Chart
import com.dima.tradechart.component.ChartConfig
import com.dima.tradechart.series.LineSeries

class XYAxisRender(private val chart: Chart) : BaseRender {

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