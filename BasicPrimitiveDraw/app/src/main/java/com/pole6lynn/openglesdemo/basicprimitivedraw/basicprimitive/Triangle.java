package com.pole6lynn.openglesdemo.basicprimitivedraw.basicprimitive;

import android.opengl.GLES30;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

public class Triangle {
    private static final String TAG = "Triangle";

    private float[] mVerticesData = {
            0.0f, 0.5f, 0.0f,       //v1
            1.0f, 0.0f, 0.0f, 1.0f, //c1
            -0.5f, -0.5f, 0.0f,     //v2
            0.0f, 1.0f, 0.0f, 1.0f, //c2
            0.5f, -0.5f, 0.0f,      //v3
            0.0f, 0.0f, 1.0f, 1.0f, //c3
    };
    private short[] mIndicesData = {
            0, 1, 2,
    };
    private FloatBuffer mVerticesBuffer;
    private ShortBuffer mIndicesBuffer;

    private final int VERTEX_POS_SIZE = 3;
    private final int VERTEX_COLOR_SIZE = 4;
    private final int VERTEX_POS_INDEX = 0;
    private final int VERTEX_COLOR_INDEX = 1;
    private final int VERTEX_STRIDE = 4 * (VERTEX_POS_SIZE + VERTEX_COLOR_SIZE);

    private int[] mVBOIds = new int[2];
    private int[] mVAOIds = new int[1];

    private float[] mColorData = {0.0f, 1.0f, 0.0f, 0.0f};

    private String mVertexShaderSrc =
            "#version 300 es                                    \n"
            +"layout (location = 0) in vec4 a_Position;          \n"
            +"layout (location = 1) in vec4 a_Color;             \n"
            +"out vec4 vColor;                                 \n"
            +"void main() {                                     \n"
            +"    gl_Position = a_Position;                      \n"
            +"    vColor = a_Color;                              \n"
            +"}";
    private String mFragmentShaderSrc =
            "#version 300 es                                    \n"
            +"precision mediump float;                          \n"
            +"in vec4 vColor;                                   \n"
            +"out vec4 fragColor;                               \n"
            +"void main() {                                     \n"
            +"    fragColor = vColor;                           \n"
            +"}";

    private int mVertexShader;
    private int mFragmentShader;
    private int mProgramObject;


    public Triangle() {
        mVerticesBuffer = ByteBuffer.allocateDirect(mVerticesData.length * 4).
                order(ByteOrder.nativeOrder()).asFloatBuffer();
        mVerticesBuffer.put(mVerticesData).position(0);

        mIndicesBuffer = ByteBuffer.allocateDirect ( mIndicesData.length * 2 )
                .order ( ByteOrder.nativeOrder() ).asShortBuffer();
        mIndicesBuffer.put ( mIndicesData ).position ( 0 );
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

        GLES30.glGetAttribLocation(mProgramObject, "a_Position");

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
            //compileInfoLength = compiled[1];
            String compileInfo = GLES30.glGetShaderInfoLog(shader);
            Log.e(TAG,"compileInfo = " + compileInfo);
            GLES30.glDeleteShader(shader);
            return 0;
        }
        return shader;
    }

    private void initVBOAndVAO() {
        if (mVBOIds[0] == 0 || mVBOIds[1] == 0) {
            GLES30.glGenBuffers(2, mVBOIds, 0);

            mVerticesBuffer.position(0);
            GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, mVBOIds[0]);
            GLES30.glBufferData(GLES30.GL_ARRAY_BUFFER, mVerticesData.length * 4,
                    mVerticesBuffer, GLES30.GL_STATIC_DRAW);

            mIndicesBuffer.position(0);
            GLES30.glBindBuffer(GLES30.GL_ELEMENT_ARRAY_BUFFER, mVBOIds[1]);
            GLES30.glBufferData(GLES30.GL_ELEMENT_ARRAY_BUFFER, mIndicesData.length * 2,
                    mIndicesBuffer, GLES30.GL_STATIC_DRAW);
        }

        GLES30.glGenVertexArrays(1, mVAOIds, 0);
        GLES30.glBindVertexArray(mVAOIds[0]);

        GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, mVBOIds[0]);
        GLES30.glBindBuffer(GLES30.GL_ELEMENT_ARRAY_BUFFER, mVBOIds[1]);

        GLES30.glEnableVertexAttribArray(VERTEX_POS_INDEX);
        GLES30.glEnableVertexAttribArray(VERTEX_COLOR_INDEX);
        GLES30.glVertexAttribPointer(VERTEX_POS_INDEX, VERTEX_POS_SIZE, GLES30.GL_FLOAT,
                false, VERTEX_STRIDE, 0);
        GLES30.glVertexAttribPointer(VERTEX_COLOR_INDEX, VERTEX_COLOR_SIZE, GLES30.GL_FLOAT,
                false, VERTEX_STRIDE, VERTEX_POS_SIZE * 4);
    }
    public void draw() {

        GLES30.glUseProgram(mProgramObject);
        //Specify the vertices position data by array.
        GLES30.glVertexAttribPointer ( 0, 3, GLES30.GL_FLOAT, false, VERTEX_STRIDE, mVerticesBuffer );
        GLES30.glEnableVertexAttribArray (0);
        //Specify the vertices color data by a constant vertex values.
        GLES30.glVertexAttrib4fv(1, mColorData, 0);

        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, 3);
    }

    public void drawWithVAO() {
        GLES30.glUseProgram(mProgramObject);
        initVBOAndVAO();
        GLES30.glBindVertexArray(mVAOIds[0]);
        // Draw with the VAO settings
        GLES30.glDrawElements ( GLES30.GL_TRIANGLES, mIndicesData.length, GLES30.GL_UNSIGNED_SHORT, 0 );
        // Return to the default VAO
        GLES30.glBindVertexArray ( 0 );

    }
}
