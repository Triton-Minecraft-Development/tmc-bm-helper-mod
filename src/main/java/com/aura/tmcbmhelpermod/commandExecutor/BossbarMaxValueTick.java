package com.aura.tmcbmhelpermod.commandExecutor;

import net.minecraft.entity.boss.CommandBossBar;
import net.minecraft.server.MinecraftServer;

public class BossbarMaxValueTick {

    private static int lastMaxValue = Integer.MIN_VALUE;

    public static void resetCache() {
        lastMaxValue = Integer.MIN_VALUE;
    }

    public static void execute(MinecraftServer server) {
        if (BossbarMode.fromServer(server) != BossbarMode.CHARGING) {
            return;
        }

        int maxValue = BossbarHealth.sumMaxHealth(server);
        if (maxValue == 0 || maxValue == lastMaxValue) {
            return;
        }
        lastMaxValue = maxValue;

        CommandBossBar bar = WaveHealthBossbar.get(server);
        if (bar == null) return;

        bar.setMaxValue(maxValue);
    }
}
