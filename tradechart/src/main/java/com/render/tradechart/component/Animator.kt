package com.render.tradechart.component

class Animator(private val duration: Long) {
    private var startAnimationTs: Long = 0
    var currentPosition: Double = 0.toDouble()
        private set

    fun reset() {
        startAnimationTs = System.currentTimeMillis()
        currentPosition = 0.0
    }

    fun animate() {
        if (currentPosition >= 1) {
            return
        }
        currentPosition = (System.currentTimeMillis() - startAnimationTs).toDouble() / duration.toDouble()
        currentPosition = if (currentPosition > 1.0) 1.0 else currentPosition
    }
}
