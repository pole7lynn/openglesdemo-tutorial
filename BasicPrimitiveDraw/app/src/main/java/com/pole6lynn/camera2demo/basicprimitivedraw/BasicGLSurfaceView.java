package com.pole6lynn.camera2demo.basicprimitivedraw;

import android.content.Context;
import android.opengl.GLSurfaceView;

public class BasicGLSurfaceView extends GLSurfaceView {
    private static final String TAG = "BasicGLSurfaceView";

    private BasicRender mBasicRender;

    public BasicGLSurfaceView(Context context, int version) {
        super(context);
        setEGLContextClientVersion(version);
        mBasicRender = new BasicRender(context);
        setRenderer(mBasicRender);
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
