package com.dima.tradechart.component

import android.graphics.Canvas
import android.graphics.Color
import android.view.SurfaceHolder
import android.util.Log
import com.dima.tradechart.render.LineRender
import com.dima.tradechart.render.XYAxisRender
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception


class DrawThread(private val surfaceHolder: SurfaceHolder, private val chart: Chart) {
    var isDrawing = false

    var asisXYRender = XYAxisRender(chart)
    var lineRender = LineRender(chart)
    var canvas: Canvas? = null

    init {
        Log.d("myThread", "init")
    }

    fun initDraw() {
        try {
            canvas = surfaceHolder.lockCanvas()
            canvas?.drawColor(Color.WHITE)
            canvas?.save()
            canvas?.translate(0f, chart.height.toFloat())
            canvas?.scale(1f, -1f)
            Log.d("myThread", "run series${chart.myLineSeries.lineSeries.size}")

            asisXYRender.draw(canvas!!, null)
            lineRender.draw(canvas!!, chart.myLineSeries)
//            }
        } catch (exp: Exception) {
            exp.printStackTrace()
        } finally {
            chart.isAlreadyInit = true
            surfaceHolder.unlockCanvasAndPost(canvas)
        }
    }

    fun updateDraw() {
//        GlobalScope.launch {
        try {
            canvas = surfaceHolder.lockCanvas()

            canvas?.drawColor(Color.WHITE)
            canvas?.save()
            canvas?.translate(0f, chart.height.toFloat())
            canvas?.scale(1f, -1f)

            asisXYRender.draw(canvas!!)
            lineRender.draw(canvas!!, chart.myLineSeries)

        } finally {
            surfaceHolder.unlockCanvasAndPost(canvas)
        }

//        }
    }
}