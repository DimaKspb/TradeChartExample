package com.render.tradechart.chart

import com.render.tradechart.model.BaseRender
import com.render.tradechart.model.BaseSeries
import com.render.tradechart.model.TypeChart
import com.render.tradechart.render.LineRender
import com.render.tradechart.series.LineSeries

abstract class BaseChart {
    protected var typeChart = TypeChart.LINE
    protected var chartEnable = false

    protected val renders = arrayListOf<BaseRender>()

    protected var currentSeries: BaseSeries<*> = LineSeries()

    var width = 0
    var height = 0

    fun setSize(w: Int, h: Int) {
        width = w
        height = h

        chartEnable = true
    }

    fun setSeries(baseSeries: BaseSeries<*>) {
        chartEnable = false
        setTypeChart(baseSeries is LineSeries)

        currentSeries = baseSeries
        chartEnable = true
    }


    protected fun setTypeChart(isLine: Boolean) {
        typeChart = if (isLine) TypeChart.LINE
        else
            TypeChart.CANDLE
    }

    fun draw() {
        if (chartEnable)
            renders.forEach { it.draw(currentSeries) }
    }
}