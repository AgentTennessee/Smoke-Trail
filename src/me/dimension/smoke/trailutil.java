
package me.dimension.smoke;

import org.bukkit.ChatColor;




public class trailutil {
    public static trail plugin;
    
    public trailutil(trail instance) {
        plugin = instance;
    }
    public static String colorize(String announce)
  {if(announce.contains("&0")){
    announce = announce.replaceAll("&0", ChatColor.BLACK.toString());
  }
    if(announce.contains("&1")){
    announce = announce.replaceAll("&1", ChatColor.DARK_BLUE.toString());}
    if(announce.contains("&2")){
    announce = announce.replaceAll("&2", ChatColor.DARK_GREEN.toString());}
    if(announce.contains("&3")){
    announce = announce.replaceAll("&3", ChatColor.DARK_AQUA.toString());}
    if(announce.contains("&4")){
    announce = announce.replaceAll("&4", ChatColor.DARK_RED.toString());}
    if(announce.contains("&5")){
    announce = announce.replaceAll("&5", ChatColor.DARK_PURPLE.toString());}
    if(announce.contains("&6")){
    announce = announce.replaceAll("&6", ChatColor.GOLD.toString());}
    if(announce.contains("&7")){
    announce = announce.replaceAll("&7", ChatColor.GRAY.toString());}
    if(announce.contains("&8")){
    announce = announce.replaceAll("&8", ChatColor.DARK_GRAY.toString());}
    if(announce.contains("&9")){
    announce = announce.replaceAll("&9", ChatColor.BLUE.toString());}
    if(announce.contains("&a")){
    announce = announce.replaceAll("&a", ChatColor.GREEN.toString());}
    if(announce.contains("&b")){
    announce = announce.replaceAll("&b", ChatColor.AQUA.toString());}
    if(announce.contains("&c")){
    announce = announce.replaceAll("&c", ChatColor.RED.toString());}
    if(announce.contains("&d")){
    announce = announce.replaceAll("&d", ChatColor.LIGHT_PURPLE.toString());}
    if(announce.contains("&e")){
    announce = announce.replaceAll("&e", ChatColor.YELLOW.toString());}
    if(announce.contains("&f")){
    announce = announce.replaceAll("&f", ChatColor.WHITE.toString());}
    if(announce.contains("&k")){
    announce = announce.replaceAll("&k", ChatColor.MAGIC.toString());}
    if(announce.contains("&l")){
    announce = announce.replaceAll("&l", ChatColor.BOLD.toString());}
    if(announce.contains("&m")){
    announce = announce.replaceAll("&m", ChatColor.STRIKETHROUGH.toString());}
    if(announce.contains("&n")){
    announce = announce.replaceAll("&n", ChatColor.UNDERLINE.toString());}
    if(announce.contains("&o")){
    announce = announce.replaceAll("&o", ChatColor.ITALIC.toString());}
    if(announce.contains("&r")){
    announce = announce.replaceAll("&r", ChatColor.RESET.toString());
    }
    return announce;
  }

    
    
}
