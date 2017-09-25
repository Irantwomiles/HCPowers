package com.hcpowers.factions.runnables;

import com.hcpowers.factions.FactionManager;
import com.hcpowers.factions.commands.FactionCommands;
import com.hcpowers.factions.walls.ClaimWall;
import com.hcpowers.factions.walls.FactionMap;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ClaimRunnable extends BukkitRunnable {

    private FactionMap fmap = new FactionMap();

    public void run() {

        for(Player player : Bukkit.getServer().getOnlinePlayers()) {

            if(FactionCommands.getMap().containsKey(player.getName())) {
                fmap.updatePillars(player);
            }


        }

    }
}
