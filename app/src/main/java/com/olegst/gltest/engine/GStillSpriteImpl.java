package com.olegst.gltest.engine;

/**
 * Created by olegst on 28.06.18.
 */
import com.tiengine.graphics.GStillSprite;

import java.nio.Buffer;

public class GStillSpriteImpl extends GStillSprite {
    public void move(float x, float y, float z) {}
    public void moveAbs(float x, float y, float z) {}

    public void rotate(float x, float y, float z, float angle) {}
    public void rotateAbs(float x, float y, float z, float angle) {}

    public int prepare(Buffer buffer, int offset) {return 0;}

    public void render() {}
}
