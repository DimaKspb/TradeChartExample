package com.text.traderchart.chart.draw

import android.graphics.Canvas
import com.text.traderchart.chart.model.BaseQuote
import com.text.traderchart.chart.model.BaseSeries

class SurfaceRenderManager {
    private val myRenders = arrayListOf<BaseRender>()

    fun addRender(render: BaseRender) {
        myRenders.add(render)
    }

    fun addRender(vararg render: BaseRender) {
        myRenders.addAll(render)
    }

    fun removeRender(render: BaseRender) {
        myRenders.remove(render)
    }

    fun drawRenders(canvas: Canvas, series: BaseSeries<BaseQuote>) {
        for (i in myRenders) {
            i.draw(canvas, series)
        }
    }
}