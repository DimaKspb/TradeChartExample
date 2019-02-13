package com.dima.tradechart.render

import android.annotation.SuppressLint
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.Log
import com.dima.tradechart.component.BaseRender
import com.dima.tradechart.component.Chart
import com.dima.tradechart.series.LineSeries
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class GridRender(private val chart: Chart) : BaseRender<LineSeries>() {
    private var startPosition = chart.screenStartPosition
    private val stepWidth = chart.chartWidth / BOARD_LINES
    private val stepHeight = chart.chartHeight / BOARD_LINES

    private val formatDate = SimpleDateFormat("hh:mm", Locale.ENGLISH)
    private val timeLabels = ArrayList<Long>()

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

    @SuppressLint("SimpleDateFormat")
    override fun draw(canvas: Canvas?, series: LineSeries?) {
        series ?: return

        drawVericalLineAndLabel(canvas)
        drawHorizontalLineAndLabel(canvas)
    }

    private fun drawHorizontalLineAndLabel(canvas: Canvas?) {
        var mHeight = stepHeight
        val stepHorizontal = ((chart.myLineSeries.maxY - chart.myLineSeries.minY).toFloat() / BOARD_LINES)
        var firstValueHorizontal = (chart.myLineSeries.minY + stepHorizontal).toFloat()

        for (i in 0 until BOARD_LINES) {
            val rect = Rect()
            textPaint.getTextBounds(firstValueHorizontal.toString(), 0, firstValueHorizontal.toString().length, rect)

            canvas?.drawText(firstValueHorizontal.toString(), chart.width.toFloat() - chart.offsetRight + 5f, mHeight + rect.height() / 2, textPaint)
            canvas?.drawLine(0f, mHeight, chart.width.toFloat() - chart.offsetRight, mHeight, myPaintLine)

            firstValueHorizontal += stepHorizontal
            mHeight += stepHeight
        }
    }

    private fun drawVericalLineAndLabel(canvas: Canvas?) {
        val stepPoints = chart.chartWidth / (chart.pointOnChart * 2)
        val countPointInStep = (stepWidth / stepPoints)
        val diff = startPosition - chart.screenStartPosition
        var linePosition = chart.getSceneXValue(diff)

        if (Math.abs(diff) >= countPointInStep) {
            startPosition = chart.screenStartPosition
        }

        val stepVer = (chart.myLineSeries.visibleScreenData.size / BOARD_LINES)
        for (i in 0 until chart.myLineSeries.visibleScreenData.size step stepVer) {
            timeLabels.add(chart.myLineSeries.visibleScreenData[i].time)
        }

        for (i in 0 until BOARD_LINES) {
            val xValue = formatDate.format(Date(timeLabels[i]))
            val rect = Rect()
            textPaint.getTextBounds(xValue, 0, xValue.length, rect)

            canvas?.drawLine(linePosition, 0f, linePosition, chart.chartHeight, myPaintLine)
            canvas?.drawText(xValue, linePosition - (rect.width() / 2), chart.chartHeight + rect.height() + 5f, textPaint)

            linePosition += stepWidth

            if (i == BOARD_LINES - 1 && diff < 0) {
                canvas?.drawLine(linePosition, 0f, linePosition - (rect.width() / 2), chart.chartHeight, myPaintLine)
            }
        }

    }

    companion object {
        const val BOARD_LINES = 10
    }
}