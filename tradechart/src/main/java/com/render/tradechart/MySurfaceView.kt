package com.render.tradechart

import android.content.Context
import android.opengl.GLES30.*
import android.opengl.GLSurfaceView
import android.util.AttributeSet
import android.util.Log
import com.render.tradechart.chart.BaseChart
import com.render.tradechart.chart.MyChart
import com.render.tradechart.component.ChartConfig
import com.render.tradechart.draw.GLESDraw
import com.render.tradechart.model.data
import com.render.tradechart.series.LineSeries
import com.render.tradechart.draw.ShaderUtils
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
//        setEGLConfigChooser(MyConfigChooser())
        setRenderer(renderer)
        renderMode = RENDERMODE_WHEN_DIRTY
    }

    inner class GlRender : Renderer {
        private val vertexData: FloatBuffer = ByteBuffer.allocateDirect(10000 * 4).order(ByteOrder.nativeOrder()).asFloatBuffer()
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

        override fun onDrawFrame(p0: GL10?) {
            glClear(GL_COLOR_BUFFER_BIT)

            val ts = System.currentTimeMillis()
            render()

            lastFrameTs = ts

            val ts2 = System.currentTimeMillis()
            if (ts != ts2) {
                Log.d("GL", "FPS: " + 1000 / (ts2 - ts).toFloat())
            }
        }

        override fun onSurfaceChanged(p0: GL10?, w: Int, h: Int) {
            glViewport(0, 0, w, h)

            chart.setSize(w, h)
        }

        override fun onSurfaceCreated(p0: GL10?, p1: EGLConfig?) {
            glClearColor(0.3f, 0.3f, 0.3f, 1.0f)
            val programId = ShaderUtils.createGLPrograms(context)
            glUseProgram(programId)

            aColorLocation = glGetUniformLocation(programId, "color")
            glUniform4f(aColorLocation, 0.0f, 0.0f, 1.0f, 1.0f)
            aPositionLocation = glGetAttribLocation(programId, "a_Position")
//            vertexData.position(0)

            glVertexAttribPointer(aPositionLocation, 2, GL_FLOAT, false, 0, vertexData)
            glEnableVertexAttribArray(aPositionLocation)

            GLESDraw.init(vertexData)
        }

        private fun render() {
            chart.draw()
        }
    }


}