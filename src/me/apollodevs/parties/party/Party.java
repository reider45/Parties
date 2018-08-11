package me.apollodevs.parties.party;

import java.util.HashSet;

import me.apollodevs.parties.Parties;
import me.apollodevs.parties.utilities.Utilities;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class Party {

	ProxiedPlayer owner;
	HashSet<ProxiedPlayer> players;
	HashSet<Invite> invites;

	int maxPlayers;

	public Party(ProxiedPlayer owner) {
		this.owner = owner;
		this.players = new HashSet<ProxiedPlayer>();
		this.invites = new HashSet<Invite>();
		this.maxPlayers = 10;

		players.add(owner);
		// alert that party's been created
		Utilities.sendMessage(owner, "You have created a new party!", ChatColor.AQUA);
	}

	public void invite(ProxiedPlayer player) {
		if (!players.contains(player)) {
			invites.add(new Invite(this, player));

			// alert party they've been invited
			broadcast(player + " has been invited to your party!",
					ChatColor.AQUA);

		} else {
			// tell them they're already in the party
			Utilities.sendMessage(owner, "That player is already in your party!",
					ChatColor.AQUA);
		}
	}

	public void broadcast(String message, ChatColor c) {
		for (ProxiedPlayer p : getPlayers())
			Utilities.sendMessage(p, message, c);
	}
	
	public void partyMessage(String message, String p) {
		String msg = "§c[Party] §c" + p + "§f§7: §9" + message;
		broadcast(msg, ChatColor.DARK_PURPLE);
	}

	public Boolean isInvited(ProxiedPlayer p) {
		for (Invite i : invites) {
			if (i.getPlayer().equals(p)) {
				if (!i.isExpired())
					return true;
				else
					return false;
			}
		}
		return false;
	}

	public Invite getInvite(ProxiedPlayer p) {
		for (Invite i : invites) {
			if (i.getPlayer().equals(p)) {
				return i;
			}
		}
		return null;
	}
	
	public Boolean isOwner(ProxiedPlayer p) {
		return owner.getName().equalsIgnoreCase(p.getName());
	}
	
	public Boolean hasPlayers() {
		return players.size() > 1;
	}

	public ProxiedPlayer getOwner() {
		return owner;
	}

	public HashSet<Invite> getInvites() {
		return invites;
	}

	public void addMember(ProxiedPlayer p) {
		this.players.add(p);
	}

	public void remMember(ProxiedPlayer p) {
		this.players.remove(p);

		if (players.size() == 0)
			Parties.getHandler().deleteParty(this);
		else
			broadcast(p.getName() + " has left the party!", ChatColor.RED);
	}
	
	public void kickMember(ProxiedPlayer p) {
		this.players.remove(p);
		
		if(players.size() == 0)
			Parties.getHandler().deleteParty(this);
		else {
			broadcast(p.getName() + " has been kicked from the party!", ChatColor.RED);
			Utilities.sendMessage(p, "You have been kicked from the party!", ChatColor.RED);
		}
	}

	public HashSet<ProxiedPlayer> getPlayers() {
		return this.players;
	}

}
