package com.dima.tradechart.render

import android.animation.ValueAnimator
import android.graphics.*
import com.dima.tradechart.model.BaseRender
import com.dima.tradechart.component.Chart
import com.dima.tradechart.series.LineSeries


class LineRender(private val chart: Chart) : BaseRender {
    private val p = Path()
    private val paintLine = Paint().apply {
        color = Color.BLUE
        style = Paint.Style.STROKE
        isAntiAlias = true
        strokeWidth = 2f
    }

    override fun draw(canvas: Canvas, series: LineSeries) {
        p.rewind()

        val mySeries = series.getScreenData()
        p.moveTo(
                chart.getSceneXValue(mySeries[0].timeDouble),
                chart.getSceneYValue(mySeries[0].bid)
        )
        var prevPointX: Float? = null
        var prevPointY: Float? = null

        for (i in 0 until mySeries.size) {
            val pointX = chart.getSceneXValue(mySeries[i].timeDouble)
            val pointY = chart.getSceneYValue(mySeries[i].bid)

            if (i == 0)
                p.lineTo(pointX, pointY)
            else if
                         (prevPointX != null && prevPointY != null) {
                val midX = (prevPointX + pointX) / 2
                val midY = (prevPointY + pointY) / 2

                if (i == 1)
                    p.lineTo(midX, midY)
                else if (i == mySeries.size - 1) {
                    val objectAnimator1 = ValueAnimator.ofFloat(prevPointX, midX)
//                    val objectAnimator2 = ValueAnimator.ofFloat(prevPointY, midY)
//                    val animSet = AnimatorSet()
//                    animSet.playTogether(objectAnimator1, objectAnimator2)

//                    animSet.addListener(object : AnimatorListenerAdapter() {
//                        override fun onAnimationStart(animation: Animator?) {
//                            Log.d("Animate", "${animation.toString()}")
//                        }
//                    })
//                    animSet.start()
                } else
                    p.quadTo(prevPointX, (prevPointY), midX, (midY))

            }
            prevPointX = pointX
            prevPointY = pointY
        }

        if (prevPointY != null && prevPointX != null) {
//            p.lineTo(prevPointX, (chart.offsetBottom + prevPointY))
        }

        canvas?.drawPath(p, paintLine)
    }
}