package com.hcpowers.koth.events;

import com.hcpowers.factions.commands.FactionCommands;
import com.hcpowers.koth.Koth;
import com.hcpowers.koth.KothClaim;
import com.hcpowers.koth.commands.KothCommands;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class KothClaimEvent implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {

        Player player = event.getPlayer();

        if(event.getAction() == Action.LEFT_CLICK_BLOCK) {
            if(KothCommands.getClaiming().containsKey(player.getName())
                    && player.getItemInHand().hasItemMeta()
                    && player.getItemInHand().getItemMeta().getDisplayName().equals(ChatColor.BLUE.toString() + ChatColor.BOLD + "KOTH Claiming Wand"))
            {

                if(!player.hasPermission("hcpower.koth.admin")) {
                    player.sendMessage(ChatColor.RED + "No Permission.");
                    return;
                }

                KothClaim claim = KothCommands.getClaiming().get(player.getName());

                claim.setLoc1(event.getClickedBlock().getLocation());

                player.sendMessage(ChatColor.DARK_AQUA + "Selected Location 1 for KOTH " + claim.getKoth().getName());

            }
        }

        if(event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if(KothCommands.getClaiming().containsKey(player.getName())
                    && player.getItemInHand().hasItemMeta()
                    && player.getItemInHand().getItemMeta().getDisplayName().equals(ChatColor.BLUE.toString() + ChatColor.BOLD + "KOTH Claiming Wand"))
            {

                if(!player.hasPermission("hcpower.koth.admin")) {
                    player.sendMessage(ChatColor.RED + "No Permission.");
                    return;
                }

                KothClaim claim = KothCommands.getClaiming().get(player.getName());

                claim.setLoc2(event.getClickedBlock().getLocation());

                player.sendMessage(ChatColor.DARK_AQUA + "Selected Location 2 for KOTH " + claim.getKoth().getName());

            }
        }

        if(event.getAction() == Action.RIGHT_CLICK_AIR && player.isSneaking()) {
            if(KothCommands.getClaiming().containsKey(player.getName())
                    && player.getItemInHand().hasItemMeta()
                    && player.getItemInHand().getItemMeta().getDisplayName().equals(ChatColor.BLUE.toString() + ChatColor.BOLD + "KOTH Claiming Wand"))
            {

                if(!player.hasPermission("hcpower.koth.admin")) {
                    player.sendMessage(ChatColor.RED + "No Permission.");
                    return;
                }

                KothClaim claim = KothCommands.getClaiming().get(player.getName());

                if(claim.getLoc1() != null && claim.getLoc2() != null && claim.getLoc1() != claim.getLoc2()) {

                    Koth koth = claim.getKoth();

                    koth.setLoc1(claim.getLoc1());
                    koth.setLoc2(claim.getLoc2());

                    player.setItemInHand(null);
                    KothCommands.getClaiming().remove(player.getName());

                    player.sendMessage(ChatColor.DARK_AQUA.toString() + ChatColor.BOLD + "You have set the capzone for " + claim.getKoth().getName());


                }

            }
        }
    }
}
