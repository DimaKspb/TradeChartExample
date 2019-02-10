package com.dima.tradechart.render

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import com.dima.tradechart.component.BaseRender
import com.dima.tradechart.component.Chart
import com.dima.tradechart.series.LineSeries

class GridRender(private val chart: Chart) : BaseRender<LineSeries>() {
    private var startPosition = chart.screenStartPosition
    private val stepWidth = chart.chartWidth / BOARD_LINES

    private val myPaintLine = Paint().apply {
        color = Color.GRAY
        alpha = 150
        style = Paint.Style.STROKE
        strokeWidth = 1f
        isAntiAlias = false
    }

    override fun draw(canvas: Canvas?, series: LineSeries?) {
        series ?: return
        val stepPoints = chart.chartWidth / chart.pointOnChart
        val countPointInStep = (stepWidth / stepPoints)
        val diff = startPosition - chart.screenStartPosition
        var linePosition = chart.getSceneXValue(diff)

        if (Math.abs(diff) >= countPointInStep) {
            startPosition = chart.screenStartPosition
        }

        for (i in 0 until BOARD_LINES) {
            canvas?.drawLine(linePosition, chart.offsetBottom, linePosition, chart.height.toFloat(), myPaintLine)
            linePosition += stepWidth
            if (i == BOARD_LINES - 1 && diff < 0) {
                canvas?.drawLine(linePosition, chart.offsetBottom, linePosition, chart.height.toFloat(), myPaintLine)
            }
        }

        val stepHeight = chart.height / BOARD_LINES
        var mHeight = stepHeight

        for (i in 0..BOARD_LINES) {
            canvas?.drawLine(
                0f,
                mHeight.toFloat(),
                chart.width.toFloat() - chart.offsetRight,
                mHeight.toFloat(),
                myPaintLine
            )

            mHeight += stepHeight
        }
    }

    companion object {
        const val BOARD_LINES = 10
    }
}