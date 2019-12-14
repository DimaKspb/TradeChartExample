package com.text.traderchart.chart.draw

import android.graphics.Canvas
import com.text.traderchart.chart.model.BaseQuote
import com.text.traderchart.chart.model.BaseSeries

interface ISurfaceRender {
    fun onDrawFrame(canvas: Canvas)
}

interface BaseRender {
    fun draw(canvas: Canvas, series: BaseSeries<BaseQuote>)
}
