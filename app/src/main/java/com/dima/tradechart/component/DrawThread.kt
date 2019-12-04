package com.dima.tradechart.component

import android.graphics.Color
import android.view.SurfaceHolder
import kotlinx.coroutines.*
import java.lang.Exception


class DrawThread(private val surfaceHolder: SurfaceHolder, private val chart: Chart) {
    private var job: Job? = null
    private var isDrawing = false
    private var lastTs = 0L

    fun startDraw() {
        try {
            job = GlobalScope.launch(Dispatchers.Default) {
                isDrawing = true
                chart.isAlreadyInit = true

                while (isDrawing) {
                    val ts = System.currentTimeMillis()
                    if (ts - lastTs > 10) {
                        val canvas = surfaceHolder.lockCanvas()
                        canvas?.apply {
                            drawColor(Color.WHITE)
                            chart.drawRenders(this)

                            surfaceHolder.unlockCanvasAndPost(this)
                            lastTs = ts
                        }
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