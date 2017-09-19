package com.hcpowers.profile;

import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;

public class ProfileManager {

    private File file = null;

    private static ArrayList<PlayerProfile> profiles = new ArrayList<>();

    public PlayerProfile getProfileByPlayer(Player player) {

        for(PlayerProfile profile : profiles) {
            if(profile.getPlayer().getUniqueId() == player.getUniqueId()) {
                return profile;
            }
        }

        return null;
    }

    public void loadProfiles(Player player) {

        PlayerProfile profile = new PlayerProfile(player);

    }

}
