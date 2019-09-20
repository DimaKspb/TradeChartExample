package com.render.tradechart.chart

import com.render.tradechart.model.BaseSeries

class MyChart : BaseChart() {

    override fun setSeries(baseSeries: BaseSeries<*>) {
        currentSeries = baseSeries
    }

    override fun setSize(w: Int, h: Int) {
        width = w
        height = h
    }


}