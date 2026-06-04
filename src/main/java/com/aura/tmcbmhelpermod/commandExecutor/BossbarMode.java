package com.aura.tmcbmhelpermod.commandExecutor;

import net.minecraft.scoreboard.ReadableScoreboardScore;
import net.minecraft.scoreboard.ScoreHolder;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.scoreboard.ServerScoreboard;
import net.minecraft.server.MinecraftServer;

public enum BossbarMode {
    CHARGING(0),
    READY(1);

    public static final String OBJECTIVE = "tmc.dev.bossbar_mode";
    public static final String SCORE_HOLDER = "#tmc.dev.bossbar";

    private final int score;

    BossbarMode(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public static BossbarMode fromServer(MinecraftServer server) {
        ServerScoreboard scoreboard = server.getScoreboard();
        ScoreboardObjective objective = scoreboard.getNullableObjective(OBJECTIVE);
        if (objective == null) {
            return READY;
        }

        ScoreHolder holder = ScoreHolder.fromName(SCORE_HOLDER);
        ReadableScoreboardScore score = scoreboard.getScore(holder, objective);
        if (score == null) {
            return READY;
        }

        return score.getScore() == CHARGING.score ? CHARGING : READY;
    }
}
