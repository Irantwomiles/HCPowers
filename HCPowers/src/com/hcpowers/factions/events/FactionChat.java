package com.hcpowers.factions.events;

import com.hcpowers.core.utils.Utils;
import com.hcpowers.factions.Faction;
import com.hcpowers.factions.FactionManager;
import com.hcpowers.factions.commands.FactionCommands;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class FactionChat implements Listener {

    private Utils utils = new Utils();

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {

        Player player = event.getPlayer();

        if(FactionCommands.getChat().contains(player.getName())) {

            Faction faction = FactionManager.getManager().getFactionByPlayer(player);

            event.setCancelled(true);

            FactionManager.getManager().sendFactionMessage(ChatColor.DARK_GREEN + player.getDisplayName() + ": " + ChatColor.GREEN + event.getMessage(), faction);

            return;

        }

        if(FactionManager.getManager().isPlayerInFaction(player)) {

            event.setCancelled(true);

            Faction faction = FactionManager.getManager().getFactionByPlayer(player);

            if(event.getMessage().startsWith("!")) {
                FactionManager.getManager().sendFactionMessage(ChatColor.DARK_GREEN + player.getDisplayName() + ": " + ChatColor.GREEN + event.getMessage(), faction);
            } else {
                for(Player p : Bukkit.getServer().getOnlinePlayers()) {

                    if(faction.getMembers().contains(p.getUniqueId().toString())) {

                        p.sendMessage(ChatColor.GRAY + "[" + ChatColor.GREEN + faction.getName() + ChatColor.GRAY + "] " + player.getDisplayName() + ": " + ChatColor.WHITE + event.getMessage());

                    } else {
                        p.sendMessage(ChatColor.GRAY + "[" + ChatColor.RED + faction.getName() + ChatColor.GRAY + "] " + player.getDisplayName() + ": " + ChatColor.WHITE + event.getMessage());
                    }
                }
            }

            return;

        } else {
            event.setCancelled(true);
             utils.sendMessage(ChatColor.GRAY + "[] " + player.getDisplayName() + ": " + ChatColor.WHITE + event.getMessage());
        }
    }
}
