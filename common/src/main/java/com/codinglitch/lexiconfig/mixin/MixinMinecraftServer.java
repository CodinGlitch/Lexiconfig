package com.codinglitch.lexiconfig.mixin;

import com.codinglitch.lexiconfig.Lexiconfig;
import com.codinglitch.lexiconfig.LexiconfigApi;
import net.minecraft.commands.CommandSource;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerInfo;
import net.minecraft.server.TickTask;
import net.minecraft.util.thread.ReentrantBlockableEventLoop;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

@Mixin(MinecraftServer.class)
public abstract class MixinMinecraftServer {
    @Inject(method = "reloadResources", at = @At("TAIL"))
    private void lexiconfig$reloadResources(Collection<String> collection, CallbackInfoReturnable<CompletableFuture<Void>> cir) {
        cir.getReturnValue().handleAsync((value, throwable) -> {
            Lexiconfig.revise();
            return value;
        }, (MinecraftServer) (Object) this);
    }
}
