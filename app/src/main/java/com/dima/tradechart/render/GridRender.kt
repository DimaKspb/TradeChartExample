package com.dima.tradechart.render

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import com.dima.tradechart.component.BaseRender
import com.dima.tradechart.component.BasesQuote
import com.dima.tradechart.component.Chart
import com.dima.tradechart.component.Quote
import com.dima.tradechart.series.LineSeries

class GridRender(private val chart: Chart) : BaseRender<LineSeries>() {
    private var startPosition = chart.screenStartPosition
    private val stepWidth = chart.chartWidth / BOARD_LINES
    private val stepHeight = chart.chartHeight / BOARD_LINES

    private val myPaintLine = Paint().apply {
        color = Color.GRAY
        alpha = 150
        style = Paint.Style.STROKE
        strokeWidth = 1f
        isAntiAlias = false
    }

    private val textPaint = Paint().apply {
        color = Color.BLACK
        textSize = 22f
    }

    override fun draw(canvas: Canvas?, series: LineSeries?) {
        series ?: return

        //vertical
        val stepPoints = chart.chartWidth / (chart.pointOnChart * 2)
        val countPointInStep = (stepWidth / stepPoints)
        val diff = startPosition - chart.screenStartPosition
        var linePosition = chart.getSceneXValue(diff)

        if (Math.abs(diff) >= countPointInStep) {
            startPosition = chart.screenStartPosition
        }

        for (i in 0 until BOARD_LINES) {
            canvas?.drawLine(linePosition, 0f, linePosition, chart.chartHeight, myPaintLine)
            canvas?.drawText("Date", linePosition, chart.chartHeight + 20, textPaint)
            linePosition += stepWidth
            if (i == BOARD_LINES - 1 && diff < 0) {
                canvas?.drawLine(linePosition, 0f, linePosition, chart.chartHeight, myPaintLine)
            }
        }

        //horizontal
        var mHeight = stepHeight
        for (i in 0..BOARD_LINES) {
            canvas?.drawLine(0f, mHeight.toFloat(), chart.width.toFloat() - chart.offsetRight, mHeight.toFloat(), myPaintLine)

            mHeight += stepHeight
        }
    }

    companion object {
        const val BOARD_LINES = 10
    }
}