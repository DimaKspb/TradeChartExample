package com.dima.tradechart.component

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View


class MyScroller(context: Context, private val chart: Chart?) : GestureDetector.OnGestureListener, ScaleGestureDetector.OnScaleGestureListener, View.OnTouchListener {
    override fun onShowPress(e: MotionEvent?) {

    }

    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        return true
    }

    override fun onDown(e: MotionEvent?): Boolean {
        return true
    }

    override fun onLongPress(e: MotionEvent?) {
    }

    var lastX: Float = 0f
    var isScale = false

    private var gestureScale: ScaleGestureDetector? = null
    var gestureDetector: GestureDetector? = null

    private var mIsStarted: Boolean = false
    private var scrollDelta = 0f
    private var scaleDelta = 0f


    override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
//        Log.d("MyScroller", "onFling ${e1?.action} ${e2?.action}")
        return true
    }

    override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean {
        e2 ?: return true
        if (isScale) return true

        chart?.scrolling(e2.x >= lastX)
        lastX = e2.x

        return true
    }

    init {
        gestureDetector = GestureDetector(context, this)
        gestureScale = ScaleGestureDetector(context, this)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        gestureScale?.onTouchEvent(event)
        gestureDetector?.onTouchEvent(event)

        return true
    }

    override fun onScaleBegin(detector: ScaleGestureDetector?): Boolean {
        Log.d("MyScrollerStart", "${detector?.currentSpanX}")
        isScale = true
        scaleDelta = detector?.currentSpanX!!
        return true
    }

    //
    override fun onScaleEnd(detector: ScaleGestureDetector?) {
        Log.d("MyScrollerEnd", "${detector?.currentSpanX}")
        isScale = false
        sclaeFacotr = 20
        sclaeFacotr2 = -20
    }

    var sclaeFacotr = 20
    var sclaeFacotr2 = -20

    override fun onScale(detector: ScaleGestureDetector?): Boolean {
        if (scaleDelta - detector?.currentSpanX!! > sclaeFacotr) {
            chart?.scale(true)
            sclaeFacotr += sclaeFacotr
            Log.d("MyScrollerIn", "${sclaeFacotr}")
        } else if (scaleDelta - detector.currentSpanX < sclaeFacotr2) {
            chart?.scale(false)
            sclaeFacotr2 += sclaeFacotr2
            Log.d("MyScrollerIn", "${sclaeFacotr2}")
        }

        return true
    }
}

