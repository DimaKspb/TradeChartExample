package com.text.traderchart.chart.draw

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Build
import android.util.Log
import android.view.SurfaceHolder
import com.text.traderchart.chart.component.logging
import java.lang.Exception

class DrawThread(private val drawListener: ISurfaceRender) : Thread() {
    private var isDrawing = false
    private var lastTs = 0L
    private var surfaceHolder: SurfaceHolder? = null

    private val textPaint = Paint().apply {
        textSize = 37f
        color = Color.BLACK
    }

    private val timeoutMS = 15

    override fun run() {
        try {
            isDrawing = true
            logging("start Draw")

            while (isDrawing) {
                if (surfaceHolder?.surface?.isValid == true) {
                    val canvas = getCanvas()
                    canvas?.apply {
                        drawListener.onDrawFrame(canvas)
                        surfaceHolder?.unlockCanvasAndPost(this)
                    }
                }
            }
        } catch (exp: Exception) {
            isDrawing = false
            exp.printStackTrace()
        }
    }

    private fun getCanvas(): Canvas? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            surfaceHolder?.lockHardwareCanvas()
        } else
            surfaceHolder?.lockCanvas()
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
        Log.d("Deltas", "$fps , $delta , $frameCounter")

        if (delta > 1000) {
            fps = frameCounter * 1000 / delta.toInt()


            frameCounter = 0
            lastFpsCalcUptime = now
        }
    }

    fun stopDraw() {
        isDrawing = false
        join()
    }

    fun setHolder(myHolder: SurfaceHolder) {
        surfaceHolder = myHolder
    }
}