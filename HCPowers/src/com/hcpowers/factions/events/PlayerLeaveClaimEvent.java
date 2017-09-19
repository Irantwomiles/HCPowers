package com.hcpowers.factions.events;

import com.hcpowers.factions.Faction;
import com.hcpowers.factions.FactionManager;
import com.hcpowers.factions.factionevents.PlayerLeaveClaim;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerLeaveClaimEvent implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent event) {

        Player player = event.getPlayer();

        if(FactionManager.getManager().insideClaim(event.getFrom()) && !FactionManager.getManager().insideClaim(event.getTo())) {
            Bukkit.getServer().getPluginManager().callEvent(new PlayerLeaveClaim(player, FactionManager.getManager().getFactionByLocation(event.getFrom())));
        }

    }

    @EventHandler
    public void onLeave(PlayerLeaveClaim event) {

        Player player = event.getPlayer();

        Faction faction = event.getFaction();

        if(faction.isSystem()) {

            if(faction.isDeathban()) {
                player.sendMessage(ChatColor.YELLOW + "You have left the claim of " + ChatColor.RED + faction.getName() + ChatColor.GRAY + " (" + ChatColor.DARK_RED + "Deathban" + ChatColor.GRAY + ")");
            } else {
                player.sendMessage(ChatColor.YELLOW + "You have left the claim of " + ChatColor.GREEN + faction.getName() + ChatColor.GRAY + " (" + ChatColor.DARK_GREEN + "Safezone" + ChatColor.GRAY + ")");
            }

        } else {
            if(FactionManager.getManager().isPlayerInFaction(player)) {

                Faction pfaction = FactionManager.getManager().getFactionByPlayer(player);

                if(faction.getName().equalsIgnoreCase(pfaction.getName())) {
                    player.sendMessage(ChatColor.YELLOW + "You have left the claim of " + ChatColor.GREEN + faction.getName() + ChatColor.GRAY + " (" + ChatColor.DARK_RED + "Deathban" + ChatColor.GRAY + ")");
                } else {
                    player.sendMessage(ChatColor.YELLOW + "You have left the claim of " + ChatColor.RED + faction.getName() + ChatColor.GRAY + " (" + ChatColor.DARK_RED + "Deathban" + ChatColor.GRAY + ")");
                }

            } else {
                player.sendMessage(ChatColor.YELLOW + "You have left the claim of " + ChatColor.RED + faction.getName() + ChatColor.GRAY + " (" + ChatColor.DARK_RED + "Deathban" + ChatColor.GRAY + ")");
            }
        }
    }

}
