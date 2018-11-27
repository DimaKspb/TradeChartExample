package com.dima.tradechart.component

import android.graphics.Canvas
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception
import kotlin.collections.ArrayList

interface BasesQuote

class Candle : BasesQuote {
    var open: Double = 0.0
    var close: Double = 0.0
    var low: Double = 0.0
    var height: Double = 0.0
    var time: Long = 0

    constructor(open: Double, close: Double, low: Double, height: Double, time: Long) {
        this.open = open
        this.close = close
        this.low = low
        this.height = height
        this.time = time
    }

    constructor(jsonArray: JSONObject) {
        try {
            this.open = jsonArray.getDouble("open") / 100000
            this.close = (jsonArray.getDouble("open") + jsonArray.getDouble("close")) / 100000
            this.low = (jsonArray.getDouble("open") + jsonArray.getDouble("low")) / 100000
            this.height = (jsonArray.getDouble("open") + jsonArray.getDouble("high")) / 100000
            this.time = jsonArray.getLong("ctm")
        } catch (exp: Exception) {
            exp.printStackTrace()
        }

    }
}

data class Quote(var bid: Double, val ask: Double, val time: Long) : BasesQuote

interface BaseSeries<T> {
    fun getData(): ArrayList<T>

    fun getScreenData(chart: Chart): MutableList<out T>

    fun addHistory(array: ArrayList<Candle>)
    fun addLastQuote(quote: Quote, frame: Double)
}

abstract class BaseRender<T> {

    abstract fun draw(canvas: Canvas?, series: T? = null)
}

enum class TypeChart {
    CANDLE, LINE
}

