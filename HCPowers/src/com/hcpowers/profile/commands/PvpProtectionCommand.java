package com.hcpowers.profile.commands;

import com.hcpowers.profile.PlayerProfile;
import com.hcpowers.profile.ProfileManager;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PvpProtectionCommand implements CommandExecutor {

    private ProfileManager pm = new ProfileManager();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;

        PlayerProfile profile = pm.getProfileByPlayer(player);

        if(cmd.getName().equalsIgnoreCase("pvp")) {
            if(args.length < 1) {
                player.sendMessage(ChatColor.BLUE + "/pvp enable");
            }

            if(args[0].equalsIgnoreCase("enable")) {

                if(args.length < 2) {
                    if(profile.getPvpprot() > 0) {
                        profile.setPvpprot(0);
                        player.sendMessage(ChatColor.BLUE + "Disabled PvP-Protection");
                    } else {
                        player.sendMessage(ChatColor.RED + "You don't have PvP-Protection");
                    }
                    return true;
                }

                if(player.hasPermission("hcpower.pvp.enable")) {

                    Player target = Bukkit.getPlayer(args[1]);

                    if(target == null) {
                        player.sendMessage(ChatColor.RED + "Can't find that player");
                        return true;
                    }

                    PlayerProfile targetProfile = pm.getProfileByPlayer(target);

                    if(targetProfile.getPvpprot() > 0) {
                        player.sendMessage(ChatColor.BLUE + "Disabled PvP-Protection for " + target.getName());
                        target.sendMessage(ChatColor.BLUE + "Disabled PvP-Protection");
                    } else {
                        player.sendMessage(ChatColor.RED + "That player has no PvP-Protection");
                    }
                }
            }
        }

        return true;
    }
}
