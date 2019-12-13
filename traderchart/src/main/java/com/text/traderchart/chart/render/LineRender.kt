package com.text.traderchart.chart.render

import android.graphics.*
import com.text.traderchart.chart.component.Chart
import com.text.traderchart.chart.model.BaseQuote
import com.text.traderchart.chart.model.BaseRender
import com.text.traderchart.chart.model.BaseSeries


class LineRender(private val chart: Chart) : BaseRender {

    private val p = Path()

    private val paintLine = Paint().apply {
        color = Color.BLUE
        style = Paint.Style.STROKE
        isAntiAlias = true
        strokeWidth = 2f
    }

    private val matrix = Matrix()

    override fun draw(canvas: Canvas, series: BaseSeries<BaseQuote>) {
        drawAsLine(canvas, series.getData())
    }

    private fun drawAsLine(canvas: Canvas, series: ArrayList<out BaseQuote>) {
        val source = arrayListOf<Float>()

        for (i in 0 until series.size step 2) {
            if (i != 0) {
                source.add(chart.getSceneXValue(series[i - 1].getCurrentTime()))
                source.add(chart.getSceneYValue(series[i - 1].getCurrentValue()))
            }
            source.add(chart.getSceneXValue(series[i].getCurrentTime()))
            source.add(chart.getSceneYValue(series[i].getCurrentValue()))

            source.add(chart.getSceneXValue(series[i + 1].getCurrentTime()))
            source.add(chart.getSceneYValue(series[i + 1].getCurrentValue()))
        }

        val floatArray = FloatArray(source.size / 2)

        for (i in 0 until source.size/2){
            floatArray[i] = source[i]
        }
        canvas.drawLines(floatArray, paintLine)
    }

    private fun drawAsPath(canvas: Canvas, series: BaseSeries<BaseQuote>) {
        p.reset()

        val points = floatArrayOf()
        val mySeries = series.getData()

        val x0 = chart.getSceneXValue(mySeries[0].getCurrentTime())
        val y0 = chart.getSceneYValue(mySeries[0].getCurrentValue())

        p.moveTo(x0, y0)

        for (i in 1 until mySeries.size) {
            p.lineTo(
                chart.getSceneXValue(mySeries[i].getCurrentTime()),
                chart.getSceneYValue(mySeries[i].getCurrentValue())
            )
        }

//        matrix.mapPoints(pathsPointsTransformed, offset, points, offset, count)

        canvas.drawPath(p, paintLine)
    }
}