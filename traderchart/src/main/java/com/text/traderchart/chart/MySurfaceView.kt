package com.text.traderchart.chart

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.text.traderchart.chart.component.Chart
import com.dima.tradechart.component.ChartConfig
import com.text.traderchart.chart.component.DrawThread
import com.dima.tradechart.component.MyScroller
import com.dima.tradechart.model.Quote
import com.dima.tradechart.model.data
import com.dima.tradechart.series.LineSeries
import java.util.*
import java.util.concurrent.ThreadLocalRandom


class MySurfaceView : SurfaceView, SurfaceHolder.Callback {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private var myHolder: SurfaceHolder
    private var chart: Chart? = null

    private var scroller: MyScroller? = null
    private var drawThread = DrawThread()

    init {
        myHolder = holder
        holder.addCallback(this)
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        Log.d("Surface", "surfaceChanged$width , $height")
        ChartConfig.setDisplayMetrics(resources.displayMetrics)

        myHolder = holder

        chart = Chart(height, width).apply {
            scroller = MyScroller(context, this)
            val series = LineSeries().apply {
                addAllPoint(data())
                setSeries(mySeries)
            }
            setSeries(series)

            drawThread.setHolder(myHolder)
            drawThread.setChart(this)

            drawThread.start()
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        scroller?.onTouch(this, event)

        val action = event.action

        return true
    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {
        Log.d("Surface", "surfaceDestroyed")
        drawThread?.stopDraw()
    }

    override fun surfaceCreated(holder: SurfaceHolder?) {
        Log.d("Surface", "surfaceCreated")
    }

    fun initRandomData() {
        chart?.updateLastQuote(Quote(ThreadLocalRandom.current().nextDouble(1.15080, 1.15100), 0.0, Calendar.getInstance().timeInMillis))
    }

    fun move(i: Int) {
        chart?.scrolling(i >= 0)
    }
}