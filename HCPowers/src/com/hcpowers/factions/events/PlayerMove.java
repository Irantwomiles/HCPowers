package com.hcpowers.factions.events;

import com.hcpowers.core.Core;
import com.hcpowers.factions.FactionManager;
import com.hcpowers.factions.walls.ClaimWall;
import com.hcpowers.profile.PlayerProfile;
import com.hcpowers.profile.ProfileManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMove implements Listener {

    private PlayerDamage playerDamage = new PlayerDamage();
    private ClaimWall wall = new ClaimWall();
    private ProfileManager pm = new ProfileManager();

    @EventHandler
    public void onMove(PlayerMoveEvent event) {

        Player player = event.getPlayer();

        PlayerProfile profile = pm.getProfileByPlayer(player);

        if(event.getFrom() != event.getTo()) {

            if((FactionManager.getManager().getFactionByLocation(event.getTo()) != null) && playerDamage.getPvptimer().containsKey(player.getName())) {

                player.teleport(player.getLocation().add(event.getFrom().toVector().subtract(event.getTo().toVector()).normalize().multiply(2)));

            }
        }

        if (wall.getWall().containsKey(player.getName())) {

            if (Core.getInstance().getConfig().getBoolean("enable-wall")) {

                wall.hideWall(player);

                for (Location loc : wall.locationsToUpdate(player, wall.getWall().get(player.getName()), wall.getNearByFactions(player))) {

                    player.sendBlockChange(loc, Material.getMaterial(95), (byte) 14);

                }
            }
        }

    }

}
