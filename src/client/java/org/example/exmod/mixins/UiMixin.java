package org.example.exmod.mixins;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import finalforeach.cosmicreach.ui.UI;
import org.example.exmod.ModeSelect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(UI.class)
public class UiMixin {
    @Unique
    private static final Color background = new Color(Color.BLACK.r, Color.BLACK.g, Color.BLACK.b, 0.4f);

    @Shadow
    public static boolean renderUI;

    @Shadow
    ShapeRenderer shapeRenderer;

    @Inject(method = "render", at = @At("TAIL"))
    private void drawBrokenBlocks(CallbackInfo ci) {
        if (!UiMixin.renderUI) {
            return;
        }

        if (!Gdx.input.isKeyPressed(Input.Keys.F4)) {
            return;
        }

        Gdx.gl.glEnable(3042);
        Gdx.gl.glDepthFunc(519);
        Gdx.gl.glBlendFunc(770, 771);
        Gdx.gl.glCullFace(1028);

        this.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        this.shapeRenderer.setColor(UiMixin.background);

        ModeSelect.boxPosX = -((float) ModeSelect.BOX_WIDTH / 2);
        ModeSelect.boxPosY = (float) ((-ModeSelect.BOX_HEIGHT) * 1.5);

        this.shapeRenderer.rect(ModeSelect.boxPosX, ModeSelect.boxPosY, ModeSelect.BOX_WIDTH, ModeSelect.BOX_HEIGHT);

        this.shapeRenderer.end();

        this.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        this.shapeRenderer.setColor(Color.LIGHT_GRAY);
        this.shapeRenderer.rect(ModeSelect.boxPosX + 3, ModeSelect.boxPosY + 3, ModeSelect.BOX_WIDTH - 6, ModeSelect.BOX_HEIGHT - 6);

        this.shapeRenderer.end();
    }
}
