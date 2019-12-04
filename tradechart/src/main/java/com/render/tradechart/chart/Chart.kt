//package com.render.tradechart.chart
//
//import android.widget.Scroller
//import com.render.tradechart.component.ChartConfig.offsetBottom
//import com.render.tradechart.component.ChartConfig.offsetRight
//import com.render.tradechart.draw.MyDraw
//import com.render.tradechart.model.*
//import com.render.tradechart.render.GridRender
//import com.render.tradechart.render.LineRender
//import com.render.tradechart.render.XYAxisRender
//import com.render.tradechart.series.LineSeries
//
//class Chart : BaseChart {
//    override val typeChart = TypeChart.LINE
//    var isChartInit = false
//
//    var mySeries: BaseSeries<*> = LineSeries()
//    private var isEndChartVisible = true
//
//    var frame = 1
//
//    private val dY = mySeries.maxY - mySeries.minY
//
//    private var itScrolling: Scroller? = null
//    var isLeft = false
//
//    val chartHeight = height - 50f
//    val chartWidth = width - offsetRight
//
//    private val axisXYRender = XYAxisRender(this)
//    private val lineRender = LineRender(this)
//    private val gridRender = GridRender(this)
//
//    init {
////        mySeries.allSeries = data()
////        isChartInit = true
//    }
//
////    fun getSceneXValue(x: Double): Float = ((x - mySeries.minX) * chartWidth / (mySeries.maxX - mySeries.minX).toFloat()).toFloat()
////    fun getSceneYValue(bid: Double): Float = ((chartHeight - (bid - getMinY()) * chartHeight / (getMaxY() - getMinY()) - offsetBottom).toFloat())
//
//    fun updateLastQuote(lineSeries: Quote) {
//        mySeries.updateLasteQuote(lineSeries, frame.toDouble())
////        if (isEndChartVisible)
////            screenStartPosition++
//
//    }
//
//    fun scrolling(needGoToLeft: Boolean): Boolean {
////        return mySeries.moveAt(needGoToLeft)
//        return false
//    }
//
//    fun scale(isZoomOut: Boolean) {
////        mySeries.scale(isZoomOut)
//    }
//
//    fun drawRenders(canvas: MyDraw, ts: Long) {
//        axisXYRender.draw(canvas)
//        gridRender.draw(canvas, mySeries)
//        lineRender.draw(canvas, mySeries as LineSeries)
//
////        if (itScrolling?.computeScrollOffset() == true) {
////            scrolling(isLeft)
////        }
//    }
//
//    fun setScroller(scrolling: Scroller?, value: Boolean = false) {
//        itScrolling = scrolling
//        isLeft = value
//    }
//
//    fun setSeries(mySeries: BaseSeries<BaseQuote>) {
//        isChartInit = false
//        this.mySeries = mySeries
//        isChartInit = true
//    }
//
//    fun getSeries() = mySeries
//
//
//    override fun toString(): String {
//        return "Chart(height=$height, width=$width, typeChart=$typeChart)"
//    }
//}