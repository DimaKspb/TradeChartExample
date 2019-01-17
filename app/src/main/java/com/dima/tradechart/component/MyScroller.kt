package com.dima.tradechart.component

import android.annotation.SuppressLint
import android.content.Context
import android.support.v4.view.GestureDetectorCompat
import android.util.Log
import android.view.*
import android.widget.Scroller

class MyScroller(context: Context, private val chart: Chart?) : GestureDetector.SimpleOnGestureListener(), View.OnTouchListener {
    var lastX: Float = 0f

    override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean {
        Log.d("MyScroller", "onFling $velocityX")
        return true
    }

    override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean {
        Log.d("MyScroller", "$distanceX , ${e1?.x} , ${e2?.x}")
        if (e2!!.x < lastX) {
            chart?.scrolling(false)
        } else {
            chart?.scrolling(true)
        }
        lastX = e2.x
        return true
    }

    private var mIsStarted: Boolean = false
    var scaleGestureDetector: GestureDetectorCompat? = null
    private var scrollDelta = 0f
    private var scaleDelta = 0f

    init {
        scaleGestureDetector = GestureDetectorCompat(context, this)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
//        Log.d("Scrolling", "${event?.action} x0=${event?.getX(0)} x=${event?.x}, ${event?.xPrecision}")
//        when (event?.action) {
//            MotionEvent.ACTION_DOWN -> {
//                scrollDelta = event.x
//            }
//            MotionEvent.ACTION_MOVE -> {
//                startScroll(1, 0, 100, 0)
//                while (!isFinished) {
//                    Log.d("MotionEvent", "")
//                    if (Math.abs(scrollDelta - event.x) < chart?.width!! / chart.pointOnChart) {
//                        if (scrollDelta > event.x) {
//        Log.d("MyScroll", "touch")
//                            if (chart.scrolling(false)) {
////                                mOnMyScrollerListener?.onUpdateDraw()
//                            } else {
//                                abortAnimation()
//                                break
//                            }
//                        } else if (scrollDelta < event.x && chart.getStartScreenPosition() != 0) {
////                            Log.d("Scroll", "minus ${chart.getStartScreenPosition()}")
//                            if (chart.scrolling(true)) {
////                                mOnMyScrollerListener?.onUpdateDraw()
//                            } else {
//                                abortAnimation()
//                                break
//                            }
//                        }
//                    }
//                    computeScrollOffset()
//                }
//                scaleGestureDetector?.onTouchEvent(event)
//            }
//            MotionEvent.ACTION_UP -> {
//
//            }
//        }
        return true
    }

//    override fun onScaleBegin(detector: ScaleGestureDetector?): Boolean {
//        Log.d("onScaleBegin", "${detector?.currentSpanX}")
//        scaleDelta = detector?.currentSpanX!!
//        return true
//    }
//
//    override fun onScaleEnd(detector: ScaleGestureDetector?) {
//        Log.d("onScaleEnd", "${detector?.currentSpanX}")
//    }
//
//    override fun onScale(detector: ScaleGestureDetector?): Boolean {
//        if (Math.abs(scaleDelta - detector?.currentSpanX!!) > 50) {
//            chart?.scale()
////            mOnMyScrollerListener?.onUpdateDraw()
//        }
//        Log.d("onScaleDetector", "${Math.abs(scaleDelta - detector.currentSpanX)}")
//        return true
//    }
}
