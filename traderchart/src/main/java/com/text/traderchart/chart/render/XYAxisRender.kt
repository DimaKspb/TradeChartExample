package com.text.traderchart.chart.render

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.text.traderchart.chart.model.BaseRender
import com.text.traderchart.chart.component.Chart
import com.text.traderchart.chart.component.ChartConfig
import com.text.traderchart.chart.model.BaseQuote
import com.text.traderchart.chart.model.BaseSeries

class XYAxisRender(private val chart: Chart) : BaseRender {

    private val myPaintLine = Paint().apply {
        color = Color.RED
        style = Paint.Style.STROKE
        strokeWidth = 2f
    }

    override fun draw(canvas: Canvas, series: BaseSeries<BaseQuote>) {
        //horizontal
        canvas.drawLine(0f, chart.chartHeight, chart.width.toFloat() - ChartConfig.offsetRight, chart.chartHeight, myPaintLine)
        //vertical
        canvas.drawLine(chart.width.toFloat() - ChartConfig.offsetRight, chart.chartHeight, chart.width.toFloat() - ChartConfig.offsetRight, 0f, myPaintLine)
    }
}