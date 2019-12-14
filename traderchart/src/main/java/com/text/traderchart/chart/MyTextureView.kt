package com.text.traderchart.chart

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.SurfaceTexture
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.TextureView
import com.text.traderchart.chart.component.Chart
import com.text.traderchart.chart.component.ChartConfig
import com.text.traderchart.chart.component.MyScroller
import com.text.traderchart.chart.component.logging
import com.text.traderchart.chart.draw.SurfaceRenderManager
import com.text.traderchart.chart.model.Quote
import com.text.traderchart.chart.model.data
import com.text.traderchart.chart.render.GridRender
import com.text.traderchart.chart.render.LineRender
import com.text.traderchart.chart.render.AreaRender
import com.text.traderchart.chart.series.LineSeries
import java.util.*
import java.util.concurrent.ThreadLocalRandom


class MyTextureView : TextureView {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private var chart: Chart = Chart()
    private var scroller: MyScroller? = null

    private val surfaceRenderManager = SurfaceRenderManager()

    //Mock data
    private val series = LineSeries().apply {
        addAllPoint(data())
    }

    init {
        surfaceTextureListener = TextViewListener()
        invalidate()
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        scroller?.onTouch(this, event)

        val action = event.action

        return true
    }

    fun initRandomData() {
        chart.updateLastQuote(Quote(ThreadLocalRandom.current().nextDouble(1.15080, 1.15100), 0.0, Calendar.getInstance().timeInMillis))
    }

    inner class TextViewListener : SurfaceTextureListener {

        override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture?, width: Int, height: Int) {
            logging("changed $width ,$height")
            chart.apply {
                initSize(height, width)
                setSeries(series)
            }

            surfaceRenderManager.apply {
                addRender(AreaRender(chart))
                addRender(GridRender(chart))
                addRender(LineRender(chart))
            }
        }

        override fun onSurfaceTextureUpdated(surface: SurfaceTexture?) {
            logging("update")
            val canvas = lockCanvas()
            surfaceRenderManager.drawRenders(canvas, chart.getSeries())
            unlockCanvasAndPost(canvas)
        }

        override fun onSurfaceTextureDestroyed(surface: SurfaceTexture?): Boolean {
            return false
        }

        override fun onSurfaceTextureAvailable(surface: SurfaceTexture?, width: Int, height: Int) {
            logging("Created")
            ChartConfig.setDisplayMetrics(resources.displayMetrics)
            ChartConfig.logging = true
        }

    }

}