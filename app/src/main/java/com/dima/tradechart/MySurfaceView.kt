package com.dima.tradechart

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.dima.tradechart.MainActivity
import com.dima.tradechart.component.Chart
import com.dima.tradechart.component.DrawThread
import com.dima.tradechart.component.MyScroller
import com.dima.tradechart.component.Quote
import kotlinx.coroutines.delay
import java.util.*
import java.util.concurrent.ThreadLocalRandom
import android.text.method.Touch.onTouchEvent
import android.view.GestureDetector
import android.view.MotionEvent


class MySurfaceView : SurfaceView, SurfaceHolder.Callback {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private var chart: Chart? = null
    private var myHolder: SurfaceHolder
    private var scroller: MyScroller? = null
    private var myThread: DrawThread? = null

    init {
        myHolder = holder
        holder.addCallback(this)
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        Log.d("Surface", "surfaceChanged$width , $height")
        myHolder = holder
        chart = Chart(height, width)

        chart?.let {
            scroller = MyScroller(context, it)
            myThread = DrawThread(myHolder, it)

            myThread?.initDraw()
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        scroller?.scaleGestureDetector?.onTouchEvent(event)
        return true
    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {
        Log.d("Surface", "surfaceDestroyed")
    }

    override fun surfaceCreated(holder: SurfaceHolder?) {
        Log.d("Surface", "surfaceCreated")
    }

    suspend fun initRandomData() {
        while(true) {
            delay(1500)
            chart?.updateLastQuote(Quote(ThreadLocalRandom.current().nextDouble(1.15100, 1.15120), 0.0, Calendar.getInstance().timeInMillis))
        }
    }
}