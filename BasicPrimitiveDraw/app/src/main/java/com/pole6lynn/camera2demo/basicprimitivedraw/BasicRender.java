package com.pole6lynn.camera2demo.basicprimitivedraw;

import android.content.Context;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class BasicRender implements GLSurfaceView.Renderer {
    private static final String TAG = "BasicRender";
    private Context mContext;
    private int mViewWidth;
    private int mViewHeight;

    private String mVertexShaderSrc =
            "#version 300 es                                  \n"
            + "layout(location = 0) in vec4 vPosition;    \n"
            + "void main() {                               \n"
            + "    gl_Position = vPosition;               \n"
            + "}";
    private String mFragmentShaderSrc =
            "#version 300 es                                 \n"
            + "precision mediump float;                    \n"
            + "out vec4 fragColor;\n"
            + "void main() {\n"
            + "    fragColor = vec4(1.0f, 0.0f, 0.0f, 0.0f);\n"
            + "}\n";

    private float[] mVerticesData = {
            0.0f, 0.5f, 0.0f,
            -0.5f, -0.5f, 0.0f,
            0.5f, -0.5f, 0.0f,
    };
    private FloatBuffer mVerticesDataBuffer;

    private int mVertexShader;
    private int mFragmentShader;
    private int mProgramObject;

    public BasicRender(Context context) {
        mContext = context;
        mVerticesDataBuffer = ByteBuffer.allocateDirect(mVerticesData.length * 4).
                order(ByteOrder.nativeOrder()).asFloatBuffer();
        mVerticesDataBuffer.put(mVerticesData).position(0);
    }

    private int loadShader(int shaderType, String shaderSrc) {
        int shader;
        int[] complied = new int[1];

        shader = GLES30.glCreateShader(shaderType);
        if (shader == 0) {
            Log.e(TAG, "Create shader error.");
            return 0;
        }
        GLES30.glShaderSource(shader, shaderSrc);
        GLES30.glCompileShader(shader);
        GLES30.glGetShaderiv(shader, GLES30.GL_COMPILE_STATUS, complied, 0);
        if (complied[0] == 0) {
            Log.e(TAG, "Compile shader error.");
            GLES30.glDeleteShader(shader);
            return 0;
        }

        return shader;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        Log.i(TAG, "Surface created.");
        GLES30.glClearColor(0.0f,1.0f,0.0f,0.0f);
        mVertexShader = loadShader(GLES30.GL_VERTEX_SHADER, mVertexShaderSrc);
        mFragmentShader = loadShader(GLES30.GL_FRAGMENT_SHADER, mFragmentShaderSrc);
        int[] linked = new int[1];
        mProgramObject = GLES30.glCreateProgram();
        if (mProgramObject == 0) {
            Log.e(TAG, "Create program error.");
            return;
        }
        GLES30.glAttachShader(mProgramObject, mVertexShader);
        GLES30.glAttachShader(mProgramObject, mFragmentShader);
        GLES30.glLinkProgram(mProgramObject);
        GLES30.glGetProgramiv(mProgramObject, GLES30.GL_LINK_STATUS, linked, 0);
        if (linked[0] == 0) {
            Log.e(TAG, "The program link error.");
            GLES30.glDeleteProgram(mProgramObject);
            return;
        }
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        Log.i(TAG, "surface size changed.");
        mViewWidth = width;
        mViewHeight = height;
        GLES30.glViewport(0,0, mViewWidth, mViewHeight);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT);
        GLES30.glUseProgram(mProgramObject);
        GLES30.glVertexAttribIPointer(0, 3, GLES30.GL_FLOAT,0, mVerticesDataBuffer);
        GLES30.glEnableVertexAttribArray(0);
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, 3);
    }
}
