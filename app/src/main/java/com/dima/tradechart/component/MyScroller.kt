package com.dima.tradechart.component

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.widget.Scroller
import kotlinx.coroutines.*


class MyScroller(private val context: Context, private val chart: Chart?) : GestureDetector.OnGestureListener,
    ScaleGestureDetector.OnScaleGestureListener, View.OnTouchListener {
    private var scaleFactor = 20
    private var scaleFactor2 = -20

    val job = Job()
    val ioScope = CoroutineScope(Dispatchers.IO + job)

    var lastX: Float = 0f
    var isScale = false

    private var gestureScale: ScaleGestureDetector? = null
    private var gestureDetector: GestureDetector? = null
    private var scroller: Scroller? = null
    private var scaleDelta = 0f

    init {
        gestureDetector = GestureDetector(context, this)
        gestureScale = ScaleGestureDetector(context, this)
    }

    private var mLastFlingY: Int = 0
    private var mLastY: Float = 0.toFloat()
    private var mDeltaY: Float = 0.toFloat()
    private var mScrollEventChecker: Scroller? = null

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
//        gestureScale?.onTouchEvent(event)
//        gestureDetector?.onTouchEvent(event)
        val action = event?.action

        when (action) {
            MotionEvent.ACTION_DOWN -> {
                mLastY = event.y
                return true
            }

            MotionEvent.ACTION_MOVE -> {
                val movingDelta = (event.y - mLastY).toInt()
                mDeltaY += movingDelta
//                Log.d("Scroll1", "$mDeltaY")

                return true
            }

            MotionEvent.ACTION_UP -> {
                mScrollEventChecker = Scroller(context)
                mScrollEventChecker?.startScroll(0, 0, 0, -mDeltaY.toInt(), 400)
                android.os.Handler().post(object : Runnable {
                    override fun run() {
                        if (mScrollEventChecker?.computeScrollOffset() == true) {
                            val curY = mScrollEventChecker?.currY
                            val delta = curY!! - mLastFlingY
                            Log.d("Scroll2", "$mDeltaY")

                            mLastFlingY = curY
                            Handler().post(this)
                        } else {
                            mLastFlingY = 0
                            mDeltaY = 0F
                        }
                    }
                })
            }
        }

        return true
    }

    override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
        Log.d("MyScroller", "onFling $velocityX")
//        scroller?.forceFinished(true)

//        ioScope.cancel()
        GlobalScope.launch(Dispatchers.IO) {
            scroller = Scroller(context)
            scroller?.fling(0, 0, (-velocityX).toInt(), 0, -5000, 5000, 0, 0)
            while (scroller?.computeScrollOffset() == true) {
                if (scroller!!.currX.toDouble() % 10 == 0.0) {
                    Log.d("MyScroller", "while ${scroller?.currX}")
                    chart?.scrolling(-velocityX <= lastX)
                    lastX = scroller?.currX?.toFloat()!!
                }
            }
        }

        return true
    }

    override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean {
        Log.d("MyScroller", "onScroll ${e1?.action} ${e2?.action}")
        e2 ?: return true
        if (isScale) return true
        if (scroller?.isFinished == false) return true

//        chart?.scrolling(e2.x >= lastX)
//        lastX = e2.x

        return true
    }

    override fun onScaleBegin(detector: ScaleGestureDetector?): Boolean {
        Log.d("MyScrollerStart", "${detector?.currentSpanX}")
        isScale = true
        scaleDelta = detector?.currentSpanX!!
        return true
    }

    override fun onScaleEnd(detector: ScaleGestureDetector?) {
        Log.d("MyScrollerEnd", "${detector?.currentSpanX}")
        isScale = false
        scaleFactor = 20
        scaleFactor2 = -20
    }

    override fun onScale(detector: ScaleGestureDetector?): Boolean {
        if (scaleDelta - detector?.currentSpanX!! > scaleFactor) {
            chart?.scale(true)
            scaleFactor += scaleFactor
            Log.d("MyScrollerIn", "$scaleFactor")
        } else if (scaleDelta - detector.currentSpanX < scaleFactor2) {
            chart?.scale(false)
            scaleFactor2 += scaleFactor2
            Log.d("MyScrollerIn", "$scaleFactor2")
        }

        return true
    }

    override fun onShowPress(e: MotionEvent?) {

    }

    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        Log.d("MyScroller", "tap2")
        if (scroller?.isFinished == false)
            scroller?.forceFinished(true)

        return true
    }

    override fun onDown(e: MotionEvent?): Boolean {
        Log.d("MyScroller", "tap1")
        return true
    }

    override fun onLongPress(e: MotionEvent?) {
    }
}

