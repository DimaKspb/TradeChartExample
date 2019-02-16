package com.dima.tradechart

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.widget.Scroller
import com.dima.tradechart.component.Chart
import com.dima.tradechart.component.DrawThread
import com.dima.tradechart.component.MyScroller
import com.dima.tradechart.component.Quote
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
            //            scroller = MyScroller(context, this)
            myThread = DrawThread(myHolder, this)

            myThread?.startDraw()
        }
    }

    private var mLastFlingY: Int = 0
    private var mLastY: Float = 0.toFloat()
    private var mDeltaY: Float = 0.toFloat()
    private var mScrollEventChecker: Scroller? = null
    private var counter = 0

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
//        gestureDetector?.onTouchEvent(event)
        val action = event.action

        when (action) {
            MotionEvent.ACTION_DOWN -> {
                mLastY = event.x
                counter = 0
//                mScrollEventChecker?.forceFinished(true)
                return true
            }

            MotionEvent.ACTION_MOVE -> {
                val movingDelta = (event.x - mLastY).toInt()
                mDeltaY += movingDelta
//                Log.d("Scroll1", "$mDeltaY")

                return true
            }

            MotionEvent.ACTION_UP -> {
                mScrollEventChecker = Scroller(context)
                mScrollEventChecker?.fling(0, 0, 60, 0, -500, 500, 0, 0)
                android.os.Handler().post(object : Runnable {
                    override fun run() {
                        if (mScrollEventChecker?.isFinished == true) {
                            Log.i("Scroller", "scroller is finished, done with fling");
                            return;
                        }
                        val more = mScrollEventChecker?.computeScrollOffset()
                        Log.d("Scroller", "${mScrollEventChecker?.currX}")
                        chart?.scrolling(mDeltaY >= 0)
                        if (more == true)
                            Handler().post(this)
                        else {
                            mLastFlingY = 0
                            mDeltaY = 0F
                        }
                    }
                })
            }
        }

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