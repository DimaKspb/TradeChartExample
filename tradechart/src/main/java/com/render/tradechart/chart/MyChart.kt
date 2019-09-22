package com.render.tradechart.chart

import com.render.tradechart.model.BaseSeries
import com.render.tradechart.model.TypeChart
import com.render.tradechart.render.LineRender
import com.render.tradechart.series.LineSeries

class MyChart : BaseChart() {


    init {
        renders.add(LineRender(this))
    }

}