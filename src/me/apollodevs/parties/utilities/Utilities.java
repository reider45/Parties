package me.apollodevs.parties.utilities;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class Utilities {
	
	public static void sendMessage(ProxiedPlayer p, String message, ChatColor color) {
		p.sendMessage(new ComponentBuilder(message).color(color).create());
	}

}
