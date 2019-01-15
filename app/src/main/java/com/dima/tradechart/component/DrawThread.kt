package com.dima.tradechart.component

import android.graphics.Color
import android.view.SurfaceHolder
import android.util.Log
import com.dima.tradechart.render.LineRender
import com.dima.tradechart.render.XYAxisRender
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception


class DrawThread(private val surfaceHolder: SurfaceHolder, private val chart: Chart) {
    var isDrawing = false

    private var axisXYRender = XYAxisRender(chart)
    private var lineRender = LineRender(chart)

    init {
        Log.d("myThread", "init")
    }

    fun initDraw() {
        try {
            GlobalScope.launch(Dispatchers.Default) {
                isDrawing = true
                chart.isAlreadyInit = true
                while (isDrawing) {
                    val canvas = surfaceHolder.lockCanvas()

                    canvas?.apply {
                        drawColor(Color.WHITE)
                        translate(0f, chart.height.toFloat())
                        canvas.scale(1f, -1f)
                        Log.d("DrawThread", "size series:${chart.myLineSeries.lineSeries.size}")

                        axisXYRender.draw(this)
                        lineRender.draw(this, chart.myLineSeries)

                        surfaceHolder.unlockCanvasAndPost(this)
                    }
                }
            }
        } catch (exp: Exception) {
            exp.printStackTrace()
            isDrawing = false
        } finally {
            chart.isAlreadyInit = true
        }
    }
}