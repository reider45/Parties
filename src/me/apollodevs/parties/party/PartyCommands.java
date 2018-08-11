package me.apollodevs.parties.party;

import me.apollodevs.parties.Parties;
import me.apollodevs.parties.utilities.Utilities;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class PartyCommands extends Command {

	public PartyCommands() {
		super("party");
	}

	public void execute(final CommandSender cs, final String[] args) {
		if (cs instanceof ProxiedPlayer) {
			ProxiedPlayer p = (ProxiedPlayer) cs;

			Party party = Parties.getHandler().getParty(p);

			if (args.length < 1 || args[0].equalsIgnoreCase("help")) {
				p.sendMessage(new ComponentBuilder("=================").color(ChatColor.GOLD).append("Party Menu").color(ChatColor.DARK_GRAY).append("=================").color(ChatColor.GOLD).create());
				p.sendMessage(new ComponentBuilder("/party list").color(ChatColor.GREEN).append("|").color(ChatColor.DARK_GRAY).append("Shows the members of your party!").color(ChatColor.AQUA).create());
				p.sendMessage(new ComponentBuilder("/party leave").color(ChatColor.GREEN).append("|").color(ChatColor.DARK_GRAY).append("Leave/delete your current party!").color(ChatColor.AQUA).create());
				p.sendMessage(new ComponentBuilder("/party chat").color(ChatColor.GREEN).append("|").color(ChatColor.DARK_GRAY).append("Chat with your party!").color(ChatColor.AQUA).create());
				p.sendMessage(new ComponentBuilder("/party invite").color(ChatColor.GREEN).append("|").color(ChatColor.DARK_GRAY).append("Invite a player!").color(ChatColor.AQUA).create());
				p.sendMessage(new ComponentBuilder("/party accept").color(ChatColor.GREEN).append("|").color(ChatColor.DARK_GRAY).append("Accept invitation!").color(ChatColor.AQUA).create());
				p.sendMessage(new ComponentBuilder("/party kick").color(ChatColor.GREEN).append("|").color(ChatColor.DARK_GRAY).append("Kick a player from your party!").color(ChatColor.AQUA).create());
				p.sendMessage(new ComponentBuilder("===========================================").color(ChatColor.GOLD).create());
			} else if (args[0].equalsIgnoreCase("create")) {
				if(party == null)
					Parties.getHandler().createParty(p);
				else
					Utilities.sendMessage(p, "You're already in a party!", ChatColor.RED);
			} else if(args[0].equalsIgnoreCase("join")) {
				if(args.length > 1) {

					ProxiedPlayer target = Parties.getPlugin().getProxy().getPlayer(args[1]);
					if(target != null) {
						if(party != null)
							party.remMember(p);
						Party theirParty = Parties.getHandler().getParty(target);
						if(theirParty != null) {
							if(theirParty.isInvited(p)) {
								theirParty.getInvite(p).accept();
							} else {
								Utilities.sendMessage(p, "You have no invites from that party!", ChatColor.RED);
							}
						} else {
							Utilities.sendMessage(p, "No party found!", ChatColor.RED);	
						}


					} else {
						Utilities.sendMessage(p, "No party found!", ChatColor.RED);
					}
				}
			} else if(args[0].equalsIgnoreCase("invite")) {
				if(args.length > 1) {

					if(party == null)
						party = Parties.getHandler().createParty(p);

					if(party.isOwner(p)){
						ProxiedPlayer target = Parties.getPlugin().getProxy().getPlayer(args[1]);
						if(target != null) {
							if(Parties.getHandler().getParty(target) == party){
								Utilities.sendMessage(p, "That player's already in your party!", ChatColor.RED);
							}else {
								party.invite(target);
							}
						}else{
							Utilities.sendMessage(p, "No player found by the name of " + args[1], ChatColor.RED);
						}
					}else{
						Utilities.sendMessage(p, "You don't own this party!", ChatColor.RED);
					}

				}
			} else if(party == null) {
				Utilities.sendMessage(p, "You're not in a party! Create one with /party create!", ChatColor.RED);
			} else if (args[0].equalsIgnoreCase("list")) {
				Utilities.sendMessage(p, " ", ChatColor.WHITE);
				Utilities.sendMessage(p, "================================", ChatColor.GOLD);
				Utilities.sendMessage(p, "Party Owner: " + party.getOwner(), ChatColor.AQUA);
				Utilities.sendMessage(p, "Party Members: ", ChatColor.AQUA);
				Utilities.sendMessage(p, party.getPlayers().toString().replaceAll("[\\[\\]]", ""), ChatColor.GREEN);
				Utilities.sendMessage(p, "================================", ChatColor.GOLD);
			} else if(args[0].equalsIgnoreCase("leave")) {
				party.remMember(p);
				Utilities.sendMessage(p, "You have left the party!", ChatColor.RED);
			} else if(args[0].equalsIgnoreCase("chat")) {
				if(args.length > 1) {

					String msg = "";
					for (int i = 1; i < args.length; i++) 
						msg += args[i] + " ";
					
					party.partyMessage(msg, p.getName());

				}
			} else if(args[0].equalsIgnoreCase("kick")) {
				if(args.length > 1) {

					if(party.isOwner(p)) {

						ProxiedPlayer target = Parties.getPlugin().getProxy().getPlayer(args[1]);
						if(party.getPlayers().contains(target)) {

							if(!target.equals(p)) {
								party.kickMember(target);
							} else {
								Utilities.sendMessage(p, "You can't kick yourself, silly!", ChatColor.GREEN);
							}

						} else {
							Utilities.sendMessage(p, "That player isn't in your party!", ChatColor.GREEN);
						}

					} else {
						Utilities.sendMessage(p, "You don't own that party!", ChatColor.GREEN);
					}

				}
			}

		}

	}

}
