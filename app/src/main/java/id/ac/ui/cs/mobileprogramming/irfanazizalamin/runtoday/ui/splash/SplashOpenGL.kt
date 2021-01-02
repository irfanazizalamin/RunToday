package id.ac.ui.cs.mobileprogramming.irfanazizalamin.runtoday.ui.splash

import android.content.Context
import android.opengl.GLES30
import android.opengl.GLSurfaceView
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class SplashOpenGL(context: Context?) : GLSurfaceView(context) {
    private class Renderer : GLSurfaceView.Renderer {
        override fun onDrawFrame(gl: GL10) {
            GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT)
        }

        override fun onSurfaceChanged(gl: GL10, width: Int, height: Int) {
        }

        override fun onSurfaceCreated(gl: GL10, config: EGLConfig) {
            GLES30.glClearColor(1f, 0F, 0F , 1f)
        }
    }

    init {
        // Pick an EGLConfig with RGB8 color, 16-bit depth, no stencil,
        // supporting OpenGL ES 2.0 or later backwards-compatible versions.
        setEGLContextClientVersion(3)
        preserveEGLContextOnPause = true
        setRenderer(Renderer())
    }
} 