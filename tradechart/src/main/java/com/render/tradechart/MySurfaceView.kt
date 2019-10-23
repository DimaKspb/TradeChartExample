package com.render.tradechart

import android.content.Context
import android.opengl.GLES20.*
import android.opengl.GLSurfaceView
import android.util.AttributeSet
import android.util.Log
import com.render.tradechart.chart.BaseChart
import com.render.tradechart.chart.MyChart
import com.render.tradechart.component.ChartConfig
import com.render.tradechart.draw.ShaderUtils
import com.render.tradechart.model.data
import com.render.tradechart.series.LineSeries
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class MySurfaceView : GLSurfaceView {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    private val renderer = GlRender()

    init {
        setEGLContextClientVersion(3)
        setRenderer(renderer)
    }

    inner class GlRender : Renderer {
        private val vertexData: FloatBuffer =
            ByteBuffer.allocateDirect(10000 * 4).order(ByteOrder.nativeOrder()).asFloatBuffer()

        private val resolutData =
            ByteBuffer.allocateDirect(2 * 64).order(ByteOrder.nativeOrder()).asFloatBuffer()

        private val sizeScreen = FloatArray(2)

        private var aColorLocation: Int = 0
        private var aPositionLocation: Int = 0

        private var lastFrameTs: Long = 0

        private var chart: BaseChart = MyChart()

        init {
            ChartConfig.setDisplayMetrics(resources.displayMetrics)
            chart.apply {
                val mySeries = LineSeries()
                mySeries.updateLastQuote(data())
                setSeries(mySeries)
            }
        }

        val lastTime = System.currentTimeMillis()
        override fun onDrawFrame(p0: GL10?) {
            glClear(GL_COLOR_BUFFER_BIT)

            val ts = System.currentTimeMillis()
            time = (ts - lastTime) / 1000f
            render()

            lastFrameTs = ts

            val ts2 = System.currentTimeMillis()
            if (ts != ts2) {
                Log.d("GL", "FPS: " + 1000 / (ts2 - ts).toFloat())
            }
        }

        override fun onSurfaceChanged(p0: GL10?, w: Int, h: Int) {
            glViewport(0, 0, w, h)
            resolutData.put(floatArrayOf(w.toFloat(), h.toFloat()))
            resolutData.position(0)
            vertexData.put(trinagle)
            vertexData.position(0)

            glVertexAttribPointer(aPositionLocation, 2, GL_FLOAT, false, 0, vertexData)
            glEnableVertexAttribArray(aPositionLocation)
            glUniform2fv(reslutionLoc, 1, resolutData)
        }

        var time = 0f
        var timeLoc = 0
        var reslutionLoc = 0

        override fun onSurfaceCreated(p0: GL10?, p1: EGLConfig?) {
            glClearColor(0.3f, 0.3f, 0.3f, 1.0f)
            val programId = ShaderUtils.createGLPrograms(context)

            glUseProgram(programId)
            aPositionLocation = glGetAttribLocation(programId, "vPosition")
            reslutionLoc = glGetUniformLocation(programId, "uResolution")
            timeLoc = glGetUniformLocation(programId, "uTime")

            Log.d("location($programId) ", "$timeLoc,$reslutionLoc,$aPositionLocation")
        }

        private val trinagle = floatArrayOf(
            -1f, 1f,
            1f, 1f,
            1f, -1f,

            -1f, 1f,
            -1f, -1f,
            1f, -1f
        )

        private fun render() {
            glUniform1f(timeLoc, time)

            glDrawArrays(GL_LINE_STRIP, 0, trinagle.size / 2)
        }
    }
}