package com.render.tradechart.component

import android.graphics.Path
import android.util.Log
import com.render.tradechart.model.BaseSeries
import com.render.tradechart.model.BaseQuote


class PathAnimation : Path() {

    fun lineTo(arrayList: ArrayList<BaseSeries<BaseQuote>>) {

    }

    override fun lineTo(x: Float, y: Float) {
        Log.d("Animator1", "$x , $y")
    }
}