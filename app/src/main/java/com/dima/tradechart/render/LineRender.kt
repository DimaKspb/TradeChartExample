package com.dima.tradechart.render

import android.graphics.Canvas
import android.graphics.Path
import com.dima.tradechart.component.BaseRender
import com.dima.tradechart.component.Chart
import com.dima.tradechart.series.LineSeries


class LineRender(private val chart: Chart) : BaseRender<LineSeries>() {
    private val p = Path()

    override fun draw(canvas: Canvas?, series: LineSeries?) {
        series ?: return
        p.rewind()

        val mySeries = series.getScreenData(chart)
        p.moveTo(0f, 0f)
        for (i in 0 until mySeries.size) {
            p.lineTo(chart.getSceneXValue(i).toFloat(), chart.getSceneYValue(mySeries[i].bid).toFloat())
            canvas?.drawPath(p, chart.paintLine)
        }
    }
}