package com.text.traderchart.chart.component

import android.annotation.SuppressLint
import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.widget.Scroller
import com.text.traderchart.chart.component.Chart


class MyScroller(context: Context, private val chart: Chart?) : GestureDetector.SimpleOnGestureListener(),
    ScaleGestureDetector.OnScaleGestureListener, View.OnTouchListener {
    private var scaleFactor = 20
    private var scaleFactor2 = -20

    var lastX: Float = 0f
    var isScale = false

    private var gestureScale: ScaleGestureDetector? = null
    private var gestureDetector: GestureDetector? = null
    private var scroller: Scroller? = Scroller(context)
    private var scaleDelta = 0f

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

    override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
        scroller?.fling(0, 0, -velocityX.toInt(), 0, -1000, 1000, 0, 0)
//        chart?.setScroller(scroller, velocityX >= lastX)

        return false
    }

    override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean {
        e2 ?: return true
        if (isScale) return true
        if (scroller?.isFinished == false) return true

        chart?.scrolling(distanceX < 0)

        return true
    }

    override fun onScaleBegin(detector: ScaleGestureDetector?): Boolean {
        isScale = true
        scaleDelta = detector?.currentSpanX!!
        return true
    }

    override fun onScaleEnd(detector: ScaleGestureDetector?) {
        isScale = false
        scaleFactor = 20
        scaleFactor2 = -20
    }

    override fun onScale(detector: ScaleGestureDetector?): Boolean {
        if (scaleDelta - detector?.currentSpanX!! > scaleFactor) {
            chart?.scale(true)
            scaleFactor += scaleFactor
        } else if (scaleDelta - detector.currentSpanX < scaleFactor2) {
            chart?.scale(false)
            scaleFactor2 += scaleFactor2
        }

        return true
    }

    override fun onShowPress(e: MotionEvent?) {

    }

    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        if (scroller?.isFinished == false)
            scroller?.forceFinished(true)


        return true
    }

    override fun onDown(e: MotionEvent?): Boolean {
        scroller?.forceFinished(true)

        return true
    }

    override fun onLongPress(e: MotionEvent?) {
    }
}

