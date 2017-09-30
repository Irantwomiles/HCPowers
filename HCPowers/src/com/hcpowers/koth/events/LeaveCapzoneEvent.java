package com.hcpowers.koth.events;

import com.hcpowers.factions.FactionManager;
import com.hcpowers.koth.Koth;
import com.hcpowers.koth.KothManager;
import com.hcpowers.koth.kothevents.EnterCapzone;
import com.hcpowers.koth.kothevents.LeaveCapzone;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class LeaveCapzoneEvent implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if(KothManager.getManager().insideCapzone(event.getFrom()) && !KothManager.getManager().insideCapzone(event.getTo()) && player.getWorld().getName().equals(KothManager.getManager().getKothByLocation(event.getFrom()).getLoc1().getWorld().getName())) {
            Bukkit.getServer().getPluginManager().callEvent(new LeaveCapzone(player, KothManager.getManager().getKothByLocation(event.getFrom())));
        }
    }

    @EventHandler
    public void onEnter(LeaveCapzone event) {

        Player player = event.getPlayer();
        Koth koth = event.getKoth();

        player.sendMessage(ChatColor.YELLOW + "Leaving capzone " + ChatColor.RED.toString() + ChatColor.BOLD + koth.getName());

        if(koth.isActive()) {

            if(koth.getPlayers().contains(player.getName())) {

                if(koth.getPlayers().get(0).equalsIgnoreCase(player.getName())) {
                    koth.setCurrentTimer(koth.getTimer());
                }

                koth.getPlayers().remove(player.getName());
            }

        } else {
            player.sendMessage(ChatColor.RED + "This KOTH is currently inactive");
        }
    }

}
