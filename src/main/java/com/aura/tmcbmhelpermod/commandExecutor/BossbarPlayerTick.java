package com.aura.tmcbmhelpermod.commandExecutor;

import net.minecraft.scoreboard.ScoreHolder;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardObjective;
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
    private static boolean lastEnabledState = false;

    public static void execute(ServerWorld world) {
        MinecraftServer server = world.getServer();
        if (server == null) return;

        // collect current players
        Set<UUID> currentPlayers = new HashSet<>();
        for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
            currentPlayers.add(player.getUuid());
        }

        // read sb value
        boolean enabled = isBossbarEnabled(server);

        // proceed if smth changed
        if (currentPlayers.equals(lastPlayers) && enabled == lastEnabledState) {
            return;
        }

        lastPlayers.clear();
        lastPlayers.addAll(currentPlayers);
        lastEnabledState = enabled;

        ServerCommandSource source = server.getCommandSource()
                .withWorld(world)
                .withPosition(new Vec3d(0, 0, 0));

        if (enabled) {
            server.getCommandManager().parseAndExecute(
                    source,
                    "bossbar set tmc.dev.wave_health players @a"
            );
        } else {
            server.getCommandManager().parseAndExecute(
                    source,
                    "bossbar set tmc.dev.wave_health players"
            );
        }
    }

    private static boolean isBossbarEnabled(MinecraftServer server) {
        Scoreboard scoreboard = server.getScoreboard();
        ScoreboardObjective objective = scoreboard.getNullableObjective("tmc.dev.bossbar_enabled");

        if (objective == null) return false;

        return scoreboard.getOrCreateScore(ScoreHolder.fromName("#tmc.dev.bossbar"), objective).getScore() == 1;
    }
}
