package com.olegst.gltest.engine;

/**
 * Created by olegst on 28.06.18.
 */
import com.tiengine.controls.GControlFactory;
import com.tiengine.controls.GButton;

public class GLControlFactory implements GControlFactory.Interface {
    public GButton newButton() {
        return null;
    }

    static {
        GControlFactory.setControlFactory(new GLControlFactory());
    }
}
