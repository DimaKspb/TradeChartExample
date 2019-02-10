package com.dima.tradechart.render

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.graphics.*
import android.util.Log
import android.view.animation.PathInterpolator
import com.dima.tradechart.component.BaseRender
import com.dima.tradechart.component.Chart
import com.dima.tradechart.series.LineSeries

class LineRender(private val chart: Chart) : BaseRender<LineSeries>() {
    private val p = Path()
    private val paintLine = Paint().apply {
        color = Color.BLUE
        style = Paint.Style.STROKE
        isAntiAlias = true
        strokeWidth = 2f
    }

    override fun draw(canvas: Canvas?, series: LineSeries?) {
        series ?: return
        p.rewind()

        val mySeries = series.getScreenData(chart)
        p.moveTo(chart.getSceneXValue(0), chart.getSceneYValue(mySeries[0].bid))
        var prevPointX: Float? = null
        var prevPointY: Float? = null

        for (i in 0 until mySeries.size) {
            val pointX = chart.getSceneXValue(i)
            val pointY = chart.getSceneYValue(mySeries[i].bid)

            if (i == 0)
                p.lineTo(pointX, pointY)
            else if (prevPointX != null && prevPointY != null) {
                val midX = (prevPointX + pointX) / 2
                val midY = (prevPointY + pointY) / 2

                if (i == 1)
                    p.lineTo(midX, midY)
                else
                    p.quadTo(prevPointX, prevPointY, midX, midY)

            }
            prevPointX = pointX
            prevPointY = pointY
        }

        if (prevPointY != null && prevPointX != null) {
            p.lineTo(prevPointX, prevPointY)
        }

        canvas?.drawPath(p, paintLine)
    }
}