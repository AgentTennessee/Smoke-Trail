package me.dimension.smoke;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.Metrics;

public class trail extends JavaPlugin {

    public static trail plugin;
    public static final Logger log = Logger.getLogger("Minecraft");
    public boolean enabled = false;
    public String lol = "[Smoke Trail] ";
    public String Multi = "[Multi Trail] ";
    /*   public String Custom1 = "[Custom Trail 1] ";
     public String Custom2 = "[Custom Trail 2] ";
     public String Custom3 = "[Custom Trail 3] ";
     public String Dynamic = "[Dyna Trail] "; */
    public String[] trails = {"smoke", "fire", "ender", "flowers", "loot", "star", "hearts", "crit", "sweat", "disco", "blood", "sparks", "breadcrumbs", "magma", "letters", "fireworks", "snow", "happy", "magic", "music", "multi"};
    public ChatColor Red = ChatColor.RED;
    public ChatColor Blue = ChatColor.BLUE;
    public String on = "is now on";
    public String off = "is now off";
    public final HashMap<String, ArrayList<String>> modelist = new HashMap();
    public final HashMap<String, ArrayList<String>> skulllist = new HashMap();
    public final traillistener pl = new traillistener(this);
    public final trailutil util = new trailutil(this);

    @Override
    public void onEnable() {
        trail.log.log(Level.INFO, "{0} is now enabled!", lol);
        setupCommands();
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(pl, this);

        this.saveResource("config.yml", false);
        this.getConfig();
        this.reloadConfig();
        if(this.getConfig().contains("Messages.breadcrumb")){
            String fix = this.getConfig().getString("Messages.breadcrumb.enabled");
            String fix2 = this.getConfig().getString("Messages.breadcrumb.disabled");
            this.getConfig().set("Messages.breadcrumb.enabled", null);
            this.getConfig().set("Messages.breadcrumb.disabled",null);
            this.getConfig().set("Messages.breadcrumbs.enabled", fix);
            this.getConfig().set("Messages.breadcrumbs.disabled",fix2);
            this.saveConfig();
                    
        }

        try {
            Metrics metrics = new Metrics(this);
            metrics.start();
            trail.log.log(Level.INFO, "Smoke Trail Metrics loaded");
        } catch (IOException e) {
            // Failed to submit the stats :-(
        }



        if (this.getConfig().getConfigurationSection("Users") == null) {
        } else {
            for (String str : this.getConfig().getConfigurationSection("Users").getKeys(false)) {

                String p = this.getConfig().getString("Users." + str);
                String[] split = p.split(",");
                ArrayList<String> trailuser = new ArrayList<String>();
                trailuser.addAll(Arrays.asList(split));
                modelist.put(str, trailuser);
            }
        }
        /*if (this.getConfig().getConfigurationSection("Skulls") == null) {
         } else {
         for (String str : this.getConfig().getConfigurationSection("Skulls").getKeys(false)) {

         String p = this.getConfig().getString("Skulls." + str);
         String[] split = p.split(",");
         ArrayList<String> userskulls = new ArrayList<String>();
         userskulls.addAll(Arrays.asList(split));
         skulllist.put(str, userskulls);
         }
         }
         */

        this.saveConfig();
    }

    @Override
    public void onDisable() {
        removeallItems(pl.allitems);
        trail.log.log(Level.INFO, "{0}is now disabled", this.lol);
        for (Entry<String, ArrayList<String>> pointstostore : modelist.entrySet()) {
            String skulls = null;
            for (int i = 0; i < pointstostore.getValue().size(); i++) {

                skulls += pointstostore.getValue().get(i);
                skulls += ",";

                this.getConfig().set("Users." + pointstostore.getKey(), skulls);
            }
        }
        /* for (Entry<String, ArrayList<String>> pointstostore : skulllist.entrySet()) {
         String skulls = null;
         for (int i = 0; i < pointstostore.getValue().size(); i++) {
                
         skulls += pointstostore.getValue().get(i);
         skulls += ",";
                
         }
         this.getConfig().set("Skulls." + pointstostore.getKey(), skulls);

         }*/

    }

