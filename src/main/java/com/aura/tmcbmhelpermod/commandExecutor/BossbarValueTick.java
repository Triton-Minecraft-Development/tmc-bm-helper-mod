package com.aura.tmcbmhelpermod.commandExecutor;

import net.minecraft.entity.boss.CommandBossBar;
import net.minecraft.server.MinecraftServer;

public class BossbarValueTick {
    private static final int CHARGE_TICKS = 100;

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
                if (current >= max || max == 0) {
                    yield current;
                }
                int increment = Math.max(1, (max + CHARGE_TICKS - 1) / CHARGE_TICKS);
                yield Math.min(current + increment, max);
            }
        };

        if (value == lastValue) {
            return;
        }
        lastValue = value;
        bar.setValue(value);
    }
}
