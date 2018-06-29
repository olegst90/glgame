package com.olegst.gltest.engine;

/**
 * Created by olegst on 6/29/18.
 */
import com.tiengine.graphics.GGraphicHost;

import java.nio.Buffer;
import java.nio.FloatBuffer;

public class GLGraphicHost extends GGraphicHost {
    @Override
    public void prepare() {
        // still sprites
        Buffer vb = FloatBuffer.allocate(__state.__still_sprites.size() * 4);
        for (int i = 0; i < __state.__still_sprites.size(); i++) {
            __state.__still_sprites.get(i).prepare(vb, i * 4);
        }
    }

    @Override
    public void render() {

    }
}
