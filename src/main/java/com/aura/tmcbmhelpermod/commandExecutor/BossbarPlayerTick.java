package com.aura.tmcbmhelpermod.commandExecutor;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class BossbarPlayerTick {

    private static final Set<UUID> lastPlayers = new HashSet<>();

    public static void execute(ServerWorld world) {
        MinecraftServer server = world.getServer();
        if (server == null) return;

        // collect current players
        Set<UUID> currentPlayers = new HashSet<>();
        for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
            currentPlayers.add(player.getUuid());
        }

        // only run command if something actually changed
        if (currentPlayers.equals(lastPlayers)) {
            return;
        }

        lastPlayers.clear();
        lastPlayers.addAll(currentPlayers);

        ServerCommandSource source = server.getCommandSource()
                .withWorld(world)
                .withPosition(new Vec3d(0, 0, 0));

        server.getCommandManager().parseAndExecute(
                source,
                "bossbar set tmc.dev.wave_health players @a"
        );
    }
}