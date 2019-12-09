package com.text.traderchart.chart.component

import android.graphics.Color
import android.graphics.Paint
import android.view.SurfaceHolder
import java.lang.Exception

class DrawThread : Thread() {
    private var isDrawing = false
    private var lastTs = 0L
    private var chart = Chart()
    private var surfaceHolder: SurfaceHolder? = null

    private val textPaint = Paint().apply {
        textSize = 37f
        color = Color.BLACK
    }

    fun setChart(chart: Chart) {
        this.chart = chart
    }

    override fun run() {
        try {
            isDrawing = true

            while (isDrawing) {
                if (!chart.isChartInit) return

                val ts = System.currentTimeMillis()
                measureFps(ts)
                if (surfaceHolder?.surface?.isValid == true) {
                    val canvas = surfaceHolder?.lockCanvas()
                    canvas?.apply {

                        drawColor(Color.WHITE)
                        chart.drawRenders(this, ts)

                        canvas.drawText(fps.toString(), 20F, (height / 2).toFloat(), textPaint)

                        surfaceHolder?.unlockCanvasAndPost(this)


                        lastTs = ts
                    }
                }
            }
        } catch (exp: Exception) {
            isDrawing = false
            exp.printStackTrace()
        } finally {
            chart.isChartInit = true
        }
    }

    private var lastFpsCalcUptime: Long = 0
    private var frameCounter = 0
    private var fps = 0
    private val FPS_CALC_INTERVAL = 1000


    private fun measureFps(ts: Long) {
        if (lastFpsCalcUptime == 0L || lastFpsCalcUptime - ts > 1000) {
            lastFpsCalcUptime = ts
        }

        frameCounter++
        val now = System.currentTimeMillis()
        val delta = now - lastFpsCalcUptime
        if (delta > 1000) {
            fps = frameCounter * 1000 / delta.toInt()

            frameCounter = 0
            lastFpsCalcUptime = now
        }
    }

    fun stopDraw() {
        isDrawing = false
        chart.isChartInit = false
        join()
    }

    fun setHolder(myHolder: SurfaceHolder) {
        surfaceHolder = myHolder
    }
}