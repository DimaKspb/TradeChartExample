package com.text.traderchart.chart.render

import android.graphics.*
import com.text.traderchart.chart.component.Chart
import com.text.traderchart.chart.component.logging
import com.text.traderchart.chart.draw.BaseRender
import com.text.traderchart.chart.model.BaseQuote
import com.text.traderchart.chart.model.BaseSeries


class LineRender(private val chart: Chart) : BaseRender {

    private val p = Path()

    private val paintLine = Paint().apply {
        color = Color.BLUE
        style = Paint.Style.STROKE
        isAntiAlias = true
        strokeWidth = 2f
    }

    init {
        logging("init $chart")
    }

    private val matrix = Matrix()

    override fun draw(canvas: Canvas, series: BaseSeries<BaseQuote>) {
        drawAsPath(canvas, series.getData())
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

        for (i in 0 until source.size / 2) {
            floatArray[i] = source[i]
        }
        canvas.drawLines(floatArray, paintLine)
    }

    private fun drawAsPath(canvas: Canvas, series: ArrayList<out BaseQuote>) {
        p.rewind()

        val x0 = chart.getSceneXValue(series[0].getCurrentTime())
        val y0 = chart.getSceneYValue(series[0].getCurrentValue())

        p.moveTo(x0, y0)

        for (i in 1 until series.size / 4) {
            p.lineTo(
                    chart.getSceneXValue(series[i].getCurrentTime()),
                    chart.getSceneYValue(series[i].getCurrentValue())
            )
        }
        canvas.drawPath(p, paintLine)

//        matrix.mapPoints(pathsPointsTransformed, offset, points, offset, count)

    }
}