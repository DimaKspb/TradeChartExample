package com.text.traderchart.chart

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.text.traderchart.chart.component.Chart
import com.text.traderchart.chart.component.ChartConfig
import com.text.traderchart.chart.draw.DrawThread
import com.text.traderchart.chart.component.MyScroller
import com.text.traderchart.chart.component.logging
import com.text.traderchart.chart.draw.ISurfaceRender
import com.text.traderchart.chart.draw.SurfaceRenderManager
import com.text.traderchart.chart.model.BaseQuote
import com.text.traderchart.chart.model.BaseSeries
import com.text.traderchart.chart.model.Quote
import com.text.traderchart.chart.render.GridRender
import com.text.traderchart.chart.render.LineRender
import com.text.traderchart.chart.render.AreaRender
import java.util.*
import java.util.concurrent.ThreadLocalRandom


class MySurfaceView : SurfaceView, SurfaceHolder.Callback, ISurfaceRender {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    private var myHolder: SurfaceHolder

    private var chart: Chart = Chart()
    private var scroller: MyScroller? = null

    private val surfaceRenderManager = SurfaceRenderManager()
    private var drawThread = DrawThread(this)

    init {
        myHolder = holder
        drawThread.setHolder(myHolder)
        holder.addCallback(this)
        drawThread.start()
    }

    override fun onDrawFrame(canvas: Canvas) {
        surfaceRenderManager.drawRenders(canvas, chart.getSeries())

        chart.updateAnimator()
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        Log.d("Surface", "surfaceChanged$width , $height ,$format")
        myHolder = holder
        chart.initSize(height, width)
        surfaceRenderManager.addRender(AreaRender(chart), GridRender(chart), LineRender(chart))
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        Log.d("Surface", "surfaceDestroyed")
//        drawThread.stopDraw()
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        Log.d("Surface", "surfaceCreated")
        ChartConfig.setDisplayMetrics(resources.displayMetrics)
        ChartConfig.logging = true
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        scroller?.onTouch(this, event)

        val action = event.action

        return true
    }

    fun setSeries(series: BaseSeries<BaseQuote>) {
        chart.setSeriesWithAnimate(series)
    }

    fun initRandomData() {
        chart.updateLastQuote(
            Quote(
                ThreadLocalRandom
                    .current()
                    .nextDouble(1.15080, 1.15100),
                0.0,
                Calendar.getInstance().timeInMillis
            )
        )
    }

    fun moveToLeft() {

    }
}