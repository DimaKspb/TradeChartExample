package com.dima.tradechart.series

import com.dima.tradechart.component.BaseSeries
import com.dima.tradechart.component.Candle
import com.dima.tradechart.component.Chart
import com.dima.tradechart.component.Quote
import java.util.*
import kotlin.collections.ArrayList

class LineSeries : BaseSeries<Quote> {
    var lineSeries = Collections.synchronizedList(ArrayList<Quote>())
    val visibleScreenData = Collections.synchronizedList(mutableListOf<Quote>())

    var minY = 0.0
    var maxY = 0.0

    override fun addHistory(array: ArrayList<Candle>) {
        lineSeries = array.map { Quote(it.open, 0.0, it.time) } as ArrayList<Quote>

        maxY = lineSeries.maxBy { it.bid }?.bid ?: 0.0
        minY = lineSeries.minBy { it.bid }?.bid ?: 0.0
    }

    override fun addLastQuote(quote: Quote, frame: Double) {
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
            lineSeries[lineSeries.size - 1].bid = quote.bid
        } else {
//            Log.d("Series", "add ${quote.time} , ${getLast().time}")
            lineSeries.add(quote)
        }
    }

    private fun getLast() = lineSeries[lineSeries.size - 1]

    override fun getScreenData(chart: Chart): MutableList<Quote> {
        visibleScreenData.clear()
        visibleScreenData.addAll(lineSeries.subList(chart.screenStartPosition, chart.getEndScreenPosition()))

        updateExtremes(visibleScreenData)

        return visibleScreenData
    }

    private fun updateExtremes(series: MutableList<Quote>) {
        maxY = series.maxBy { it.bid }?.bid ?: 0.0
        minY = series.minBy { it.bid }?.bid ?: 0.0
    }

    override fun getData(): ArrayList<Quote> {
        return lineSeries as ArrayList<Quote>
    }

    override fun toString(): String {
        return "LineSeries(lineSeries=$lineSeries)"
    }
}