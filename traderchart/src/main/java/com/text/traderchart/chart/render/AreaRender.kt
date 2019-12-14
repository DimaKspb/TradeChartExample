package com.text.traderchart.chart.render

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.text.traderchart.chart.component.Chart
import com.text.traderchart.chart.component.ChartConfig
import com.text.traderchart.chart.draw.BaseRender
import com.text.traderchart.chart.model.BaseQuote
import com.text.traderchart.chart.model.BaseSeries

class AreaRender(private val chart: Chart) : BaseRender {

    private val myPaintLine = Paint().apply {
        color = Color.RED
        style = Paint.Style.STROKE
        strokeWidth = 2f
    }

    override fun onUpdate(chart: Chart) {

    }

    override fun onDraw(canvas: Canvas, series: BaseSeries<BaseQuote>) {
        //Background
        canvas.drawColor(Color.WHITE)
        //horizontal
        canvas.drawLine(0f, chart.heightWithPadding, chart.width - ChartConfig.offsetRight, chart.heightWithPadding, myPaintLine)
        //vertical
        canvas.drawLine(chart.width - ChartConfig.offsetRight, chart.heightWithPadding, chart.width - ChartConfig.offsetRight, 0f, myPaintLine)
    }


}