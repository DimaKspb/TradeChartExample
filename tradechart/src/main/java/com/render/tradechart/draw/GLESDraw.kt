package com.render.tradechart.draw

import android.graphics.Paint
import android.graphics.Path
import android.opengl.GLES32.*
import com.render.tradechart.model.Quote
import java.nio.FloatBuffer
import java.util.ArrayList

object GLESDraw  {
    var triangleCoords = floatArrayOf(
        -1.0f, 1.0f,
        -0.8f, -1.0f,
        -0.7f, 1.0f,
        -0.6f, -1.0f,
        -0.5f, 1.0f,
        -0.4f, -1.0f,
        -0.3f, 1.0f,
        -0.2f, -1.0f,
        -0.1f, 1.0f,
        0.5f, 0.5f,
        0.7f, 0.0f,
        0.8f, 0.5f
    )

    private var buffer = FloatArray(10000 * 4)

    fun init(vertexData: FloatBuffer) {
        vertexData.put(triangleCoords)
    }

    fun drawLine(startX: Float, startY: Float, stopX: Float, stopY: Float, paint: Paint) {

    }

    fun drawText(text: String, x: Float, y: Float, paint: Paint) {

    }

    fun drawPath(p: Path, paintLine: Paint) {

    }

    fun drawLines(screenData: ArrayList<out Quote>, paintLine: Paint) {
        glLineWidth(2f)
        glDrawArrays(GL_LINE_STRIP, 0, triangleCoords.size / 2)

    }
}