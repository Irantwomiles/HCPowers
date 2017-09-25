package com.hcpowers.factions.commands;

import com.hcpowers.core.Core;
import com.hcpowers.factions.Claim;
import com.hcpowers.factions.Faction;
import com.hcpowers.factions.FactionManager;
import com.hcpowers.factions.walls.FactionMap;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class FactionCommands implements CommandExecutor {

    private static HashMap<String, Claim> claiming = new HashMap<>();
    private static HashMap<String, ArrayList<Faction>> map = new HashMap<>();
    private static ArrayList<String> chat = new ArrayList<>();

    private FactionMap fmap = new FactionMap();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

       if(!(sender instanceof Player)) {
           return true;
       }

       Player player = (Player) sender;

       if(cmd.getName().equalsIgnoreCase("faction")) {
           if(args.length < 1) {
               player.sendMessage(ChatColor.DARK_GRAY.toString() + ChatColor.STRIKETHROUGH + "--------------------------------------------------");
               player.sendMessage(ChatColor.GOLD.toString() + ChatColor.STRIKETHROUGH + "-= " + ChatColor.GOLD + "Team Commands" + ChatColor.GOLD.toString() + ChatColor.STRIKETHROUGH + "=-");
               player.sendMessage(ChatColor.YELLOW + "");
               player.sendMessage(ChatColor.RED + "Team Captain Commands");
               player.sendMessage(ChatColor.YELLOW + "/f create <name>");
               player.sendMessage(ChatColor.YELLOW + "/f disband");
               player.sendMessage(ChatColor.YELLOW + "/f invite <player>");
               player.sendMessage(ChatColor.YELLOW + "/f uninvite <player>");
               player.sendMessage(ChatColor.YELLOW + "/f kick <player>");
               player.sendMessage(ChatColor.YELLOW + "/f leader <player>");
               player.sendMessage(ChatColor.YELLOW + "/f promote <player>");
               player.sendMessage(ChatColor.YELLOW + "/f demote <player>");
               player.sendMessage(ChatColor.YELLOW + "/f claim");
               player.sendMessage(ChatColor.YELLOW + "/f withdraw <amount>");
               player.sendMessage(ChatColor.YELLOW + "/f sethome");
               player.sendMessage(ChatColor.YELLOW + "/f announce <message>");
               player.sendMessage(ChatColor.YELLOW + "");
               player.sendMessage(ChatColor.DARK_GRAY + "Team Member Commands");
               player.sendMessage(ChatColor.GRAY + "/f join <f>");
               player.sendMessage(ChatColor.GRAY + "/f leave");
               player.sendMessage(ChatColor.GRAY + "/f chat");
               player.sendMessage(ChatColor.GRAY + "/f join <f>");
               player.sendMessage(ChatColor.GRAY + "/f who <f>");
               player.sendMessage(ChatColor.GRAY + "/f map");
               player.sendMessage(ChatColor.GRAY + "/f balance");
               player.sendMessage(ChatColor.GRAY + "/f deposit <amount>");
               player.sendMessage(ChatColor.GRAY + "/f home ");
               player.sendMessage(ChatColor.DARK_GRAY.toString() + ChatColor.STRIKETHROUGH + "--------------------------------------------------");

               return true;
           }

           if(args[0].equalsIgnoreCase("create")) {

               if(args.length < 2) {
                   player.sendMessage(ChatColor.GRAY + "/f create [name]");
                   return true;
               }

               FactionManager.getManager().createFaction(args[1], player);

           }

           if(args[0].equalsIgnoreCase("createsystem")) {

               if(args.length < 2) {
                   player.sendMessage(ChatColor.GRAY + "/f createsystem [name]");
                   return true;
               }

               FactionManager.getManager().createSystemFaction(args[1], player);

           }

           if(args[0].equalsIgnoreCase("disband")) {

               if(args.length < 2) {
                   FactionManager.getManager().disbandFactionByPlayer(player);
                   return true;
               }

               FactionManager.getManager().disbandFactionByName(args[1], player);

           }

           if(args[0].equalsIgnoreCase("invite")) {
               if(args.length < 2) {
                   player.sendMessage(ChatColor.GRAY + "/f invite [player]");
                   return true;
               }

               Player target = Bukkit.getPlayer(args[1]);

               if(target == null) {
                   player.sendMessage(ChatColor.RED + "Couldn't find that player.");
                   return true;
               }

               FactionManager.getManager().invitePlayer(player, target);
           }

           if(args[0].equalsIgnoreCase("uninvite")) {
               if(args.length < 2) {
                   player.sendMessage(ChatColor.GRAY + "/f uninvite [player]");
                   return true;
               }

               Player target = Bukkit.getPlayer(args[1]);

               if(target == null) {
                   player.sendMessage(ChatColor.RED + "Couldn't find that player.");
                   return true;
               }

               FactionManager.getManager().uninvitePlayer(player, target);
           }


           if(args[0].equalsIgnoreCase("join")) {
               if(args.length < 2) {
                   player.sendMessage(ChatColor.GRAY + "/f join [faction]");
                   return true;
               }

               FactionManager.getManager().joinFaction(player, args[1]);
           }

           if(args[0].equalsIgnoreCase("leave")) {

               FactionManager.getManager().leaveFaction(player);

           }

           if(args[0].equalsIgnoreCase("map")) {

               if(!map.containsKey(player.getName())) {

                   Location loc1 = new Location(Bukkit.getWorld(Core.getInstance().getConfig().getString("faction-world")), player.getLocation().getBlockX() + 50, 0, player.getLocation().getBlockZ() + 50);
                   Location loc2 = new Location(Bukkit.getWorld(Core.getInstance().getConfig().getString("faction-world")), player.getLocation().getBlockX() - 50, 0, player.getLocation().getBlockZ() - 50);

                   map.put(player.getName(), fmap.factionMap(player, loc1, loc2));

                   player.sendMessage(ChatColor.YELLOW + "Looking for near by factions");

                   String msg = ChatColor.YELLOW + "Found: " + ChatColor.AQUA;

                   for(Faction faction : getMap().get(player.getName())) {
                       msg = msg + faction.getName() + ChatColor.GRAY + "[" + faction.onlinePlayerCount() + "/" + faction.getMembers().size() + "] " + ChatColor.AQUA;
                   }

                   player.sendMessage(msg);

               } else {
                   player.sendMessage(ChatColor.RED + "Hiding faction pillars");
                   fmap.hidePillars(player);
                   getMap().remove(player.getName());

               }
           }

           if(args[0].equalsIgnoreCase("who") || args[0].equalsIgnoreCase("show")) {

               if (args.length < 2) {
                   FactionManager.getManager().factionInfoByPlayer(player, player);
                   return true;
               }

               Player target = Bukkit.getPlayer(args[1]);

               if(target == null) {
                   FactionManager.getManager().factionInfoByName(player, args[1]);
                   return true;
               }

               FactionManager.getManager().factionInfoByPlayer(player, target);
           }

           if(args[0].equalsIgnoreCase("chat")) {

               if(!FactionManager.getManager().isPlayerInFaction(player)) {
                   player.sendMessage(ChatColor.RED + "Must be in a faction to do this command");
                   return true;
               }

               if(chat.contains(player.getName())) {
                   chat.remove(player.getName());
                   player.sendMessage(ChatColor.YELLOW + "Now talking in: " + ChatColor.WHITE + "Public Chat");
               } else {
                   chat.add(player.getName());
                   player.sendMessage(ChatColor.YELLOW + "Now talking in: " + ChatColor.GREEN + "Faction Chat");
               }

           }

           if(args[0].equalsIgnoreCase("claim")) {

               ItemStack wand = new ItemStack(Material.STICK, 1);
               ItemMeta wmeta = wand.getItemMeta();

               wmeta.setDisplayName(ChatColor.YELLOW.toString() + ChatColor.BOLD + "Claiming Wand");
               wmeta.setLore(Arrays.asList(ChatColor.GRAY + "Right Click to set position 1", ChatColor.GRAY + "Left Click to set position 2", ChatColor.GREEN + "Shit + Right Click to Claim"));

               wand.setItemMeta(wmeta);

               if(args.length < 2) {
                   if(FactionManager.getManager().getFactionByPlayer(player) == null) {
                       player.sendMessage(ChatColor.RED + "You are not in a faction");
                       return true;
                   }

                   Faction faction = FactionManager.getManager().getFactionByPlayer(player);

                   if(faction.getLeader().equalsIgnoreCase(player.getUniqueId().toString())
                           || faction.getCaptains().contains(player.getUniqueId().toString()))
                   {

                       claiming.put(player.getName(), new Claim(faction, null, null));

                       player.getInventory().addItem(wand);

                       player.sendMessage(ChatColor.GREEN.toString() + ChatColor.BOLD + "Claiming Mode Enabled");

                   }

                   return true;
               }

               Faction faction = FactionManager.getManager().getFactionByName(args[1]);

               if(faction == null) {
                   player.sendMessage(ChatColor.RED + "Couldn't find that faction");
                   return true;
               }

               if(!player.hasPermission("hcpowers.factions.admin")) {
                   player.sendMessage(ChatColor.RED + "No Permission.");
                   return true;
               }

               claiming.put(player.getName(), new Claim(faction, null, null));

               player.getInventory().addItem(wand);

               player.sendMessage(ChatColor.GREEN.toString() + ChatColor.BOLD + "Claiming Mode Enabled");

           }

           if(args[0].equalsIgnoreCase("system")) {

               if(!player.hasPermission("hcpowers.factions.admin")) {
                   player.sendMessage(ChatColor.RED + "No Permission.");
                   return true;
               }

               if(args.length < 2) {
                   player.sendMessage(ChatColor.GRAY + "/f system [name]");
                   return true;
               }

               FactionManager.getManager().setFactionSystem(FactionManager.getManager().getFactionByName(args[1]), player);

           }

           if(args[0].equalsIgnoreCase("deathban")) {

               if(!player.hasPermission("hcpowers.factions.admin")) {
                   player.sendMessage(ChatColor.RED + "No Permission.");
                   return true;
               }

               if(args.length < 2) {
                   player.sendMessage(ChatColor.GRAY + "/f deathban [name]");
                   return true;
               }

               FactionManager.getManager().setDeathbanStatus(FactionManager.getManager().getFactionByName(args[1]), player);

           }

       }

        return true;
    }

    public static HashMap<String, Claim> getClaiming() {
        return claiming;
    }
    public static HashMap<String, ArrayList<Faction>> getMap() {
        return map;
    }
    public static ArrayList<String> getChat() {return chat;}
}
