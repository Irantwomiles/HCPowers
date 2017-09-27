package com.hcpowers.koth.commands;

import com.hcpowers.core.utils.Utils;
import com.hcpowers.koth.Koth;
import com.hcpowers.koth.KothClaim;
import com.hcpowers.koth.KothManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.HashMap;

public class KothCommands implements CommandExecutor {

    private static HashMap<String, KothClaim> claiming = new HashMap<>();

    private Utils utils = new Utils();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(!(sender instanceof Player)) {return true;}

        Player player = (Player) sender;

        if(cmd.getName().equalsIgnoreCase("koth")) {

            if(!player.hasPermission("hcpower.koth.admin")) {
                player.sendMessage(ChatColor.RED + "No Permission.");
                return true;
            }

            if(args.length < 1) {
                player.sendMessage(ChatColor.GRAY + "/koth create <name>");
                player.sendMessage(ChatColor.GRAY + "/koth claim <name>");
                player.sendMessage(ChatColor.GRAY + "/koth setloot <name>");
                player.sendMessage(ChatColor.GRAY + "/koth settime <name> <time>");
                player.sendMessage(ChatColor.GRAY + "/koth start <name>");
                player.sendMessage(ChatColor.GRAY + "/koth stop <name>");
                return true;
            }

            if(args[0].equalsIgnoreCase("create")) {

                if(args.length < 2) {
                    player.sendMessage(ChatColor.RED + "/koth create <name>");
                    return true;
                }

                KothManager.getManager().createKoth(args[1], player);
            }

            if(args[0].equalsIgnoreCase("claim")) {

                ItemStack wand = new ItemStack(Material.STICK, 1);
                ItemMeta wmeta = wand.getItemMeta();

                wmeta.setDisplayName(ChatColor.BLUE.toString() + ChatColor.BOLD + "KOTH Claiming Wand");
                wmeta.setLore(Arrays.asList(ChatColor.GRAY + "Right Click to set position 1", ChatColor.GRAY + "Left Click to set position 2", ChatColor.GREEN + "Shit + Right Click to Claim"));

                wand.setItemMeta(wmeta);

                if (args.length < 2) {
                    player.sendMessage(ChatColor.RED + "/koth claim <name>");
                    return true;
                }

                if(getClaiming().containsKey(player.getName())) {
                    getClaiming().remove(player.getName());
                    player.sendMessage(ChatColor.RED.toString() + ChatColor.BOLD + "Claiming Mode Disabled");
                } else {

                    if(KothManager.getManager().getKothByName(args[1]) != null) {

                        claiming.put(player.getName(), new KothClaim(KothManager.getManager().getKothByName(args[1]), null, null));

                        player.getInventory().addItem(wand);

                        player.sendMessage(ChatColor.BLUE.toString() + ChatColor.BOLD + "Claiming Mode Enabled");

                    }
                }

            }

            if(args[0].equalsIgnoreCase("settime")) {
                if (args.length < 3) {
                    player.sendMessage(ChatColor.RED + "/koth settime <name> <time>");
                    return true;
                }

                try {

                    if(KothManager.getManager().getKothByName(args[1]) != null) {

                        Koth koth = KothManager.getManager().getKothByName(args[1]);

                        int timer = Integer.parseInt(args[2]);

                        koth.setTimer(timer);

                        player.sendMessage(ChatColor.GREEN + "You have set the time for " + koth.getName() + " " + utils.toMMSS(timer));

                    }

                } catch (Exception e) {
                    player.sendMessage(ChatColor.RED + "/koth settime <name> <timer>");
                }
            }

            if(args[0].equalsIgnoreCase("start")) {
                if (args.length < 2) {
                    player.sendMessage(ChatColor.RED + "/koth start <name>");
                    return true;
                }


                if (KothManager.getManager().getKothByName(args[1]) != null) {

                    Koth koth = KothManager.getManager().getKothByName(args[1]);

                    if(!koth.isActive()) {
                        koth.setActive(true);
                        koth.setCurrentTimer(koth.getTimer());
                        Bukkit.broadcastMessage(ChatColor.YELLOW + "KOTH " + ChatColor.GOLD + koth.getName() + ChatColor.YELLOW + " is now Active " + ChatColor.GRAY + "(" + utils.toMMSS(koth.getTimer()) + ")");
                    } else {
                       player.sendMessage(ChatColor.RED + "That KOTH is already active");
                    }
                }
            }

            if(args[0].equalsIgnoreCase("stop")) {
                if (args.length < 2) {
                    player.sendMessage(ChatColor.RED + "/koth start <name>");
                    return true;
                }

                if (KothManager.getManager().getKothByName(args[1]) != null) {

                    Koth koth = KothManager.getManager().getKothByName(args[1]);

                    if(koth.isActive()) {
                        koth.setCurrentTimer(-1);
                        koth.setActive(false);
                        Bukkit.broadcastMessage(ChatColor.RED + "KOTH " + ChatColor.GOLD + koth.getName() + ChatColor.YELLOW + " is now Inactive ");
                    } else {
                        player.sendMessage(ChatColor.RED + "That KOTH is already inactive");
                    }
                }
            }

        }

        return true;
    }

    public static HashMap<String, KothClaim> getClaiming() {
        return claiming;
    }
}
