package com.hcpowers.profile;

import com.hcpowers.core.utils.Utils;
import com.hcpowers.core.utils.scoreboard.PlayerBoard;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class SetupProfile implements Listener {

    private ProfileManager pm = new ProfileManager();

    private PlayerBoard pb = new PlayerBoard();
    private Utils utils = new Utils();
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();

        pm.loadProfile(event.getPlayer());

        pb.loadEntries(player);

        pb.setPlayerBoard(event.getPlayer());

        PlayerProfile profile = pm.getProfileByPlayer(player);

        if(profile.getPvpprot() > 0) {
            pb.addLine(player, player.getScoreboard(), ChatColor.BLUE.toString() + ChatColor.BOLD + "Protection: " + ChatColor.WHITE + utils.toMMSS(profile.getPvpprot()), "pvpprotection");
        }

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        pm.saveProfile(event.getPlayer());
    }
}
