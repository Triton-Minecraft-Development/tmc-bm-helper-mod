package com.aura.tmcbmhelpermod.commandExecutor;

import net.minecraft.entity.boss.CommandBossBar;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;

public final class WaveHealthBossbar {
    public static final Identifier ID = Identifier.of("tmc.dev.wave_health");

    private WaveHealthBossbar() {
    }

    public static CommandBossBar get(MinecraftServer server) {
        return server.getBossBarManager().get(ID);
    }
}
