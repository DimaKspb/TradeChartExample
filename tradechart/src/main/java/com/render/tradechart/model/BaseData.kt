package com.render.tradechart.model

import org.json.JSONArray
import kotlin.collections.ArrayList

interface BaseQuote {
    fun getCurrentTime(): Double
    fun getCurrentValue(): Double
}

class Candle : BaseQuote {
    var open: Double = 0.0
    var close: Double = 0.0
    var low: Double = 0.0
    var height: Double = 0.0
    var time: Long = 0
    var timeToDouble: Double = 0.0

    constructor(open: Double, close: Double, low: Double, height: Double, time: Long) {
        this.open = open
        this.close = close
        this.low = low
        this.height = height
        this.time = time
        timeToDouble = time.toDouble()
    }

    override fun getCurrentTime() = time.toDouble()
    override fun getCurrentValue() = open
}

data class Quote(var bid: Double, val ask: Double, val time: Long) : BaseQuote {
    val timeDouble = time.toDouble()

    override fun getCurrentTime() = timeDouble
    override fun getCurrentValue() = bid
}

abstract class BaseSeries<T : BaseQuote> {
    abstract val visibleScreenData: ArrayList<T>
    abstract val allSeries: ArrayList<T>

    protected var screenStartPosition = 0
    protected var screenFinishPosition = 0
    protected var currentFrame = 1

    var minY = 0.0
    var maxY = 0.0
    var minX = 0.0
    var maxX = 0.0

    abstract fun getData(): ArrayList<out T>
    abstract fun getScreenData(): ArrayList<out T>

    fun setFrame(frame: Int) {
        currentFrame = frame
    }

    abstract fun updateLastQuote(array: ArrayList<Candle>)
    abstract fun updateAllQuote(quote: Quote)

    fun calcMinMax() {
        if (visibleScreenData.size == 0) return

        maxY = visibleScreenData.maxBy { it.getCurrentValue() }?.getCurrentValue() ?: 0.0
        minY = visibleScreenData.minBy { it.getCurrentValue() }?.getCurrentValue() ?: 0.0

        minX = visibleScreenData[0].getCurrentTime()
        maxX = visibleScreenData.last().getCurrentTime()
    }
}

interface BaseRender {
    fun draw(series: BaseSeries<*>)
}

enum class TypeChart {
    CANDLE, LINE
}

