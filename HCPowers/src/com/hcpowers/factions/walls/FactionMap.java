package com.hcpowers.factions.walls;

import com.hcpowers.core.Core;
import com.hcpowers.factions.Faction;
import com.hcpowers.factions.FactionManager;
import com.hcpowers.factions.commands.FactionCommands;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class FactionMap {

    public ArrayList<Faction> factionMap(Player player, Location loc1, Location loc2) {

        ArrayList<Location> locations = new ArrayList<>();
        ArrayList<Faction> factions = new ArrayList<>();

        int locMax_x = Math.max(loc1.getBlockX(), loc2.getBlockX());
        int locMin_x = Math.min(loc1.getBlockX(), loc2.getBlockX());

        int locMax_z = Math.max(loc1.getBlockZ(), loc2.getBlockZ());
        int locMin_z = Math.min(loc1.getBlockZ(), loc2.getBlockZ());


        for (int i = locMin_x; i < locMax_x; i++) {
            for (int j = locMin_z; j < locMax_z; j++) {
                Location loc = new Location(Bukkit.getWorld(Core.getInstance().getConfig().getString("faction-world")), i, 0, j);
                locations.add(loc);
            }
        }

        for(int i = locations.size()-1; i >= 0; i--) {

            Location loc = locations.get(i);

            if(FactionManager.getManager().getFactionByLocation(loc) != null) {

                Faction faction = FactionManager.getManager().getFactionByLocation(loc);

                factions.add(faction);

                int facMax_x = Math.max(faction.getLoc1().getBlockX(), faction.getLoc2().getBlockX());
                int facMin_x = Math.min(faction.getLoc1().getBlockX(), faction.getLoc2().getBlockX());

                int facMax_z = Math.max(faction.getLoc1().getBlockZ(), faction.getLoc2().getBlockZ());
                int facMin_z = Math.min(faction.getLoc1().getBlockZ(), faction.getLoc2().getBlockZ());

                for (int j = facMin_x; j < facMax_x + 1; j++) {
                    for (int k = facMin_z; k < facMax_z + 1; k++) {

                        Location facLoc = new Location(Bukkit.getWorld(Core.getInstance().getConfig().getString("faction-world")), j, 0, k);

                        if (locations.contains(facLoc)) {
                            locations.remove(facLoc);
                        }
                    }
                }
            }
        }

        return factions;
    }

    public void hidePillars(Player player) {

        if(FactionCommands.getMap().get(player.getName()).size() > 0) {

            for(Faction faction : FactionCommands.getMap().get(player.getName())) {

                if(faction.getLoc1() != null && faction.getLoc2() != null) {
                    int maxx = Math.max(faction.getLoc1().getBlockX(), faction.getLoc2().getBlockX());
                    int minx = Math.min(faction.getLoc1().getBlockX(), faction.getLoc2().getBlockX());

                    int maxz = Math.max(faction.getLoc1().getBlockZ(), faction.getLoc2().getBlockZ());
                    int minz = Math.min(faction.getLoc1().getBlockZ(), faction.getLoc2().getBlockZ());


                    for(int i = 4; i < 256; i++) {

                        Location loc1 = new Location(Bukkit.getWorld("world"), minx, i, minz);
                        Location loc2 = new Location(Bukkit.getWorld("world"), maxx, i, maxz);
                        Location loc3 = new Location(Bukkit.getWorld("world"), maxx, i, minz);
                        Location loc4 = new Location(Bukkit.getWorld("world"), minx, i, maxz);

                        Block block1 = Bukkit.getWorld("world").getBlockAt(loc1);
                        Block block2 = Bukkit.getWorld("world").getBlockAt(loc2);
                        Block block3 = Bukkit.getWorld("world").getBlockAt(loc3);
                        Block block4 = Bukkit.getWorld("world").getBlockAt(loc4);

                        if(block1.getType() == Material.AIR) {
                            player.sendBlockChange(loc1, Material.AIR, (byte) 0);
                        }

                        if(block2.getType() == Material.AIR) {
                            player.sendBlockChange(loc2, Material.AIR, (byte) 0);
                        }

                        if(block3.getType() == Material.AIR) {
                            player.sendBlockChange(loc3, Material.AIR, (byte) 0);
                        }

                        if(block4.getType() == Material.AIR) {
                            player.sendBlockChange(loc4, Material.AIR, (byte) 0);
                        }

                    }
                }
            }
        }
    }

    public void updatePillars(Player player) {

        int block = 0;

        if(FactionCommands.getMap().get(player.getName()).size() > 0) {

            for(Faction faction : FactionCommands.getMap().get(player.getName())) {

                if(faction.getLoc1() != null && faction.getLoc2() != null) {
                    int maxx = Math.max(faction.getLoc1().getBlockX(), faction.getLoc2().getBlockX());
                    int minx = Math.min(faction.getLoc1().getBlockX(), faction.getLoc2().getBlockX());

                    int maxz = Math.max(faction.getLoc1().getBlockZ(), faction.getLoc2().getBlockZ());
                    int minz = Math.min(faction.getLoc1().getBlockZ(), faction.getLoc2().getBlockZ());

                    block++;

                    if(block > 15) {
                        block = 0;
                    }

                    for(int i = 4; i < 256; i++) {

                        Location loc1 = new Location(Bukkit.getWorld("world"), minx, i, minz);
                        Location loc2 = new Location(Bukkit.getWorld("world"), maxx, i, maxz);
                        Location loc3 = new Location(Bukkit.getWorld("world"), maxx, i, minz);
                        Location loc4 = new Location(Bukkit.getWorld("world"), minx, i, maxz);

                        Block block1 = Bukkit.getWorld("world").getBlockAt(loc1);
                        Block block2 = Bukkit.getWorld("world").getBlockAt(loc2);
                        Block block3 = Bukkit.getWorld("world").getBlockAt(loc3);
                        Block block4 = Bukkit.getWorld("world").getBlockAt(loc4);

                        if(block1.getType() == Material.AIR) {
                            player.sendBlockChange(loc1, Material.getMaterial(95), (byte) block);
                        }

                        if(block2.getType() == Material.AIR) {
                            player.sendBlockChange(loc2, Material.getMaterial(95), (byte) block);
                        }

                        if(block3.getType() == Material.AIR) {
                            player.sendBlockChange(loc3, Material.getMaterial(95), (byte) block);
                        }

                        if(block4.getType() == Material.AIR) {
                            player.sendBlockChange(loc4, Material.getMaterial(95), (byte) block);
                        }

                    }
                }
            }
        }
    }

}
