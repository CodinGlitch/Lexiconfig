package com.codinglitch.lexiconfig.mixin;

import com.codinglitch.lexiconfig.FabricLexiconfig;
import com.codinglitch.lexiconfig.LexiconfigApi;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;

@Mixin(Minecraft.class)
public abstract class MixinMinecraft {
    @Inject(method = "onGameLoadFinished", at = @At("TAIL"))
    private static void lexiconfig$onGameLoadFinished(CallbackInfo ci) {
        FabricLexiconfig.onPostInitialize();
    }
}
