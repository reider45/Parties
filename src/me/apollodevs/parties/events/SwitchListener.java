package me.apollodevs.parties.events;

import me.apollodevs.parties.Parties;
import me.apollodevs.parties.party.Party;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class SwitchListener implements Listener {
	
	@EventHandler
	public void onServerSwitch(ServerSwitchEvent e) {
		ProxiedPlayer p = e.getPlayer();
		
		if(Parties.getHandler().getParty(p) != null) {
			Party party = Parties.getHandler().getParty(p);
			
			if(party.hasPlayers() && party.getOwner().equals(p)) {
				// Valid party
				
				for(ProxiedPlayer member : party.getPlayers()) {
					if(member != null && !member.equals(party.getOwner()))
						member.connect(p.getServer().getInfo());
				}
				party.broadcast("You have been switched to server " + p.getServer().getInfo().getName(), ChatColor.GOLD);
				
			}
			
		}
	}
}
