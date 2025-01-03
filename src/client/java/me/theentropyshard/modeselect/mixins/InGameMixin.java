package me.theentropyshard.modeselect.mixins;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import finalforeach.cosmicreach.entities.player.Gamemode;
import finalforeach.cosmicreach.entities.player.Player;
import finalforeach.cosmicreach.gamestates.GameState;
import finalforeach.cosmicreach.gamestates.InGame;
import finalforeach.cosmicreach.ui.FontRenderer;
import finalforeach.cosmicreach.ui.UI;
import me.theentropyshard.modeselect.ModeSelect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGame.class)
public class InGameMixin extends GameState {
    @Shadow
    private static Player localPlayer;

    @Unique
    private static final Texture exampleMod$fluidVacuumTexture = new Texture(Gdx.files.internal("assets/modeselect/images/blue_light.png"));

    @Unique
    private static final Texture exampleMod$ironPickaxeTexture = new Texture(Gdx.files.internal("base/textures/items/pickaxe_iron.png"));

    @Unique
    private static final Vector2 exampleMod$textDimTmp = new Vector2();

    @Inject(method = "render", at = @At("TAIL"))
    private void renderSelector(CallbackInfo ci) {
        if (!UI.renderUI) {
            return;
        }

        if (!Gdx.input.isKeyPressed(Input.Keys.G)) {
            return;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.H)) {
            InGameMixin.exampleMod$switchGamemode();
        }

        String gamemodeText = InGameMixin.exampleMod$getGamemodeText();

        GameState.batch.begin();

        FontRenderer.getTextDimensions(super.uiViewport, gamemodeText, InGameMixin.exampleMod$textDimTmp);
        FontRenderer.drawText(
                GameState.batch,
                super.uiViewport,
                gamemodeText,
                -(InGameMixin.exampleMod$textDimTmp.x / 2),
                ModeSelect.BOX_POS_Y + InGameMixin.exampleMod$textDimTmp.y
        );

        FontRenderer.getTextDimensions(super.uiViewport, "[H] Next", InGameMixin.exampleMod$textDimTmp);
        FontRenderer.drawText(
                GameState.batch,
                super.uiViewport,
                "[H] Next",
                -(InGameMixin.exampleMod$textDimTmp.x / 2), ModeSelect.BOX_POS_Y + ModeSelect.BOX_HEIGHT - InGameMixin.exampleMod$textDimTmp.y * 2
        );

        InGameMixin.exampleMod$drawContainer(ModeSelect.CREATIVE_POS_X, InGameMixin.localPlayer.gamemode == Gamemode.CREATIVE);
        InGameMixin.exampleMod$drawItemTexture(
                InGameMixin.exampleMod$fluidVacuumTexture,
                ModeSelect.CREATIVE_POS_X + 2, ModeSelect.ITEMS_POS_Y + 2,
                28, 28
        );

        InGameMixin.exampleMod$drawContainer(ModeSelect.SURVIVAL_POS_X, InGameMixin.localPlayer.gamemode == Gamemode.SURVIVAL);
        InGameMixin.exampleMod$drawItemTexture(
                InGameMixin.exampleMod$ironPickaxeTexture,
                ModeSelect.SURVIVAL_POS_X + 3, ModeSelect.ITEMS_POS_Y + 3,
                26, 26
        );

        GameState.batch.end();
    }

    @Unique
    private static String exampleMod$getGamemodeText() {
        if (InGameMixin.localPlayer.gamemode == Gamemode.SURVIVAL) {
            return "Survival";
        } else if (InGameMixin.localPlayer.gamemode == Gamemode.CREATIVE) {
            return "Creative";
        } else {
            throw new RuntimeException("Player has unexpected gamemode! " + InGameMixin.localPlayer.gamemode);
        }
    }

    @Unique
    private static void exampleMod$switchGamemode() {
        if (InGameMixin.localPlayer.gamemode == Gamemode.SURVIVAL) {
            InGameMixin.localPlayer.gamemode = Gamemode.CREATIVE;
            InGameMixin.localPlayer.getEntity().noClip = true;
        } else if (InGameMixin.localPlayer.gamemode == Gamemode.CREATIVE) {
            InGameMixin.localPlayer.gamemode = Gamemode.SURVIVAL;
            InGameMixin.localPlayer.getEntity().noClip = false;
        }
    }

    @Unique
    private static void exampleMod$drawContainer(float x, boolean selected) {
        if (selected) {
            GameState.batch.draw(UI.containerSelected9Patch.getTexture(), x, ModeSelect.ITEMS_POS_Y);
        } else {
            GameState.batch.draw(UI.container9Patch.getTexture(), x, ModeSelect.ITEMS_POS_Y);
        }
    }

    @Unique
    private static void exampleMod$drawItemTexture(Texture texture, float x, float y, int width, int height) {
        GameState.batch.draw(
                texture,
                x, y,
                width, height,
                0, 0,
                texture.getWidth(), texture.getHeight(),
                false, true
        );
    }
}