fun data(): ArrayList<Candle> {
    val myArray = ArrayList<Candle>()
    val data =
            JSONArray(
                    "[{\"close\":-1,\"ctm\":1539140880,\"high\":1,\"low\":-5,\"open\":115112,\"vol\":17},{\"close\":-3,\"ctm\":1539140940,\"high\":0,\"low\":-4,\"open\":115112,\"vol\":11},{\"close\":-4,\"ctm\":1539141000,\"high\":0,\"low\":-6,\"open\":115108,\"vol\":7},{\"close\":-3,\"ctm\":1539141060,\"high\":4,\"low\":-3,\"open\":115105,\"vol\":14},{\"close\":-5,\"ctm\":1539141120,\"high\":1,\"low\":-7,\"open\":115100,\"vol\":22},{\"close\":0,\"ctm\":1539141180,\"high\":2,\"low\":-1,\"open\":115094,\"vol\":8},{\"close\":-5,\"ctm\":1539141240,\"high\":0,\"low\":-5,\"open\":115093,\"vol\":9},{\"close\":-1,\"ctm\":1539141300,\"high\":0,\"low\":-1,\"open\":115089,\"vol\":6},{\"close\":-1,\"ctm\":1539141360,\"high\":0,\"low\":-1,\"open\":115089,\"vol\":6},{\"close\":-1,\"ctm\":1539141420,\"high\":0,\"low\":-1,\"open\":115089,\"vol\":2},{\"close\":4,\"ctm\":1539141480,\"high\":5,\"low\":-1,\"open\":115084,\"vol\":12},{\"close\":-1,\"ctm\":1539141540,\"high\":0,\"low\":-1,\"open\":115089,\"vol\":2},{\"close\":-1,\"ctm\":1539141600,\"high\":0,\"low\":-1,\"open\":115089,\"vol\":2},{\"close\":-2,\"ctm\":1539141660,\"high\":0,\"low\":-3,\"open\":115086,\"vol\":8},{\"close\":-5,\"ctm\":1539141720,\"high\":4,\"low\":-5,\"open\":115085,\"vol\":22},{\"close\":4,\"ctm\":1539141780,\"high\":4,\"low\":0,\"open\":115081,\"vol\":8},{\"close\":1,\"ctm\":1539141840,\"high\":2,\"low\":0,\"open\":115087,\"vol\":6},{\"close\":5,\"ctm\":1539141900,\"high\":5,\"low\":-1,\"open\":115089,\"vol\":8},{\"close\":-4,\"ctm\":1539141960,\"high\":0,\"low\":-4,\"open\":115093,\"vol\":6},{\"close\":2,\"ctm\":1539142020,\"high\":2,\"low\":0,\"open\":115088,\"vol\":3},{\"close\":3,\"ctm\":1539142080,\"high\":5,\"low\":0,\"open\":115091,\"vol\":14},{\"close\":-9,\"ctm\":1539142140,\"high\":0,\"low\":-9,\"open\":115093,\"vol\":6},{\"close\":-7,\"ctm\":1539142200,\"high\":0,\"low\":-7,\"open\":115085,\"vol\":6},{\"close\":-6,\"ctm\":1539142260,\"high\":0,\"low\":-6,\"open\":115079,\"vol\":9},{\"close\":0,\"ctm\":1539142320,\"high\":0,\"low\":-1,\"open\":115068,\"vol\":11},{\"close\":-1,\"ctm\":1539142380,\"high\":0,\"low\":-1,\"open\":115069,\"vol\":2},{\"close\":-3,\"ctm\":1539142440,\"high\":0,\"low\":-5,\"open\":115069,\"vol\":9},{\"close\":10,\"ctm\":1539142500,\"high\":10,\"low\":-4,\"open\":115063,\"vol\":17},{\"close\":-1,\"ctm\":1539142560,\"high\":0,\"low\":-1,\"open\":115074,\"vol\":2},{\"close\":-2,\"ctm\":1539142620,\"high\":0,\"low\":-2,\"open\":115070,\"vol\":3},{\"close\":-5,\"ctm\":1539142680,\"high\":0,\"low\":-7,\"open\":115066,\"vol\":12},{\"close\":0,\"ctm\":1539142740,\"high\":1,\"low\":-1,\"open\":115060,\"vol\":11},{\"close\":1,\"ctm\":1539142800,\"high\":1,\"low\":0,\"open\":115059,\"vol\":2},{\"close\":0,\"ctm\":1539142860,\"high\":0,\"low\":0,\"open\":115059,\"vol\":1},{\"close\":-1,\"ctm\":1539142920,\"high\":1,\"low\":-1,\"open\":115060,\"vol\":4},{\"close\":6,\"ctm\":1539142980,\"high\":6,\"low\":0,\"open\":115060,\"vol\":11},{\"close\":-4,\"ctm\":1539143040,\"high\":2,\"low\":-4,\"open\":115067,\"vol\":11},{\"close\":1,\"ctm\":1539143100,\"high\":2,\"low\":0,\"open\":115064,\"vol\":6},{\"close\":0,\"ctm\":1539143160,\"high\":0,\"low\":-1,\"open\":115064,\"vol\":7},{\"close\":10,\"ctm\":1539143220,\"high\":16,\"low\":-1,\"open\":115065,\"vol\":30},{\"close\":3,\"ctm\":1539143280,\"high\":4,\"low\":0,\"open\":115076,\"vol\":10},{\"close\":-1,\"ctm\":1539143340,\"high\":1,\"low\":-3,\"open\":115078,\"vol\":13},{\"close\":-11,\"ctm\":1539143400,\"high\":0,\"low\":-11,\"open\":115075,\"vol\":17},{\"close\":-1,\"ctm\":1539143460,\"high\":4,\"low\":-1,\"open\":115065,\"vol\":21},{\"close\":0,\"ctm\":1539143520,\"high\":4,\"low\":0,\"open\":115065,\"vol\":13},{\"close\":2,\"ctm\":1539143580,\"high\":3,\"low\":-1,\"open\":115066,\"vol\":7},{\"close\":-2,\"ctm\":1539143640,\"high\":4,\"low\":-2,\"open\":115069,\"vol\":12},{\"close\":-11,\"ctm\":1539143700,\"high\":0,\"low\":-12,\"open\":115069,\"vol\":14},{\"close\":0,\"ctm\":1539143760,\"high\":0,\"low\":-1,\"open\":115059,\"vol\":3},{\"close\":-4,\"ctm\":1539143820,\"high\":0,\"low\":-4,\"open\":115062,\"vol\":5},{\"close\":-3,\"ctm\":1539143880,\"high\":0,\"low\":-5,\"open\":115059,\"vol\":12},{\"close\":6,\"ctm\":1539143940,\"high\":6,\"low\":-1,\"open\":115057,\"vol\":16},{\"close\":-2,\"ctm\":1539144000,\"high\":5,\"low\":-3,\"open\":115060,\"vol\":36},{\"close\":-1,\"ctm\":1539144060,\"high\":0,\"low\":-2,\"open\":115059,\"vol\":10},{\"close\":4,\"ctm\":1539144120,\"high\":8,\"low\":0,\"open\":115059,\"vol\":17},{\"close\":-4,\"ctm\":1539144180,\"high\":0,\"low\":-4,\"open\":115061,\"vol\":3},{\"close\":-1,\"ctm\":1539140880,\"high\":1,\"low\":-5,\"open\":115112,\"vol\":17},{\"close\":-3,\"ctm\":1539140940,\"high\":0,\"low\":-4,\"open\":115112,\"vol\":11},{\"close\":-4,\"ctm\":1539141000,\"high\":0,\"low\":-6,\"open\":115108,\"vol\":7},{\"close\":-3,\"ctm\":1539141060,\"high\":4,\"low\":-3,\"open\":115105,\"vol\":14},{\"close\":-5,\"ctm\":1539141120,\"high\":1,\"low\":-7,\"open\":115100,\"vol\":22},{\"close\":0,\"ctm\":1539141180,\"high\":2,\"low\":-1,\"open\":115094,\"vol\":8},{\"close\":-5,\"ctm\":1539141240,\"high\":0,\"low\":-5,\"open\":115093,\"vol\":9},{\"close\":-1,\"ctm\":1539141300,\"high\":0,\"low\":-1,\"open\":115089,\"vol\":6},{\"close\":-1,\"ctm\":1539141360,\"high\":0,\"low\":-1,\"open\":115089,\"vol\":6},{\"close\":-1,\"ctm\":1539141420,\"high\":0,\"low\":-1,\"open\":115089,\"vol\":2},{\"close\":4,\"ctm\":1539141480,\"high\":5,\"low\":-1,\"open\":115084,\"vol\":12},{\"close\":-1,\"ctm\":1539141540,\"high\":0,\"low\":-1,\"open\":115089,\"vol\":2},{\"close\":-1,\"ctm\":1539141600,\"high\":0,\"low\":-1,\"open\":115089,\"vol\":2},{\"close\":-2,\"ctm\":1539141660,\"high\":0,\"low\":-3,\"open\":115086,\"vol\":8},{\"close\":-5,\"ctm\":1539141720,\"high\":4,\"low\":-5,\"open\":115085,\"vol\":22},{\"close\":4,\"ctm\":1539141780,\"high\":4,\"low\":0,\"open\":115081,\"vol\":8},{\"close\":1,\"ctm\":1539141840,\"high\":2,\"low\":0,\"open\":115087,\"vol\":6},{\"close\":5,\"ctm\":1539141900,\"high\":5,\"low\":-1,\"open\":115089,\"vol\":8},{\"close\":-4,\"ctm\":1539141960,\"high\":0,\"low\":-4,\"open\":115093,\"vol\":6},{\"close\":2,\"ctm\":1539142020,\"high\":2,\"low\":0,\"open\":115088,\"vol\":3},{\"close\":3,\"ctm\":1539142080,\"high\":5,\"low\":0,\"open\":115091,\"vol\":14},{\"close\":-9,\"ctm\":1539142140,\"high\":0,\"low\":-9,\"open\":115093,\"vol\":6},{\"close\":-7,\"ctm\":1539142200,\"high\":0,\"low\":-7,\"open\":115085,\"vol\":6},{\"close\":-6,\"ctm\":1539142260,\"high\":0,\"low\":-6,\"open\":115079,\"vol\":9},{\"close\":0,\"ctm\":1539142320,\"high\":0,\"low\":-1,\"open\":115068,\"vol\":11},{\"close\":-1,\"ctm\":1539142380,\"high\":0,\"low\":-1,\"open\":115069,\"vol\":2},{\"close\":-3,\"ctm\":1539142440,\"high\":0,\"low\":-5,\"open\":115069,\"vol\":9},{\"close\":10,\"ctm\":1539142500,\"high\":10,\"low\":-4,\"open\":115063,\"vol\":17},{\"close\":-1,\"ctm\":1539142560,\"high\":0,\"low\":-1,\"open\":115074,\"vol\":2},{\"close\":-2,\"ctm\":1539142620,\"high\":0,\"low\":-2,\"open\":115070,\"vol\":3},{\"close\":-5,\"ctm\":1539142680,\"high\":0,\"low\":-7,\"open\":115066,\"vol\":12},{\"close\":0,\"ctm\":1539142740,\"high\":1,\"low\":-1,\"open\":115060,\"vol\":11},{\"close\":1,\"ctm\":1539142800,\"high\":1,\"low\":0,\"open\":115059,\"vol\":2},{\"close\":0,\"ctm\":1539142860,\"high\":0,\"low\":0,\"open\":115059,\"vol\":1},{\"close\":-1,\"ctm\":1539142920,\"high\":1,\"low\":-1,\"open\":115060,\"vol\":4},{\"close\":6,\"ctm\":1539142980,\"high\":6,\"low\":0,\"open\":115060,\"vol\":11},{\"close\":-4,\"ctm\":1539143040,\"high\":2,\"low\":-4,\"open\":115067,\"vol\":11},{\"close\":1,\"ctm\":1539143100,\"high\":2,\"low\":0,\"open\":115064,\"vol\":6},{\"close\":0,\"ctm\":1539143160,\"high\":0,\"low\":-1,\"open\":115064,\"vol\":7},{\"close\":10,\"ctm\":1539143220,\"high\":16,\"low\":-1,\"open\":115065,\"vol\":30},{\"close\":3,\"ctm\":1539143280,\"high\":4,\"low\":0,\"open\":115076,\"vol\":10},{\"close\":-1,\"ctm\":1539143340,\"high\":1,\"low\":-3,\"open\":115078,\"vol\":13},{\"close\":-11,\"ctm\":1539143400,\"high\":0,\"low\":-11,\"open\":115075,\"vol\":17},{\"close\":-1,\"ctm\":1539143460,\"high\":4,\"low\":-1,\"open\":115065,\"vol\":21},{\"close\":0,\"ctm\":1539143520,\"high\":4,\"low\":0,\"open\":115065,\"vol\":13},{\"close\":2,\"ctm\":1539143580,\"high\":3,\"low\":-1,\"open\":115066,\"vol\":7},{\"close\":-2,\"ctm\":1539143640,\"high\":4,\"low\":-2,\"open\":115069,\"vol\":12},{\"close\":-11,\"ctm\":1539143700,\"high\":0,\"low\":-12,\"open\":115069,\"vol\":14},{\"close\":0,\"ctm\":1539143760,\"high\":0,\"low\":-1,\"open\":115059,\"vol\":3},{\"close\":-4,\"ctm\":1539143820,\"high\":0,\"low\":-4,\"open\":115062,\"vol\":5},{\"close\":-3,\"ctm\":1539143880,\"high\":0,\"low\":-5,\"open\":115059,\"vol\":12},{\"close\":6,\"ctm\":1539143940,\"high\":6,\"low\":-1,\"open\":115057,\"vol\":16},{\"close\":-2,\"ctm\":1539144000,\"high\":5,\"low\":-3,\"open\":115060,\"vol\":36},{\"close\":-1,\"ctm\":1539144060,\"high\":0,\"low\":-2,\"open\":115059,\"vol\":10},{\"close\":4,\"ctm\":1539144120,\"high\":8,\"low\":0,\"open\":115059,\"vol\":17},{\"close\":-4,\"ctm\":1539144180,\"high\":0,\"low\":-4,\"open\":115061,\"vol\":3},{\"close\":-1,\"ctm\":1539140880,\"high\":1,\"low\":-5,\"open\":115112,\"vol\":17},{\"close\":-3,\"ctm\":1539140940,\"high\":0,\"low\":-4,\"open\":115112,\"vol\":11},{\"close\":-4,\"ctm\":1539141000,\"high\":0,\"low\":-6,\"open\":115108,\"vol\":7},{\"close\":-3,\"ctm\":1539141060,\"high\":4,\"low\":-3,\"open\":115105,\"vol\":14},{\"close\":-5,\"ctm\":1539141120,\"high\":1,\"low\":-7,\"open\":115100,\"vol\":22},{\"close\":0,\"ctm\":1539141180,\"high\":2,\"low\":-1,\"open\":115094,\"vol\":8},{\"close\":-5,\"ctm\":1539141240,\"high\":0,\"low\":-5,\"open\":115093,\"vol\":9},{\"close\":-1,\"ctm\":1539141300,\"high\":0,\"low\":-1,\"open\":115089,\"vol\":6},{\"close\":-1,\"ctm\":1539141360,\"high\":0,\"low\":-1,\"open\":115089,\"vol\":6},{\"close\":-1,\"ctm\":1539141420,\"high\":0,\"low\":-1,\"open\":115089,\"vol\":2},{\"close\":4,\"ctm\":1539141480,\"high\":5,\"low\":-1,\"open\":115084,\"vol\":12},{\"close\":-1,\"ctm\":1539141540,\"high\":0,\"low\":-1,\"open\":115089,\"vol\":2},{\"close\":-1,\"ctm\":1539141600,\"high\":0,\"low\":-1,\"open\":115089,\"vol\":2},{\"close\":-2,\"ctm\":1539141660,\"high\":0,\"low\":-3,\"open\":115086,\"vol\":8},{\"close\":-5,\"ctm\":1539141720,\"high\":4,\"low\":-5,\"open\":115085,\"vol\":22},{\"close\":4,\"ctm\":1539141780,\"high\":4,\"low\":0,\"open\":115081,\"vol\":8},{\"close\":1,\"ctm\":1539141840,\"high\":2,\"low\":0,\"open\":115087,\"vol\":6},{\"close\":5,\"ctm\":1539141900,\"high\":5,\"low\":-1,\"open\":115089,\"vol\":8},{\"close\":-4,\"ctm\":1539141960,\"high\":0,\"low\":-4,\"open\":115093,\"vol\":6},{\"close\":2,\"ctm\":1539142020,\"high\":2,\"low\":0,\"open\":115088,\"vol\":3},{\"close\":3,\"ctm\":1539142080,\"high\":5,\"low\":0,\"open\":115091,\"vol\":14},{\"close\":-9,\"ctm\":1539142140,\"high\":0,\"low\":-9,\"open\":115093,\"vol\":6},{\"close\":-7,\"ctm\":1539142200,\"high\":0,\"low\":-7,\"open\":115085,\"vol\":6},{\"close\":-6,\"ctm\":1539142260,\"high\":0,\"low\":-6,\"open\":115079,\"vol\":9},{\"close\":0,\"ctm\":1539142320,\"high\":0,\"low\":-1,\"open\":115068,\"vol\":11},{\"close\":-1,\"ctm\":1539142380,\"high\":0,\"low\":-1,\"open\":115069,\"vol\":2},{\"close\":-3,\"ctm\":1539142440,\"high\":0,\"low\":-5,\"open\":115069,\"vol\":9},{\"close\":10,\"ctm\":1539142500,\"high\":10,\"low\":-4,\"open\":115063,\"vol\":17},{\"close\":-1,\"ctm\":1539142560,\"high\":0,\"low\":-1,\"open\":115074,\"vol\":2},{\"close\":-2,\"ctm\":1539142620,\"high\":0,\"low\":-2,\"open\":115070,\"vol\":3},{\"close\":-5,\"ctm\":1539142680,\"high\":0,\"low\":-7,\"open\":115066,\"vol\":12},{\"close\":0,\"ctm\":1539142740,\"high\":1,\"low\":-1,\"open\":115060,\"vol\":11},{\"close\":1,\"ctm\":1539142800,\"high\":1,\"low\":0,\"open\":115059,\"vol\":2},{\"close\":0,\"ctm\":1539142860,\"high\":0,\"low\":0,\"open\":115059,\"vol\":1},{\"close\":-1,\"ctm\":1539142920,\"high\":1,\"low\":-1,\"open\":115060,\"vol\":4},{\"close\":6,\"ctm\":1539142980,\"high\":6,\"low\":0,\"open\":115060,\"vol\":11},{\"close\":-4,\"ctm\":1539143040,\"high\":2,\"low\":-4,\"open\":115067,\"vol\":11},{\"close\":1,\"ctm\":1539143100,\"high\":2,\"low\":0,\"open\":115064,\"vol\":6},{\"close\":0,\"ctm\":1539143160,\"high\":0,\"low\":-1,\"open\":115064,\"vol\":7},{\"close\":10,\"ctm\":1539143220,\"high\":16,\"low\":-1,\"open\":115065,\"vol\":30},{\"close\":3,\"ctm\":1539143280,\"high\":4,\"low\":0,\"open\":115076,\"vol\":10},{\"close\":-1,\"ctm\":1539143340,\"high\":1,\"low\":-3,\"open\":115078,\"vol\":13},{\"close\":-11,\"ctm\":1539143400,\"high\":0,\"low\":-11,\"open\":115075,\"vol\":17},{\"close\":-1,\"ctm\":1539143460,\"high\":4,\"low\":-1,\"open\":115065,\"vol\":21},{\"close\":0,\"ctm\":1539143520,\"high\":4,\"low\":0,\"open\":115065,\"vol\":13},{\"close\":2,\"ctm\":1539143580,\"high\":3,\"low\":-1,\"open\":115066,\"vol\":7},{\"close\":-2,\"ctm\":1539143640,\"high\":4,\"low\":-2,\"open\":115069,\"vol\":12},{\"close\":-11,\"ctm\":1539143700,\"high\":0,\"low\":-12,\"open\":115069,\"vol\":14},{\"close\":0,\"ctm\":1539143760,\"high\":0,\"low\":-1,\"open\":115059,\"vol\":3},{\"close\":-4,\"ctm\":1539143820,\"high\":0,\"low\":-4,\"open\":115062,\"vol\":5},{\"close\":-3,\"ctm\":1539143880,\"high\":0,\"low\":-5,\"open\":115059,\"vol\":12},{\"close\":6,\"ctm\":1539143940,\"high\":6,\"low\":-1,\"open\":115057,\"vol\":16},{\"close\":-2,\"ctm\":1539144000,\"high\":5,\"low\":-3,\"open\":115060,\"vol\":36},{\"close\":-1,\"ctm\":1539144060,\"high\":0,\"low\":-2,\"open\":115059,\"vol\":10},{\"close\":4,\"ctm\":1539144120,\"high\":8,\"low\":0,\"open\":115059,\"vol\":17},{\"close\":-4,\"ctm\":1539144180,\"high\":0,\"low\":-4,\"open\":115061,\"vol\":3}]"
            )

    for (i in 0 until data.length()) {
//        myArray.add(Candle((data[i] as JSONObject)))
    }

    return myArray
}

