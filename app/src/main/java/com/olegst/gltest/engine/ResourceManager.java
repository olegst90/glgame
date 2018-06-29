package com.olegst.gltest.engine;

/**
 * Created by olegst on 6/29/18.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;

import com.tiengine.utils.GResourceFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ResourceManager implements GResourceFactory.Interface {
    Context context;
    GLSurfaceView surfaceView;
    static Logger logger = LoggerFactory.getLogger(ResourceManager.class);

    public ResourceManager(Context context, GLSurfaceView surfaceView) {
        this.context = context;
        this.surfaceView = surfaceView;
    }

    @Override
    public InputStream loadScript(String name) throws FileNotFoundException {
        try {
            return context.getAssets().open(name);
        } catch (IOException e) {
            throw new FileNotFoundException();
        }
    }

   // load texture and return it's openGL handle
   int loadTextureInternal(String name) throws IOException {
        final int[] textureHandle = new int[1];

        GLES20.glGenTextures(1, textureHandle, 0);

        if (textureHandle[0] != 0)
        {
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inScaled = false;   // No pre-scaling

            // Read in the resource
            final Bitmap bitmap = BitmapFactory.decodeStream(context.getAssets().open(name), null, options);

            // Bind to the texture in OpenGL
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureHandle[0]);

            // Set filtering
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);

            // Load the bitmap into the bound texture.
            GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);

            // Recycle the bitmap, since its data has been loaded into OpenGL.
            bitmap.recycle();
        }

        if (textureHandle[0] == 0)
        {
            throw new IOException("Error loading texture.");
        }

        return textureHandle[0];
    }

    class TexInfo {
        String name;
        int id;
    }

    Map<Integer, TexInfo> __textures = new HashMap<>();
    Map<String, Integer> __tex_ids = new HashMap<>();
    static int __next_id = 0;
    @Override
    public int loadTexture(String name) throws IOException {
        Integer info_id = __tex_ids.get(name);
        if (info_id != null) {
            return info_id.intValue();
        }
        Integer id = __next_id++;

        final TexInfo info = new TexInfo();
        info.name = name;
        __textures.put(id, info);
        __tex_ids.put(name, id);
        surfaceView.queueEvent(new Runnable() {
            @Override
            public void run() {
                try {
                    info.id = loadTextureInternal(info.name);
                } catch (IOException e) {
                    logger.error("Could not load texture\n");
                }
            }
        });
        return id.intValue();
    }

    @Override
    public void reloadTextures() throws IOException {
        for (Map.Entry<Integer, TexInfo> i : __textures.entrySet()) {
            final TexInfo info = i.getValue();
            surfaceView.queueEvent(new Runnable() {
                @Override
                public void run() {
                    try {
                        info.id = loadTextureInternal(info.name);
                    } catch (IOException e) {
                        logger.error("Could not load texture\n");
                    }
                }
            });
        }
    }

}
