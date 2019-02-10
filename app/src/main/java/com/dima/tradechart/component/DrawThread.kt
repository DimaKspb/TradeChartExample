package com.dima.tradechart.component

import android.animation.ObjectAnimator
import android.graphics.Color
import android.os.Build
import android.support.annotation.RequiresApi
import android.view.SurfaceHolder
import android.util.Log
import com.dima.tradechart.render.GridRender
import com.dima.tradechart.render.LineRender
import com.dima.tradechart.render.XYAxisRender
import kotlinx.coroutines.*
import java.lang.Exception


class DrawThread(private val surfaceHolder: SurfaceHolder, private val chart: Chart) {
    var isDrawing = false

    private val axisXYRender = XYAxisRender(chart)
    private val lineRender = LineRender(chart)
    private val gridRender: GridRender by lazy { GridRender(chart) }
    private var job: Job? = null

    fun startDraw() {
        try {
            job = GlobalScope.launch(Dispatchers.Default) {
                isDrawing = true
                chart.isAlreadyInit = true

                while (isDrawing) {
                    delay(10)

                    val canvas = surfaceHolder.lockCanvas()
                    canvas?.apply {
                        drawColor(Color.WHITE)
                        translate(0f, chart.height.toFloat())
                        canvas.scale(1f, -1f)
                        axisXYRender.draw(this)
                        lineRender.draw(this, chart.myLineSeries)
                        gridRender.draw(this, chart.myLineSeries)

                        surfaceHolder.unlockCanvasAndPost(this)
                    }
                }
            }
        } catch (exp: Exception) {
            isDrawing = false
            exp.printStackTrace()
        } finally {
            chart.isAlreadyInit = true
        }
    }

    fun stopDraw() {
        isDrawing = false
        chart.isAlreadyInit = false
        job?.cancel()
    }
}