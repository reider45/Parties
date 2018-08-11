package me.apollodevs.parties.party;

import java.util.HashSet;

import me.apollodevs.parties.utilities.Utilities;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class PartyHandler {

	HashSet<Party> parties;

	public PartyHandler() {
		this.parties = new HashSet<Party>();
	}

	public Party getParty(ProxiedPlayer player) {
		for (Party p : parties) {
			if (p.getPlayers().contains(player))
				return p;
		}
		return null;
	}

	public Party createParty(ProxiedPlayer player) {
		Party p = new Party(player);
		parties.add(p);
		return p;
	}

	public void deleteParty(Party p) {
		for (ProxiedPlayer s : p.getPlayers())
			Utilities.sendMessage(s, "Your party has been disbanded!", ChatColor.RED);
		parties.remove(p);
	}

}
