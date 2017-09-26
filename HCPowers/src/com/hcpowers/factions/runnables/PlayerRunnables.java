package com.hcpowers.factions.runnables;

import com.hcpowers.factions.Faction;
import com.hcpowers.factions.FactionManager;
import com.hcpowers.factions.commands.FactionCommands;
import com.hcpowers.profile.PlayerProfile;
import com.hcpowers.profile.ProfileManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerRunnables extends BukkitRunnable {

    private ProfileManager pm = new ProfileManager();

    public void run() {

        for(Player player : Bukkit.getServer().getOnlinePlayers()) {

            PlayerProfile profile = pm.getProfileByPlayer(player);

            if(profile.getPvptimer() > 0) {
                profile.setPvptimer(profile.getPvptimer() - 1);
            }

            updateProtection(player, profile);

            if (FactionCommands.getHome().containsKey(player.getName())) {
                FactionCommands.getHome().put(player.getName(), FactionCommands.getHome().get(player.getName()) - 1);

                if (FactionCommands.getHome().get(player.getName()) <= 0) {

                    if (FactionManager.getManager().isPlayerInFaction(player)) {

                        FactionCommands.getHome().remove(player.getName());
                        player.teleport(FactionManager.getManager().getFactionByPlayer(player).getHome());
                        player.sendMessage(ChatColor.GOLD + "Teleported to faction home");

                    }
                }
            }
        }
    }

    private void updateProtection(Player player, PlayerProfile profile) {

        if(FactionManager.getManager().getFactionByLocation(player.getLocation()) == null) {

            if(profile.getPvpprot() > 0) {
                profile.setPvpprot(profile.getPvpprot() - 1);

                if(profile.getPvpprot() <- 0)
                    profile.setPvpprot(0);
            }
        } else {
            Faction faction = FactionManager.getManager().getFactionByLocation(player.getLocation());

            if(faction.isSystem()) {
                if(faction.isDeathban()) {
                    if(profile.getPvpprot() > 0) {
                        profile.setPvpprot(profile.getPvpprot() - 1);

                        if(profile.getPvpprot() <- 0)
                            profile.setPvpprot(0);
                    }
                } else {
                    profile.setPvpprot(profile.getPvpprot());
                }
            }
        }

    }
}
