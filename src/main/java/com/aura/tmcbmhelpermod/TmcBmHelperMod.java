package com.aura.tmcbmhelpermod;

import com.aura.tmcbmhelpermod.commandExecutor.*;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.world.ServerWorld;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TmcBmHelperMod implements ModInitializer {
	public static final String MOD_ID = "tmc-bm-helper-mod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static boolean hasCreatedBossbar = false;

	@Override
	public void onInitialize() {
		LOGGER.info("Meow");
		ServerTickEvents.END_WORLD_TICK.register(this::onWorldTick);
		ServerTickEvents.END_SERVER_TICK.register(server -> {
			BossbarMaxValueTick.execute(server);
			BossbarValueTick.execute(server);
		});
	}

	private void onWorldTick(ServerWorld world) {
		if (world == null) return;

		if (!hasCreatedBossbar) {
			CreateBossbar.execute(world);
			hasCreatedBossbar = true;
		}

		BossbarPlayerTick.execute(world);
	}
}