package com.hcpowers.factions.walls;

import com.hcpowers.core.Core;
import com.hcpowers.factions.Faction;
import com.hcpowers.factions.FactionManager;
import com.hcpowers.profile.PlayerProfile;
import com.hcpowers.profile.ProfileManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class ClaimWall {

    private static HashMap<String, ArrayList<Faction>> wall = new HashMap<>();

    private FactionMap fmap = new FactionMap();

    private ProfileManager pm = new ProfileManager();

    public void hideWall(Player player) {

        Location loc1 = new Location(Bukkit.getWorld(Core.getInstance().getConfig().getString("faction-world")), player.getLocation().getBlockX() + 50, 0, player.getLocation().getBlockZ() + 50);
        Location loc2 = new Location(Bukkit.getWorld(Core.getInstance().getConfig().getString("faction-world")), player.getLocation().getBlockX() - 50, 0, player.getLocation().getBlockZ() - 50);

        ArrayList<Faction> factions = fmap.factionMap(player, loc1, loc2);

        if(factions.size() > 0) {

            for(Faction faction : factions) {

                if(faction.getLoc1() != null && faction.getLoc2() != null) {
                    int maxx = Math.max(faction.getLoc1().getBlockX(), faction.getLoc2().getBlockX());
                    int minx = Math.min(faction.getLoc1().getBlockX(), faction.getLoc2().getBlockX());

                    int maxz = Math.max(faction.getLoc1().getBlockZ(), faction.getLoc2().getBlockZ());
                    int minz = Math.min(faction.getLoc1().getBlockZ(), faction.getLoc2().getBlockZ());


                    for (int i = minx; i < maxx; i++) {

                        for (int y = player.getLocation().getBlockY(); y < player.getLocation().getBlockY() + 20; y++) {

                            Location l = new Location(Bukkit.getWorld(Core.getInstance().getConfig().getString("faction-world")), i, y, minz);

                            player.sendBlockChange(l, Material.AIR, (byte) 0);
                        }

                    }

                    for (int i = minz; i < maxz; i++) {

                        for (int y = player.getLocation().getBlockY(); y < player.getLocation().getBlockY() + 5; y++) {

                            Location l = new Location(Bukkit.getWorld(Core.getInstance().getConfig().getString("faction-world")), maxx, y, i);

                            player.sendBlockChange(l, Material.AIR, (byte) 0);

                        }
                    }


                    for (int i = minx; i < maxx + 1; i++) {

                        for (int y = player.getLocation().getBlockY(); y < player.getLocation().getBlockY() + 5; y++) {

                            Location l = new Location(Bukkit.getWorld(Core.getInstance().getConfig().getString("faction-world")), i, y, maxz);

                            player.sendBlockChange(l, Material.AIR, (byte) 0);
                        }
                    }

                    for (int i = minz; i < maxz; i++) {

                        for (int y = player.getLocation().getBlockY(); y < player.getLocation().getBlockY() + 5; y++) {

                            Location l = new Location(Bukkit.getWorld(Core.getInstance().getConfig().getString("faction-world")), minx, y, i);

                            player.sendBlockChange(l, Material.AIR, (byte) 0);
                        }
                    }
                }
            }
        }
    }

    public ArrayList<Location> getNearByFactions(Player player) {

        PlayerProfile profile = pm.getProfileByPlayer(player);

        wall.get(player.getName()).clear();

        ArrayList<Location> locations = new ArrayList<>();

        int minX = player.getLocation().getBlockX() - 5;
        int maxX = player.getLocation().getBlockX() + 5;

        int minZ = player.getLocation().getBlockZ() - 5;
        int maxZ = player.getLocation().getBlockZ() + 5;

        for (int x = minX; x < maxX; x++) {
            for (int z = minZ; z < maxZ; z++) {

                Location loc = new Location(Bukkit.getWorld(Core.getInstance().getConfig().getString("faction-world")), x, 0, z);

                if(FactionManager.getManager().getFactionByLocation(loc) != null) {

                    Faction faction = FactionManager.getManager().getFactionByLocation(loc);

                    if(profile.getPvpprot() > 0 && profile.getPvptimer() <= 0) {
                        if(faction.isSystem() && !faction.isDeathban())
                            continue;

                        if(!wall.get(player.getName()).contains(faction)) {
                            wall.get(player.getName()).add(faction);
                        }

                        locations.add(loc);

                    } else if(profile.getPvptimer() > 0 && profile.getPvpprot() > 0) {

                        if(!wall.get(player.getName()).contains(faction)) {
                            wall.get(player.getName()).add(faction);
                        }

                        locations.add(loc);
                    } else if(profile.getPvptimer() > 0 && profile.getPvpprot() <= 0) {
                        if(!faction.isSystem() && faction.isDeathban())
                            continue;

                        if(!wall.get(player.getName()).contains(faction)) {
                            wall.get(player.getName()).add(faction);
                        }

                        locations.add(loc);
                    }
                }
            }
        }

        return locations;
    }

    public ArrayList<Location> locationsToUpdate(Player player, ArrayList<Faction> factions, ArrayList<Location> locations) {

        ArrayList<Location> locs = new ArrayList<>();

        for(Faction faction : factions) {

            int maxx = Math.max(faction.getLoc1().getBlockX(), faction.getLoc2().getBlockX());
            int minx = Math.min(faction.getLoc1().getBlockX(), faction.getLoc2().getBlockX());

            int maxz = Math.max(faction.getLoc1().getBlockZ(), faction.getLoc2().getBlockZ());
            int minz = Math.min(faction.getLoc1().getBlockZ(), faction.getLoc2().getBlockZ());

            for (int i = minx; i < maxx; i++) {

                for (int y = player.getLocation().getBlockY(); y < player.getLocation().getBlockY() + 5; y++) {

                    Location l = new Location(Bukkit.getWorld(Core.getInstance().getConfig().getString("faction-world")), i, y, minz);

                    for (Location loc : locations) {

                        if (loc.getBlockX() == l.getBlockX() && loc.getBlockZ() == l.getBlockZ()) {
                            locs.add(l);
                        }
                    }

                    player.sendBlockChange(l, Material.AIR, (byte) 0);
                }

            }

            for (int i = minz; i < maxz; i++) {

                for (int y = player.getLocation().getBlockY(); y < player.getLocation().getBlockY() + 5; y++) {

                    Location l = new Location(Bukkit.getWorld(Core.getInstance().getConfig().getString("faction-world")), maxx, y, i);

                    for(Location loc : locations) {

                        if(loc.getBlockX() == l.getBlockX() && loc.getBlockZ() == l.getBlockZ()) {
                            locs.add(l);
                        }
                    }

                    player.sendBlockChange(l, Material.AIR, (byte) 0);

                }
            }


            for (int i = minx; i < maxx + 1; i++) {

                for (int y = player.getLocation().getBlockY(); y < player.getLocation().getBlockY() + 5; y++) {

                    Location l = new Location(Bukkit.getWorld(Core.getInstance().getConfig().getString("faction-world")), i, y, maxz);

                    for(Location loc : locations) {

                        if(loc.getBlockX() == l.getBlockX() && loc.getBlockZ() == l.getBlockZ()) {
                            locs.add(l);
                        }
                    }

                    player.sendBlockChange(l, Material.AIR, (byte) 0);
                }
            }

            for (int i = minz; i < maxz; i++) {

                for (int y = player.getLocation().getBlockY(); y < player.getLocation().getBlockY() + 5; y++) {

                    Location l = new Location(Bukkit.getWorld(Core.getInstance().getConfig().getString("faction-world")), minx, y, i);

                    for(Location loc : locations) {

                        if(loc.getBlockX() == l.getBlockX() && loc.getBlockZ() == l.getBlockZ()) {
                            locs.add(l);
                        }
                    }

                    player.sendBlockChange(l, Material.AIR, (byte) 0);
                }
            }
        }

        return locs;
    }

    public static HashMap<String, ArrayList<Faction>> getWall() {
        return wall;
    }

}
