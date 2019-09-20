package com.render.tradechart.render

import android.graphics.Color
import android.graphics.Paint
import com.render.tradechart.model.BaseRender
import com.render.tradechart.chart.Chart
import com.render.tradechart.component.ChartConfig
import com.render.tradechart.draw.MyDraw

class XYAxisRender(private val chart: Chart) :
    BaseRender {

    private val myPaintLine = Paint().apply {
        color = Color.RED
        style = Paint.Style.STROKE
        strokeWidth = 2f
    }

    override fun draw(canvas: MyDraw) {
        //horizontal
        canvas.drawLine(0f, chart.chartHeight, chart.width.toFloat() - ChartConfig.offsetRight, chart.chartHeight, myPaintLine)
        //vertical
        canvas.drawLine(chart.width.toFloat() - ChartConfig.offsetRight, chart.chartHeight, chart.width.toFloat() - ChartConfig.offsetRight, 0f, myPaintLine)
    }
}