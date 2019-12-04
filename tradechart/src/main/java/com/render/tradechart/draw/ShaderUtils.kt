package com.render.tradechart.draw


import android.content.Context
import android.content.res.Resources
import android.opengl.GLES30.*
import android.util.Log
import com.render.tradechart.R
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

object ShaderUtils {
   private val status = IntArray(1)

    fun createGLPrograms(context: Context): Int {
        val vertexShaderId = createShader(context, GL_VERTEX_SHADER, R.raw.vertex)
        val fragmentShaderId = createShader(context, GL_FRAGMENT_SHADER, R.raw.fragment)

        return createShaderProgram(vertexShaderId, fragmentShaderId)
    }

    private fun createShader(context: Context, type: Int, shaderRawId: Int): Int {
        val shaderText = readTextFromRaw(context, shaderRawId)
        val shaderId = glCreateShader(type)

        glShaderSource(shaderId, shaderText)
        glCompileShader(shaderId)
        glGetShaderiv(shaderId, GL_COMPILE_STATUS, status, 0)

        getShaderCompileLog(shaderId)

        if (status[0] == 0) {
            glDeleteShader(shaderId)
            return 0
        }

        return shaderId
    }


    private fun createShaderProgram(vertexShaderId: Int, fragmentShaderId: Int): Int {
        val programId = glCreateProgram()
        if (programId == 0) {
            return 0
        }
        glAttachShader(programId, vertexShaderId)
        glAttachShader(programId, fragmentShaderId)
        glLinkProgram(programId)
        glGetProgramiv(programId, GL_LINK_STATUS, status, 0)
        if (status[0] == 0) {
            glDeleteProgram(programId)
            return 0
        }
        return programId
    }

    private fun getShaderCompileLog(shader: Int) {
        Log.d("GL Error($shader)", glGetShaderInfoLog(shader))
    }

    private fun readTextFromRaw(context: Context, resourceId: Int): String {
        val stringBuilder = StringBuilder()
        try {
            var bufferedReader: BufferedReader? = null
            try {
                val inputStream = context.resources.openRawResource(resourceId)
                bufferedReader = BufferedReader(InputStreamReader(inputStream))
                var line = bufferedReader.readLine()
                while (line != null) {
                    stringBuilder.append(line)
                    stringBuilder.append("\r\n")
                    line = bufferedReader.readLine()
                }
            } finally {
                bufferedReader?.close()
            }
        } catch (ioex: IOException) {
            ioex.printStackTrace()
        } catch (nfex: Resources.NotFoundException) {
            nfex.printStackTrace()
        }

        return stringBuilder.toString()
    }
}
