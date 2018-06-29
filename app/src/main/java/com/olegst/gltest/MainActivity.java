package com.olegst.gltest;

import android.support.v7.app.AppCompatActivity;
import android.app.ActivityManager;
import android.os.Bundle;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.widget.Toast;

import com.olegst.gltest.engine.GLGraphicHost;
import com.olegst.gltest.engine.ResourceManager;
import com.tiengine.scripting.ScriptHost;
import com.tiengine.graphics.GGraphicHost;
import com.tiengine.controls.GControlHost;
import com.tiengine.utils.GResourceFactory;
import android.util.Log;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    private GLSurfaceView glSurfaceView;
    private boolean rendererSet = false;

    Thread scriptThread;
    ScriptHost scriptHost;
    GGraphicHost graphicHost;
    GControlHost controlHost;

    void initEngine() {
        GResourceFactory.setResourceFactory(new ResourceManager(this, glSurfaceView));
        graphicHost = new GLGraphicHost();
        controlHost = new GControlHost(graphicHost);
        scriptHost = new ScriptHost();
        scriptHost.setGraphicHost(graphicHost);
        scriptHost.setControlHost(controlHost);

        scriptThread = new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("main","Creating script engine");
                scriptHost.loadScript("root.lua");
                try {
                    while (scriptHost.scriptLoop() == ScriptHost.CONTINUE) {
                        //terminate if...
                    }
                } catch(Exception e) {
                    Log.e("<main>", "Abnormal script engine termination");
                }
            }
        });
        scriptThread.start();
        Log.d("[main]","Script thread started");
        //terminate?
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        glSurfaceView = new GLSurfaceView(this);
        initEngine();

        // Проверяем поддерживается ли OpenGL ES 2.0.
        final ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        final ConfigurationInfo configurationInfo = activityManager.getDeviceConfigurationInfo();
        final boolean supportsEs2 = configurationInfo.reqGlEsVersion >= 0x20000;
        if (supportsEs2) {
// Request an OpenGL ES 2.0 compatible context. glSurfaceView.setEGLContextClientVersion(2);
// Assign our renderer.
            glSurfaceView.setRenderer(new GLRenderer(graphicHost)); rendererSet = true;
        } else {
            Toast.makeText(this, "This device does not support OpenGL ES 2.0.", Toast.LENGTH_LONG).show(); return;
        }

        setContentView(glSurfaceView);

    }

    @Override
    protected void onPause() { super.onPause();
        if (rendererSet) {
            glSurfaceView.onPause();
        }
    }
    @Override
    protected void onResume() { super.onResume();
        if (rendererSet) {
            glSurfaceView.onResume();
        }
    }
}
