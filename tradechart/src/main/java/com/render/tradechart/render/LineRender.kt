package com.render.tradechart.render

import android.animation.ValueAnimator
import android.graphics.*
import com.render.tradechart.chart.BaseChart
import com.render.tradechart.draw.GLESDraw
import com.render.tradechart.model.BaseRender
import com.render.tradechart.model.BaseSeries
import com.render.tradechart.series.LineSeries

class LineRender(private val chart: BaseChart) : BaseRender {
    private val p = Path()
    private val paintLine = Paint().apply {
        color = Color.BLUE
        style = Paint.Style.STROKE
        isAntiAlias = true
        strokeWidth = 2f
    }

    override fun draw(series: BaseSeries<*>) {
        series as LineSeries

        GLESDraw.drawLines(series.getScreenData(), paintLine)
    }
}