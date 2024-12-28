package org.example.exmod.mixins;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.viewport.Viewport;
import finalforeach.cosmicreach.entities.player.Gamemode;
import finalforeach.cosmicreach.entities.player.Player;
import finalforeach.cosmicreach.gamestates.GameState;
import finalforeach.cosmicreach.gamestates.InGame;
import finalforeach.cosmicreach.items.Item;
import finalforeach.cosmicreach.rendering.items.ItemRenderer;
import finalforeach.cosmicreach.ui.FontRenderer;
import finalforeach.cosmicreach.ui.UI;
import finalforeach.cosmicreach.ui.widgets.ItemSlotWidget;
import org.example.exmod.ModeSelect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGame.class)
public class InGameMixin extends GameState {
    @Shadow
    private static Player localPlayer;

    private static Texture fluidVacuumTexture = new Texture(Gdx.files.internal("base/textures/items/fluid_vacuum.png"));

    @Inject(method = "render", at = @At("TAIL"))
    private void renderSelector(CallbackInfo ci) {
        if (!UI.renderUI) {
            return;
        }

        if (!Gdx.input.isKeyPressed(Input.Keys.F4)) {
            return;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.F5)) {
            if (InGameMixin.localPlayer.gamemode == Gamemode.SURVIVAL) {
                InGameMixin.localPlayer.gamemode = Gamemode.CREATIVE;
                InGameMixin.localPlayer.getEntity().noClip = true;
            } else if (InGameMixin.localPlayer.gamemode == Gamemode.CREATIVE) {
                InGameMixin.localPlayer.gamemode = Gamemode.SURVIVAL;
                InGameMixin.localPlayer.getEntity().noClip = false;
            }
        }

        String gamemodeText;

        if (InGameMixin.localPlayer.gamemode == Gamemode.SURVIVAL) {
            gamemodeText = "Survival";
        } else if (InGameMixin.localPlayer.gamemode == Gamemode.CREATIVE) {
            gamemodeText = "Creative";
        } else {
            throw new RuntimeException("Player has unexpected gamemode! " + InGameMixin.localPlayer.gamemode);
        }

        Vector2 textDim = new Vector2();
        FontRenderer.getTextDimensions(super.uiViewport, gamemodeText, textDim);

        GameState.batch.begin();

        FontRenderer.drawText(
                GameState.batch,
                super.uiViewport,
                gamemodeText,
                -(textDim.x / 2), (float) (ModeSelect.boxPosY + textDim.y)
        );

        textDim.set(0, 0);
        FontRenderer.getTextDimensions(super.uiViewport, "[ F5 ] Next", textDim);

        FontRenderer.drawText(
                GameState.batch,
                super.uiViewport,
                "[ F5 ] Next",
                -(textDim.x / 2), (float) (ModeSelect.boxPosY) + ModeSelect.BOX_HEIGHT - textDim.y * 2
        );

        GameState.batch.end();

        GameState.batch.begin();

        GameState.batch.draw(UI.container9Patch.getTexture(), 0, 0);


        GameState.batch.draw(fluidVacuumTexture, 0, 0, 30, 30);

        GameState.batch.end();
    }
}
