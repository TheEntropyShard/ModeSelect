package org.example.exmod;

import com.github.puzzle.core.loader.launch.provider.mod.entrypoint.impls.ClientModInitializer;

public class ModeSelect implements ClientModInitializer {
    public static final int BOX_WIDTH = 250;
    public static final int BOX_HEIGHT = 120;

    public static float boxPosX;
    public static float boxPosY;

    @Override
    public void onInit() {
        Constants.LOGGER.info("Hello From INIT");
    }

}
