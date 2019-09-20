package com.render.tradechart.chart

import com.render.tradechart.model.BaseRender
import com.render.tradechart.model.BaseSeries
import com.render.tradechart.model.TypeChart

abstract class BaseChart {
    protected var typeChart = TypeChart.LINE
    protected var chartEnable = false

    protected val renders = arrayListOf<BaseRender>()

    protected var currentSeries: BaseSeries<*>? = null

    protected var width = 0
    protected var height = 0

    abstract fun setSeries(baseSeries: BaseSeries<*>)
    abstract fun setSize(w: Int, h: Int)
}