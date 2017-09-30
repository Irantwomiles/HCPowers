package com.hcpowers.koth.events;

import com.hcpowers.koth.Koth;
import com.hcpowers.koth.KothManager;
import com.hcpowers.koth.kothevents.CaptureKoth;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Random;

public class CaptureKothEvent implements Listener {

    private static HashMap<String, Koth> looter = new HashMap<>();

    @EventHandler
    public void onCapture(CaptureKoth event) {

        Player player = event.getPlayer();
        Koth koth = event.getKoth();

        looter.put(player.getUniqueId().toString(), koth);
    }

    @EventHandler
    public void onOpen(PlayerInteractEvent event) {

        Player player = event.getPlayer();

        if(event.getAction() == null) {
            return;
        }

        if(event.getAction() == Action.RIGHT_CLICK_BLOCK) {

            if(looter.containsKey(player.getUniqueId().toString())) {

                Location location = looter.get(player.getUniqueId().toString()).getLootLoc();
                Location bLocation = event.getClickedBlock().getLocation();

                Koth koth = looter.get(player.getUniqueId().toString());

                if(location != null && bLocation.getBlockX() == location.getBlockX() && bLocation.getBlockZ() == location.getBlockZ() && bLocation.getBlockY() == location.getBlockY() && location.getWorld().getName().equalsIgnoreCase(bLocation.getWorld().getName())) {

                    if(koth.getLootTimer() > 0) {

                        Inventory inv = Bukkit.createInventory(player, 36, ChatColor.RED + "Loot");

                        int fill = new Random().nextInt(5) + 1;

                        if(fill <= 1) {
                            fill = 3;
                        }

                        for(int i = 0; i < fill; i++) {

                            int pick = new Random().nextInt(koth.getLoot().length);

                            ItemStack item = koth.getLoot()[pick];

                            inv.setItem(i, item);

                        }

                        player.openInventory(inv);

                        looter.remove(player.getUniqueId().toString());

                        event.setCancelled(true);

                    }
                }
            }
        }
    }
}
