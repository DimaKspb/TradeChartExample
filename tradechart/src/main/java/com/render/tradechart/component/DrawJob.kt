//package com.render.tradechart.component
//
//import android.graphics.Color
//import android.graphics.Paint
//import android.view.SurfaceHolder
//import kotlinx.coroutines.*
//import java.lang.Exception
//
//
//class DrawJob(private val surfaceHolder: SurfaceHolder, private val chart: Chart) {
//    private var job: Job? = null
//    private var isDrawing = false
//    private var lastTs = 0L
//    private val textPaint = Paint().apply {
//        textSize = 37f
//        color = Color.BLACK
//    }
//
//    fun startDraw() {
//        try {
//            job = GlobalScope.launch(Dispatchers.Default) {
//                isDrawing = true
//
//                while (isDrawing) {
//                    if (!chart.isChartInit) return@launch
//
//                    val ts = System.currentTimeMillis()
//                    measureFps(ts)
//                    if (surfaceHolder.surface.isValid) {
//                        val canvas = surfaceHolder.lockCanvas()
//                        canvas?.apply {
//
//                            drawColor(Color.WHITE)
//                            chart.drawRenders(this, ts)
//
//                            canvas.drawText(fps.toString(), 20F, (height / 2).toFloat(), textPaint)
//
//                            surfaceHolder.unlockCanvasAndPost(this)
//
//
//                            lastTs = ts
//                        }
//                    }
//                }
//            }
//        } catch (exp: Exception) {
//            isDrawing = false
//            exp.printStackTrace()
//        } finally {
//            chart.isChartInit = true
//        }
//    }
//
//    private var lastFpsCalcUptime: Long = 0
//    private var frameCounter = 0
//    private var fps = 0
//    private val FPS_CALC_INTERVAL = 1000
//
//
//    private fun measureFps(ts: Long) {
//        if (lastFpsCalcUptime == 0L || lastFpsCalcUptime - ts > 1000) {
//            lastFpsCalcUptime = ts
//        }
//
//        frameCounter++
//        val now = System.currentTimeMillis()
//        val delta = now - lastFpsCalcUptime
//        if (delta > 1000) {
//            fps = frameCounter * 1000 / delta.toInt()
//
//            frameCounter = 0
//            lastFpsCalcUptime = now
//        }
//    }
//
//    fun stopDraw() {
//        isDrawing = false
//        chart.isChartInit = false
//        job?.cancel()
//    }
//}