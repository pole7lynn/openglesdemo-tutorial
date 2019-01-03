package com.pole6lynn.openglesdemo.basicprimitivedraw.basicprimitive;

import android.opengl.GLES30;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

public class Rectangle {
    private static final String TAG = "Rectangle";

    private int mVertexShader;
    private int mFragmentShader;
    private int mProgramObject;

    private String mVertexShaderSrc =
            "#version 300 es                                           \n" +
                    "layout(location = 0) in vec4 a_Position;          \n" +
                    "layout(location = 1) in vec4 a_Color;             \n" +
                    "out vec4 v_Color;                                  \n" +
                    "void main() {                                     \n" +
                    "    v_Color = a_Color;                            \n" +
                    "    gl_Position = a_Position;                     \n" +
                    "}";
    private String mFragmentShaderSrc =
            "#version 300 es                                           \n" +
                    "precision mediump float;                          \n" +
                    "in vec4 v_Color;                                  \n" +
                    "out vec4 fragColor;                               \n" +
                    "void main() {                                     \n" +
                    "    fragColor = v_Color;                          \n" +
                    "}";

    private float[] mVertexData = {
            -0.5f, 0.5f, 0.0f,      //v0
            1.0f, 0.0f, 0.0f, 1.0f, //c0
            -0.5f, -0.5f, 0.0f,     //v1
            0.0f, 1.0f, 0.0f, 1.0f, //c1
            0.5f, 0.5f, 0.0f,       //v2
            0.0f, 0.0f, 1.0f, 1.0f, //c2
            0.5f, -0.5f, 0.0f,      //v3
            1.0f, 1.0f, 1.0f, 1.0f, //c3

    };
    private short[] mIndicesData = {
            0, 1, 2, 3
    };

    private final int VERTEX_POS_SIZE = 3;
    private final int VERTEX_COLOR_SIZE = 4;

    private final int VERTEX_POS_INDEX = 0;
    private final int VERTEX_COLOR_INDEX = 1;

    private final int VERTEX_POS_OFFEST = 0;
    private final int VETREX_COLOR_OFFEST = 3;

    private FloatBuffer mVertices;
    private ShortBuffer mIndices;

    private FloatBuffer mVertexMapBuffer;
    private ShortBuffer mIndicesMapBuffer;

    private int[] mVBOIds = new int[2];

    public Rectangle() {
        mVertices = ByteBuffer.allocateDirect(mVertexData.length * 4).
                order(ByteOrder.nativeOrder()).asFloatBuffer();
        mVertices.put(mVertexData).position(0);

        mIndices = ByteBuffer.allocateDirect(mIndicesData.length * 2).
                order(ByteOrder.nativeOrder()).asShortBuffer();
        mIndices.put(mIndicesData).position(0);

    }

    public void init() {
        mVertexShader = loaderShader(GLES30.GL_VERTEX_SHADER, mVertexShaderSrc);
        mFragmentShader = loaderShader(GLES30.GL_FRAGMENT_SHADER, mFragmentShaderSrc);

        mProgramObject = GLES30.glCreateProgram();
        if (mProgramObject == 0) {
            Log.e(TAG, "Create program object failed.");
            return;
        }
        GLES30.glAttachShader(mProgramObject, mVertexShader);
        GLES30.glAttachShader(mProgramObject, mFragmentShader);
        GLES30.glLinkProgram(mProgramObject);

        int[] linked = new int[1];
        GLES30.glGetProgramiv(mProgramObject, GLES30.GL_LINK_STATUS, linked, 0);
        if (linked[0] == 0) {
            Log.e(TAG, "Link program error.");
            GLES30.glDeleteProgram(mProgramObject);
            return;
        }
    }

