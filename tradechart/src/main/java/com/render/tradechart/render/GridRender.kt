package com.render.tradechart.render

import android.graphics.Color
import android.graphics.Paint
import com.render.tradechart.model.BaseRender
import com.render.tradechart.chart.Chart
import com.render.tradechart.draw.MyDraw
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.*
import com.render.tradechart.model.BaseQuote
import java.text.NumberFormat
import java.text.ParseException


class GridRender(private val chart: Chart) : BaseRender<BaseQuote> {
//    private val stepWidth = chart.chartWidth / BOARD_LINES
//    private val stepHeight = chart.chartHeight / BOARD_LINES

    private val horizontalValues = java.util.ArrayList<Double>()
    private val verticalValues = java.util.ArrayList<Double>()

    private var yLabelsVerticalOffset = 0f

    private val formatDate = SimpleDateFormat("hh:mm", Locale.ENGLISH)

    private val gridLinesPaint = Paint().apply {
        color = Color.GRAY
        alpha = 150
        style = Paint.Style.STROKE
        strokeWidth = 1f
        isAntiAlias = false
    }

    private val textLabelsPaint = Paint().apply {
        color = Color.BLACK
        textSize = 22f
    }

    override fun draw(canvas: MyDraw, baseSeries: BaseQuote) {
        computeGrid(verticalValues, chart.mySeries.minY, chart.mySeries.maxY, VERTICAL_LINES_COUNT)

        for (value in verticalValues) {
            if (value.isNaN()) {
                continue
            }
            drawYLabel(canvas, chart, value)
        }

        computeGrid(horizontalValues, chart.mySeries.minX, chart.mySeries.maxX,
                HORIZONTAL_LINES_COUNT
        )

        for (value in horizontalValues)
            drawXLabel(canvas, chart, value)

        drawerBorder(canvas, chart)
    }

    private fun drawerBorder(canvas: MyDraw, chart: Chart) {
        canvas.drawLine(0f, chart.height.toFloat(), chart.width.toFloat(), chart.height.toFloat(), gridLinesPaint)
        canvas.drawLine(chart.width.toFloat(), 0f, chart.width.toFloat(), chart.height.toFloat(), gridLinesPaint)
    }

    private fun drawXLabel(canvas: MyDraw, chart: Chart, values: Double) {
        canvas.drawLine(chart.getSceneXValue(values), 0f, chart.getSceneXValue(values), chart.height.toFloat(), gridLinesPaint)
        val label = formatDate.format(values)
        val labelWidth = textLabelsPaint.measureText(label)

        canvas.drawText(label!!, (chart.getSceneXValue(values)) - labelWidth / 2f, chart.height - 40f, textLabelsPaint)
    }

    private fun drawYLabel(canvas: MyDraw, chart: Chart, values: Double) {
        canvas.drawLine(0f, chart.getSceneYValue(values), chart.width.toFloat(), chart.getSceneYValue(values), gridLinesPaint)
        val label = values.toString()
        val labelWidth = textLabelsPaint.measureText(label)

        canvas.drawText(label, chart.width - labelWidth - 40f, chart.getSceneYValue(values) - yLabelsVerticalOffset, textLabelsPaint)
    }

    private fun computeGrid(values: java.util.ArrayList<Double>, s: Double, e: Double, num: Int) {
        FORMAT.maximumFractionDigits = 5
        val xStep = roundUp(abs(s - e) / num.toDouble())
        val xStart = xStep * ceil(s / xStep)
        val xEnd = xStep * floor(e / xStep)

        val numLines = 1 + ((xEnd - xStart) / xStep).toInt()
        values.clear()
        for (idx in 0 until numLines) {
            var ln = xStart + idx.toDouble() * xStep
            try {
                ln = FORMAT.parse(FORMAT.format(ln)).toDouble()
            } catch (var12: ParseException) {
                var12.printStackTrace()
            } catch (var13: NumberFormatException) {
                var13.printStackTrace()
            } catch (var14: ArrayIndexOutOfBoundsException) {
                var14.printStackTrace()
            }

            values.add(ln)
        }
    }

    private fun roundUp(value: Double): Double {
        val exponent = floor(log10(value)).toInt()
        var rval = value * 10.0.pow((-exponent).toDouble())
        when {
            rval > 5.0 -> rval = 10.0
            rval > 2.0 -> rval = 5.0
            rval > 1.0 -> rval = 2.0
        }

        rval *= 10.0.pow(exponent.toDouble())
        return rval
    }

    companion object {
        private const val HORIZONTAL_LINES_COUNT = 5
        private const val VERTICAL_LINES_COUNT = 5
        private val FORMAT = NumberFormat.getNumberInstance()


    }
}