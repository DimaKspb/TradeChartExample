package com.dima.tradechart

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

class MySurfaceView : SurfaceView, SurfaceHolder.Callback, Observer {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private var chart: Chart? = null
    private var myHolder: SurfaceHolder

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
            setOnTouchListener(MyScroller(context, it))
            myThread = DrawThread(myHolder, it)

            myThread?.initDraw()
        }
    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {
        Log.d("Surface", "surfaceDestroyed")
    }

    override fun surfaceCreated(holder: SurfaceHolder?) {
        Log.d("Surface", "surfaceCreated")
    }

    suspend fun initRandomData() {
        for (i in 0..10) {
            delay(500)
            chart?.updateLastQuote(
                Quote(
                    ThreadLocalRandom.current().nextDouble(1.52100, 1.52400),
                    0.0,
                    Calendar.getInstance().timeInMillis
                )
            )
        }
    }

    override fun update(o: Observable?, arg: Any?) {

    }
}