fun data(): ArrayList<Quote> {
    val myArray = ArrayList<Candle>()
    val data = JSONArray("[{\"close\":-1,\"ctm\":1539140880,\"high\":1,\"low\":-5,\"open\":115112,\"vol\":17},{\"close\":-3,\"ctm\":1539140940,\"high\":0,\"low\":-4,\"open\":115112,\"vol\":11},{\"close\":-4,\"ctm\":1539141000,\"high\":0,\"low\":-6,\"open\":115108,\"vol\":7},{\"close\":-3,\"ctm\":1539141060,\"high\":4,\"low\":-3,\"open\":115105,\"vol\":14},{\"close\":-5,\"ctm\":1539141120,\"high\":1,\"low\":-7,\"open\":115100,\"vol\":22},{\"close\":0,\"ctm\":1539141180,\"high\":2,\"low\":-1,\"open\":115094,\"vol\":8},{\"close\":-5,\"ctm\":1539141240,\"high\":0,\"low\":-5,\"open\":115093,\"vol\":9},{\"close\":-1,\"ctm\":1539141300,\"high\":0,\"low\":-1,\"open\":115089,\"vol\":6},{\"close\":-1,\"ctm\":1539141360,\"high\":0,\"low\":-1,\"open\":115089,\"vol\":6},{\"close\":-1,\"ctm\":1539141420,\"high\":0,\"low\":-1,\"open\":115089,\"vol\":2},{\"close\":4,\"ctm\":1539141480,\"high\":5,\"low\":-1,\"open\":115084,\"vol\":12},{\"close\":-1,\"ctm\":1539141540,\"high\":0,\"low\":-1,\"open\":115089,\"vol\":2},{\"close\":-1,\"ctm\":1539141600,\"high\":0,\"low\":-1,\"open\":115089,\"vol\":2},{\"close\":-2,\"ctm\":1539141660,\"high\":0,\"low\":-3,\"open\":115086,\"vol\":8},{\"close\":-5,\"ctm\":1539141720,\"high\":4,\"low\":-5,\"open\":115085,\"vol\":22},{\"close\":4,\"ctm\":1539141780,\"high\":4,\"low\":0,\"open\":115081,\"vol\":8},{\"close\":1,\"ctm\":1539141840,\"high\":2,\"low\":0,\"open\":115087,\"vol\":6},{\"close\":5,\"ctm\":1539141900,\"high\":5,\"low\":-1,\"open\":115089,\"vol\":8},{\"close\":-4,\"ctm\":1539141960,\"high\":0,\"low\":-4,\"open\":115093,\"vol\":6},{\"close\":2,\"ctm\":1539142020,\"high\":2,\"low\":0,\"open\":115088,\"vol\":3},{\"close\":3,\"ctm\":1539142080,\"high\":5,\"low\":0,\"open\":115091,\"vol\":14},{\"close\":-9,\"ctm\":1539142140,\"high\":0,\"low\":-9,\"open\":115093,\"vol\":6},{\"close\":-7,\"ctm\":1539142200,\"high\":0,\"low\":-7,\"open\":115085,\"vol\":6},{\"close\":-6,\"ctm\":1539142260,\"high\":0,\"low\":-6,\"open\":115079,\"vol\":9},{\"close\":0,\"ctm\":1539142320,\"high\":0,\"low\":-1,\"open\":115068,\"vol\":11},{\"close\":-1,\"ctm\":1539142380,\"high\":0,\"low\":-1,\"open\":115069,\"vol\":2},{\"close\":-3,\"ctm\":1539142440,\"high\":0,\"low\":-5,\"open\":115069,\"vol\":9},{\"close\":10,\"ctm\":1539142500,\"high\":10,\"low\":-4,\"open\":115063,\"vol\":17},{\"close\":-1,\"ctm\":1539142560,\"high\":0,\"low\":-1,\"open\":115074,\"vol\":2},{\"close\":-2,\"ctm\":1539142620,\"high\":0,\"low\":-2,\"open\":115070,\"vol\":3},{\"close\":-5,\"ctm\":1539142680,\"high\":0,\"low\":-7,\"open\":115066,\"vol\":12},{\"close\":0,\"ctm\":1539142740,\"high\":1,\"low\":-1,\"open\":115060,\"vol\":11},{\"close\":1,\"ctm\":1539142800,\"high\":1,\"low\":0,\"open\":115059,\"vol\":2},{\"close\":0,\"ctm\":1539142860,\"high\":0,\"low\":0,\"open\":115059,\"vol\":1},{\"close\":-1,\"ctm\":1539142920,\"high\":1,\"low\":-1,\"open\":115060,\"vol\":4},{\"close\":6,\"ctm\":1539142980,\"high\":6,\"low\":0,\"open\":115060,\"vol\":11},{\"close\":-4,\"ctm\":1539143040,\"high\":2,\"low\":-4,\"open\":115067,\"vol\":11},{\"close\":1,\"ctm\":1539143100,\"high\":2,\"low\":0,\"open\":115064,\"vol\":6},{\"close\":0,\"ctm\":1539143160,\"high\":0,\"low\":-1,\"open\":115064,\"vol\":7},{\"close\":10,\"ctm\":1539143220,\"high\":16,\"low\":-1,\"open\":115065,\"vol\":30},{\"close\":3,\"ctm\":1539143280,\"high\":4,\"low\":0,\"open\":115076,\"vol\":10},{\"close\":-1,\"ctm\":1539143340,\"high\":1,\"low\":-3,\"open\":115078,\"vol\":13},{\"close\":-11,\"ctm\":1539143400,\"high\":0,\"low\":-11,\"open\":115075,\"vol\":17},{\"close\":-1,\"ctm\":1539143460,\"high\":4,\"low\":-1,\"open\":115065,\"vol\":21},{\"close\":0,\"ctm\":1539143520,\"high\":4,\"low\":0,\"open\":115065,\"vol\":13},{\"close\":2,\"ctm\":1539143580,\"high\":3,\"low\":-1,\"open\":115066,\"vol\":7},{\"close\":-2,\"ctm\":1539143640,\"high\":4,\"low\":-2,\"open\":115069,\"vol\":12},{\"close\":-11,\"ctm\":1539143700,\"high\":0,\"low\":-12,\"open\":115069,\"vol\":14},{\"close\":0,\"ctm\":1539143760,\"high\":0,\"low\":-1,\"open\":115059,\"vol\":3},{\"close\":-4,\"ctm\":1539143820,\"high\":0,\"low\":-4,\"open\":115062,\"vol\":5},{\"close\":-3,\"ctm\":1539143880,\"high\":0,\"low\":-5,\"open\":115059,\"vol\":12},{\"close\":6,\"ctm\":1539143940,\"high\":6,\"low\":-1,\"open\":115057,\"vol\":16},{\"close\":-2,\"ctm\":1539144000,\"high\":5,\"low\":-3,\"open\":115060,\"vol\":36},{\"close\":-1,\"ctm\":1539144060,\"high\":0,\"low\":-2,\"open\":115059,\"vol\":10},{\"close\":4,\"ctm\":1539144120,\"high\":8,\"low\":0,\"open\":115059,\"vol\":17},{\"close\":-4,\"ctm\":1539144180,\"high\":0,\"low\":-4,\"open\":115061,\"vol\":3}]")

    for (i in 0 until data.length()) {
        myArray.add(Candle((data[i] as JSONObject)))
    }

    return myArray.map { Quote(it.open, 0.0, it.time) } as ArrayList<Quote>
}
