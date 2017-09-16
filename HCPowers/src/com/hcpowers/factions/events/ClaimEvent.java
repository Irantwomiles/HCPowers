package com.hcpowers.factions.events;

import com.hcpowers.core.Core;
import com.hcpowers.factions.Claim;
import com.hcpowers.factions.FactionManager;
import com.hcpowers.factions.commands.FactionCommands;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class ClaimEvent implements Listener {

    @EventHandler
    public void onClaim(PlayerInteractEvent event) {

        Player player = event.getPlayer();

        if(event.getAction() == null) {
            return;
        }

        if(event.getAction() == Action.LEFT_CLICK_BLOCK) {

            if(FactionCommands.getClaiming().containsKey(player.getName())
                    && player.getItemInHand().hasItemMeta()
                    && player.getItemInHand().getItemMeta().getDisplayName().equals(ChatColor.YELLOW.toString() + ChatColor.BOLD + "Claiming Wand"))
            {

                Claim claim = FactionCommands.getClaiming().get(player.getName());

                claim.setLoc1(event.getClickedBlock().getLocation());
                claim.setLoc2(event.getClickedBlock().getLocation());

                if(!claim.getFaction().isSystem()) {

                    if(claim.getFaction().getBalance() >= claimCost(claim.getLoc1(), claim.getLoc2())) {
                        player.sendMessage(ChatColor.GREEN + "Picked Corner 1 " + ChatColor.GRAY + "(X:" + claim.getLoc1().getBlockX() + " Z:" + claim.getLoc1().getBlockZ() + ") "
                                + ChatColor.GREEN + "$" + claimCost(claim.getLoc1(), claim.getLoc2()));

                    } else {
                        player.sendMessage(ChatColor.GREEN + "Picked Corner 1 " + ChatColor.GRAY + "(X:" + claim.getLoc1().getBlockX() + " Z:" + claim.getLoc1().getBlockZ() + ") "
                                + ChatColor.RED + "$" + claimCost(claim.getLoc1(), claim.getLoc2()));
                    }
                } else {

                    if(player.hasPermission("hcpowers.faction.admin")) {
                        claim.setLoc1(event.getClickedBlock().getLocation());
                        player.sendMessage(ChatColor.GREEN + "Picked corner 1 for System Faction " + ChatColor.GRAY + claim.getFaction().getName());
                    } else {
                        player.sendMessage(ChatColor.RED + "No Permission.");
                    }

                }
            }

        }

        if(event.getAction() == Action.RIGHT_CLICK_BLOCK) {

            if(FactionCommands.getClaiming().containsKey(player.getName())
                    && player.getItemInHand().hasItemMeta()
                    && player.getItemInHand().getItemMeta().getDisplayName().equals(ChatColor.YELLOW.toString() + ChatColor.BOLD + "Claiming Wand"))
            {

                Claim claim = FactionCommands.getClaiming().get(player.getName());

                if(claim.getLoc1() == null) {
                    player.sendMessage(ChatColor.RED + "Please select the first corner before selecting the second corner!");
                    return;
                }

                claim.setLoc2(event.getClickedBlock().getLocation());

                if(!claim.getFaction().isSystem()) {

                    if(claim.getFaction().getBalance() >= claimCost(claim.getLoc1(), claim.getLoc2())) {
                        player.sendMessage(ChatColor.GREEN + "Picked Corner 2 " + ChatColor.GRAY + "(X:" + claim.getLoc1().getBlockX() + " Z:" + claim.getLoc1().getBlockZ() + ") "
                                + ChatColor.GREEN + "$" + claimCost(claim.getLoc1(), claim.getLoc2()));

                    } else {
                        player.sendMessage(ChatColor.GREEN + "Picked Corner 2 " + ChatColor.GRAY + "(X:" + claim.getLoc1().getBlockX() + " Z:" + claim.getLoc1().getBlockZ() + ") "
                                + ChatColor.RED + "$" + claimCost(claim.getLoc1(), claim.getLoc2()));
                    }

                } else {
                    if(player.hasPermission("hcpowers.faction.admin")) {

                        claim.setLoc2(event.getClickedBlock().getLocation());

                        player.sendMessage(ChatColor.GREEN + "Picked corner 2 for System Faction " + ChatColor.GRAY + claim.getFaction().getName());
                    }else {
                        player.sendMessage(ChatColor.RED + "No Permission.");
                    }
                }
            }

        }

        if(event.getAction() == Action.RIGHT_CLICK_AIR && player.isSneaking()) {

            if(FactionCommands.getClaiming().containsKey(player.getName())
                    && player.getItemInHand().hasItemMeta()
                    && player.getItemInHand().getItemMeta().getDisplayName().equals(ChatColor.YELLOW.toString() + ChatColor.BOLD + "Claiming Wand"))
            {

                Claim claim = FactionCommands.getClaiming().get(player.getName());

                if(claim.getLoc1() == null || claim.getLoc2() == null) {
                    player.sendMessage(ChatColor.RED + "You have not selected two corners to represent your claim");
                    return;
                }

                if(claim.getFaction().isSystem()) {

                    if(player.hasPermission("hcpowers.factions.admin")) {
                        if(FactionManager.getManager().canClaim(claim.getLoc1(), claim.getLoc2())) {
                            claim.getFaction().setLoc1(claim.getLoc1());
                            claim.getFaction().setLoc2(claim.getLoc2());
                            FactionCommands.getClaiming().remove(player.getName());

                            player.sendMessage(ChatColor.GOLD + "You have claimed this land for the System Faction " + ChatColor.YELLOW + claim.getFaction().getName());

                            player.setItemInHand(null);
                        } else {
                            player.sendMessage(ChatColor.RED + "You can't claim this land");
                        }
                    } else {
                        player.sendMessage(ChatColor.RED + "No Permission.");
                    }

                } else {
                    if(claim.getFaction().getBalance() >= claimCost(claim.getLoc1(), claim.getLoc2())) {

                        if(FactionManager.getManager().canClaim(claim.getLoc1(), claim.getLoc2())) {

                            claim.getFaction().setLoc1(claim.getLoc1());
                            claim.getFaction().setLoc2(claim.getLoc2());

                            claim.getFaction().setBalance(claim.getFaction().getBalance() - claimCost(claim.getLoc1(), claim.getLoc2()));

                            FactionManager.getManager().sendFactionMessage(ChatColor.GREEN + player.getName() + ChatColor.YELLOW + " has claim land for your faction", claim.getFaction());

                            player.setItemInHand(null);

                            FactionCommands.getClaiming().remove(player.getName());

                        } else {
                            player.sendMessage(ChatColor.RED + "You can't claim this land");
                        }
                    } else {
                        player.sendMessage(ChatColor.RED + "Your faction does not have enough money to claim this land");
                    }
                }

            }

        }

    }

    public int claimCost(Location loc1, Location loc2) {

        int cost = 0;

        int maxX = Math.max(loc1.getBlockX(), loc2.getBlockX());
        int minX = Math.min(loc1.getBlockX(), loc2.getBlockX());

        int maxZ = Math.max(loc1.getBlockZ(), loc2.getBlockZ());
        int minZ = Math.min(loc1.getBlockZ(), loc2.getBlockZ());

        Location costLoc1 = new Location(Bukkit.getWorld(Core.getInstance().getConfig().getString("faction-world")), maxX, 0, maxZ);
        Location costLoc2 = new Location(Bukkit.getWorld(Core.getInstance().getConfig().getString("faction-world")), minX, 0, maxZ);
        Location costLoc3 = new Location(Bukkit.getWorld(Core.getInstance().getConfig().getString("faction-world")), minX, 0, minZ);

        int width = Math.abs((int) costLoc1.distance(costLoc2));
        int height = Math.abs((int) costLoc2.distance(costLoc3));

        cost = (width * height) * 2;

        return cost;
    }
}
