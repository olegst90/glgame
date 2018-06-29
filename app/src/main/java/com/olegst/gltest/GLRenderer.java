package com.olegst.gltest;

import android.opengl.GLSurfaceView.Renderer;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glViewport;
import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;

import com.olegst.gltest.engine.ResourceManager;
import com.tiengine.graphics.GGraphicHost;
import com.tiengine.utils.GResourceFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class GLRenderer implements Renderer {
    static Logger logger = LoggerFactory.getLogger(ResourceManager.class);

    GGraphicHost graphicHost;
    public GLRenderer(GGraphicHost ghost) {
        graphicHost = ghost;
    }
    @Override
    public void onSurfaceCreated(GL10 glUnused, EGLConfig config) {
        try {
            GResourceFactory.resourceFactory().reloadTextures();
        } catch (IOException e) {
            logger.error("Could not reload textures");
        }

        graphicHost.prepare();
    }

    @Override
    // Set the OpenGL viewport to fill the entire surface.
    public void onSurfaceChanged(GL10 glUnused, int width, int height) {
        glViewport(0, 0, width, height);
    }

    @Override
    // Clear the rendering surface.
    public void onDrawFrame(GL10 glUnused) {
        glClear(GL_COLOR_BUFFER_BIT);
    }

}