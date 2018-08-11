package me.apollodevs.parties.events;

import me.apollodevs.parties.Parties;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class DisconnectListener implements Listener {
	
	@EventHandler
	public void onQuit(PlayerDisconnectEvent e) {
		ProxiedPlayer p = e.getPlayer();
		
		if(Parties.getHandler().getParty(p) != null) {
			Parties.getHandler().getParty(p).remMember(p);
		}
	}

}
