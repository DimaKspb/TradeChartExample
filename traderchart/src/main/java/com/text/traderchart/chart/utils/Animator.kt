package com.text.traderchart.chart.utils

import android.util.Log

class Animator(private val duration: Long) {
    private var startAnimationTs = 0L
    var currentPosition: Double = 0.toDouble()
    private var lastFpsCalcUptime: Long = 0
    private var delta = 0L
    private var action: ((Float) -> Unit)? = null

    fun getProgress(action: (Float) -> Unit) {
        startAnimationTs = System.currentTimeMillis()
        this.action = action
    }

    fun onUpdate() {
//        action ?: return
////        if (lastFpsCalcUptime == 0L || lastFpsCalcUptime - startAnimationTs > 1000) {
////            lastFpsCalcUptime = startAnimationTs
////        }
//        val now = System.currentTimeMillis()
//        delta = ((now - startAnimationTs) / 1000f).toLong()
//
//        if (delta > 1.0) {
//            startAnimationTs = now
//            action?.invoke(1f)
//            action = null
//        }
//        action?.invoke(delta.toFloat() / 1000f)

        action ?: return
        if (currentPosition >= 1) {
            action?.invoke(1f)
            action = null
            return
        }
        currentPosition = (System.currentTimeMillis() - startAnimationTs).toDouble() / duration.toDouble()
        currentPosition = if (currentPosition > 1.0) 1.0 else currentPosition

        action?.invoke(currentPosition.toFloat())

    }
}

