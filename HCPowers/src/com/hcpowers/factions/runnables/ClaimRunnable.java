package com.hcpowers.factions.runnables;

import com.hcpowers.factions.FactionManager;
import com.hcpowers.factions.commands.FactionCommands;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ClaimRunnable extends BukkitRunnable {

    public void run() {

        for(Player player : Bukkit.getServer().getOnlinePlayers()) {

            if(FactionCommands.getMap().containsKey(player.getName())) {

                FactionManager.getManager().updatePillars(player);

            }
        }

    }
}
