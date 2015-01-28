package me.dimension.smoke;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
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
//Done Trails:
//Smoke,Fire,Ender,Flower,Loot,Stars,Hearts,Crit,Sweat,Disco,Blood.Sparks,Breadcrumb,Magma,Letters,Happy
//Magic,Music,Anger,Cloud
    public String[] trails = {"smoke", "fire", "ender", "flowers", "loot", "stars", "hearts", "crit", "sweat", 
        "disco", "blood", "sparks", "breadcrumb", "magma", "letters", "happy", "magic", "music", "anger","clouds","multi"};
    public ChatColor Red = ChatColor.RED;
    public ChatColor Blue = ChatColor.BLUE;
    public String on = "is now on";
    public String off = "is now off";
    public final HashMap<String, ArrayList<String>> modelist = new HashMap();
    public final traillistener pl = new traillistener(this);
    public final trailutil util = new trailutil(this);

    @Override
    public void onEnable() {
        trail.log.log(Level.INFO, "{0} is now enabled!", lol);
        setupCommands();
        PluginManager pm = getServer().getPluginManager();
        this.saveResource("config.yml", false);
        this.getConfig();
        this.reloadConfig();
        loadValues();
        pm.registerEvents(pl, this);
        fixConfig();
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
    }

    //Make commands work
    public void setupCommands() {
        PluginCommand trail = getCommand("trail");
        CommandExecutor commandExecutor = new CommandExecutor() {
            @Override
            public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
                if ((sender instanceof Player) && (args.length > 0)) {
                    trail.this.commandHandler((Player) sender, args);
                } else if ((sender instanceof Player) && (args.length == 0)) {
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
        if (Arrays.asList(trails).contains(args[0]) && !args[0].equalsIgnoreCase("multi")) {
            if ((args.length == 1) && (player.hasPermission("smoketrail.use." + args[0]))) {

                switchTrails(player.getName(), args[0]);

            } else if ((args.length == 2) && (player.hasPermission("smoketrail.other." + args[0]))) {
                Player player2 = Bukkit.getServer().getPlayer(args[1]);
                if(player2 == null){
                    player.sendMessage(ChatColor.RED + args[0] + " is not a real person");
                } else {
                    switchTrails(player2.getName(), args[0]);
                    player.sendMessage(ChatColor.GREEN + "Trail toggled for " + player2.getName());
                }

            } else {
                player.sendMessage(this.Red + "You don't have permission to do this");
            }
        } else if (args[0].equalsIgnoreCase("off")) {
            if ((args.length == 1) && (player.hasPermission("smoketrail.use.off"))) {
                if (this.modelist.containsKey(player.getName())) {
                    if (this.modelist.containsKey(player.getName())) {
                        this.modelist.remove(player.getName());
                        this.getConfig().set("Users." + player.getName(), null);
                        this.saveConfig();
                        player.sendMessage(trailutil.colorize(this.getConfig().getString("Messages.DisableMessage")));
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
                    player2.sendMessage(this.lol + "Your trail(s) is now off!");

                } else {
                    player.sendMessage("This player is not using a trail.");
                }
            } else {
                player.sendMessage(this.Red + "You don't have permission to do this");
            }

        } else if (args[0].equalsIgnoreCase("reload")) {
            if (player.hasPermission("smoketrail.reload")) {
                this.reloadConfig();
                loadValues();
            } else {
                player.sendMessage(ChatColor.RED + "You don't have permission to use this command");
            }
        } else if (args[0].equalsIgnoreCase("list")) {
            String result = "";
            String comma = ", ";
            for (String s : trails) {
                if (s.equalsIgnoreCase("multi")) {
                    if (player.hasPermission("smoketrail.multi")) {
                        result += ChatColor.BLUE + s;
                    } else {
                        result += ChatColor.RED + s;
                    }
                } else {
                    if (player.hasPermission("smoketrail.use." + s)) {
                        result += ChatColor.BLUE + s;
                    } else {
                        result += ChatColor.RED + s;
                    }

                }
                result += ChatColor.WHITE + comma;
            }

            player.sendMessage(result);

        } else if (args[0].equalsIgnoreCase("multi")) {
            if (player.hasPermission("smoketrail.multi")) {
                if (args.length > 1) {
                    if (this.modelist.containsKey(player.getName())) {
                        this.modelist.get(player.getName()).clear();
                    } else {
                        this.modelist.put(player.getName(), new ArrayList<String>());
                    }
                    List<String> trailstoggled = new ArrayList<String>();

                    
                    if (this.getConfig().contains("Users." + player.getName())) {
                        this.getConfig().set("Users." + player.getName(), null);
                    } else {
                        this.getConfig().set("Users." + player.getName(), null);
                    }
                    for (int i = 1; i < args.length; i++) {
                        
                        if (Arrays.asList(trails).contains(args[i])) {
                            if (player.hasPermission("trails.use." + args[i])) {
                                this.modelist.get(player.getName()).add(args[i]);
                                this.getConfig().set("Users." + player, this.getConfig().get("Users." + player.getName()) + "," + args[i]);
                                trailstoggled.add(args[i]);
                                this.saveConfig();

                            }
                        } else {
                            
                                player.sendMessage(Red + "Error: " + args[i] + " is not a trail!"  );
                               
                            
                        }
                    }
                    if (trailstoggled.size() >= 1) {
                        player.sendMessage(ChatColor.GREEN + Multi + ChatColor.BLUE + "Trails enabled: " + trailstoggled.toString());
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


    public void loadValues() {
        pl.FireLow = getConfig().getInt("TrailValues.FireLow");
        pl.FireHigh = getConfig().getInt("TrailValues.FireHigh");

        pl.SmokeLow = getConfig().getInt("TrailValues.SmokeLow");
        pl.SmokeHigh = getConfig().getInt("TrailValues.SmokeHigh");

        pl.HeartLow = getConfig().getInt("TrailValues.HeartLow");
        pl.HeartHigh = getConfig().getInt("TrailValues.HeartsHigh");

        pl.EnderHigh = getConfig().getInt("TrailValues.EnderHigh");
        pl.EnderLow = getConfig().getInt("TrailValues.EnderLow");

        pl.CritHigh = getConfig().getInt("TrailValues.CritHigh");
        pl.CritLow = getConfig().getInt("TrailValues.CritLow");

        pl.SweatHigh = getConfig().getInt("TrailValues.SweatHigh");
        pl.SweatLow = getConfig().getInt("TrailValues.SweatLow");

        pl.DiscoHigh = getConfig().getInt("TrailValues.DiscoHigh");
        pl.DiscoLow = getConfig().getInt("TrailValues.DiscoLow");

        pl.MagmaHigh = getConfig().getInt("TrailValues.MagmaHigh");
        pl.MagmaLow = getConfig().getInt("TrailValues.MagmaLow");

        pl.LetterHigh = getConfig().getInt("TrailValues.LetterHigh");
        pl.LetterLow = getConfig().getInt("TrailValues.LetterLow");

        pl.SparkHigh = getConfig().getInt("TrailValues.SparkHigh");
        pl.SparkLow = getConfig().getInt("TrailValues.SparkLow");

        pl.BreadHigh = getConfig().getInt("TrailValues.BreadHigh");
        pl.BreadLow = getConfig().getInt("TrailValues.BreadLow");

        pl.BloodHigh = getConfig().getInt("TrailValues.BloodHigh");
        pl.BloodLow = getConfig().getInt("TrailValues.BloodLow");

        pl.MagicHigh = getConfig().getInt("TrailValues.MagicHigh");
        pl.MagicLow = getConfig().getInt("TrailValues.MagicLow");

        pl.HappyHigh = getConfig().getInt("TrailValues.HappyHigh");
        pl.HappyLow = getConfig().getInt("TrailValues.HappyLow");
        
        pl.AngerHigh = getConfig().getInt("TrailValues.HappyHigh");
        pl.AngerLow = getConfig().getInt("TrailValues.HappyLow");
        
        pl.CloudHigh = getConfig().getInt("TrailValues.HappyHigh");
        pl.CloudLow = getConfig().getInt("TrailValues.HappyLow");
        
        pl.MusicHigh = getConfig().getInt("TrailValues.MusicHigh");
        pl.MusicLow = getConfig().getInt("TrailValues.MusicLow");

    }
   public void fixConfig(){
        //Nothing here for newest update because I want people to refresh their config file.
   }
}
