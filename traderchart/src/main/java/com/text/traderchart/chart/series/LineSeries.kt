package com.text.traderchart.chart.series

import com.text.traderchart.chart.model.BaseSeries
import com.text.traderchart.chart.model.Candle
import com.text.traderchart.chart.model.Quote
import kotlin.collections.ArrayList

class LineSeries : BaseSeries<Quote>() {

    override val visibleScreenData = ArrayList<Quote>()
    override val allSeries = ArrayList<Quote>()

    override fun addAllPoint(array: ArrayList<Candle>) {
        allSeries.clear()
        allSeries.addAll(array.map { Quote(it.open, 0.0, it.time) } as ArrayList<Quote>)

        screenStartPosition = allSeries.size / 2
        screenFinishPosition = allSeries.size

        onUpdateSeries()
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

        onUpdateSeries()
    }

    private fun getLast() = allSeries[allSeries.size - 1]

    override fun getScreenData(): ArrayList<out Quote> {
        return visibleScreenData
    }

    override fun getData(): ArrayList<Quote> {
        return allSeries as ArrayList<Quote>
    }

    override fun toString(): String {
        return "LineSeries(allSeries=$allSeries)"
    }

    private fun onUpdateSeries() {
        synchronized(allSeries) {
            visibleScreenData.clear()
            visibleScreenData.addAll(allSeries.subList(screenStartPosition, screenFinishPosition))
        }

        calcMinMax()
    }
}