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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception


class DrawThread(private val surfaceHolder: SurfaceHolder, private val chart: Chart) {
    var isDrawing = false

    private var axisXYRender = XYAxisRender(chart)
    private var lineRender = LineRender(chart)
    private var gridRender = GridRender(chart)

    init {
        Log.d("myThread", "init")
    }

    fun initDraw() {
        try {
            GlobalScope.launch(Dispatchers.Default) {
                isDrawing = true
                chart.isAlreadyInit = true
//                Log.d("DrawThread", "size series:${chart.myLineSeries.lineSeries.size}")
                while (isDrawing) {
//                    delay(1000)
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
            exp.printStackTrace()
            isDrawing = false
        } finally {
            chart.isAlreadyInit = true
        }
    }
}