    //Make commands work
    public void setupCommands() {
        PluginCommand trail = getCommand("trail");
        CommandExecutor commandExecutor = new CommandExecutor() {
            @Override
            public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
                if (((sender instanceof Player))
                        && (args.length > 0)) {
                    trail.this.commandHandler((Player) sender, args);
                } else if (((sender instanceof Player))
                        && (args.length == 0)) {
                    Random random = new Random();
                    sender.sendMessage(ChatColor.RED + "For a list of trails do /trail list or try out /trail " + trails[random.nextInt(trails.length)]);
                }

                return true;
            }
        };
        if (trail != null) {
            trail.setExecutor(commandExecutor);
        }
    }
    //Parse the player and the arguments he has

    public void commandHandler(Player player, String[] args) {
        //Allow the player/player chosen from argument to use any of the trails based off of what they chose in their command
        if (args[0].equalsIgnoreCase("fire")) {
            if ((args.length == 1) && (player.hasPermission("smoketrail.use.fire"))) {

                switchTrails(player.getName(), "fire");

            } else if ((args.length == 2) && (player.hasPermission("smoketrail.other.fire"))) {
                Player player2 = Bukkit.getServer().getPlayer(args[1]);

                switchTrails(player2.getName(), "fire");

            } else {
                player.sendMessage(this.Red + "You don't have permission to do this");
            }
        } else if (args[0].equalsIgnoreCase("disco")) {
            if ((args.length == 1) && (player.hasPermission("smoketrail.use.disco"))) {

                switchTrails(player.getName(), "disco");

            } else if ((args.length == 2) && (player.hasPermission("smoketrail.other.disco"))) {
                Player player2 = Bukkit.getServer().getPlayer(args[1]);

                switchTrails(player2.getName(), "disco");

            } else {
                player.sendMessage(this.Red + "You don't have permission to do this");
            }
        } else if (args[0].equalsIgnoreCase("sparks")) {
            if ((args.length == 1) && (player.hasPermission("smoketrail.use.sparks"))) {

                switchTrails(player.getName(), "sparks");

            } else if ((args.length == 2) && (player.hasPermission("smoketrail.other.sparks"))) {
                Player player2 = Bukkit.getServer().getPlayer(args[1]);

                switchTrails(player2.getName(), "sparks");

            } else {
                player.sendMessage(this.Red + "You don't have permission to do this");
            }
        } else if (args[0].equalsIgnoreCase("magma")) {
            if ((args.length == 1) && (player.hasPermission("smoketrail.use.magma"))) {

                switchTrails(player.getName(), "magma");

            } else if ((args.length == 2) && (player.hasPermission("smoketrail.other.magma"))) {
                Player player2 = Bukkit.getServer().getPlayer(args[1]);

                switchTrails(player2.getName(), "magma");

            } else {
                player.sendMessage(this.Red + "You don't have permission to do this");
            }
        } else if (args[0].equalsIgnoreCase("breadcrumbs")) {
            if ((args.length == 1) && (player.hasPermission("smoketrail.use.breadcrumb"))) {

                switchTrails(player.getName(), "breadcrumbs");

            } else if ((args.length == 2) && (player.hasPermission("smoketrail.other.breadcrumb"))) {
                Player player2 = Bukkit.getServer().getPlayer(args[1]);
                switchTrails(player2.getName(), "breadcrumbs");

            } else {
                player.sendMessage(this.Red + "You don't have permission to do this");
            }
        } else if (args[0].equalsIgnoreCase("letters")) {
            if ((args.length == 1) && (player.hasPermission("smoketrail.use.letters"))) {

                switchTrails(player.getName(), "letters");

            } else if ((args.length == 2) && (player.hasPermission("smoketrail.other.letters"))) {
                Player player2 = Bukkit.getServer().getPlayer(args[1]);

                switchTrails(player2.getName(), "letters");

            } else {
                player.sendMessage(this.Red + "You don't have permission to do this");
            }
        } else if (args[0].equalsIgnoreCase("happy")) {
            if ((args.length == 1) && (player.hasPermission("smoketrail.use.happy"))) {

                switchTrails(player.getName(), "happy");

            } else if ((args.length == 2) && (player.hasPermission("smoketrail.other.happy"))) {
                Player player2 = Bukkit.getServer().getPlayer(args[1]);

                switchTrails(player2.getName(), "happy");

            } else {
                player.sendMessage(this.Red + "You don't have permission to do this");
            }
        } else if (args[0].equalsIgnoreCase("fireworks")) {
            if ((args.length == 1) && (player.hasPermission("smoketrail.use.fireworks"))) {

                switchTrails(player.getName(), "fireworks");

            } else if ((args.length == 2) && (player.hasPermission("smoketrail.other.fireworks"))) {
                Player player2 = Bukkit.getServer().getPlayer(args[1]);

                switchTrails(player2.getName(), "fireworks");

            } else {
                player.sendMessage(this.Red + "You don't have permission to do this");
            }
        } else if (args[0].equalsIgnoreCase("snow")) {
            if ((args.length == 1) && (player.hasPermission("smoketrail.use.snow"))) {

                switchTrails(player.getName(), "snow");

            } else if ((args.length == 2) && (player.hasPermission("smoketrail.other.snow"))) {
                Player player2 = Bukkit.getServer().getPlayer(args[1]);

                switchTrails(player2.getName(), "snow");

            } else {
                player.sendMessage(this.Red + "You don't have permission to do this");
            }
        } else if (args[0].equalsIgnoreCase("magic")) {
            if ((args.length == 1) && (player.hasPermission("smoketrail.use.magic"))) {

                switchTrails(player.getName(), "magic");

            } else if ((args.length == 2) && (player.hasPermission("smoketrail.other.magic"))) {
                Player player2 = Bukkit.getServer().getPlayer(args[1]);

                switchTrails(player2.getName(), "magic");

            } else {
                player.sendMessage(this.Red + "You don't have permission to do this");
            }
        }  else if (args[0].equalsIgnoreCase("ender")) {
            if ((args.length == 1) && (player.hasPermission("smoketrail.use.ender"))) {
                
                    switchTrails(player.getName(), "ender");
               
            } else if ((args.length == 2) && (player.hasPermission("smoketrail.other.ender"))) {
                Player player2 = Bukkit.getServer().getPlayer(args[1]);
                
                    switchTrails(player2.getName(), "ender");
               
            } else {
                player.sendMessage(this.Red + "You don't have permission to do this");
            }
        } else if (args[0].equalsIgnoreCase("blood")) {
            if ((args.length == 1) && (player.hasPermission("smoketrail.use.blood"))) {
              
                    switchTrails(player.getName(), "blood");
              
            } else if ((args.length == 2) && (player.hasPermission("smoketrail.other.blood"))) {
                Player player2 = Bukkit.getServer().getPlayer(args[1]);
               
                    switchTrails(player2.getName(), "blood");
               
            } else {
                player.sendMessage(this.Red + "You don't have permission to do this");
            }
        } else if (args[0].equalsIgnoreCase("sweat")) {
            if ((args.length == 1) && (player.hasPermission("smoketrail.use.sweat"))) {
               
                    switchTrails(player.getName(), "sweat");
                
            } else if ((args.length == 2) && (player.hasPermission("smoketrail.other.sweat"))) {
                Player player2 = Bukkit.getServer().getPlayer(args[1]);
                
                    switchTrails(player2.getName(), "sweat");
               
            } else {
                player.sendMessage(this.Red + "You don't have permission to do this");
            }
        } else if (args[0].equalsIgnoreCase("hearts")) {
            if ((args.length == 1) && (player.hasPermission("smoketrail.use.hearts"))) {
               
                    switchTrails(player.getName(), "hearts");

            } else if ((args.length == 2) && (player.hasPermission("smoketrail.other.hearts"))) {
                Player player2 = Bukkit.getServer().getPlayer(args[1]);
                
                    switchTrails(player2.getName(), "hearts");


            } else {
                player.sendMessage(this.Red + "You don't have permission to do this");
            }
        } else if (args[0].equalsIgnoreCase("crit")) {
            if ((args.length == 1) && (player.hasPermission("smoketrail.use.crit"))) {
                if (this.modelist.containsKey(player.getName())) {
                    switchTrails(player.getName(), "crit");
                } else {
                    switchTrails(player.getName(), "crit");
                }


            } else if ((args.length == 2) && (player.hasPermission("smoketrail.other.crit"))) {
                Player player2 = Bukkit.getServer().getPlayer(args[1]);
                if (this.modelist.containsKey(player2.getName())) {
                    switchTrails(player2.getName(), "crit");
                } else {
                    switchTrails(player2.getName(), "crit");
                }
            } else {
                player.sendMessage(this.Red + "You don't have permission to do this");
            }

        } else if (args[0].equalsIgnoreCase("smoke")) {
            if ((args.length == 1) && (player.hasPermission("smoketrail.use.smoke"))) {
               
                    switchTrails(player.getName(), "smoke");


            } else if ((args.length == 2) && (player.hasPermission("smoketrail.other.smoke"))) {
                Player player2 = Bukkit.getServer().getPlayer(args[1]);
               
                    switchTrails(player2.getName(), "smoke");

            } else {
                player.sendMessage(this.Red + "You don't have permission to do this");
            }

        } else if (args[0].equalsIgnoreCase("flowers")) {
            if ((args.length == 1) && (player.hasPermission("smoketrail.use.flowers"))) {
            
                    switchTrails(player.getName(), "flowers");


               
            } else if ((args.length == 2) && (player.hasPermission("smoketrail.other.flowers"))) {
                Player player2 = Bukkit.getServer().getPlayer(args[1]);
                
                    switchTrails(player2.getName(), "flowers");

                
            } else {
                player.sendMessage(this.Red + "You don't have permission to do this");
            }

        } else if (args[0].equalsIgnoreCase("loot")) {
            if ((args.length == 1) && (player.hasPermission("smoketrail.use.loot"))) {
               
       
                    switchTrails(player.getName(), "loot");

                
            } else if ((args.length == 2) && (player.hasPermission("smoketrail.other.loot"))) {
                Player player2 = Bukkit.getServer().getPlayer(args[1]);
                
                    switchTrails(player2.getName(), "loot");


            } else {
                player.sendMessage(this.Red + "You don't have permission to do this");
            }
        } else if (args[0].equalsIgnoreCase("stars")) {
            if ((args.length == 1) && (player.hasPermission("smoketrail.use.stars"))) {

                switchTrails(player.getName(), "stars");

            } else if ((args.length == 2) && (player.hasPermission("smoketrail.other.stars"))) {
                Player player2 = Bukkit.getServer().getPlayer(args[1]);

                switchTrails(player2.getName(), "stars");

            } else {
                player.sendMessage(this.Red + "You don't have permission to do this");
            }


        } else if (args[0].equalsIgnoreCase("music")) {
            if ((args.length == 1) && (player.hasPermission("smoketrail.use.music"))) {
                if (this.modelist.containsKey(player.getName())) {
                    switchTrails(player.getName(), "music");


                } else {
                    switchTrails(player.getName(), "music");

                }
            } else if ((args.length == 2) && (player.hasPermission("smoketrail.other.music"))) {
                Player player2 = Bukkit.getServer().getPlayer(args[1]);
               
                    switchTrails(player2.getName(), "music");

               
            } else {
                player.sendMessage(this.Red + "You don't have permission to do this");
            }
        } else if (args[0].equalsIgnoreCase("off")) {
            if ((args.length == 1) && (player.hasPermission("smoketrail.use.off"))) {
                if (this.modelist.containsKey(player.getName())) {
                    if (this.modelist.containsKey(player.getName())) {
                        this.modelist.remove(player.getName());
                        this.skulllist.remove(player.getName());
                        this.getConfig().set("Users." + player.getName(), null);
                        this.saveConfig();
                        player.sendMessage(trailutil.colorize(this.getConfig().getString("Messages.off")));
                    } else {
                        player.sendMessage("This player is not using a trail.");
                    }
                } else {
                    player.sendMessage("You need to have a trail on in order to turn it off.");

                }
            } else if ((args.length == 2) && (player.hasPermission("smoketrail.other.off"))) {
                Player player2 = Bukkit.getServer().getPlayer(args[1]);
                if (this.modelist.containsKey(player2.getName())) {
                    this.modelist.remove(player2.getName());
                    this.skulllist.remove(player2.getName());
                    player2.sendMessage(this.lol + "Your trail(s) is now off!");

                } else {
                    player.sendMessage("This player is not using a trail.");
                }
            } else {
                player.sendMessage(this.Red + "You don't have permission to do this");
            }

        } else if (args[0].equalsIgnoreCase("list")) {
            String result = "";
            String comma = ", ";
            for (String s : trails) {
                result += s;
                result += comma;
            }


            player.sendMessage(ChatColor.BLUE + result);
        } else if (args[0].equalsIgnoreCase("multi")) {
            if (player.hasPermission("smoketrail.multi")) {
                if (args.length > 1) {
                    if (this.modelist.containsKey(player.getName())) {
                        this.modelist.get(player.getName()).clear();
                    } else {
                        this.modelist.put(player.getName(), new ArrayList<String>());
                    }
                    String trailstoggled = "";
                    Boolean invalid = false;
                    if (this.getConfig().contains("Users." + player.getName())) {
                        this.getConfig().set("Users." + player.getName(), null);
                    } else {
                        this.getConfig().set("Users." + player.getName(), null);
                    }
                    for (int i = 0; i < args.length; i++) {
                        if (args[i].equalsIgnoreCase("multi")) {
                        } else if (Arrays.asList(trails).contains(args[i])) {
                            if (player.hasPermission("trails.use." + args[i])) {
                                this.modelist.get(player.getName()).add(args[i]);
                                this.getConfig().set("Users." + player, this.getConfig().get("Users." + player.getName()) + "," + args[i]);
                                trailstoggled += args[i] += " ";
                                this.saveConfig();
                            }
                        } else {
                            if (invalid = false) {
                                player.sendMessage(Red + "Invalid trail(s) found!");
                                invalid = true;
                            }
                        }
                    }
                    if (trailstoggled.length() > 1) {
                        player.sendMessage(ChatColor.GREEN + Multi + ChatColor.BLUE + "Trails enabled: " + trailstoggled);
                    }
                } else {
                    player.sendMessage(this.Red + "You need to supply trails to use.");
                }
            } else {
                player.sendMessage(this.Red + "You don't have permission to do this");
            }
        } else if (!Arrays.asList(trails).contains(args[0])) {
            player.sendMessage(ChatColor.RED + "That command is not recognized");
        }


    }

    //Remove every item spawned by the plugin
    public void removeallItems(ArrayList<Entity> ent) {
        Iterator<Entity> i = ent.iterator();
        while (i.hasNext()) {
            Entity next = i.next();
            next.remove();
        }



    }
    //Remove every item spawned by the player associated

    public void removeplayerItems(HashMap<Player, ArrayList<Entity>> ent, HashMap<Player, ArrayList<Entity>> ent1, HashMap<Player, ArrayList<Entity>> ent2, HashMap<Player, ArrayList<Entity>> ent3, Player player) {
        if (ent.containsKey(player)) {
            Iterator<Entity> i = ent.get(player).iterator();
            while (i.hasNext()) {
                Entity next = i.next();
                next.remove();
            }
        }
        if (ent1.containsKey(player)) {
            Iterator<Entity> i = ent1.get(player).iterator();
            while (i.hasNext()) {
                Entity next = i.next();
                next.remove();
            }
        }
        if (ent2.containsKey(player)) {
            Iterator<Entity> i = ent2.get(player).iterator();
            while (i.hasNext()) {
                Entity next = i.next();
                next.remove();
            }
        }
        if (ent3.containsKey(player)) {
            Iterator<Entity> i = ent3.get(player).iterator();
            while (i.hasNext()) {
                Entity next = i.next();
                next.remove();
            }
        }
    }

    //Reset all trails used by the player
    public void switchTrails(String player, String newtrail) {
        if (this.modelist.containsKey(player)) {
            if (this.modelist.get(player).contains(newtrail)) {
                this.modelist.get(player).remove(newtrail);
                this.reloadConfig();
                Bukkit.getPlayer(player).sendMessage(trailutil.colorize(this.getConfig().getString("Messages." + newtrail + ".disabled")));
                removeplayerItems(pl.diamonds, pl.flower, pl.stars, pl.skulls, Bukkit.getPlayer(player));
                
                
                this.getConfig().set("Users." + player, null);
                this.saveConfig();
            } else {
                this.modelist.get(player).clear();
                this.modelist.get(player).add(newtrail);
                this.reloadConfig();
                this.getConfig().set("Users." + player, null);
                this.getConfig().set("Users." + player, newtrail);
                Bukkit.getPlayer(player).sendMessage(trailutil.colorize(this.getConfig().getString("Messages." + newtrail + ".enabled")));
                removeplayerItems(pl.diamonds, pl.flower, pl.stars, pl.skulls, Bukkit.getPlayer(player));
                this.saveConfig();
            }
        } else {
            this.modelist.put(player, new ArrayList<String>());
            this.modelist.get(player).add(newtrail);
            Bukkit.getPlayer(player).sendMessage(trailutil.colorize(this.getConfig().getString("Messages." + newtrail + ".enabled")));
            this.reloadConfig();
            this.getConfig().set("Users." + player, newtrail);
            removeplayerItems(pl.diamonds, pl.flower, pl.stars, pl.skulls, Bukkit.getPlayer(player));
            this.saveConfig();
        }
    }

    public void removeSkull(String player, String newskull) {
        String p = this.getConfig().getString("Skulls." + player);
        String[] split = p.split(",");
        ArrayList<String> trailuser = new ArrayList<String>();
        trailuser.addAll(Arrays.asList(split));
        trailuser.remove(newskull);
        String skulls = null;
        for (String trailuser1 : trailuser) {
            skulls += trailuser1;
            skulls += ",";
        }
        this.getConfig().set("Skulls." + player, skulls);
        this.saveConfig();

    }

    public void addSkull(String player, String newskull) {
        this.reloadConfig();
        this.getConfig().set("Skulls." + player, this.getConfig().get("Skulls" + player) + "," + newskull);
        this.saveConfig();

    }
    /* public void reloadConfig() {
     if (this.getConfig()File == null) {
     this.getConfig()File = new File(getDataFolder(), "this.getConfig().yml");
     }
     this.getConfig() = YamlConfiguration.loadConfiguration(this.getConfig()File);
 
     // Look for defaults in the jar
     InputStream defConfigStream = this.getResource("this.getConfig().yml");
     if (defConfigStream != null) {
     YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
     this.getConfig().setDefaults(defConfig);
     saveDefaultConfig();
     }
     }
     public FileConfiguration getConfig() {
     if (this.getConfig() == null) {
     reloadConfig();
     }
     return this.getConfig();
     }
     public void saveConfig() {
     if (this.getConfig() == null || this.getConfig()File == null) {
     return;
     }
     try {
     getConfig().save(this.getConfig()File);
     } catch (IOException ex) {
     plugin.getLogger().log(Level.SEVERE, "Could not save this.getConfig() to " + this.getConfig()File, ex);
     }
     }
     public void reloadMessages() {
     if (messagesFile == null) {
     messagesFile = new File(getDataFolder(), "messages.yml");
     }
     messages = YamlConfiguration.loadConfiguration(messagesFile);
 
     // Look for defaults in the jar
     InputStream defConfigStream = this.getResource("messages.yml");
     if (defConfigStream != null) {
     YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
     messages.setDefaults(defConfig);
     saveDefaultMessages();
     }
     }
     public FileConfiguration getMessages() {
     if (messages == null) {
     reloadMessages();
     }
     return messages;
     }
     public void saveMessages() {
     if (messages == null || messagesFile == null) {
     return;
     }
     try {
     getMessages().save(messagesFile);
     } catch (IOException ex) {
     plugin.getLogger().log(Level.SEVERE, "Could not save this.getConfig() to " + messagesFile, ex);
     }
     }
     public void saveDefaultConfig() {
     if (this.getConfig()File == null) {
     this.getConfig()File = new File(getDataFolder(), "this.getConfig().yml");
     }
     if (!this.getConfig()File.exists()) {            
     this.saveResource("this.getConfig().yml", false);
     }
     }
     public void saveDefaultMessages() {
     if (messagesFile == null) {
     messagesFile = new File(getDataFolder(), "messages.yml");
     }
     if (!messagesFile.exists()) {            
     this.saveResource("messages.yml", false);
     }
     }*/
}
