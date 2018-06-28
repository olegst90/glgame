package com.olegst.gltest.engine;

/**
 * Created by olegst on 28.06.18.
 */

import com.tiengine.graphics.GGraphicFactory;
import com.tiengine.graphics.GSprite;
import com.tiengine.graphics.GTextArea;

public class GLGraphicFactory implements GGraphicFactory.Interface {
    public GSprite newSprite(){
        return null;
    }
    public GTextArea newTextArea() {
        return null;
    }

    static {
        GGraphicFactory.setGraphicFactory(new GLGraphicFactory());
    }
}
