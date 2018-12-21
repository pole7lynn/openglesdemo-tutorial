package com.pole6lynn.openglesdemo.basicprimitivedraw.basicprimitive;

import android.opengl.GLES30;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class Triangle {
    private static final String TAG = "Triangle";

    private float[] mVerticesData = {
            0.0f, 1.0f, 0.0f,
            -1.0f, -1.0f, 0.0f,
            1.0f, -1.0f, 0.0f
    };
    private FloatBuffer mVerticesBuffer;

    private String mVertexShaderSrc =
            "#version 300 es                                    \n"
            +"layout (location = 0) in vec4 vPosition;          \n"
            +"void main() {                                     \n"
            +"    gl_Position = vPosition;                      \n"
            +"}";
    private String mFragmentShaderSrc =
            "#version 300 es                                    \n"
            //+"precision mediump float;                          \n"
            +"out vec4 fragColor;                               \n"
            +"void main() {                                     \n"
            +"    fragColor = vec4(1.0f, 0.0f, 0.0f, 1.0f);     \n"
            +"}";

    private int mVertexShader;
    private int mFragmentShader;
    private int mProgramObject;


    public Triangle() {
        mVerticesBuffer = ByteBuffer.allocateDirect(mVerticesData.length * 4).
                order(ByteOrder.nativeOrder()).asFloatBuffer();
        mVerticesBuffer.put(mVerticesData).position(0);
    }

    public void init() {
        mVertexShader = loadShader(GLES30.GL_VERTEX_SHADER, mVertexShaderSrc);
        mFragmentShader = loadShader(GLES30.GL_FRAGMENT_SHADER, mFragmentShaderSrc);

        mProgramObject = GLES30.glCreateProgram();
        int[] linked = new int[1];
        if (mProgramObject == 0) {
            Log.e(TAG, "Create program object error.");
            return;
        }
        GLES30.glAttachShader(mProgramObject, mVertexShader);
        GLES30.glAttachShader(mProgramObject, mFragmentShader);
        GLES30.glLinkProgram(mProgramObject);
        GLES30.glGetProgramiv(mProgramObject, GLES30.GL_LINK_STATUS, linked, 0);
        if (linked[0] == 0) {
            Log.e(TAG, "link program error");
            GLES30.glDeleteProgram(mProgramObject);
            return;
        }
    }
    private int loadShader(int shaderType, String src) {
        int shader;
        int[] compiled = new int[1];

        shader = GLES30.glCreateShader(shaderType);
        if (shader == 0) {
            Log.e(TAG, "Create shader fail " + shaderType);
            return 0;
        }
        GLES30.glShaderSource(shader, src);
        GLES30.glCompileShader(shader);

        GLES30.glGetShaderiv(shader, GLES30.GL_COMPILE_STATUS, compiled, 0);
        if (compiled[0] == 0) {
            Log.e(TAG, "Compile shader error.");
            GLES30.glDeleteShader(shader);
            return 0;
        }
        return shader;
    }

    public void draw() {
        GLES30.glUseProgram(mProgramObject);
        GLES30.glVertexAttribPointer ( 0, 3, GLES30.GL_FLOAT, false, 0, mVerticesBuffer );
        GLES30.glEnableVertexAttribArray (0);
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, 3);
    }
}
