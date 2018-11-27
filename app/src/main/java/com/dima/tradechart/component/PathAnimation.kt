package com.dima.tradechart.component

import android.graphics.Path
import android.util.Log
import com.dima.tradechart.component.BaseSeries
import com.dima.tradechart.component.BasesQuote


class PathAnimation : Path() {

    fun lineTo(arrayList: ArrayList<BaseSeries<BasesQuote>>) {

    }

    override fun lineTo(x: Float, y: Float) {
        Log.d("Animator1", "$x , $y")
    }
}