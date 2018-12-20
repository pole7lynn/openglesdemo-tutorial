package com.pole6lynn.camera2demo.basicprimitivedraw;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "BasicPrimitiveDraw";

    private final int CONTEXT_CLIENT_VERSION = 3;
    private BasicGLSurfaceView mBasicGLSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (detectGLESVersion()) {
            Log.i(TAG, "Current version of OpenGL ES is 3.0.");
            mBasicGLSurfaceView = new BasicGLSurfaceView(
                    this, CONTEXT_CLIENT_VERSION);
            setContentView(mBasicGLSurfaceView);
        } else {
            Log.i(TAG, "Current version of OpenGL ES is 2.0.");
            mBasicGLSurfaceView = new BasicGLSurfaceView(
                    this, 2);
            setContentView(mBasicGLSurfaceView);
        }
    }

    private boolean detectGLESVersion() {
        ActivityManager activityManager = (ActivityManager) getSystemService(
                Context.ACTIVITY_SERVICE);
        ConfigurationInfo configurationInfo = activityManager.getDeviceConfigurationInfo();
        return (configurationInfo.reqGlEsVersion >= 0x30000);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //mBasicGLSurfaceView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //mBasicGLSurfaceView.onPause();
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
