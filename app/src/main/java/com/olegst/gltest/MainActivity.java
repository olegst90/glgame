package com.olegst.gltest;

import android.support.v7.app.AppCompatActivity;
import android.app.ActivityManager;
import android.os.Bundle;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.widget.Toast;
import com.tiengine.scripting.ScriptEngine;
import android.util.Log;
import com.tiengine.utils.ResourceFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity implements ResourceFactory.Interface {
    private GLSurfaceView glSurfaceView;
    private boolean rendererSet = false;

    @Override
    public InputStream loadScript(String name) throws FileNotFoundException {
        try {
            return getAssets().open(name);
        } catch (IOException e) {
            throw new FileNotFoundException();
        }
    }

    Thread scriptThread;

    void initEngine() {
        ResourceFactory.setResourceFactory(this);

        scriptThread = new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("main","Creating script engine");
                ScriptEngine engine = new ScriptEngine();
                engine.loadScript("root.lua");
                try {
                    while (engine.scriptLoop() == ScriptEngine.CONTINUE) {
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
        initEngine();
        glSurfaceView = new GLSurfaceView(this);

        // Проверяем поддерживается ли OpenGL ES 2.0.
        final ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        final ConfigurationInfo configurationInfo = activityManager.getDeviceConfigurationInfo();
        final boolean supportsEs2 = configurationInfo.reqGlEsVersion >= 0x20000;
        if (supportsEs2) {
// Request an OpenGL ES 2.0 compatible context. glSurfaceView.setEGLContextClientVersion(2);
// Assign our renderer.
            glSurfaceView.setRenderer(new GLRenderer()); rendererSet = true;
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
