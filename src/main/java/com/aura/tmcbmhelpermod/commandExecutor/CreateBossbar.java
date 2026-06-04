package com.aura.tmcbmhelpermod.commandExecutor;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

public class CreateBossbar {

    public static void execute(ServerWorld world) {
        MinecraftServer server = world.getServer();
        if (server == null) return;

        ServerCommandSource source = server.getCommandSource()
                .withWorld(world)
                .withPosition(new Vec3d(0, 0, 0));

        server.getCommandManager().parseAndExecute(source, "bossbar add tmc.dev.wave_health \"Wave Health\"");
        server.getCommandManager().parseAndExecute(source, "bossbar set tmc.dev.wave_health color red");
        server.getCommandManager().parseAndExecute(
                source,
                "scoreboard objectives add " + BossbarMode.OBJECTIVE + " dummy"
        );
        server.getCommandManager().parseAndExecute(
                source,
                "scoreboard players set " + BossbarMode.SCORE_HOLDER + " " + BossbarMode.OBJECTIVE + " " + BossbarMode.READY.getScore()
        );
        server.getCommandManager().parseAndExecute(source, "scoreboard objectives add tmc.dev.bossbar_enabled dummy");
        server.getCommandManager().parseAndExecute(source, "scoreboard players set #tmc.dev.bossbar tmc.dev.bossbar_enabled 0");
    }

}
