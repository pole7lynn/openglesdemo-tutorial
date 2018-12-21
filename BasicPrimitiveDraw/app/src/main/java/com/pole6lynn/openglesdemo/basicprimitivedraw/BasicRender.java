package com.pole6lynn.openglesdemo.basicprimitivedraw;

import android.content.Context;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.util.Log;

import com.pole6lynn.openglesdemo.basicprimitivedraw.basicprimitive.Triangle;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class BasicRender implements GLSurfaceView.Renderer {
    private static final String TAG = "BasicRender";

    private int mViewWidth;
    private int mViewHeight;

    private Triangle mTriangle;

    public BasicRender(Context context) {
        mTriangle = new Triangle();

    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES30.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        mTriangle.init();
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
        mTriangle.draw();
    }
}
