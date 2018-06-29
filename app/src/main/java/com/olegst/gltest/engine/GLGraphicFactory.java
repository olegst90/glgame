package com.olegst.gltest.engine;

/**
 * Created by olegst on 28.06.18.
 */

import com.tiengine.graphics.GGraphicFactory;
import com.tiengine.graphics.GStillSprite;
import com.tiengine.graphics.GDynamicSprite;
import com.tiengine.graphics.GTextArea;

import java.nio.Buffer;

public class GLGraphicFactory implements GGraphicFactory.Interface {
    @Override
    public GStillSprite newStillSprite() {
        return new GStillSprite() {
            @Override
            public void move(float x, float y, float z) {
            }
            @Override
            public void moveAbs(float x, float y, float z) {
            }
            @Override
            public void rotate(float x, float y, float z, float angle) {
            }
            @Override
            public void rotateAbs(float x, float y, float z, float angle) {
            }
            @Override
            public int prepare(Buffer buffer, int offset) { return 0;
            }
            @Override
            public void render() {
            }
        };
    }
    @Override
    public GDynamicSprite newDynamicSprite() {
        return new GDynamicSprite() {
            @Override
            public void move(float x, float y, float z) {
            }
            @Override
            public void moveAbs(float x, float y, float z) {
            }
            @Override
            public void rotate(float x, float y, float z, float angle) {
            }
            @Override
            public void rotateAbs(float x, float y, float z, float angle) {
            }
            @Override
            public int prepare(Buffer buffer, int offset) { return 0;
            }
            @Override
            public void render() {
            }
        };
    }
    @Override
    public GTextArea newTextArea() {
        return new GTextArea() {
            @Override
            public void move(float x, float y, float z) {
            }
            @Override
            public void moveAbs(float x, float y, float z) {
            }
            @Override
            public void rotate(float x, float y, float z, float angle) {
            }
            @Override
            public void rotateAbs(float x, float y, float z, float angle) {
            }
            @Override
            public int prepare(Buffer buffer, int offset) { return 0;
            }
            @Override
            public void render() {
            }
        };
    }

    static {
        GGraphicFactory.setGraphicFactory(new GLGraphicFactory());
    }
}
