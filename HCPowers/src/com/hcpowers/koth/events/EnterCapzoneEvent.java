package com.hcpowers.koth.events;

import com.hcpowers.factions.FactionManager;
import com.hcpowers.factions.factionevents.PlayerEnterClaim;
import com.hcpowers.koth.Koth;
import com.hcpowers.koth.KothManager;
import com.hcpowers.koth.kothevents.EnterCapzone;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class EnterCapzoneEvent implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if(!KothManager.getManager().insideCapzone(event.getFrom()) && KothManager.getManager().insideCapzone(event.getTo()) && player.getWorld().getName().equals(KothManager.getManager().getKothByLocation(event.getTo()).getLoc1().getWorld().getName())) {
            Bukkit.getServer().getPluginManager().callEvent(new EnterCapzone(player, KothManager.getManager().getKothByLocation(event.getTo())));
        }
    }

    @EventHandler
    public void onEnter(EnterCapzone event) {

        Player player = event.getPlayer();
        Koth koth = event.getKoth();

        player.sendMessage(ChatColor.YELLOW + "Entering capzone " + ChatColor.GREEN.toString() + ChatColor.BOLD + koth.getName());

        if(!FactionManager.getManager().isPlayerInFaction(player)) {
            player.sendMessage(ChatColor.RED + "Only people in factions can claim KOTH");
            return;
        }

        if(koth.isActive()) {

            if(!koth.getPlayers().contains(player.getName())) {
                koth.getPlayers().add(player.getName());
            }

            if(koth.getPlayers().size() == 1) {
                player.sendMessage(ChatColor.YELLOW + "You are now capping KOTH " + ChatColor.RED + koth.getName());
            }
        } else {
            player.sendMessage(ChatColor.RED + "This KOTH is currently inactive");
        }
    }
}
