package com.hcpowers.factions.events;

import com.hcpowers.factions.Faction;
import com.hcpowers.factions.FactionManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class PlaceBlocksInClaim implements Listener {

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {

        Player player = event.getPlayer();


        if(FactionManager.getManager().insideClaim(event.getBlock().getLocation())) {
            Faction faction = FactionManager.getManager().getClaimByLocation(event.getBlock().getLocation());

            if(faction.isSystem()) {
                if(!player.hasPermission("hcpowers.factions.admin")) {
                    event.setCancelled(true);
                    return;
                }
            }

        }

        if(!FactionManager.getManager().isPlayerInFaction(player)) {
            if(FactionManager.getManager().insideClaim(event.getBlock().getLocation())) {
                player.sendMessage(ChatColor.RED + "Can't place blocks in the claim of " + ChatColor.LIGHT_PURPLE + FactionManager.getManager().getClaimByLocation(event.getBlock().getLocation()).getName());
                event.setCancelled(true);
                return;
            }
        }

        if(FactionManager.getManager().isPlayerInFaction(player)) {

            Faction bfaction = FactionManager.getManager().getClaimByLocation(event.getBlock().getLocation());
            Faction pfaction = FactionManager.getManager().getFactionByPlayer(player);

            if(!pfaction.getName().equalsIgnoreCase(bfaction.getName()) && !pfaction.isRaidable()) {
                player.sendMessage(ChatColor.RED + "Can't place blocks in the claim of " + ChatColor.LIGHT_PURPLE + bfaction.getName());
                event.setCancelled(true);
                return;
            }
        }
    }

}
