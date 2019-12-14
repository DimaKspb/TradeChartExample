package com.dima.tradechart.component

import android.graphics.Path
import android.util.Log
import com.text.traderchart.chart.model.BaseSeries
import com.text.traderchart.chart.model.BaseQuote


class PathAnimation : Path() {

    fun lineTo(arrayList: ArrayList<BaseSeries<BaseQuote>>) {

    }

    override fun lineTo(x: Float, y: Float) {
        Log.d("Animator1", "$x , $y")
    }
}