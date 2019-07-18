package com.dima.tradechart

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.widget.Scroller
import com.dima.tradechart.component.Chart
import com.dima.tradechart.component.ChartConfig
import com.dima.tradechart.component.DrawJob
import com.dima.tradechart.component.MyScroller
import com.dima.tradechart.model.Quote
import com.dima.tradechart.model.data
import com.dima.tradechart.series.LineSeries
import kotlinx.coroutines.delay
import java.util.*
import java.util.concurrent.ThreadLocalRandom


class MySurfaceView : SurfaceView, SurfaceHolder.Callback {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private var myHolder: SurfaceHolder
    private var chart: Chart? = null

    private var scroller: MyScroller? = null
    private var myJob: DrawJob? = null

    init {
        myHolder = holder
        holder.addCallback(this)
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        Log.d("Surface", "surfaceChanged$width , $height")
        ChartConfig.setDisplayMetrics(resources.displayMetrics)

        myHolder = holder
        chart = Chart(height, width)

        chart?.apply {
            scroller = MyScroller(context, this)
            myJob = DrawJob(myHolder, this)
            val mySeries = LineSeries()
            mySeries.addAllPoint(data())
            setSeries(mySeries)

            myJob?.startDraw()
        }
    }

    private var mLastFlingY: Int = 0
    private var mLastY: Float = 0.toFloat()
    private var mDeltaY: Float = 0.toFloat()
    private var mScrollEventChecker: Scroller? = null
    private var counter = 0

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        scroller?.onTouch(this, event)

        val action = event.action

        return true
    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {
        Log.d("Surface", "surfaceDestroyed")
        myJob?.stopDraw()
    }

    override fun surfaceCreated(holder: SurfaceHolder?) {
        Log.d("Surface", "surfaceCreated")
    }

    suspend fun initRandomData() {
        while (true) {
            delay(1500)
            chart?.updateLastQuote(
                Quote(
                    ThreadLocalRandom.current().nextDouble(1.15080, 1.15100),
                    0.0,
                    Calendar.getInstance().timeInMillis
                )
            )
        }
    }

    fun move(i: Int) {
        chart?.scrolling(i >= 0)
    }
}