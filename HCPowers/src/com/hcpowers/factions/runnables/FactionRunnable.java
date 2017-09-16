package com.hcpowers.factions.runnables;

import com.hcpowers.factions.Faction;
import com.hcpowers.factions.FactionManager;
import org.bukkit.scheduler.BukkitRunnable;

public class FactionRunnable extends BukkitRunnable {

    public void run() {

        for(Faction faction : FactionManager.getManager().getFactions()) {

            if(faction.getFreezetime() > 0) {
                faction.setFreezetime(faction.getFreezetime() - 1);
            }

            if(faction.getDtr() < faction.getMaxDtr()) {
                if(faction.getFreezetime() <= 0) {
                    faction.setDtr(faction.getDtr() + 0.001);
                }
            }

            if(faction.getDtr() > faction.getMaxDtr()) {
                faction.setDtr(faction.getMaxDtr());
            }

        }

    }

}
