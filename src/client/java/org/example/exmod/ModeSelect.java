package org.example.exmod;

import com.github.puzzle.core.loader.launch.provider.mod.entrypoint.impls.ClientModInitializer;

public class ModeSelect implements ClientModInitializer {
    public static final int BOX_WIDTH = 188;
    public static final int BOX_HEIGHT = 120;

    public static final float BOX_POS_X = -((float) ModeSelect.BOX_WIDTH / 2);
    public static final float BOX_POS_Y = (float) ((-ModeSelect.BOX_HEIGHT) * 1.5);

    public static final float CREATIVE_POS_X = -((float) ModeSelect.BOX_WIDTH / 2 - 40);
    public static final float SURVIVAL_POS_X = ((float) ModeSelect.BOX_WIDTH / 2 - 72);
    public static final float ITEMS_POS_Y = -((float) ModeSelect.BOX_HEIGHT / 2 + 80);

    @Override
    public void onInit() {

    }
}
