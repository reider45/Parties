package me.apollodevs.parties;

import me.apollodevs.parties.events.ChatListener;
import me.apollodevs.parties.events.DisconnectListener;
import me.apollodevs.parties.events.SwitchListener;
import me.apollodevs.parties.party.PartyCommands;
import me.apollodevs.parties.party.PartyHandler;
import net.md_5.bungee.api.plugin.Plugin;

public class Parties extends Plugin {

	private static Parties plugin;
	private static PartyHandler partyHandler;

	public void onEnable() {
		plugin = this;

		partyHandler = new PartyHandler();
		
		getProxy().getPluginManager().registerListener(this, new ChatListener());
		getProxy().getPluginManager().registerListener(this, new DisconnectListener());
		getProxy().getPluginManager().registerListener(this, new SwitchListener());
		getProxy().getPluginManager().registerCommand(this, new PartyCommands());
	}

	public void onDisable() {
		plugin = null;
		partyHandler = null;
	}

	public static Parties getPlugin() {
		return plugin;
	}

	public static PartyHandler getHandler() {
		return partyHandler;
	}

}
