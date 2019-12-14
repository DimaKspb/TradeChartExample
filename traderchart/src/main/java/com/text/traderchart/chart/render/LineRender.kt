package com.text.traderchart.chart.render

import android.graphics.*
import android.graphics.Paint.ANTI_ALIAS_FLAG
import com.text.traderchart.chart.component.Chart
import com.text.traderchart.chart.component.logging
import com.text.traderchart.chart.draw.BaseRender
import com.text.traderchart.chart.model.BaseQuote
import com.text.traderchart.chart.model.BaseSeries


class LineRender(private var chart: Chart) : BaseRender {

    private val p = Path()

    private val paintLine = Paint(ANTI_ALIAS_FLAG).apply {
        color = Color.BLUE
        strokeWidth = 2f
        style = Paint.Style.STROKE
        pathEffect = CornerPathEffect(14f)
    }

    init {
        logging("init $chart")
    }

    private val matrix = Matrix()

    override fun onDraw(canvas: Canvas, series: BaseSeries<BaseQuote>) {
        drawAsPath(canvas, series.getScreenData())
    }

    override fun onUpdate(chart: Chart) {
//        this.chart = chart
    }

    private fun drawAsLine(canvas: Canvas, series: ArrayList<out BaseQuote>) {
        val ts = System.currentTimeMillis()

        val source = FloatArray(series.size * 4)
        var _i = 0
        for (i in 0 until series.size) {
            if (i >= 2) {
                source[_i++] = (chart.getSceneXValue(series[i - 1].getTime()))
                source[_i++] = (chart.getSceneYValue(series[i - 1].getValue()))
            }
            source[_i++] = (chart.getSceneXValue(series[i].getTime()))
            source[_i++] = (chart.getSceneYValue(series[i].getValue()))
        }

        canvas.drawLines(source, paintLine)
        logging(System.currentTimeMillis() - ts)
    }

    private fun drawAsPath(canvas: Canvas, series: ArrayList<out BaseQuote>) {
        p.rewind()

        for (i in 0 until series.size) {
            p.lineTo(
                    chart.getSceneXValue(series[i].getTime()),
                    chart.getSceneYValue(series[i].getValue())
            )
        }
        canvas.drawPath(p, paintLine)

//        matrix.mapPoints(pathsPointsTransformed, offset, points, offset, count)

    }
}