package com.hcpowers.factions.events;

import com.hcpowers.factions.Faction;
import com.hcpowers.factions.FactionManager;
import com.hcpowers.profile.PlayerProfile;
import com.hcpowers.profile.ProfileManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.HashMap;

public class PlayerDamage implements Listener {

    private ProfileManager pm = new ProfileManager();

    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {

        if(event.getEntity() instanceof Player && event.getDamager() instanceof Player) {

            Player hit = (Player) event.getEntity();
            Player damager = (Player) event.getDamager();

            PlayerProfile damagerProfile = pm.getProfileByPlayer(damager);
            PlayerProfile hitProfile = pm.getProfileByPlayer(hit);

            if(hitProfile.getPvpprot() > 0) {
                damager.sendMessage(ChatColor.RED + "That player still has PvP-Protection");
                event.setCancelled(true);
                return;
            }

            if(damagerProfile.getPvpprot() > 0) {
                damager.sendMessage(ChatColor.RED + "You still have PvP-Protection! /pvpenable");
                event.setCancelled(true);
                return;
            }

            if(FactionManager.getManager().getFactionByLocation(hit.getLocation()) != null) {

                Faction faction = FactionManager.getManager().getFactionByLocation(hit.getLocation());

                if(faction.isSystem() && !faction.isDeathban()) {
                    damager.sendMessage(ChatColor.YELLOW + "Can't damage " + ChatColor.DARK_GREEN + hit.getName() + ChatColor.YELLOW + " inside of " + ChatColor.AQUA + faction.getName());
                    event.setCancelled(true);
                    return;
                }

            }

            if(FactionManager.getManager().getFactionByLocation(damager.getLocation()) != null) {

                Faction faction = FactionManager.getManager().getFactionByLocation(damager.getLocation());

                if(faction.isSystem() && !faction.isDeathban()) {
                    damager.sendMessage(ChatColor.YELLOW + "Can't damage " + ChatColor.DARK_GREEN + hit.getName() + ChatColor.YELLOW + " while inside of " + ChatColor.AQUA + faction.getName());
                    event.setCancelled(true);
                    return;
                }

            }

            if(FactionManager.getManager().isPlayerInFaction(hit) && FactionManager.getManager().isPlayerInFaction(damager)) {

                Faction hitFaction = FactionManager.getManager().getFactionByPlayer(hit);
                Faction damFaction = FactionManager.getManager().getFactionByPlayer(damager);

                if(hitFaction.getName().equalsIgnoreCase(damFaction.getName())) {
                    damager.sendMessage(ChatColor.YELLOW + "Can't damage " + ChatColor.DARK_GREEN + hit.getName() + ChatColor.YELLOW + ", because you are in the same faction!");
                    event.setCancelled(true);
                } else {
                    damagerProfile.setPvptimer(60);
                    hitProfile.setPvptimer(10);
                }
            } else {
                damagerProfile.setPvptimer(60);
                hitProfile.setPvptimer(10);
            }
        }
    }
}
