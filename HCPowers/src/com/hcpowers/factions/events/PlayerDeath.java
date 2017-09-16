package com.hcpowers.factions.events;

import com.hcpowers.factions.Faction;
import com.hcpowers.factions.FactionManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeath implements Listener {

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {

        Player player = event.getEntity();

        if(FactionManager.getManager().isPlayerInFaction(player)) {

            Faction faction = FactionManager.getManager().getFactionByPlayer(player);

            faction.setDtr(faction.getDtr() - 1.0);

            faction.setFreezetime(60 * 60);

            FactionManager.getManager().sendFactionMessage(ChatColor.RED + player.getName() + ChatColor.YELLOW + " has died!" + ChatColor.GRAY + " (DTR: " + faction.getDtr() + ")", faction);

        }
    }
}
