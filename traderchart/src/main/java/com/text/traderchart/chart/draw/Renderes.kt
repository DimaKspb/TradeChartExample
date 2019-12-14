package com.text.traderchart.chart.draw

import android.graphics.Canvas
import com.text.traderchart.chart.component.Chart
import com.text.traderchart.chart.model.BaseQuote
import com.text.traderchart.chart.model.BaseSeries

interface ISurfaceRender {
    fun onDrawFrame(canvas: Canvas, lastFrameTime: Long)
}

interface BaseRender {
    fun onDraw(canvas: Canvas, series: BaseSeries<BaseQuote>)
    fun onUpdate(chart: Chart)
}
