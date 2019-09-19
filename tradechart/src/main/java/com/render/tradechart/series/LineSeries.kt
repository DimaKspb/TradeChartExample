package com.render.tradechart.series

import com.render.tradechart.component.ChartConfig.pointOnChart
import com.render.tradechart.model.BaseSeries
import com.render.tradechart.model.Candle
import com.render.tradechart.model.Quote
import kotlin.collections.ArrayList

class LineSeries : BaseSeries<Quote>() {

    override val visibleScreenData = ArrayList<Quote>()
    override val allSeries = ArrayList<Quote>()

    override fun addAllPoint(array: ArrayList<Candle>) {
        allSeries.clear()
        allSeries.addAll(array.map {
            Quote(
                it.open,
                0.0,
                it.time
            )
        } as ArrayList<Quote>)

        screenFinishPosition = allSeries.size
        screenStartPosition = allSeries.size - 10
    }

    override fun addOnePoint(quote: Quote, frame: Double) {
        if (getLast().time > quote.time) return
//        Log.d("SeriesS1", getLast().time.toString() + "," + getLast().bid)
        if (getLast().time + (frame * 1000) > quote.time) {
//            quote.time = getLast().time + frame + 10
//            Log.d("SeriesS3", quote.time.toLong().toString())
        }
        if (getLast().bid == quote.bid) return
//
        if (quote.time - getLast().time <= frame) {
//            Log.d("Series", "change ${quote.time} , ${getLast().time}")
            allSeries[allSeries.size - 1].bid = quote.bid
        } else {
//            Log.d("Series", "add ${quote.time} , ${getLast().time}")
            allSeries.add(quote)
        }
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