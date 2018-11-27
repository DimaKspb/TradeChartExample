package com.dima.tradechart

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.dima.tradechart.MainActivity
import com.dima.tradechart.component.Chart
import com.dima.tradechart.component.DrawThread
import com.dima.tradechart.component.MyScroller
import com.dima.tradechart.component.Quote
import java.util.*
import java.util.concurrent.ThreadLocalRandom

class MySurfaceView : SurfaceView, SurfaceHolder.Callback, Observer, MyScroller.OnMyScrollerListener {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private var chart: Chart? = null
    private var myHolder: SurfaceHolder
    private var myThread: DrawThread? = null
    private var scroller: MyScroller? = null

    init {
        myHolder = holder
        holder.addCallback(this)
    }

    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
        Log.d("Surface", "surfaceChanged$width , $height")
        myHolder = holder!!
        chart = Chart(height, width, context as MainActivity)
        scroller = MyScroller(context, this, chart)
        scroller?.setOnFinishListener(this)
        setOnTouchListener(scroller)

        myThread = DrawThread(myHolder, chart!!)
        myThread?.initDraw()
    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {
        Log.d("Surface", "surfaceDestroyed")
    }

    override fun surfaceCreated(holder: SurfaceHolder?) {
        Log.d("Surface", "surfaceCreated")
    }

    override fun onUpdateDraw() {
        myThread?.updateDraw()
    }

    fun initRandomData() {
        for (i in 0..10) {
            Thread.sleep(1500)
            chart?.updateLastQuote(Quote(ThreadLocalRandom.current().nextDouble(1.52100, 1.52400), 0.0, Calendar.getInstance().timeInMillis))
            myThread?.updateDraw()
        }
    }

    override fun update(o: Observable?, arg: Any?) {

    }
}