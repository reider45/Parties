package me.apollodevs.parties.events;

import me.apollodevs.parties.Parties;
import me.apollodevs.parties.party.Party;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ChatListener implements Listener {

	@EventHandler
	public void onChat(ChatEvent e) {

		if(!(e.getSender() instanceof ProxiedPlayer)) return;

		if(e.getMessage().startsWith("@")){
			ProxiedPlayer p = (ProxiedPlayer) e.getSender();
			if(Parties.getHandler().getParty(p) != null) {
				Party party = Parties.getHandler().getParty(p);

				if(party.hasPlayers()){
					party.partyMessage(e.getMessage().replaceFirst("@", ""), p.getName());
					e.setCancelled(true);
				}

			}
		}
	}

}
