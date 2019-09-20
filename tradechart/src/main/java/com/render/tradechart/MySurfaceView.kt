package com.render.tradechart

import android.content.Context
import android.opengl.GLES10.glClearColor
import android.opengl.GLES20.*
import android.opengl.GLSurfaceView
import android.util.AttributeSet
import android.util.Log
import com.render.tradechart.chart.Chart
import com.render.tradechart.chart.MyChart
import com.render.tradechart.component.ChartConfig
import com.render.tradechart.model.data
import com.render.tradechart.series.LineSeries
import com.render.tradechart.utils.ShaderUtils
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10


class MySurfaceView : GLSurfaceView {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)


    init {
        setEGLContextClientVersion(3)
        val renderer = GlRender()
        setRenderer(renderer)
    }

    inner class GlRender : Renderer {
        private val vertexData: FloatBuffer = ByteBuffer.allocateDirect(10000 * 4).order(ByteOrder.nativeOrder()).asFloatBuffer()
        private var aColorLocation: Int = 0
        private var aPositionLocation: Int = 0

        private var lastFrameTs: Long = 0


        private var chart = MyChart()

        init {
            ChartConfig.setDisplayMetrics(resources.displayMetrics)
            chart.apply {
                val mySeries = LineSeries()
                mySeries.addAllPoint(data())
                setSeries(mySeries)
            }
        }

        override fun onDrawFrame(p0: GL10?) {
            glClear(GL_COLOR_BUFFER_BIT)

            val ts = System.currentTimeMillis()
            renderLayouts()
            lastFrameTs = ts

            val ts2 = System.currentTimeMillis()
            if (ts != ts2) {
                Log.d("GL", "FPS: " + 1000 / (ts2 - ts).toInt())
            }
        }

        override fun onSurfaceChanged(p0: GL10?, w: Int, h: Int) {
            glViewport(0, 0, w, h)
            chart.setSize(w, h)
        }

        override fun onSurfaceCreated(p0: GL10?, p1: EGLConfig?) {
            glClearColor(0.3f, 0.3f, 0.3f, 1.0f)
            val vertexShaderId = ShaderUtils.createShader(context, GL_VERTEX_SHADER, R.raw.vertex_shader)
            val fragmentShaderId = ShaderUtils.createShader(context, GL_FRAGMENT_SHADER, R.raw.fragment_shader)
            val programId = ShaderUtils.createProgram(vertexShaderId, fragmentShaderId)
            glUseProgram(programId)

            aPositionLocation = glGetAttribLocation(programId, "a_Position")
            vertexData.position(0)
            glVertexAttribPointer(aPositionLocation, 2, GL_FLOAT, false, 20, vertexData)
            glEnableVertexAttribArray(aPositionLocation)

            aColorLocation = glGetAttribLocation(programId, "a_Color")
            vertexData.position(2)
            glVertexAttribPointer(aColorLocation, 3, GL_FLOAT, false, 20, vertexData)
            glEnableVertexAttribArray(aColorLocation)
        }
    }

    private fun renderLayouts() {

    }
}