package com.render.tradechart.series

import com.render.tradechart.component.ChartConfig.pointOnChart
import com.render.tradechart.model.BaseSeries
import com.render.tradechart.model.Candle
import com.render.tradechart.model.Quote
import kotlin.collections.ArrayList

class LineSeries : BaseSeries<Quote>() {

    override val visibleScreenData = ArrayList<Quote>()
    override val allSeries = ArrayList<Quote>()

    override fun updateLastQuote(array: ArrayList<Candle>) {
        allSeries.clear()
        allSeries.addAll(array.map {
            Quote(it.open,0.0,it.time)
        } as ArrayList<Quote>)

//        screenFinishPosition = allSeries.size
//        screenStartPosition = allSeries.size - 10
    }

    override fun updateAllQuote(quote: Quote) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun getLast() = allSeries[allSeries.size - 1]

    override fun getScreenData(): ArrayList<out Quote> {
        synchronized(allSeries) {
            visibleScreenData.clear()
            visibleScreenData.addAll(allSeries.subList(screenStartPosition, screenFinishPosition))
        }

        calcMinMax()

        return visibleScreenData
    }

    override fun getData(): ArrayList<Quote> {
        return allSeries as ArrayList<Quote>
    }

    override fun toString(): String {
        return "LineSeries(allSeries=$allSeries)"
    }

    fun moveAt(needGoToLeft: Boolean): Boolean {
        when {
            screenStartPosition == 0 && needGoToLeft -> return false
            screenStartPosition >= (allSeries.size - (pointOnChart / 2)) && !needGoToLeft -> return false
            needGoToLeft -> screenStartPosition--
            !needGoToLeft -> screenStartPosition++
        }

        return false
    }

    fun scale(isZoomOut: Boolean) {
        when {
            screenStartPosition == 1 || pointOnChart >= 160 -> return
            pointOnChart == allSeries.size - 1 && isZoomOut || pointOnChart <= 20 && !isZoomOut -> return
            isZoomOut -> pointOnChart += 10
            else -> pointOnChart -= 10
        }
    }

    fun getEndScreenPosition(): Int {
        return when {
            screenStartPosition == 0 && pointOnChart <= allSeries.size -> screenStartPosition + pointOnChart
            screenStartPosition + pointOnChart >= allSeries.size -> {
//                isEndChartVisible = true
                allSeries.size - 1
            }
            else -> {
//                isEndChartVisible = false
                screenStartPosition + pointOnChart
            }
        }
    }
}