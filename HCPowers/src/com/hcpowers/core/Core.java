package com.hcpowers.core;

import com.hcpowers.factions.FactionManager;
import com.hcpowers.factions.commands.FactionCommands;
import com.hcpowers.factions.events.*;
import com.hcpowers.factions.runnables.FactionRunnable;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class Core extends JavaPlugin {

    private File file = null;

    private static Core instance;

    private FactionRunnable factionRunnable = new FactionRunnable();

    public void onEnable() {

        instance = this;

        FactionManager.getManager().loadFactions();

        setupFiles(file);

        registerCommands();
        registerEvents();

        factionRunnable.runTaskTimer(this, 20, 20);

    }

    public void onDisable() {
        FactionManager.getManager().saveFactions();
    }

    private void setupFiles(File file) {
        file = new File(this.getDataFolder() + "/Factions");

        if (!file.exists()) {

            file.mkdir();
            file = new File(this.getDataFolder() + "/Factions", "deleteme.yml");
            new YamlConfiguration();

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

            new YamlConfiguration();

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
    }

    public static Core getInstance() {
        return instance;
    }

}

