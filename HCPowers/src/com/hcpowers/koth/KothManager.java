package com.hcpowers.koth;

import com.hcpowers.core.Core;
import com.hcpowers.factions.Faction;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class KothManager {

    private File file;

    private static ArrayList<Koth> koths = new ArrayList<>();

    private static KothManager km;

    private KothManager() {}

    public static KothManager getManager() {
        if(km == null) {
            km = new KothManager();
        }

        return km;
    }

    public void loadKoths() {

        file = new File(Core.getInstance().getDataFolder(), "koth.yml");

        if(file.exists()) {

            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

            for(String name : config.getConfigurationSection("koth").getKeys(false)) {

                Koth koth = new Koth(name);

                int timer = config.getInt("koth." + name + ".timer");

                boolean active = config.getBoolean("koth." + name + ".active");

                koth.setTimer(timer);
                koth.setCurrentTimer(timer);
                koth.setActive(active);

                if(config.contains("koth." + name + ".loc1")) {

                    int x = config.getInt("koth." + name + ".loc1.x");
                    int y = config.getInt("koth." + name + ".loc1.y");
                    int z = config.getInt("koth." + name + ".loc1.z");

                    String world = config.getString("koth." + name + ".loc1.world");

                    Location location = new Location(Bukkit.getWorld(world), x, y, z);

                    koth.setLoc1(location);

                }

                if(config.contains("koth." + name + ".loc2")) {

                    int x = config.getInt("koth." + name + ".loc2.x");
                    int y = config.getInt("koth." + name + ".loc2.y");
                    int z = config.getInt("koth." + name + ".loc2.z");

                    String world = config.getString("koth." + name + ".loc2.world");

                    Location location = new Location(Bukkit.getWorld(world), x, y, z);

                    koth.setLoc2(location);

                }

                if(config.contains("koth." + name + ".lootloc")) {

                    int x = config.getInt("koth." + name + ".lootloc.x");
                    int y = config.getInt("koth." + name + ".lootloc.y");
                    int z = config.getInt("koth." + name + ".lootloc.z");

                    String world = config.getString("koth." + name + ".lootloc.world");

                    Location location = new Location(Bukkit.getWorld(world), x, y, z);

                    koth.setLootLoc(location);

                }

                if(config.contains("koth." + name + ".loot")) {

                    List<?> items = config.getList("koth." + name + ".loot");

                    ItemStack[] loot = new ItemStack[items.size()];

                    for(int i = 0; i < items.size(); i++) {
                        loot[i] = (ItemStack) items.get(i);
                    }

                    koth.setLoot(loot);
                }

                koths.add(koth);

            }

        }

    }

    public void saveKoths() {

        file = new File(Core.getInstance().getDataFolder(), "koth.yml");

        if(file.exists()) {

            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

            for(Koth koth : koths) {

                String name = koth.getName();;

                config.set("koth." + name + ".loot", koth.getLoot());
                config.set("koth." + name + ".timer", koth.getTimer());
                config.set("koth." + name + ".active", koth.isActive());

                if(koth.getLoot() != null) {
                    if(config.contains("koth." + name + ".loot")) {
                        config.set("koth." + name + ".loot", koth.getLoot());
                    } else {
                        config.createSection("koth." + name + ".loot");
                        config.set("koth." + name + ".loot", koth.getLoot());
                    }
                } else {
                    if(config.contains("koth." + name + ".loot")) {
                        config.set("koth." + name + ".loot", null);
                    }
                }

                if(koth.getLoc1() != null) {

                    if(config.contains("koth." + name + ".loc1")) {
                        config.set("koth." + name + ".loc1.x", koth.getLoc1().getBlockX());
                        config.set("koth." + name + ".loc1.y", koth.getLoc1().getBlockY());
                        config.set("koth." + name + ".loc1.z", koth.getLoc1().getBlockZ());
                        config.set("koth." + name + ".loc1.world", koth.getLoc1().getWorld().getName());
                    } else {
                        config.createSection("koth." + name + ".loc1.x");
                        config.createSection("koth." + name + ".loc1.y");
                        config.createSection("koth." + name + ".loc1.z");
                        config.createSection("koth." + name + ".loc1.world");

                        config.set("koth." + name + ".loc1.x", koth.getLoc1().getBlockX());
                        config.set("koth." + name + ".loc1.y", koth.getLoc1().getBlockY());
                        config.set("koth." + name + ".loc1.z", koth.getLoc1().getBlockZ());
                        config.set("koth." + name + ".loc1.world", koth.getLoc1().getWorld().getName());
                    }
                } else {
                    if(config.contains("koth." + name + ".loc1")) {
                        config.set("koth." + name + ".loc1", null);
                    }
                }

                if(koth.getLoc2() != null) {

                    if(config.contains("koth." + name + ".loc2")) {
                        config.set("koth." + name + ".loc2.x", koth.getLoc2().getBlockX());
                        config.set("koth." + name + ".loc2.y", koth.getLoc2().getBlockY());
                        config.set("koth." + name + ".loc2.z", koth.getLoc2().getBlockZ());
                        config.set("koth." + name + ".loc2.world", koth.getLoc2().getWorld().getName());
                    } else {
                        config.createSection("koth." + name + ".loc2.x");
                        config.createSection("koth." + name + ".loc2.y");
                        config.createSection("koth." + name + ".loc2.z");
                        config.createSection("koth." + name + ".loc2.world");

                        config.set("koth." + name + ".loc2.x", koth.getLoc2().getBlockX());
                        config.set("koth." + name + ".loc2.y", koth.getLoc2().getBlockY());
                        config.set("koth." + name + ".loc2.z", koth.getLoc2().getBlockZ());
                        config.set("koth." + name + ".loc2.world", koth.getLoc2().getWorld().getName());
                    }
                } else {
                    if(config.contains("koth." + name + ".loc2")) {
                        config.set("koth." + name + ".loc2", null);
                    }
                }

                if(koth.getLootLoc() != null) {

                    if(config.contains("koth." + name + ".lootloc")) {
                        config.set("koth." + name + ".lootloc.x", koth.getLootLoc().getBlockX());
                        config.set("koth." + name + ".lootloc.y", koth.getLootLoc().getBlockY());
                        config.set("koth." + name + ".lootloc.z", koth.getLootLoc().getBlockZ());
                        config.set("koth." + name + ".lootloc.world", koth.getLootLoc().getWorld().getName());
                    } else {
                        config.createSection("koth." + name + ".lootloc.x");
                        config.createSection("koth." + name + ".lootloc.y");
                        config.createSection("koth." + name + ".lootloc.z");
                        config.createSection("koth." + name + ".lootloc.world");

                        config.set("koth." + name + ".lootloc.x", koth.getLootLoc().getBlockX());
                        config.set("koth." + name + ".lootloc.y", koth.getLootLoc().getBlockY());
                        config.set("koth." + name + ".lootloc.z", koth.getLootLoc().getBlockZ());
                        config.set("koth." + name + ".lootloc.world", koth.getLootLoc().getWorld().getName());
                    }
                } else {
                    if(config.contains("koth." + name + ".lootloc")) {
                        config.set("koth." + name + ".lootloc", null);
                    }
                }

                try {
                    config.save(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        } else {

            file = new File(Core.getInstance().getDataFolder(), "koth.yml");

            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

            for(Koth koth : koths) {

                String name = koth.getName();

                config.createSection("koth." + name + ".timer");
                config.createSection("koth." + name + ".active");


                config.set("koth." + name + ".timer", koth.getTimer());
                config.set("koth." + name + ".active", koth.isActive());


                if(koth.getLoot() != null) {
                    config.createSection("koth." + name + ".loot");

                    ArrayList<ItemStack> list = new ArrayList<>();

                    for(int i = 0; i < koth.getLoot().length; i++) {

                        if(koth.getLoot()[i] != null) {
                            list.add(koth.getLoot()[i]);
                        }

                    }

                    config.set("koth." + name + ".loot", list);
                }

                if(koth.getLoc1() != null) {
                    config.createSection("koth." + name + ".loc1.x");
                    config.createSection("koth." + name + ".loc1.y");
                    config.createSection("koth." + name + ".loc1.z");
                    config.createSection("koth." + name + ".loc1.world");

                    config.set("koth." + name + ".loc1.x", koth.getLoc1().getBlockX());
                    config.set("koth." + name + ".loc1.y", koth.getLoc1().getBlockY());
                    config.set("koth." + name + ".loc1.z", koth.getLoc1().getBlockZ());
                    config.set("koth." + name + ".loc1.world", koth.getLoc1().getWorld().getName());
                }

                if (koth.getLoc2() != null) {

                    config.createSection("koth." + name + ".loc2.x");
                    config.createSection("koth." + name + ".loc2.y");
                    config.createSection("koth." + name + ".loc2.z");
                    config.createSection("koth." + name + ".loc2.world");

                    config.set("koth." + name + ".loc2.x", koth.getLoc2().getBlockX());
                    config.set("koth." + name + ".loc2.y", koth.getLoc2().getBlockY());
                    config.set("koth." + name + ".loc2.z", koth.getLoc2().getBlockZ());
                    config.set("koth." + name + ".loc2.world", koth.getLoc2().getWorld().getName());
                }

                if (koth.getLootLoc() != null) {

                    config.createSection("koth." + name + ".lootloc.x");
                    config.createSection("koth." + name + ".lootloc.y");
                    config.createSection("koth." + name + ".lootloc.z");
                    config.createSection("koth." + name + ".lootloc.world");

                    config.set("koth." + name + ".lootloc.x", koth.getLootLoc().getBlockX());
                    config.set("koth." + name + ".lootloc.y", koth.getLootLoc().getBlockY());
                    config.set("koth." + name + ".lootloc.z", koth.getLootLoc().getBlockZ());
                    config.set("koth." + name + ".lootloc.world", koth.getLootLoc().getWorld().getName());
                }

                try {
                    config.save(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void createKoth(String name, Player player) {

        if(getKothByName(name) != null) {
            player.sendMessage(ChatColor.RED + "A KOTH by that name already exists");
            return;
        }

        Koth koth = new Koth(name);

        koths.add(koth);

        player.sendMessage(ChatColor.YELLOW + "You have created KOTH " + ChatColor.DARK_PURPLE + name);

    }

    public Koth getKothByName(String name) {
        for(Koth koth : koths) {
            if(koth.getName().equalsIgnoreCase(name)) {
                return koth;
            }
        }

        return null;
    }

    public boolean insideCapzone(Location loc) {
        for(Koth koth : koths) {

            if(koth.getLoc1() != null && koth.getLoc2() != null) {
                Location loc1 = koth.getLoc1();
                Location loc2 = koth.getLoc2();

                int xMax = Math.max(loc1.getBlockX(), loc2.getBlockX());
                int zMax = Math.max(loc1.getBlockZ(), loc2.getBlockZ());
                int yMax = Math.max(loc1.getBlockY(), loc2.getBlockY());

                int xMin = Math.min(loc1.getBlockX(), loc2.getBlockX());
                int zMin = Math.min(loc1.getBlockZ(), loc2.getBlockZ());
                int yMin = Math.min(loc1.getBlockY(), loc2.getBlockY());

                if ((loc.getBlockX() >= xMin) && (loc.getBlockX() <= xMax)) {
                    if ((loc.getBlockZ() >= zMin) && (loc.getBlockZ() <= zMax)) {
                        if ((loc.getBlockY() >= yMin) && (loc.getBlockY() <= yMax)) {
                            if(loc.getWorld().getName().equals(loc1.getWorld().getName())) {
                                return true;
                            }
                        }
                    }
                }
            }
        }

        return false;
    }

    public Koth getKothByLocation(Location loc) {
        for(Koth koth : koths) {

            if(koth.getLoc1() != null && koth.getLoc2() != null) {
                Location loc1 = koth.getLoc1();
                Location loc2 = koth.getLoc2();


                int xMax = Math.max(loc1.getBlockX(), loc2.getBlockX());
                int zMax = Math.max(loc1.getBlockZ(), loc2.getBlockZ());
                int yMax = Math.max(loc1.getBlockY(), loc2.getBlockY());

                int xMin = Math.min(loc1.getBlockX(), loc2.getBlockX());
                int zMin = Math.min(loc1.getBlockZ(), loc2.getBlockZ());
                int yMin = Math.min(loc1.getBlockY(), loc2.getBlockY());

                if ((loc.getBlockX() >= xMin) && (loc.getBlockX() <= xMax)) {
                    if ((loc.getBlockZ() >= zMin) && (loc.getBlockZ() <= zMax)) {
                        if ((loc.getBlockY() >= yMin) && (loc.getBlockY() <= yMax)) {
                            if(loc.getWorld().getName().equals(loc1.getWorld().getName())) {
                                return koth;
                            }
                        }
                    }
                }
            }
        }

        return null;
    }

    public static ArrayList<Koth> getKoths() {
        return koths;
    }
}
