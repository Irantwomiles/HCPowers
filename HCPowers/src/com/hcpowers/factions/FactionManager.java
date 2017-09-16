package com.hcpowers.factions;

import com.hcpowers.core.Core;
import com.hcpowers.core.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FactionManager {

    private File file = null;
    private File fFile;

    private Utils utils = new Utils();

    private static ArrayList<Faction> factions = new ArrayList<>();

    private static FactionManager fm;

    public static FactionManager getManager() {

        if(fm == null) {
            fm = new FactionManager();
        }

        return fm;
    }

    public  void loadFactions() {

        fFile = new File(Core.getInstance().getDataFolder(), "factions.yml");

        if(fFile.exists()) {

            fFile = new File(Core.getInstance().getDataFolder(), "factions.yml");

            YamlConfiguration listConfig = YamlConfiguration.loadConfiguration(fFile);
            listConfig.get("factions");

            List<String> allFac = listConfig.getStringList("factions");

            for(int i = 0; i < allFac.size(); i++) {
                String name = allFac.get(i);

                file = new File(Core.getInstance().getDataFolder() + "/Factions", name + ".yml");

                YamlConfiguration fac = YamlConfiguration.loadConfiguration(file);

                for(String f : fac.getConfigurationSection("factions").getKeys(false)) {

                    String facName = fac.getString("factions." + f + ".name");

                    boolean system = fac.getBoolean("factions." + f + ".system");

                    Faction faction = new Faction(facName);

                    faction.setSystem(system);

                    if(!faction.isSystem()) {

                        String leader = fac.getString("factions." + f + ".leader");

                        List<String> members = fac.getStringList("factions." + f + ".members");
                        List<String> captains = fac.getStringList("factions." + f + ".captains");

                        String motd = fac.getString("factions." + f + ".motd");

                        int balance = fac.getInt("factions." + f + ".balance");
                        int freezeTime = fac.getInt("factions." + f + ".freezeTime");

                        double dtr = fac.getDouble("factions." + f + ".dtr");
                        double maxDtr = fac.getDouble("factions." + f + ".maxdtr");

                        boolean frozen = fac.getBoolean("factions." + f + ".frozen");

                        if(fac.contains("factions." + f + ".loc1") && fac.contains("factions." + f + ".loc2")) {
                            int x1 = fac.getInt("factions." + f + ".loc1.x");
                            int z1 = fac.getInt("factions." + f + ".loc1.z");

                            int x2 = fac.getInt("factions." + f + ".loc2.x");
                            int z2 = fac.getInt("factions." + f + ".loc2.z");

                            Location loc1 = new Location(Bukkit.getWorld("world"), x1, 0, z1);
                            Location loc2 = new Location(Bukkit.getWorld("world"), x2, 0, z2);

                            faction.setLoc1(loc1);
                            faction.setLoc2(loc2);
                        }

                        if(fac.contains("factions." + f + ".home")) {

                            int x = fac.getInt("factions." + f + ".home.x");
                            int y = fac.getInt("factions." + f + ".home.y");
                            int z = fac.getInt("factions." + f + ".home.z");
                            float pitch = fac.getFloat("factions." + f + ".home.pitch");
                            float yaw = fac.getFloat("factions." + f + ".home.yaw");

                            Location home = new Location(Bukkit.getWorld("world"), x, y, z);

                            home.setPitch(pitch);
                            home.setYaw(yaw);

                            faction.setHome(home);
                        }

                        faction.setLeader(leader);
                        faction.setCaptains(captains);
                        faction.setMembers(members);
                        faction.setBalance(balance);
                        faction.setDtr(dtr);
                        faction.setMaxDtr(maxDtr);
                        faction.setFreezetime(freezeTime);
                        faction.setMotd(motd);


                        faction.setSystem(false);
                        faction.setDeathban(true);
                        factions.add(faction);
                    } else {

                        String motd = fac.getString("factions." + f + ".motd");

                        if(fac.contains("factions." + f + ".loc1") && fac.contains("factions." + f + ".loc2")) {
                            int x1 = fac.getInt("factions." + f + ".loc1.x");
                            int z1 = fac.getInt("factions." + f + ".loc1.z");

                            int x2 = fac.getInt("factions." + f + ".loc2.x");
                            int z2 = fac.getInt("factions." + f + ".loc2.z");

                            Location loc1 = new Location(Bukkit.getWorld("world"), x1, 0, z1);
                            Location loc2 = new Location(Bukkit.getWorld("world"), x2, 0, z2);

                            faction.setLoc1(loc1);
                            faction.setLoc2(loc2);
                        }

                        if(fac.contains("factions." + f + ".home")) {

                            int x = fac.getInt("factions." + f + ".home.x");
                            int y = fac.getInt("factions." + f + ".home.y");
                            int z = fac.getInt("factions." + f + ".home.z");
                            float pitch = fac.getFloat("factions." + f + ".home.pitch");
                            float yaw = fac.getFloat("factions." + f + ".home.yaw");

                            Location home = new Location(Bukkit.getWorld("world"), x, y, z);

                            home.setPitch(pitch);
                            home.setYaw(yaw);

                            faction.setHome(home);
                        }

                        boolean deathban = fac.getBoolean("factions." + f + ".deathban");

                        faction.setMotd(motd);
                        faction.setDeathban(deathban);
                        faction.setSystem(true);

                        factions.add(faction);
                    }

                }
            }
        }

    }

    public void saveFactions() {

        fFile = new File(Core.getInstance().getDataFolder(), "factions.yml");

        if (fFile.exists()) {

            fFile = new File(Core.getInstance().getDataFolder(), "factions.yml");

            YamlConfiguration listConfig = YamlConfiguration.loadConfiguration(fFile);

            List<String> f = listConfig.getStringList("factions");

            f.clear();

            for (int i = 0; i < factions.size(); i++) {
                f.add(factions.get(i).getName());

            }
            // set new list to config
            listConfig.set("factions", f);

            try {
                listConfig.save(fFile);
            } catch (IOException e) {
                e.printStackTrace();
            }

            for (String name : f) {

                Faction faction = getFactionByName(name);

                file = new File(Core.getInstance().getDataFolder() + "/Factions", name + ".yml");

                if (!file.exists()) {

                    file = new File(Core.getInstance().getDataFolder() + "/Factions", name + ".yml");

                    YamlConfiguration facConfig = YamlConfiguration.loadConfiguration(file);

                    if(faction.isSystem()) {

                        facConfig.createSection("factions." + name + ".name");
                        facConfig.createSection("factions." + name + ".system");
                        facConfig.createSection("factions." + name + ".deathban");
                        facConfig.createSection("factions." + name + ".motd");

                        facConfig.set("factions." + name + ".name", faction.getName());
                        facConfig.set("factions." + name + ".system", faction.isSystem());
                        facConfig.set("factions." + name + ".deathban", faction.isDeathban());
                        facConfig.set("factions." + name + ".motd", faction.getMotd());

                        if (faction.getLoc1() != null) {
                            facConfig.createSection("factions." + name + ".loc1.x");
                            facConfig.createSection("factions." + name + ".loc1.z");

                            facConfig.set("factions." + name + ".loc1.x", faction.getLoc1().getBlockX());
                            facConfig.set("factions." + name + ".loc1.z", faction.getLoc1().getBlockZ());
                        }

                        if (faction.getLoc2() != null) {
                            facConfig.createSection("factions." + name + ".loc2.x");
                            facConfig.createSection("factions." + name + ".loc2.z");

                            facConfig.set("factions." + name + ".loc2.x", faction.getLoc2().getBlockX());
                            facConfig.set("factions." + name + ".loc2.z", faction.getLoc2().getBlockZ());
                        }

                        if (faction.getHome() != null) {
                            facConfig.createSection("factions." + name + ".home.x");
                            facConfig.createSection("factions." + name + ".home.y");
                            facConfig.createSection("factions." + name + ".home.z");
                            facConfig.createSection("factions." + name + ".home.pitch");
                            facConfig.createSection("factions." + name + ".home.yaw");
                            facConfig.createSection("factions." + name + ".world");

                            facConfig.set("factions." + name + ".home.x", faction.getHome().getBlockX());
                            facConfig.set("factions." + name + ".home.y", faction.getHome().getBlockY());
                            facConfig.set("factions." + name + ".home.z", faction.getHome().getBlockZ());
                            facConfig.set("factions." + name + ".home.pitch", faction.getHome().getPitch());
                            facConfig.set("factions." + name + ".home.yaw", faction.getHome().getYaw());
                            facConfig.set("factions." + name + ".world", faction.getHome().getWorld().getName());
                        }

                        try {
                            facConfig.save(file);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    } else {
                        facConfig.createSection("factions." + name + ".name");
                        facConfig.createSection("factions." + name + ".leader");
                        facConfig.createSection("factions." + name + ".captains"); // List
                        facConfig.createSection("factions." + name + ".members"); // List
                        facConfig.createSection("factions." + name + ".motd");

                        facConfig.createSection("factions." + name + ".balance");
                        facConfig.createSection("factions." + name + ".dtr");
                        facConfig.createSection("factions." + name + ".maxdtr");
                        facConfig.createSection("factions." + name + ".freezeTime");

                        facConfig.createSection("factions." + name + ".regen");
                        facConfig.createSection("factions." + name + ".frozen");

                        facConfig.set("factions." + name + ".name", faction.getName());
                        facConfig.set("factions." + name + ".leader", faction.getLeader());
                        facConfig.set("factions." + name + ".motd", faction.getMotd());

                        facConfig.set("factions." + name + ".captains", faction.getCaptains());

                        facConfig.set("factions." + name + ".members", faction.getMembers());

                        if (faction.getLoc1() != null) {
                            facConfig.createSection("factions." + name + ".loc1.x");
                            facConfig.createSection("factions." + name + ".loc1.z");

                            facConfig.set("factions." + name + ".loc1.x", faction.getLoc1().getBlockX());
                            facConfig.set("factions." + name + ".loc1.z", faction.getLoc1().getBlockZ());
                        }

                        if (faction.getLoc2() != null) {
                            facConfig.createSection("factions." + name + ".loc2.x");
                            facConfig.createSection("factions." + name + ".loc2.z");

                            facConfig.set("factions." + name + ".loc2.x", faction.getLoc2().getBlockX());
                            facConfig.set("factions." + name + ".loc2.z", faction.getLoc2().getBlockZ());
                        }

                        if (faction.getHome() != null) {
                            facConfig.createSection("factions." + name + ".home.x");
                            facConfig.createSection("factions." + name + ".home.y");
                            facConfig.createSection("factions." + name + ".home.z");
                            facConfig.createSection("factions." + name + ".home.pitch");
                            facConfig.createSection("factions." + name + ".home.yaw");

                            facConfig.set("factions." + name + ".home.x", faction.getHome().getBlockX());
                            facConfig.set("factions." + name + ".home.y", faction.getHome().getBlockY());
                            facConfig.set("factions." + name + ".home.z", faction.getHome().getBlockZ());
                            facConfig.set("factions." + name + ".home.pitch", faction.getHome().getPitch());
                            facConfig.set("factions." + name + ".home.yaw", faction.getHome().getYaw());

                        }

                        facConfig.set("factions." + name + ".balance", faction.getBalance());
                        facConfig.set("factions." + name + ".dtr",
                                faction.getDtr());
                        facConfig.set("factions." + name + ".maxdtr",
                                faction.getMaxDtr());
                        facConfig.set("factions." + name + ".freezeTime", faction.getFreezetime());

                        try {
                            facConfig.save(file);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                } else {

                    file = new File(Core.getInstance().getDataFolder() + "/Factions", name + ".yml");

                    YamlConfiguration facConfig = YamlConfiguration.loadConfiguration(file);

                    if(faction.isSystem()) {
                        //TODO: System faction stuff

                        facConfig.set("factions." + name + ".name", faction.getName());
                        facConfig.set("factions." + name + ".system", faction.isSystem());
                        facConfig.set("factions." + name + ".deathban", faction.isDeathban());
                        facConfig.set("factions." + name + ".motd", faction.getMotd());

                        if (faction.getLoc1() != null) {
                            facConfig.set("factions." + name + ".loc1.x", faction.getLoc1().getBlockX());
                            facConfig.set("factions." + name + ".loc1.z", faction.getLoc1().getBlockZ());
                        } else {
                            facConfig.set("factions." + name + ".loc1", null);
                        }

                        if (faction.getLoc2() != null) {
                            facConfig.set("factions." + name + ".loc2.x", faction.getLoc2().getBlockX());
                            facConfig.set("factions." + name + ".loc2.z", faction.getLoc2().getBlockZ());
                        } else {
                            facConfig.set("factions." + name + ".loc2", null);
                        }

                        if (faction.getHome() != null) {
                            facConfig.set("factions." + name + ".home.x", faction.getHome().getBlockX());
                            facConfig.set("factions." + name + ".home.y", faction.getHome().getBlockY());
                            facConfig.set("factions." + name + ".home.z", faction.getHome().getBlockZ());

                            facConfig.set("factions." + name + ".home.pitch", faction.getHome().getPitch());
                            facConfig.set("factions." + name + ".home.yaw", faction.getHome().getYaw());

                        } else {
                            facConfig.set("factions." + name + ".home", null);
                        }

                        try {
                            facConfig.save(file);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {

                        facConfig.set("factions." + name + ".name", faction.getName());
                        facConfig.set("factions." + name + ".leader", faction.getLeader());
                        facConfig.set("factions." + name + ".motd", faction.getMotd());

                        facConfig.set("factions." + name + ".captains", faction.getCaptains());

                        facConfig.set("factions." + name + ".members", faction.getMembers());


                        if (faction.getLoc1() != null) {
                            facConfig.set("factions." + name + ".loc1.x", faction.getLoc1().getBlockX());
                            facConfig.set("factions." + name + ".loc1.z", faction.getLoc1().getBlockZ());
                        } else {
                            facConfig.set("factions." + name + ".loc1", null);
                        }

                        if (faction.getLoc2() != null) {
                            facConfig.set("factions." + name + ".loc2.x", faction.getLoc2().getBlockX());
                            facConfig.set("factions." + name + ".loc2.z", faction.getLoc2().getBlockZ());
                        } else {
                            facConfig.set("factions." + name + ".loc2", null);
                        }

                        if (faction.getHome() != null) {
                            facConfig.set("factions." + name + ".home.x", faction.getHome().getBlockX());
                            facConfig.set("factions." + name + ".home.y", faction.getHome().getBlockY());
                            facConfig.set("factions." + name + ".home.z", faction.getHome().getBlockZ());

                            facConfig.set("factions." + name + ".home.pitch", faction.getHome().getPitch());
                            facConfig.set("factions." + name + ".home.yaw", faction.getHome().getYaw());

                        } else {
                            facConfig.set("factions." + name + ".home", null);
                        }

                        facConfig.set("factions." + name + ".balance", faction.getBalance());
                        facConfig.set("factions." + name + ".dtr",
                                faction.getDtr());
                        facConfig.set("factions." + name + ".maxdtr",
                                faction.getMaxDtr());
                        facConfig.set("factions." + name + ".freezeTime", faction.getFreezetime());

                        try {
                            facConfig.save(file);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                faction.getInvites().clear();

            }

        }
    }

    public void createFaction(String name, Player player) {

        String[] notAllowed = { ",", ";", "!", "@", "#", "$",
                "%", "^", "&", "*", "(", ")", "+", "=", "`",
                "~", ".", "<", ">", "/", "\"", ":", ";", "{",
                "}", "?" };

        for(String no : notAllowed) {
            if (name.contains(no)) {
                player.sendMessage(ChatColor.RED + "You can't use those characters in your faction name");
                return;
            }
        }

        if(getFactionByName(name) != null) {
            player.sendMessage(ChatColor.RED + "A faction by that name already exists!");
            return;
        }

        if(isPlayerInFaction(player)) {
            player.sendMessage(ChatColor.RED + "You are already in a faction!");
            return;
        }

        Faction faction = new Faction(name);

        faction.setLeader(player.getUniqueId().toString());
        faction.getMembers().add(player.getUniqueId().toString());

        factions.add(faction);

        Bukkit.broadcastMessage(ChatColor.GREEN + player.getName() + ChatColor.YELLOW + " has created the faction " + ChatColor.RED + name);
    }

    public void disbandFactionByName(String name, Player player) {

        if(getFactionByName(name) == null) {
            return;
        }

        Faction faction = getFactionByName(name);

        if(faction.isSystem()) {

            if(player.hasPermission("hcpower.factions.admin")) {

                if(factions.contains(faction)) {

                    factions.remove(faction);

                    player.sendMessage(ChatColor.RED + "You have disbanded the system faction " + ChatColor.DARK_RED + name);

                    return;
                }
            } else {
                player.sendMessage(ChatColor.RED + "No Permission.");
            }

        } else {

            if(player.getUniqueId().toString().equals(faction.getLeader())) {
                if(faction.getMembers().contains(player.getUniqueId().toString())) {

                    if(factions.contains(faction)) {

                        factions.remove(faction);

                        sendFactionMessage(ChatColor.RED + player.getName() + " has disbanded the faction!", faction);

                        return;
                    }
                }
            }

        }

    }

    public void disbandFactionByPlayer(Player player) {

        if(getFactionByPlayer(player) == null) {
            player.sendMessage(ChatColor.RED + "You are not in a faction.");
            return;
        }

        Faction faction = getFactionByPlayer(player);

        if(faction.isSystem()) {
            player.sendMessage(ChatColor.RED + "You can't disband system factions that way. /f disband [name]");
            return;
        }

        if (player.getUniqueId().toString().equals(faction.getLeader())) {
            if (faction.getMembers().contains(player.getUniqueId().toString())) {

                if (factions.contains(faction)) {

                    sendFactionMessage(ChatColor.RED + player.getName() + " has disbanded the faction!", faction);

                    factions.remove(faction);

                    return;
                }
            }
        }


    }

    public void setDeathbanStatus(Faction faction, Player player) {

        if(faction == null) {
            player.sendMessage(ChatColor.RED + "Couldn't find that faction.");
            return;
        }

        if(faction.isSystem()) {
            if(faction.isDeathban()) {
                faction.setDeathban(false);
                player.sendMessage(ChatColor.GOLD + "You have set this factions land to a " + ChatColor.GREEN + "Safezone");
            } else {
                faction.setDeathban(true);
                player.sendMessage(ChatColor.GOLD + "You have set this factions land to a " + ChatColor.DARK_RED + "Deathban");
            }
        } else {
            player.sendMessage(ChatColor.RED + "You can't do this for normal factions");
        }
    }

    public void setFactionSystem(Faction faction, Player player) {

        if(faction == null) {
            player.sendMessage(ChatColor.RED + "Couldn't find that faction.");
            return;
        }

        if(faction.isSystem()) {
            faction.setSystem(false);
            player.sendMessage(ChatColor.GOLD + "You have set this faction to a regular faction type.");
        } else {
            faction.setSystem(true);
            player.sendMessage(ChatColor.GOLD + "You have set this faction to a system faction type.");
        }

    }

    public void invitePlayer(Player player, Player target) {

        if(getFactionByPlayer(player) == null) {
            player.sendMessage(ChatColor.RED + "You are not in a faction.");
            return;
        }

        Faction faction = getFactionByPlayer(player);

        if(faction.isSystem()) {
            player.sendMessage(ChatColor.RED + "Can't do that in system factions");
            return;
        }

        if(faction.getLeader().equals(player.getUniqueId().toString()) || faction.getCaptains().contains(player.getUniqueId().toString())) {

            if(!faction.getMembers().contains(target.getUniqueId().toString()) && !faction.getInvites().contains(player.getUniqueId().toString())) {
                sendFactionMessage(ChatColor.GREEN + player.getName() + ChatColor.YELLOW + " has invited " + ChatColor.GOLD + target.getName(), faction);
                target.sendMessage(ChatColor.YELLOW + "You have been invited to join the faction " + ChatColor.GREEN + faction.getName() + ".");
            } else {
                player.sendMessage(ChatColor.RED + "Can't invite that player.");
            }
        }

    }

    public void uninvitePlayer(Player player, Player target) {

        if(getFactionByPlayer(player) == null) {
            player.sendMessage(ChatColor.RED + "You are not in a faction.");
            return;
        }

        Faction faction = getFactionByPlayer(player);

        if(faction.isSystem()) {
            player.sendMessage(ChatColor.RED + "Can't do that in system factions");
            return;
        }

        if(faction.getLeader().equals(player.getUniqueId().toString()) || faction.getCaptains().contains(player.getUniqueId().toString())) {

            if(!faction.getMembers().contains(target.getUniqueId().toString()) && faction.getInvites().contains(player.getUniqueId().toString())) {
                sendFactionMessage(ChatColor.GREEN + player.getName() + ChatColor.YELLOW + " has uninvited " + ChatColor.RED + target.getName(), faction);
                target.sendMessage(ChatColor.YELLOW + "You can no longer join the faction " + ChatColor.RED + faction.getName());
            } else {
                player.sendMessage(ChatColor.RED + "Can't uninvite that player.");
            }
        }

    }

    public void joinFaction(Player player, String name) {

        if(getFactionByName(name) == null) {
            player.sendMessage(ChatColor.RED + "You are not in a faction.");
            return;
        }

        Faction faction = getFactionByName(name);

        if(faction.isSystem()) {
            player.sendMessage(ChatColor.RED + "Can't do that in system factions");
            return;
        }

        if(!faction.getMembers().contains(player.getUniqueId().toString()) && !faction.getInvites().contains(player.getUniqueId().toString())) {
            if(faction.getMembers().size() < Core.getInstance().getConfig().getInt("faction-limit")) {
                faction.getMembers().add(player.getUniqueId().toString());
                faction.getInvites().remove(player.getUniqueId().toString());

                faction.setMaxDtr(faction.getMaxDtr() + 0.75);

                sendFactionMessage(ChatColor.GREEN + player.getName() + ChatColor.YELLOW + " has joined the faction!", faction);
            } else {
                player.sendMessage(ChatColor.RED + "That faction is already reached its player limit.");
            }
        } else {
            player.sendMessage(ChatColor.RED + "You can't join this faction");
        }

    }

    public void leaveFaction(Player player) {

        if(getFactionByPlayer(player) == null) {
            player.sendMessage(ChatColor.RED + "You are not in a faction!");
            return;
        }

        Faction faction = getFactionByPlayer(player);

        if(faction.getLeader().equalsIgnoreCase(player.getUniqueId().toString())) {
            player.sendMessage(ChatColor.RED + "You are the faction leader, you need to make someone leader before leaving!");
            return;
        }

        if(faction.getMembers().contains(player.getUniqueId().toString())) {

            if(faction.getCaptains().contains(player.getUniqueId().toString())) {
                faction.getCaptains().remove(player.getUniqueId().toString());
            }

            sendFactionMessage(ChatColor.RED + player.getName() + ChatColor.YELLOW + " has left the faction.", faction);

            faction.getMembers().remove(player.getUniqueId().toString());

        }

    }

    public Faction getFactionByName(String name) {
        for(Faction faction : factions) {
            if(faction.getName().equalsIgnoreCase(name)) {
                return faction;
            }
        }

        return null;
    }

    public Faction getFactionByPlayer(Player player) {
        for(Faction faction : factions) {
            if(faction.getMembers().contains(player.getUniqueId().toString())) {
                return faction;
            }
        }

        return null;
    }

    public boolean canClaim(Location loc1, Location loc2) {

        for(Faction faction : factions) {

            if(faction.getLoc1() != null & faction.getLoc2() != null) {
                Location facLoc1 = faction.getLoc1();
                Location facLoc2 = faction.getLoc2();

                //Faction values
                int facMax_x = Math.max(facLoc1.getBlockX(), facLoc2.getBlockX());
                int facMin_x = Math.min(facLoc1.getBlockX(), facLoc2.getBlockX());

                int facMax_z = Math.max(facLoc1.getBlockZ(), facLoc2.getBlockZ());
                int facMin_z = Math.min(facLoc1.getBlockZ(), facLoc2.getBlockZ());

                //Claim values
                int locMax_x = Math.max(loc1.getBlockX(), loc2.getBlockX());
                int locMin_x = Math.min(loc1.getBlockX(), loc2.getBlockX());

                int locMax_z = Math.max(loc1.getBlockZ(), loc2.getBlockZ());
                int locMin_z = Math.min(loc1.getBlockZ(), loc2.getBlockZ());


                for(int i = facMin_x; i < facMax_x + 1; i++) {
                    for(int j = facMin_z; j < facMax_z + 1; j++) {
                        Location loc = new Location(Bukkit.getWorld(Core.getInstance().getConfig().getString("faction-world")), i, 0, j);

                        if((loc.getBlockX() <= locMax_x) && (loc.getBlockX() >= locMin_x)) {
                            if((loc.getBlockZ() <= locMax_z) && (loc.getBlockZ() >= locMin_z)) {
                                return false;
                            }
                        }
                    }
                }
            }

        }

        return true;
    }

    public boolean insideClaim(Location loc) {
        for(Faction faction : factions) {

            if(faction.getLoc1() != null && faction.getLoc2() != null) {
                Location loc1 = faction.getLoc1();
                Location loc2 = faction.getLoc2();


                int xMax = Math.max(loc1.getBlockX(), loc2.getBlockX());
                int zMax = Math.max(loc1.getBlockZ(), loc2.getBlockZ());

                int xMin = Math.min(loc1.getBlockX(), loc2.getBlockX());
                int zMin = Math.min(loc1.getBlockZ(), loc2.getBlockZ());

                if ((loc.getBlockX() >= xMin) && (loc.getBlockX() <= xMax)) {
                    if ((loc.getBlockZ() >= zMin) && (loc.getBlockZ() <= zMax)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public Faction getClaimByLocation(Location loc) {
        for(Faction faction : factions) {

            if(faction.getLoc1() != null && faction.getLoc2() != null) {
                Location loc1 = faction.getLoc1();
                Location loc2 = faction.getLoc2();


                int xMax = Math.max(loc1.getBlockX(), loc2.getBlockX());
                int zMax = Math.max(loc1.getBlockZ(), loc2.getBlockZ());

                int xMin = Math.min(loc1.getBlockX(), loc2.getBlockX());
                int zMin = Math.min(loc1.getBlockZ(), loc2.getBlockZ());

                if ((loc.getBlockX() >= xMin) && (loc.getBlockX() <= xMax)) {
                    if ((loc.getBlockZ() >= zMin) && (loc.getBlockZ() <= zMax)) {
                        return faction;
                    }
                }
            }
        }

        return null;
    }

    public boolean isPlayerInFaction(Player player) {
        for(Faction faction : factions) {
            if(faction.getMembers().contains(player.getUniqueId().toString())) {
                return true;
            }
        }

        return false;
    }

    public void sendFactionMessage(String msg, Faction faction) {

        for(Player p : Bukkit.getServer().getOnlinePlayers()) {
            if(faction.getMembers().contains(p.getUniqueId().toString())) {
                p.sendMessage(msg);
            }
        }

    }

    public void factionInfoByName(Player player, String name) {

        if(getFactionByName(name) == null) {
            player.sendMessage(ChatColor.RED + "Couldn't find that faction");
            return;
        }

        Faction faction = getFactionByName(name);

        String msg;

        if(faction.isSystem()) {

            msg = ChatColor.GOLD.toString() + ChatColor.BOLD + faction.getName() + "\n" + ChatColor.YELLOW + "Location: ";

            if(faction.getHome() == null) {
                msg = msg + ChatColor.RED + "Not Set";
            } else {
                msg = msg + ChatColor.WHITE + faction.getHome().getBlockX() + ", " + faction.getHome().getBlockZ();
            }
            player.sendMessage(ChatColor.GRAY.toString() + ChatColor.STRIKETHROUGH + "--------------------------------------------------");
            player.sendMessage(msg);
            player.sendMessage(ChatColor.GRAY.toString() + ChatColor.STRIKETHROUGH + "--------------------------------------------------");
        } else {

            List<String> online = new ArrayList<>();
            List<String> offline = new ArrayList<>();
            List<String> offlinecaptains = new ArrayList<>();
            List<String> onlinecaptains = new ArrayList<>();

            msg = ChatColor.GOLD.toString() + ChatColor.BOLD + faction.getName() + ChatColor.GRAY + " [" + online.size() + "/" + faction.getMembers().size() + "] " + ChatColor.YELLOW + "Home: ";

            if(faction.getHome() == null) {
                msg = msg + ChatColor.RED + "Not Set" + "\n";
            } else {
                msg = msg + ChatColor.WHITE + faction.getHome().getBlockX() + ", " + faction.getHome().getBlockZ() + "\n";
            }

            msg = msg + ChatColor.YELLOW + "Balance: " + ChatColor.GREEN + "$" + faction.getBalance() + "\n";

            if(Bukkit.getOfflinePlayer(UUID.fromString(faction.getLeader())).isOnline()) {
                msg = msg + ChatColor.YELLOW + "Leader: " + ChatColor.GREEN + Bukkit.getOfflinePlayer(UUID.fromString(faction.getLeader())).getName() + "\n";
            } else {
                msg = msg + ChatColor.YELLOW + "Leader: " + ChatColor.GRAY + Bukkit.getOfflinePlayer(UUID.fromString(faction.getLeader())).getName() + "\n";
            }

            for(String str : faction.getMembers()) {

                OfflinePlayer p = Bukkit.getOfflinePlayer(UUID.fromString(str));

                if(p.isOnline()) {

                    System.out.println(p.getName());

                    if(faction.getCaptains().contains(p.getUniqueId().toString())) {
                        onlinecaptains.add(p.getName());
                    } else {
                        online.add(p.getName());
                    }
                } else {
                    if(faction.getCaptains().contains(p.getUniqueId().toString())) {
                        offlinecaptains.add(p.getName());
                    } else {
                        offline.add(p.getName());
                    }
                }

            }

            if(online.contains(Bukkit.getOfflinePlayer(UUID.fromString(faction.getLeader())).getName())) {
                online.remove(Bukkit.getOfflinePlayer(UUID.fromString(faction.getLeader())).getName());
            }

            if(offline.contains(Bukkit.getOfflinePlayer(UUID.fromString(faction.getLeader())).getName())) {
                online.remove(Bukkit.getOfflinePlayer(UUID.fromString(faction.getLeader())).getName());
            }

            if(offlinecaptains.contains(Bukkit.getOfflinePlayer(UUID.fromString(faction.getLeader())).getName())) {
                online.remove(Bukkit.getOfflinePlayer(UUID.fromString(faction.getLeader())).getName());
            }

            if(onlinecaptains.contains(Bukkit.getOfflinePlayer(UUID.fromString(faction.getLeader())).getName())) {
                online.remove(Bukkit.getOfflinePlayer(UUID.fromString(faction.getLeader())).getName());
            }

            if(faction.getCaptains().size() > 0) {
                String cap = "";

                for(String s : onlinecaptains) {
                    cap = ChatColor.GREEN + cap + "," + s;
                }

                for(String s : offlinecaptains) {
                    cap = ChatColor.GRAY + cap + "," + s;
                }

                msg = msg + ChatColor.YELLOW + "Captains: " + cap + "\n";
            }

            String memb = "";

            for(String s : online) {
                memb = ChatColor.GREEN + memb + "," + s;
            }

            for(String s : offline) {
                memb = ChatColor.GRAY + memb + "," + s;
            }


             msg = msg + ChatColor.YELLOW + "Members: " + memb + "\n";

            if (faction.isRaidable()) {
                msg = msg + ChatColor.YELLOW + "DTR: " + ChatColor.RED + utils.formatDouble(faction.getDtr()) + "\n"
                        + ChatColor.RED.toString() + ChatColor.BOLD + "[RAIDABLE]" + "\n";
            } else {
                msg = msg + ChatColor.YELLOW + "DTR: " + ChatColor.GREEN + utils.formatDouble(faction.getDtr()) + "\n";
            }

            if(faction.getFreezetime() > 0) {
                msg = msg + ChatColor.DARK_RED + "Freeze Timer: " + ChatColor.RED + utils.toMMSS(faction.getFreezetime());
            }

            player.sendMessage(ChatColor.GRAY.toString() + ChatColor.STRIKETHROUGH + "--------------------------------------------------");
            player.sendMessage(msg);
            player.sendMessage(ChatColor.GRAY.toString() + ChatColor.STRIKETHROUGH + "--------------------------------------------------");

        }

    }

    public void factionInfoByPlayer(Player player) {

        if(getFactionByPlayer(player) == null) {
            player.sendMessage(ChatColor.RED + "You're not in a faction");
            return;
        }

        Faction faction = getFactionByPlayer(player);

        String msg;

        if(faction.isSystem()) {

            msg = ChatColor.GOLD.toString() + ChatColor.BOLD + faction.getName() + "\n" + ChatColor.YELLOW + "Location: ";

            if(faction.getHome() == null) {
                msg = msg + ChatColor.RED + "Not Set";
            } else {
                msg = msg + ChatColor.WHITE + faction.getHome().getBlockX() + ", " + faction.getHome().getBlockZ();
            }

            player.sendMessage(ChatColor.GRAY.toString() + ChatColor.STRIKETHROUGH + "--------------------------------------------------");
            player.sendMessage(msg);
            player.sendMessage(ChatColor.GRAY.toString() + ChatColor.STRIKETHROUGH + "--------------------------------------------------");
        } else {
            List<String> online = new ArrayList<>();
            List<String> offline = new ArrayList<>();
            List<String> offlinecaptains = new ArrayList<>();
            List<String> onlinecaptains = new ArrayList<>();

            msg = ChatColor.GOLD.toString() + ChatColor.BOLD + faction.getName() + ChatColor.GRAY + " [" + online.size() + "/" + faction.getMembers().size() + "] " + ChatColor.YELLOW + "Home: ";

            if(faction.getHome() == null) {
                msg = msg + ChatColor.RED + "Not Set" + "\n";
            } else {
                msg = msg + ChatColor.WHITE + faction.getHome().getBlockX() + ", " + faction.getHome().getBlockZ() + "\n";
            }

            msg = msg + ChatColor.YELLOW + "Balance: " + ChatColor.GREEN + "$" + faction.getBalance() + "\n";

            if(Bukkit.getOfflinePlayer(UUID.fromString(faction.getLeader())).isOnline()) {
                msg = msg + ChatColor.YELLOW + "Leader: " + ChatColor.GREEN + Bukkit.getOfflinePlayer(UUID.fromString(faction.getLeader())).getName() + "\n";
            } else {
                msg = msg + ChatColor.YELLOW + "Leader: " + ChatColor.GRAY + Bukkit.getOfflinePlayer(UUID.fromString(faction.getLeader())).getName() + "\n";
            }

            for(String str : faction.getMembers()) {

                OfflinePlayer p = Bukkit.getOfflinePlayer(UUID.fromString(str));

                if(p.isOnline()) {

                    System.out.println(p.getName());

                    if(faction.getCaptains().contains(p.getUniqueId().toString())) {
                        onlinecaptains.add(p.getName());
                    } else {
                        online.add(p.getName());
                    }
                } else {
                    if(faction.getCaptains().contains(p.getUniqueId().toString())) {
                        offlinecaptains.add(p.getName());
                    } else {
                        offline.add(p.getName());
                    }
                }

            }

            if(online.contains(Bukkit.getOfflinePlayer(UUID.fromString(faction.getLeader())).getName())) {
                online.remove(Bukkit.getOfflinePlayer(UUID.fromString(faction.getLeader())).getName());
            }

            if(offline.contains(Bukkit.getOfflinePlayer(UUID.fromString(faction.getLeader())).getName())) {
                online.remove(Bukkit.getOfflinePlayer(UUID.fromString(faction.getLeader())).getName());
            }

            if(offlinecaptains.contains(Bukkit.getOfflinePlayer(UUID.fromString(faction.getLeader())).getName())) {
                online.remove(Bukkit.getOfflinePlayer(UUID.fromString(faction.getLeader())).getName());
            }

            if(onlinecaptains.contains(Bukkit.getOfflinePlayer(UUID.fromString(faction.getLeader())).getName())) {
                online.remove(Bukkit.getOfflinePlayer(UUID.fromString(faction.getLeader())).getName());
            }

            if(faction.getCaptains().size() > 0) {
                String cap = "";

                for(String s : onlinecaptains) {
                    cap = ChatColor.GREEN + cap + "," + s;
                }

                for(String s : offlinecaptains) {
                    cap = ChatColor.GRAY + cap + "," + s;
                }

                msg = msg + ChatColor.YELLOW + "Captains: " + cap + "\n";
            }

            String memb = "";

            for(String s : online) {
                memb = ChatColor.GREEN + memb + "," + s;
            }

            for(String s : offline) {
                memb = ChatColor.GRAY + memb + "," + s;
            }


            msg = msg + ChatColor.YELLOW + "Members: " + memb + "\n";

            if (faction.isRaidable()) {
                msg = msg + ChatColor.YELLOW + "DTR: " + ChatColor.RED + utils.formatDouble(faction.getDtr()) + "\n"
                        + ChatColor.RED.toString() + ChatColor.BOLD + "[RAIDABLE]" + "\n";
            } else {
                msg = msg + ChatColor.YELLOW + "DTR: " + ChatColor.GREEN + utils.formatDouble(faction.getDtr()) + "\n";
            }

            if(faction.getFreezetime() > 0) {
                msg = msg + ChatColor.DARK_RED + "Freeze Timer: " + ChatColor.RED + utils.toMMSS(faction.getFreezetime()) + "\n";
            }

            msg = msg + ChatColor.YELLOW + "[Announcement] " + ChatColor.LIGHT_PURPLE + faction.getMotd();

            player.sendMessage(ChatColor.GRAY.toString() + ChatColor.STRIKETHROUGH + "--------------------------------------------------");
            player.sendMessage(msg);
            player.sendMessage(ChatColor.GRAY.toString() + ChatColor.STRIKETHROUGH + "--------------------------------------------------");
        }

    }

    public ArrayList<Faction> getFactions() {
        return factions;
    }

}
