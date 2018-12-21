package com.pole6lynn.openglesdemo.basicprimitivedraw;

import android.content.Context;
import android.opengl.GLSurfaceView;

public class BasicGLSurfaceView extends GLSurfaceView {
    private static final String TAG = "BasicGLSurfaceView";

    private BasicRender mBasicRender;

    public BasicGLSurfaceView(Context context) {
        super(context);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

}
