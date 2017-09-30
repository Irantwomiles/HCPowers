package com.hcpowers.profile;

import com.hcpowers.core.Core;
import com.hcpowers.core.utils.Utils;
import com.hcpowers.core.utils.PlayerBoard;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class SetupProfile implements Listener {

    private ProfileManager pm = new ProfileManager();

    private PlayerBoard pb = new PlayerBoard();
    private Utils utils = new Utils();
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();

        pm.loadProfile(event.getPlayer());

        pb.loadEntries(player);

        pb.setPlayerBoard(event.getPlayer());

        PlayerProfile profile = pm.getProfileByPlayer(player);

        /*if(profile.getPvpprot() > 0) {
            pb.addLine(player, player.getScoreboard(), ChatColor.BLUE.toString() + ChatColor.BOLD + "Protection: " + ChatColor.WHITE + utils.toMMSS(profile.getPvpprot()), "pvpprotection");
        }*/

        if(profile.getDeathban() > System.currentTimeMillis()) {

            if(profile.getLives() > 0) {

                profile.setLives(profile.getLives() - 1);
                profile.setDeathban(0L);

                player.sendMessage(ChatColor.GOLD + "Used a life, now you have " + ChatColor.GRAY + profile.getLives() + ChatColor.GOLD + " left!");

            } else {

                int seconds = (int) ((profile.getDeathban() - System.currentTimeMillis()) / 1000);

                Bukkit.getScheduler().scheduleSyncDelayedTask(Core.getInstance(), new Runnable() {
                    public void run() {
                        if(!player.hasPermission("hcpower.deathban.bypass")) {
                            player.kickPlayer(ChatColor.RED + "You are Deathbanned for " + utils.toMMSS(seconds));
                        } else {
                            player.sendMessage(ChatColor.YELLOW + "Bypassed Deathban, Nerd!");
                        }
                    }
                }, 1);

            }

        }

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {

        Player player = event.getPlayer();

        Bukkit.getScheduler().scheduleSyncDelayedTask(Core.getInstance(), new Runnable() {

            public void run() {
                pm.saveProfile(player);
            }

        }, 10);

    }
}
