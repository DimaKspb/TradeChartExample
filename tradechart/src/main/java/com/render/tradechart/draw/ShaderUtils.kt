package com.render.tradechart.draw

import android.opengl.GLES20.GL_COMPILE_STATUS
import android.opengl.GLES20.GL_LINK_STATUS
import android.opengl.GLES20.glAttachShader
import android.opengl.GLES20.glCompileShader
import android.opengl.GLES20.glCreateProgram
import android.opengl.GLES20.glCreateShader
import android.opengl.GLES20.glDeleteProgram
import android.opengl.GLES20.glDeleteShader
import android.opengl.GLES20.glGetProgramiv
import android.opengl.GLES20.glGetShaderiv
import android.opengl.GLES20.glLinkProgram
import android.opengl.GLES20.glShaderSource


import android.content.Context
import android.content.res.Resources
import android.opengl.GLES20
import com.render.tradechart.R
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

object ShaderUtils {
    fun createProgram(vertexShaderId: Int, fragmentShaderId: Int): Int {
        val programId = glCreateProgram()
        if (programId == 0) {
            return 0
        }
        glAttachShader(programId, vertexShaderId)
        glAttachShader(programId, fragmentShaderId)
        glLinkProgram(programId)
        val linkStatus = IntArray(1)
        glGetProgramiv(programId, GL_LINK_STATUS, linkStatus, 0)
        if (linkStatus[0] == 0) {
            glDeleteProgram(programId)
            return 0
        }
        return programId
    }

    fun createShader(context: Context, type: Int, shaderRawId: Int): Int {
        val shaderText =
            readTextFromRaw(context, shaderRawId)
        return createShader(type, shaderText)
    }

    private fun createShader(type: Int, shaderText: String): Int {
        val shaderId = glCreateShader(type)
        if (shaderId == 0) {
            return 0
        }
        glShaderSource(shaderId, shaderText)
        glCompileShader(shaderId)
        val compileStatus = IntArray(1)
        glGetShaderiv(shaderId, GL_COMPILE_STATUS, compileStatus, 0)
        if (compileStatus[0] == 0) {
            glDeleteShader(shaderId)
            return 0
        }
        return shaderId
    }

    fun getProgrammID(context: Context): Int {
        val vertexShaderId = createShader(context, GLES20.GL_VERTEX_SHADER, R.raw.vertex)
        val fragmentShaderId = createShader(context, GLES20.GL_FRAGMENT_SHADER, R.raw.fragment)
        val programId = createProgram(vertexShaderId, fragmentShaderId)

        return programId
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
