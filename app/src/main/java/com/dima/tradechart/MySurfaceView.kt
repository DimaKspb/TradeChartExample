package com.dima.tradechart

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.dima.tradechart.component.*
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
    private var myThread: DrawThread? = null

    init {
        myHolder = holder
        holder.addCallback(this)
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        Log.d("Surface", "surfaceChanged$width , $height")
        myHolder = holder
        chart = Chart(height, width)

        chart?.apply {
            scroller = MyScroller(context, this)
            myThread = DrawThread(myHolder, this)

            myThread?.startDraw()
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        scroller?.onTouch(this, event)
        return true
    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {
        Log.d("Surface", "surfaceDestroyed")
        myThread?.stopDraw()
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