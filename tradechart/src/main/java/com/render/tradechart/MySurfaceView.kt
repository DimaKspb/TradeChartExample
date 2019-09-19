package com.render.tradechart

import android.annotation.SuppressLint
import android.content.Context
import android.opengl.GLES10.glClearColor
import android.opengl.GLES20.*
import android.opengl.GLES30
import android.opengl.GLSurfaceView
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceHolder
import com.render.tradechart.component.Chart
import com.render.tradechart.component.ChartConfig
import com.render.tradechart.model.Quote
import com.render.tradechart.model.data
import com.render.tradechart.series.LineSeries
import com.render.tradechart.utils.ShaderUtils
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import java.util.*
import java.util.concurrent.ThreadLocalRandom
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10


class MySurfaceView : GLSurfaceView {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    private var chart: Chart? = null

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


        init {
            ChartConfig.setDisplayMetrics(resources.displayMetrics)

            chart = Chart(height, width)

            chart?.apply {
                val mySeries = LineSeries()
                mySeries.addAllPoint(data())
                setSeries(mySeries)
            }
        }

        override fun onDrawFrame(p0: GL10?) {
            glClear(GL_COLOR_BUFFER_BIT)
            //repaint();
            val ts = System.currentTimeMillis()
            //if (ts - lastFrameTs > 10) {
//            repaint()
            //glDrawArrays(GL_LINE_STRIP, 0, 1000);
            lastFrameTs = ts
            //}
            val ts2 = System.currentTimeMillis()
            if (ts != ts2) {
                Log.d("GL", "FPS: " + 1000 / (ts2 - ts).toInt())
            }        }

        override fun onSurfaceChanged(p0: GL10?, w: Int, h: Int) {
            glViewport(0, 0, w, h )
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
}