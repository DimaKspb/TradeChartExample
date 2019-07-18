package com.dima.tradechart.component

import android.graphics.Path
import android.util.Log
import com.dima.tradechart.model.BaseSeries
import com.dima.tradechart.model.BaseQuote


class PathAnimation : Path() {

    fun lineTo(arrayList: ArrayList<BaseSeries<BaseQuote>>) {

    }

    override fun lineTo(x: Float, y: Float) {
        Log.d("Animator1", "$x , $y")
    }
}