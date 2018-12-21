package com.pole6lynn.openglesdemo.basicprimitivedraw;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private BasicGLSurfaceView mBasicGLSurfaceView;
    private final int CONTEXT_CLIENT_VERSION = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBasicGLSurfaceView = new BasicGLSurfaceView(this);
        if (detectOpenGLES30()) {
            // Tell the surface view we want to create an OpenGL ES 3.0-compatible
            // context, and set an OpenGL ES 3.0-compatible renderer.
            mBasicGLSurfaceView.setEGLContextClientVersion(CONTEXT_CLIENT_VERSION);
            mBasicGLSurfaceView.setRenderer(new BasicRender(this));
            setContentView(mBasicGLSurfaceView);
        } else {
            Log.e(TAG, "OpenGL ES 3.0 not supported on device.  Exiting...");
            finish();
        }
    }

    private boolean detectOpenGLES30() {
        ActivityManager am =
                (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        ConfigurationInfo info = am.getDeviceConfigurationInfo();
        return (info.reqGlEsVersion >= 0x30000);
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mBasicGLSurfaceView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mBasicGLSurfaceView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
