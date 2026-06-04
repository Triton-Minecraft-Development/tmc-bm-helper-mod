package com.aura.tmcbmhelpermod.commandExecutor;

import net.minecraft.server.MinecraftServer;

public final class BossbarModeState {
    private static BossbarMode lastMode;

    private BossbarModeState() {
    }

    public static BossbarMode current(MinecraftServer server) {
        BossbarMode mode = BossbarMode.fromServer(server);
        if (lastMode != null && lastMode != mode) {
            BossbarValueTick.resetCache();
            BossbarMaxValueTick.resetCache();
        }
        lastMode = mode;
        return mode;
    }
}
