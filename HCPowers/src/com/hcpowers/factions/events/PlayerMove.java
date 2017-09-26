package com.hcpowers.factions.events;

import com.hcpowers.core.Core;
import com.hcpowers.factions.Faction;
import com.hcpowers.factions.FactionManager;
import com.hcpowers.factions.commands.FactionCommands;
import com.hcpowers.factions.walls.ClaimWall;
import com.hcpowers.profile.PlayerProfile;
import com.hcpowers.profile.ProfileManager;
import net.minecraft.server.v1_7_R4.PacketPlayOutWorldParticles;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMove implements Listener {

    private ClaimWall wall = new ClaimWall();
    private ProfileManager pm = new ProfileManager();

    @EventHandler
    public void onMove(PlayerMoveEvent event) {

        Player player = event.getPlayer();

        PlayerProfile profile = pm.getProfileByPlayer(player);

        if(event.getFrom() != event.getTo()) {

            if((FactionManager.getManager().getFactionByLocation(event.getTo()) != null)) {

                Faction faction = FactionManager.getManager().getFactionByLocation(event.getTo());

                if(faction.isSystem() && faction.isDeathban() && profile.getPvpprot() > 0) {

                    player.teleport(player.getLocation().add(event.getFrom().toVector().subtract(event.getTo().toVector()).normalize().multiply(2)));
                    player.playSound(player.getLocation(), Sound.FIRE_IGNITE, 10F, 10F);

                } else if(faction.isSystem() && !faction.isDeathban() && profile.getPvptimer() > 0) {

                    player.teleport(player.getLocation().add(event.getFrom().toVector().subtract(event.getTo().toVector()).normalize().multiply(2)));
                    player.playSound(player.getLocation(), Sound.FIRE_IGNITE, 10F, 10F);

                } else if(!faction.isSystem() && faction.isDeathban() && profile.getPvpprot() > 0) {

                    player.teleport(player.getLocation().add(event.getFrom().toVector().subtract(event.getTo().toVector()).normalize().multiply(2)));
                    player.playSound(player.getLocation(), Sound.FIRE_IGNITE, 10F, 10F);

                }
            }
        }

        if(FactionCommands.getHome().containsKey(player.getName())) {

            if(!(event.getFrom().getBlockX() == event.getTo().getBlockX() && event.getFrom().getBlockY() == event.getTo().getBlockY() && event.getFrom().getBlockZ() == event.getTo().getBlockZ())) {
                FactionCommands.getHome().remove(player.getName());

                player.sendMessage(ChatColor.RED + "Teleportation cancelled!");
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
