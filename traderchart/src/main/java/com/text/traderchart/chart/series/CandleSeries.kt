package com.dima.tradechart.series

import com.dima.tradechart.component.ChartConfig.pointOnChart
import com.dima.tradechart.model.BaseSeries
import com.dima.tradechart.model.Candle
import com.dima.tradechart.model.Quote
import kotlin.collections.ArrayList

class CandleSeries : BaseSeries<Candle>() {
    override val visibleScreenData = ArrayList<Candle>()
    override val allSeries = ArrayList<Candle>()

    override fun addAllPoint(array: ArrayList<Candle>) {

    }

    override fun addOnePoint(quote: Quote, frame: Double) {
    }

    private fun getLast() = allSeries[allSeries.size - 1]

    override fun getScreenData(): ArrayList<Candle> {
        visibleScreenData.clear()
        visibleScreenData.addAll(allSeries.subList(screenStartPosition, screenFinishPosition))

//        calcMinMax()

        return visibleScreenData
    }

    private fun updateExtremesY() {

    }

    private fun updateExtremesX() {
    }

    override fun getData(): ArrayList<Candle> {
        return allSeries
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