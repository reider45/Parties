package me.apollodevs.parties.party;

import java.sql.Timestamp;
import java.util.Calendar;

import me.apollodevs.parties.Parties;
import me.apollodevs.parties.utilities.Utilities;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class Invite {
	
	Party party;
	ProxiedPlayer player;
	
	Timestamp invited;
	int timeToAccept;
	
	Boolean valid;
	
	public Invite(Party party, ProxiedPlayer player) {
		this.party = party;
		this.player = player;
		
		this.invited = new Timestamp(Calendar.getInstance().getTimeInMillis());
		
		this.timeToAccept = 1000 * 20;
		this.valid = true;
		
		// alert player they've been invited, will need a way to toggle
		Utilities.sendMessage(player,
				"You have been invited to " + party.getOwner() +"'s party! Join with /party join " + party.getOwner()
				, ChatColor.AQUA);
	}
	
	public Boolean isExpired() {
		return Calendar.getInstance().getTimeInMillis() - invited.getTime() > timeToAccept;
	}
	
	public void cancel() {
		valid = false;
		party.getInvites().remove(this);
		
		// send invite expired message
		Utilities.sendMessage(party.getOwner(), player+"'s invite has expired!", ChatColor.RED);
		Utilities.sendMessage(player, "Your invite to "+party.getOwner()+"'s party has exired!", ChatColor.RED);
	}
	
	public void accept() {
		if(Parties.getHandler().getParty(player) != null)
			Parties.getHandler().getParty(player).remMember(player);
	
		Utilities.sendMessage(player, "You have joined " + party.getOwner()+"'s party!", ChatColor.AQUA);
		party.broadcast(player+" has joined your party!", ChatColor.AQUA);
		party.addMember(player);
	
	}
	
	public void decline() {
		cancel();
	}
	
	public ProxiedPlayer getPlayer() {
		return player;
	}
	
	
	
	

}
