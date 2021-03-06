package com.hm.antiworldfly;

import java.util.logging.Level;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import com.hm.mcshared.particle.FancyMessageSender;

public class AntiWorldFlyRunnable implements Runnable {

	private Player player;
	private AntiWorldFly plugin;

	public AntiWorldFlyRunnable(Player player, AntiWorldFly awf) {
		this.player = player;
		this.plugin = awf;
	}

	@Override
	public void run() {
		if (plugin.isDisabled() || player.hasPermission("antiworldfly.fly")) {
			return;
		}

		if (!this.plugin.isAntiFlyCreative() && player.getGameMode() == GameMode.CREATIVE
				|| "SPECTATOR".equals(player.getGameMode().toString())) {
			return;
		}

		if (plugin.isChatMessage()
				&& (plugin.isNotifyNotFlying() || !plugin.isNotifyNotFlying() && player.isFlying())) {
			player.sendMessage(plugin.getChatHeader()
					+ plugin.getPluginLang().getString("fly-disabled-chat", "Flying is disabled in this world."));
		}

		if (plugin.isTitleMessage()
				&& (plugin.isNotifyNotFlying() || !plugin.isNotifyNotFlying() && player.isFlying())) {
			try {
				FancyMessageSender.sendTitle(player,
						plugin.getPluginLang().getString("fly-disabled-title", "&9AntiWorldFly"),
						plugin.getPluginLang().getString("fly-disabled-subtitle", "Flying is disabled in this world."));
			} catch (Exception e) {
				plugin.getLogger().log(Level.SEVERE, "Errors while trying to display flying disabled title: ", e);
			}
		}

		// Disable flying.
		player.setAllowFlight(false);
		player.setFlying(false);
	}
}