    private int loaderShader(int shaderType, String shaderSrc) {
        int shader;
        shader = GLES30.glCreateShader(shaderType);
        if (shader == 0) {
            Log.e(TAG, "Create shader failed");
            return 0;
        }
        GLES30.glShaderSource(shader, shaderSrc);
        GLES30.glCompileShader(shader);
        int[] compiled = new int[1];
        GLES30.glGetShaderiv(shader, GLES30.GL_COMPILE_STATUS, compiled,0);
        if (compiled[0] == 0) {
            String compileInfo = GLES30.glGetShaderInfoLog(shader);
            Log.e(TAG, "Compile shader failed. info : " + compileInfo);
            GLES30.glDeleteShader(shader);
            return 0;
        }
        return shader;
    }

    private void initVertexBufferObjects() {
        //Generate the VBO ids and load data.
        GLES30.glGenBuffers(2, mVBOIds, 0);

        mVertices.position(0);
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, mVBOIds[0]);
        GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER, mVertexData.length * 4,
                mVertices, GLES30.GL_STATIC_DRAW );

        mIndices.position(0);
        GLES30.glBindBuffer(GLES30.GL_ELEMENT_ARRAY_BUFFER, mVBOIds[1]);
        GLES30.glBufferData(GLES30.GL_ELEMENT_ARRAY_BUFFER, mIndicesData.length * 2,
                mIndices, GLES30.GL_STATIC_DRAW);

    }

    public void drawPrimitiveWithVBOs() {
        if (mVBOIds[0] == 0 || mVBOIds[1] == 0) {
            Log.i(TAG, "Create buffer object.");
            initVertexBufferObjects();
            Log.i(TAG, "mVBOIds[0] = " + mVBOIds[0] + ", mVBOIds[1] = " + mVBOIds[1]);
        }
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, mVBOIds[0]);
        GLES30.glBindBuffer(GLES30.GL_ELEMENT_ARRAY_BUFFER, mVBOIds[1]);

        GLES30.glUseProgram(mProgramObject);
        int vtxStride = 4 * (VERTEX_POS_SIZE + VERTEX_COLOR_SIZE);
        int offset = 0;
        int numIndices = 4;

        GLES30.glEnableVertexAttribArray(VERTEX_POS_INDEX);
        mVertices.position(0);
        GLES30.glVertexAttribPointer(VERTEX_POS_INDEX, VERTEX_POS_SIZE, GLES30.GL_FLOAT,
                false, vtxStride, offset);

        offset += VERTEX_POS_SIZE * 4;
        GLES30.glEnableVertexAttribArray(VERTEX_COLOR_INDEX);
        mVertices.position(VERTEX_POS_SIZE);
        GLES30.glVertexAttribPointer(VERTEX_COLOR_INDEX, VERTEX_COLOR_SIZE, GLES30.GL_FLOAT,
                false, vtxStride, offset);

        GLES30.glDrawElements(GLES30.GL_TRIANGLE_STRIP, numIndices, GLES30.GL_UNSIGNED_SHORT,
                0);

        GLES30.glDisableVertexAttribArray(VERTEX_POS_INDEX);
        GLES30.glDisableVertexAttribArray(VERTEX_COLOR_INDEX);
        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, 0);
        GLES30.glBindBuffer(GLES30.GL_ELEMENT_ARRAY_BUFFER, 0);
    }

    public void drawPrimitiveWithoutVBOs() {
        int vtxStride = 4 * (VERTEX_POS_SIZE + VERTEX_COLOR_SIZE);
        int numIndices = 4;

        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, 0);
        GLES30.glBindBuffer(GLES30.GL_ELEMENT_ARRAY_BUFFER, 0);

        GLES30.glUseProgram(mProgramObject);
        GLES30.glEnableVertexAttribArray(VERTEX_POS_INDEX);
        mVertices.position(0);
        GLES30.glVertexAttribPointer(VERTEX_POS_INDEX, VERTEX_POS_SIZE, GLES30.GL_FLOAT,
                false, vtxStride, mVertices);

        GLES30.glEnableVertexAttribArray(VERTEX_COLOR_INDEX);
        mVertices.position(VERTEX_POS_SIZE);
        GLES30.glVertexAttribPointer(VERTEX_COLOR_INDEX, VERTEX_COLOR_SIZE, GLES30.GL_FLOAT,
                false, vtxStride, mVertices);

        GLES30.glDrawElements(GLES30.GL_TRIANGLE_STRIP, numIndices, GLES30.GL_UNSIGNED_SHORT,
                mIndices);

        GLES30.glDisableVertexAttribArray(VERTEX_POS_INDEX);
        GLES30.glDisableVertexAttribArray(VERTEX_COLOR_INDEX);
    }

    public void drawWithVBOMapBuffer() {
        if (mVBOIds[0] == 0 || mVBOIds[1] == 0) {
            GLES30.glGenBuffers(2, mVBOIds, 0);

            GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, mVBOIds[0]);
            GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER, mVertexData.length *4,
                    null, GLES30.GL_STATIC_DRAW);
            mVertexMapBuffer = ((ByteBuffer)GLES30.glMapBufferRange(GLES30.GL_ARRAY_BUFFER,
                    0, mVertexData.length * 4, GLES30.GL_MAP_WRITE_BIT |
                    GLES30.GL_MAP_INVALIDATE_BUFFER_BIT)).order(ByteOrder.nativeOrder())
                    .asFloatBuffer();
            mVertexMapBuffer.put(mVertexData).position(0);
            GLES30.glUnmapBuffer(GLES30.GL_ARRAY_BUFFER);

            GLES30.glBindBuffer(GLES30.GL_ELEMENT_ARRAY_BUFFER, mVBOIds[1]);
            GLES30.glBufferData(GLES30.GL_ELEMENT_ARRAY_BUFFER, mIndicesData.length *2,
                    null, GLES30.GL_STATIC_DRAW);
            mIndicesMapBuffer = ((ByteBuffer)GLES30.glMapBufferRange(GLES30.GL_ELEMENT_ARRAY_BUFFER,
                    0, mIndicesData.length * 2, GLES30.GL_MAP_WRITE_BIT |
                            GLES30.GL_MAP_INVALIDATE_BUFFER_BIT)).order(ByteOrder.nativeOrder())
                    .asShortBuffer();
            mIndicesMapBuffer.put(mIndicesData).position(0);
            GLES30.glUnmapBuffer(GLES30.GL_ELEMENT_ARRAY_BUFFER);
        }

        int numIndices = 4;
        int vtxStride = 4 * (VERTEX_POS_SIZE + VERTEX_COLOR_SIZE);
        int offset = 0;

        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, mVBOIds[0]);
        GLES30.glBindBuffer(GLES30.GL_ELEMENT_ARRAY_BUFFER, mVBOIds[1]);

        GLES30.glEnableVertexAttribArray (VERTEX_POS_INDEX );
        GLES30.glEnableVertexAttribArray (VERTEX_COLOR_INDEX );

        GLES30.glVertexAttribPointer ( VERTEX_POS_INDEX, VERTEX_POS_SIZE,
                GLES30.GL_FLOAT, false, vtxStride, offset );

        offset += ( VERTEX_POS_SIZE * 4 );

        GLES30.glVertexAttribPointer ( VERTEX_COLOR_INDEX, VERTEX_COLOR_SIZE,
                GLES30.GL_FLOAT, false, vtxStride, offset );

        GLES30.glDrawElements ( GLES30.GL_TRIANGLE_STRIP, numIndices,
                GLES30.GL_UNSIGNED_SHORT, 0 );

        GLES30.glDisableVertexAttribArray ( VERTEX_POS_INDEX );
        GLES30.glDisableVertexAttribArray ( VERTEX_COLOR_INDEX );

        GLES30.glBindBuffer ( GLES30.GL_ARRAY_BUFFER, 0 );
        GLES30.glBindBuffer ( GLES30.GL_ELEMENT_ARRAY_BUFFER, 0 );

    }
}
