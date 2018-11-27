package com.dima.tradechart.component

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.*
import android.widget.Scroller

class MyScroller(context: Context, surfaceView: SurfaceView, val chart: Chart?) : Scroller(context), ScaleGestureDetector.OnScaleGestureListener, View.OnTouchListener {
    private var mIsStarted: Boolean = false
    private var mOnMyScrollerListener: OnMyScrollerListener? = null
    private var scaleGestureDetector: ScaleGestureDetector? = null
    private var scrollDelta = 0f
    private var scaleDelta = 0f

    init {
        scaleGestureDetector = ScaleGestureDetector(context, this)
    }

    override fun computeScrollOffset(): Boolean {
        val result = super.computeScrollOffset()
        if (!result && mIsStarted) {
            try { // Don't let any exception impact the scroll animation.
//                mOnMyScrollerListener?.onUpdateDraw()
            } catch (e: Exception) {
            }

            mIsStarted = false
        }
        return result
    }

    override fun startScroll(startX: Int, startY: Int, dx: Int, dy: Int) {
        super.startScroll(startX, startY, dx, dy)
        mIsStarted = true
    }

    override fun startScroll(startX: Int, startY: Int, dx: Int, dy: Int, duration: Int) {
        super.startScroll(startX, startY, dx, dy, duration)
        mIsStarted = true
    }

    override fun fling(startX: Int, startY: Int, velocityX: Int, velocityY: Int, minX: Int, maxX: Int, minY: Int, maxY: Int) {
        super.fling(startX, startY, velocityX, velocityY, minX, maxX, minY, maxY)
        mIsStarted = true
    }

    fun setOnFinishListener(onMyScrollerListener: OnMyScrollerListener) {
        mOnMyScrollerListener = onMyScrollerListener
    }

    interface OnMyScrollerListener {
        fun onUpdateDraw()
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
//        Log.d("Scrolling", "${event?.action} x0=${event?.getX(0)} x=${event?.x}, ${event?.xPrecision}")
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                scrollDelta = event.x
            }
            MotionEvent.ACTION_MOVE -> {
                startScroll(1, 0, 100, 0)
                while (!isFinished) {
                    Log.d("MotionEvent", "")
                    if (Math.abs(scrollDelta - event.x) < chart?.width!! / chart.pointOnChart) {
                        if (scrollDelta > event.x) {
//                            Log.d("Scroll", "plus ${chart.getStartScreenPosition()}")
                            if (chart.scrolling(false)) {
                                mOnMyScrollerListener?.onUpdateDraw()
                            } else {
                                abortAnimation()
                                break
                            }
                        } else if (scrollDelta < event.x && chart.getStartScreenPosition() != 0) {
//                            Log.d("Scroll", "minus ${chart.getStartScreenPosition()}")
                            if (chart.scrolling(true)) {
                                mOnMyScrollerListener?.onUpdateDraw()
                            } else {
                                abortAnimation()
                                break
                            }
                        }
                    }
                    computeScrollOffset()
                }
                scaleGestureDetector?.onTouchEvent(event)
            }
            MotionEvent.ACTION_UP -> {

            }
        }
        return true
    }

    override fun onScaleBegin(detector: ScaleGestureDetector?): Boolean {
//        Log.d("onScaleBegin", "${detector?.currentSpanX}")
        scaleDelta = detector?.currentSpanX!!
        return true
    }

    override fun onScaleEnd(detector: ScaleGestureDetector?) {
//        Log.d("onScaleEnd", "${detector?.currentSpanX}")
    }

    override fun onScale(detector: ScaleGestureDetector?): Boolean {
        if (Math.abs(scaleDelta - detector?.currentSpanX!!) > 50) {
            chart?.scale()
            mOnMyScrollerListener?.onUpdateDraw()
        }
        Log.d("onScaleDetector", "${Math.abs(scaleDelta - detector.currentSpanX)}")
        return true
    }
}
