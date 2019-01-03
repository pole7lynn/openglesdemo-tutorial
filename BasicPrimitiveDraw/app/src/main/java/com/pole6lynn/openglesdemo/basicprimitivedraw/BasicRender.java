package com.pole6lynn.openglesdemo.basicprimitivedraw;

import android.content.Context;
import android.opengl.EGL14;
import android.opengl.EGLDisplay;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.util.Log;

import com.pole6lynn.openglesdemo.basicprimitivedraw.basicprimitive.Rectangle;
import com.pole6lynn.openglesdemo.basicprimitivedraw.basicprimitive.Triangle;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class BasicRender implements GLSurfaceView.Renderer {
    private static final String TAG = "BasicRender";

    private EGLDisplay eglDisplay;
    private int[] major;
    private int[] minor;

    private int mViewWidth;
    private int mViewHeight;

    private Triangle mTriangle;
    private Rectangle mRectangle;

    private int[] maxVertexAttributes;

    public BasicRender(Context context) {
        /*major = new int[1];
        minor = new int[1];
        eglDisplay = EGL14.eglGetDisplay(EGL14.EGL_DEFAULT_DISPLAY);
        EGL14.eglInitialize(eglDisplay, major, 0, minor, 0);*/

        mTriangle = new Triangle();
        mRectangle = new Rectangle();

        //GLES30.glGetIntegerv(GLES30.GL_MAX_VERTEX_ATTRIBS, maxVertexAttributes, 0);

    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES30.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        mTriangle.init();
        mRectangle.init();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES30.glViewport(0, 0, width, height);
        mViewWidth = width;
        mViewHeight = height;
        Log.i(TAG, "mViewWidth = " + mViewWidth + ", mViewHeight = " + mViewHeight);

    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT);
        GLES30.glViewport(0, 0, mViewWidth, mViewHeight/2);
        mTriangle.draw();

        GLES30.glViewport(0, mViewHeight/2, mViewWidth, mViewHeight/2);
        mRectangle.drawPrimitiveWithoutVBOs();

    }
}
