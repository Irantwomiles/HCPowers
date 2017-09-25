package com.hcpowers.profile;

import com.hcpowers.core.Core;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class ProfileManager {

    private File file = null;

    private static Set<PlayerProfile> profiles = new HashSet<>();

    public PlayerProfile getProfileByPlayer(Player player) {

        for(PlayerProfile profile : profiles) {
            if(profile.getPlayer().getUniqueId() == player.getUniqueId()) {
                return profile;
            }
        }

        return null;
    }

    public void loadProfile(Player player) {

        file = new File(Core.getInstance().getDataFolder() + "/Profiles", player.getUniqueId().toString() + ".yml");

        if(file.exists()) {

            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

            PlayerProfile profile = new PlayerProfile(player);

            profile.setLives(config.getInt("lives"));
            profile.setPvpprot(config.getInt("pvpprot"));
            profile.setToggleChat(config.getBoolean("togglechat"));
            profile.setTogglepm(config.getBoolean("togglepm"));
            profile.setPlayer(player);

            profiles.add(profile);

        } else {
            createPlayerFile(player);
        }

    }

    public void saveProfile(Player player) {

        if(getProfileByPlayer(player) == null) {
            createPlayerFile(player);
            return;
        }

        file = new File(Core.getInstance().getDataFolder() + "/Profiles", player.getUniqueId().toString() + ".yml");

        if(file.exists()) {

            PlayerProfile profile = getProfileByPlayer(player);

            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

            config.set("lives", profile.getLives());
            config.set("togglechat", profile.isToggleChat());
            config.set("togglepm", profile.isTogglepm());
            config.set("pvpprot", profile.getPvpprot());

            if(profiles.contains(profile)) {
                profiles.remove(profile);
            }

            try {
                config.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            createPlayerFile(player);
        }

    }

    public void createPlayerFile(Player player) {

        file = new File(Core.getInstance().getDataFolder() + "/Profiles", player.getUniqueId().toString() + ".yml");

        if(!file.exists()) {

            file = new File(Core.getInstance().getDataFolder() + "/Profiles", player.getUniqueId().toString() + ".yml");

            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

            config.createSection("lives");
            config.createSection("togglechat");
            config.createSection("togglepm");
            config.createSection("pvpprot");

            config.set("lives", 0);
            config.set("togglechat", false);
            config.set("togglepm", false);
            config.set("pvpprot", 60 * 30);

            PlayerProfile profile = new PlayerProfile(player);

            profile.setLives(config.getInt("lives"));
            profile.setPvpprot(config.getInt("pvpprot"));
            profile.setToggleChat(config.getBoolean("togglechat"));
            profile.setTogglepm(config.getBoolean("togglepm"));
            profile.setPlayer(player);

            profiles.add(profile);

            try {
                config.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    public void createFolder() {

        file = new File(Core.getInstance().getDataFolder() + "/Profiles");

        if (!file.exists()) {

            file.mkdir();

            file = new File(Core.getInstance().getDataFolder() + "/Profiles", "deleteme.txt");

            System.out.println("[Factions] Created the directory 'Factions' with no errors!");
        }


    }

}
