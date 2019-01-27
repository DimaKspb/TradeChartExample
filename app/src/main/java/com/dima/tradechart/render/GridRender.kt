package com.dima.tradechart.render

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.dima.tradechart.component.BaseRender
import com.dima.tradechart.component.Chart
import com.dima.tradechart.series.LineSeries

class GridRender(private val chart: Chart) : BaseRender<LineSeries>() {

    companion object {
        val BOARD_LINES = 10
    }

    private val myPaintLine = Paint().apply {
        color = Color.GRAY
        alpha = 150
        style = Paint.Style.STROKE
        strokeWidth = 1f
        isAntiAlias = false
    }

    override fun draw(canvas: Canvas?, series: LineSeries?) {
        series ?: return
        val stepWidth = chart.width / BOARD_LINES
        var mWidth = stepWidth
        var mWidthSetp = mWidth - chart.getSceneXValue(chart.getStartScreenPosition())


        for (i in 0 until BOARD_LINES) {
            canvas?.drawLine(mWidthSetp, chart.offsetBottom, mWidthSetp, chart.height.toFloat(), myPaintLine)
            mWidthSetp += stepWidth
        }

        val stepHeight = chart.height / BOARD_LINES
        var mHeight = stepHeight

        for (i in 0..BOARD_LINES) {
            canvas?.drawLine(0f, mHeight.toFloat(), chart.width.toFloat() - chart.offsetRight, mHeight.toFloat(), myPaintLine)

            mHeight += stepHeight
        }
    }
}