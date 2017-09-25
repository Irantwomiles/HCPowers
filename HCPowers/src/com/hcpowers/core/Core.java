package com.hcpowers.core;

import com.hcpowers.factions.Claim;
import com.hcpowers.factions.Faction;
import com.hcpowers.factions.FactionManager;
import com.hcpowers.factions.commands.FactionCommands;
import com.hcpowers.factions.events.*;
import com.hcpowers.factions.runnables.ClaimRunnable;
import com.hcpowers.factions.runnables.FactionRunnable;
import com.hcpowers.factions.walls.ClaimWall;
import com.hcpowers.profile.ProfileManager;
import com.hcpowers.profile.SetupProfile;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Core extends JavaPlugin {

    private File file = null;

    private static Core instance;

    private FactionRunnable factionRunnable = new FactionRunnable();
    private ClaimRunnable claimRunnable = new ClaimRunnable();

    private ClaimWall wall = new ClaimWall();
    private ProfileManager pm = new ProfileManager();

    public void onEnable() {

        instance = this;

        FactionManager.getManager().loadFactions();

        setupFiles(file);

        registerCommands();
        registerEvents();

        factionRunnable.runTaskTimer(this, 20, 20);
        claimRunnable.runTaskTimer(this, 5, 5);

        wall.getWall().put("Irantwomiles", new ArrayList<Faction>());

        pm.createFolder();

        for(Player player : Bukkit.getServer().getOnlinePlayers()) {
            pm.loadProfile(player);
        }

    }

    public void onDisable() {
        FactionManager.getManager().saveFactions();

        for(Player player : Bukkit.getServer().getOnlinePlayers()) {
            pm.saveProfile(player);
        }

    }

    private void setupFiles(File file) {
        file = new File(this.getDataFolder() + "/Factions");

        if (!file.exists()) {

            file.mkdir();

            file = new File(this.getDataFolder() + "/Factions", "deleteme.yml");

            YamlConfiguration delete = YamlConfiguration
                    .loadConfiguration(file);
            delete.createSection("Delete");

            delete.set("Delete", "Delete this file, but leave the folder!");
            try {
                delete.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("[Factions] Created the directory 'Factions' with no errors!");
        }

        file = new File(this.getDataFolder(), "factions.yml");

        if (!file.exists()) {

            file = new File(this.getDataFolder(), "factions.yml");

            YamlConfiguration config = YamlConfiguration
                    .loadConfiguration(file);

            config.createSection("factions");
            try {
                config.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("Created factions.yml");
        }
    }

    private void registerCommands() {
        getCommand("faction").setExecutor(new FactionCommands());
    }

    private void registerEvents() {
        Bukkit.getServer().getPluginManager().registerEvents(new ClaimEvent(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerEnterClaimEvent(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerLeaveClaimEvent(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new BreakBlocksInClaim(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new PlaceBlocksInClaim(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerDeath(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerDamage(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new FactionChat(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerMove(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new SetupProfile(), this);
    }

    public static Core getInstance() {
        return instance;
    }

}

