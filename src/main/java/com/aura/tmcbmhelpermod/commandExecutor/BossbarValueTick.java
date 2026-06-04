package com.aura.tmcbmhelpermod.commandExecutor;

import net.minecraft.entity.boss.CommandBossBar;
import net.minecraft.server.MinecraftServer;

public class BossbarValueTick {
    private static final int CHARGE_INCREMENT = 1;

    private static int lastValue = Integer.MIN_VALUE;

    public static void resetCache() {
        lastValue = Integer.MIN_VALUE;
    }

    public static void execute(MinecraftServer server) {
        BossbarMode mode = BossbarModeState.current(server);
        CommandBossBar bar = WaveHealthBossbar.get(server);
        if (bar == null) return;

        int value = switch (mode) {
            case READY -> BossbarHealth.sumCurrentHealth(server);
            case CHARGING -> {
                int current = bar.getValue();
                int max = bar.getMaxValue();
                if (current >= max) {
                    yield current;
                }
                yield Math.min(current + CHARGE_INCREMENT, max);
            }
        };

        if (value == lastValue) {
            return;
        }
        lastValue = value;
        bar.setValue(value);
    }
}
