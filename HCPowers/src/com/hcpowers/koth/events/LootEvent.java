package com.hcpowers.koth.events;

import com.hcpowers.koth.Koth;
import com.hcpowers.koth.KothManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class LootEvent implements Listener {

    @EventHandler
    public void onClose(InventoryCloseEvent event) {

        Player player = (Player) event.getPlayer();

        if(event.getInventory().getTitle() != null) {

            String title = event.getInventory().getTitle();

            if(!player.hasPermission("hcpower.koth.admin")) {
                player.sendMessage(ChatColor.RED + "No Permission.");
                return;
            }

            if(KothManager.getManager().getKothByName(title) != null) {

                Koth koth = KothManager.getManager().getKothByName(title);

                koth.setLoot(event.getInventory().getContents());

                player.sendMessage(ChatColor.GREEN + "Loot set");

            }

        }

    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {

        if(event.getInventory().getTitle() != null) {

            String title = event.getInventory().getTitle();

            if(title.equals(ChatColor.RED + "Koth Loot")) {
                event.setCancelled(true);
            }

        }

    }
}
