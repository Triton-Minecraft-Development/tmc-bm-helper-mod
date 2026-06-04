package com.aura.tmcbmhelpermod.commandExecutor;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;

public final class BossbarHealth {
    private static final String SUMMONED_ENTITY_TAG = "tmc.dev.summoned_entity";

    private BossbarHealth() {
    }

    public static int sumCurrentHealth(MinecraftServer server) {
        double total = 0.0;
        for (ServerWorld world : server.getWorlds()) {
            for (Entity entity : world.iterateEntities()) {
                if (!(entity instanceof LivingEntity living)) continue;
                if (!entity.getCommandTags().contains(SUMMONED_ENTITY_TAG)) continue;
                total += living.getHealth();
            }
        }
        return (int) Math.ceil(total);
    }

    public static int sumMaxHealth(MinecraftServer server) {
        double total = 0.0;
        for (ServerWorld world : server.getWorlds()) {
            for (Entity entity : world.iterateEntities()) {
                if (!(entity instanceof LivingEntity living)) continue;
                if (!entity.getCommandTags().contains(SUMMONED_ENTITY_TAG)) continue;
                total += living.getMaxHealth();
            }
        }
        return (int) Math.ceil(total);
    }
}
