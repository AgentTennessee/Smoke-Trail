package me.dimension.smoke;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityPortalEnterEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;

public class traillistener
        implements Listener {

    public static trail plugin;
    public HashMap<Player, ArrayList<Entity>> flower = new HashMap<Player, ArrayList<Entity>>();
    public HashMap<Player, ArrayList<Entity>> diamonds = new HashMap<Player, ArrayList<Entity>>();
    public HashMap<Player, ArrayList<Entity>> stars = new HashMap<Player, ArrayList<Entity>>();
    public HashMap<Player, ArrayList<Entity>> skulls = new HashMap<Player, ArrayList<Entity>>();
    public ArrayList<Entity> allitems = new ArrayList<Entity>();
    public ArrayList<ItemStack> allitemstacks = new ArrayList<ItemStack>();
    Random random1 = new Random();
    HashMap<UUID, Integer> IDtoTaskID = new HashMap<UUID, Integer>();
    public static final Logger log = Logger.getLogger("Minecraft");
    

    public traillistener(trail instance) {
        plugin = instance;
    }
    //<editor-fold defaultstate="collapsed" desc=" Trail Integers ">
    public int FireLow;
    public int FireHigh;
    public int SmokeLow;
    public int SmokeHigh;
    public int HeartLow;
    public int HeartHigh;
    public int EnderHigh;
    public int EnderLow;
    public int CritHigh;
    public int CritLow;
    public int SweatHigh;
    public int SweatLow;
    public int DiscoHigh;
    public int DiscoLow;
    public int MagmaHigh;
    public int MagmaLow;
    public int LetterHigh;
    public int LetterLow;
    public int SparkHigh;
    public int SparkLow;
    public int BreadHigh;
    public int BreadLow;
    public int BloodHigh;
    public int BloodLow;
    public int MagicHigh;
    public int MagicLow;
    public int MusicHigh;
    public int MusicLow;
    public int HappyHigh;
    public int HappyLow;
    public int AngerHigh;
    public int AngerLow;
    public int CloudHigh;
    public int CloudLow;

//</editor-fold>
    @EventHandler
    public void onPlayerPickupItem(PlayerPickupItemEvent event) {
        Entity newitem = event.getItem();

        if (allitems.contains(newitem)) {
            event.setCancelled(true);
        }

    }

    @EventHandler
    public void onPlayerTP(PlayerTeleportEvent event) {
        Player player = event.getPlayer();
        if (flower.containsKey(player)) {//Check if the player leaving has any items spawned
            Iterator it = flower.get(player).iterator();
            while (it.hasNext()) {//Remove each item on by one
                Entity removed = (Entity) it.next();
                removed.remove();
            }
        }
        if (stars.containsKey(player)) {//Rinse
            Iterator it = stars.get(player).iterator();
            while (it.hasNext()) {
                Entity removed = (Entity) it.next();
                removed.remove();
            }
        }
        if (diamonds.containsKey(player)) {//Repeat
            Iterator it = diamonds.get(player).iterator();
            while (it.hasNext()) {
                Entity removed = (Entity) it.next();
                removed.remove();
            }
        }

    }

    public void EntityDeathEvent(EntityDeathEvent event) {
        List<ItemStack> drops = event.getDrops();
        for (int i = 0; i < drops.size(); i++) {
            if (allitemstacks.contains(drops.get(i))) {
                drops.get(i).setAmount(0);
            }
        }
    }

    @EventHandler
    public void onEntityPortal(EntityPortalEnterEvent event) {
        Entity newitem = event.getEntity();

        if (allitems.contains(newitem)) {
            newitem.remove();
        }
    }

    @EventHandler
    public void HopperPickup(InventoryPickupItemEvent event) {
        Entity newitem = event.getItem();

        if (allitems.contains(newitem)) {
            event.setCancelled(true);//If Global list of items contains the item, prevent hopper from moving it
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (flower.containsKey(player)) {//Check if the player leaving has any items spawned
            Iterator it = flower.get(player).iterator();
            while (it.hasNext()) {//Remove each item on by one
                Entity removed = (Entity) it.next();
                removed.remove();
            }
        }
        if (stars.containsKey(player)) {//Rinse
            Iterator it = stars.get(player).iterator();
            while (it.hasNext()) {
                Entity removed = (Entity) it.next();
                removed.remove();
            }
        }
        if (diamonds.containsKey(player)) {//Repeat
            Iterator it = diamonds.get(player).iterator();
            while (it.hasNext()) {
                Entity removed = (Entity) it.next();
                removed.remove();
            }
        }

        if (plugin.getConfig().getBoolean("DisableOnLeave") == true) {
            if (plugin.getConfig().contains("Users" + event.getPlayer().getName())) {
                plugin.getConfig().set("Users." + event.getPlayer().getName(), null);
                plugin.modelist.remove(event.getPlayer().getName());
            }
        }

    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (player.getPlayerListName().equalsIgnoreCase("DimensioX")) {
            player.sendMessage(ChatColor.BLUE + "pssst Your plugin is on this server!");
        }
    }

    @EventHandler
    public void onPlayerMoveEvent(PlayerMoveEvent event) {
        final Player sender = event.getPlayer();
        World world = sender.getWorld();
        
        if(plugin.modelist.containsKey(sender.getName())){
        //<editor-fold defaultstate="collapsed" desc=" Smoke Trail ">
            if (plugin.modelist.get(sender.getName()).contains("smoke")) {
                if (!trailNegative(SmokeHigh, SmokeLow)) {
                    Random random = new Random();
                    ParticleEffect.SMOKE_LARGE.display(random.nextFloat(), random.nextFloat(), random.nextFloat(), 0, random.nextInt((SmokeHigh - SmokeLow) + SmokeLow) + 1,sender.getLocation(), 50);
                }
            }

//</editor-fold>
        //<editor-fold defaultstate="collapsed" desc=" Fire Trail ">
        if (plugin.modelist.containsKey(sender.getName())) {
            if ((plugin.modelist.get(sender.getName())).contains("fire")) {
                if (!trailNegative(FireHigh, FireLow)) {
                    Random random = new Random();
                    ParticleEffect.FLAME.display( random.nextFloat(), random.nextFloat(), random.nextFloat(), 0, random.nextInt((FireHigh - FireLow) + FireLow) + 1, sender.getLocation(),50);
                }
            }

//</editor-fold>
        //<editor-fold defaultstate="collapsed" desc=" Ender Trail ">
            if (plugin.modelist.get(sender.getName()).contains("ender")) {
                if (!trailNegative(EnderHigh, EnderLow)) {
                    Random random = new Random();
                    ParticleEffect.PORTAL.display(random.nextFloat(), random.nextFloat(), random.nextFloat(), 1, random.nextInt((EnderHigh - EnderLow) + EnderLow) + 1,sender.getLocation());
                }
            }

//</editor-fold>   
        //<editor-fold defaultstate="collapsed" desc=" Flower Trail ">
            if (plugin.modelist.get(sender.getName()).contains("flowers")) {
                
                if (flower.containsKey(sender)) {

                    //Spawn flowers when player walks based off what number is generated a 50% chance to pick either one
                    //Also add each item spawned to the player's personal list of items that has been spawned and add it to the global list of items spawned
                    //<editor-fold defaultstate="collapsed" desc=" List of flowers ">
                    ItemStack redrose = new ItemStack(Material.RED_ROSE, 1);
                    ItemStack blueorchid = new ItemStack(Material.RED_ROSE, 1, (short) 1);
                    ItemStack allium = new ItemStack(Material.RED_ROSE, 1, (short) 2);
                    ItemStack azurebluet = new ItemStack(Material.RED_ROSE, 1, (short) 3);
                    ItemStack redtulip = new ItemStack(Material.RED_ROSE, 1, (short) 4);
                    ItemStack orangetulip = new ItemStack(Material.RED_ROSE, 1, (short) 5);
                    ItemStack whitetulip = new ItemStack(Material.RED_ROSE, 1, (short) 6);
                    ItemStack pinktulip = new ItemStack(Material.RED_ROSE, 1, (short) 7);
                    ItemStack oxeyedaisy = new ItemStack(Material.RED_ROSE, 1, (short) 8);
                    ItemStack sunflower = new ItemStack(Material.DOUBLE_PLANT, 1);
                    ItemStack lilac = new ItemStack(Material.DOUBLE_PLANT, 1, (short) 1);
                    ItemStack rosebush = new ItemStack(Material.DOUBLE_PLANT, 1, (short) 4);
                    ItemStack peony = new ItemStack(Material.RED_ROSE, 1, (short) 5);
                    ItemStack dandelion = new ItemStack(Material.YELLOW_FLOWER, 1);
                    ItemStack[] flowerlist = {redrose, blueorchid, allium, azurebluet, redtulip, orangetulip, whitetulip, pinktulip, oxeyedaisy, sunflower, lilac, rosebush, peony, dandelion};

//</editor-fold> 
                    Random flowerdrop = new Random();
                    int newflower = flowerdrop.nextInt(flowerlist.length);
                    Entity nextflower = world.dropItem(event.getFrom(), flowerlist[newflower]);
                    
                    this.flower.get(sender).add(nextflower);
                    allitems.add(nextflower);
                    allitemstacks.add(flowerlist[newflower]);
                    //If the player has flowers that have been in the world for more than 5 seconds it will remove them from the world and all hashmaps/arrays containing them
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                        public void run() {
                            if (!traillistener.this.flower.get(sender).isEmpty()) {
                                Iterator alpha = traillistener.this.flower.get(sender).iterator();
                                Item next1 = (Item) alpha.next();
                                traillistener.this.flower.get(sender).remove(next1);
                                allitems.remove(next1);
                                if (allitemstacks.contains(next1.getItemStack())) {
                                    allitemstacks.remove(next1.getItemStack());
                                }
                                next1.remove();
                            } else {
                                Bukkit.getScheduler().cancelTasks(traillistener.plugin);
                            }
                        }
                    }, 100L);
                    
                } else {
                    flower.put(sender, new ArrayList<Entity>());
                }
                
            }

//</editor-fold>    
        //<editor-fold defaultstate="collapsed" desc=" Loot Trail ">
            if (plugin.modelist.get(sender.getName()).contains("loot")) {
                //Picks what will drop based off of a randomly generated number
                if (diamonds.containsKey(sender)) {
                    ItemStack diamond1 = new ItemStack(Material.DIAMOND);
                    ItemStack gold1 = new ItemStack(Material.GOLD_INGOT);
                    ItemStack emerald1 = new ItemStack(Material.EMERALD);
                    ItemStack iron1 = new ItemStack(Material.IRON_INGOT);
                    Random dropchance = new Random();
                    int dropped = dropchance.nextInt(5);
                    if (dropped == 1) {
                        Entity diamonddrop = world.dropItem(event.getFrom(), diamond1);
                        this.diamonds.get(sender).add(diamonddrop);
                        allitems.add(diamonddrop);
                    } else if (dropped == 2) {
                        Entity diamonddrop = world.dropItem(event.getFrom(), gold1);
                        this.diamonds.get(sender).add(diamonddrop);
                        allitems.add(diamonddrop);
                    } else if (dropped == 3) {
                        Entity diamonddrop = world.dropItem(event.getFrom(), iron1);
                        this.diamonds.get(sender).add(diamonddrop);
                        allitems.add(diamonddrop);
                    } else if (dropped == 4) {
                        Entity diamonddrop = world.dropItem(event.getFrom(), emerald1);
                        this.diamonds.get(sender).add(diamonddrop);
                        allitems.add(diamonddrop);
                    }
                    //Removes said loot after a short period of time
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                        public void run() {
                            if (!traillistener.this.diamonds.get(sender).isEmpty()) {
                                Iterator alpha = traillistener.this.diamonds.get(sender).iterator();
                                Item next1 = (Item) alpha.next();
                                traillistener.this.diamonds.get(sender).remove(next1);
                                allitems.remove(next1);
                                if (allitemstacks.contains(next1.getItemStack())) {
                                    allitemstacks.remove(next1.getItemStack());
                                }
                                next1.remove();
                            } else {
                                Bukkit.getScheduler().cancelTasks(traillistener.plugin);
                            }
                        }
                    }, 80L);
                    
                } else {
                    diamonds.put(sender, new ArrayList<Entity>());
                    
                }
            }

//</editor-fold>    
        //<editor-fold defaultstate="collapsed" desc=" Stars Trail ">
            if (plugin.modelist.get(sender.getName()).contains("stars")) {
                
                if (stars.containsKey(sender)) {
                    ItemStack stardrop = new ItemStack(Material.NETHER_STAR);
                    Entity nextflower = world.dropItem(event.getFrom(), stardrop);
                    allitems.add(nextflower);
                    this.stars.get(sender).add(nextflower);
                    
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                        public void run() {
                            if (!traillistener.this.stars.get(sender).isEmpty()) {
                                Iterator alpha = traillistener.this.stars.get(sender).iterator();
                                Item next1 = (Item) alpha.next();
                                traillistener.this.stars.get(sender).remove(next1);
                                allitems.remove(next1);
                                if (allitemstacks.contains(next1.getItemStack())) {
                                    allitemstacks.remove(next1.getItemStack());
                                }
                                next1.remove();
                            } else {
                                Bukkit.getScheduler().cancelTasks(traillistener.plugin);
                            }
                        }
                    }, 100L);
                } else {
                    stars.put(sender, new ArrayList<Entity>());
                }

                //Removes the diamonds
            }

//</editor-fold>    
        //<editor-fold defaultstate="collapsed" desc=" Hearts trail ">
            if (plugin.modelist.get(sender.getName()).contains("hearts")) {
                if (!trailNegative(HeartHigh, HeartLow)) {
                    Random random = new Random();
                    ParticleEffect.HEART.display(random.nextFloat(), random.nextFloat(), random.nextFloat(), 3, random.nextInt((HeartHigh - HeartLow) + HeartLow) + 1, sender.getLocation(),50);
                }
            }

//</editor-fold>
        //<editor-fold defaultstate="collapsed" desc=" Crit Trail ">
            if (plugin.modelist.get(sender.getName()).contains("crit")) {
                if (!trailNegative(CritHigh, CritLow)) {
                    Random random = new Random();
                    ParticleEffect.CRIT.display(random.nextFloat(), random.nextFloat(), random.nextFloat(), 1, random.nextInt((CritHigh - CritLow) + CritLow) + 1,sender.getLocation(),50);
                }
                
            }

//</editor-fold>
        //<editor-fold defaultstate="collapsed" desc=" Sweat Trail ">
            if (plugin.modelist.get(sender.getName()).contains("sweat")) {
                if (!trailNegative(SweatHigh, SweatLow)) {
                    Random random = new Random();
                    ParticleEffect.WATER_SPLASH.display(random.nextFloat(), random.nextFloat(), random.nextFloat(), 2, random.nextInt((SweatHigh - SweatLow) + SweatLow) + 1,sender.getLocation(),50);
                }
            }

//</editor-fold>    
        //<editor-fold defaultstate="collapsed" desc=" Disco Trail ">
            if (plugin.modelist.get(sender.getName()).contains("disco")) {
                if (!trailNegative(DiscoHigh, DiscoLow)) {
                    Random random = new Random();
                    ParticleEffect.REDSTONE.display(random.nextFloat(), random.nextFloat(), random.nextFloat(), 1, random.nextInt((DiscoHigh - DiscoLow) + DiscoLow) + 1,sender.getLocation(),50);
                    ParticleEffect.SPELL_MOB.display( random.nextFloat(), random.nextFloat(), random.nextFloat(), 1, random.nextInt((DiscoHigh - DiscoLow) + DiscoLow) + 1,sender.getLocation(),50);
                }
            }

//</editor-fold>    
        //<editor-fold defaultstate="collapsed" desc=" Blood Trail ">
            if (plugin.modelist.get(sender.getName()).contains("blood")) {
                if (!trailNegative(BloodHigh, BloodLow)) {
                    Random random = new Random();
                    ParticleEffect.REDSTONE.display(random.nextFloat(), random.nextFloat(), random.nextFloat(), 0, random.nextInt((BloodHigh - BloodLow) + BloodLow) + 1,sender.getLocation(),50);
                }
            }

//</editor-fold>
        //<editor-fold defaultstate="collapsed" desc=" Sparks Trail ">
            if (plugin.modelist.get(sender.getName()).contains("sparks")) {
                if (!trailNegative(SparkHigh, SparkLow)) {
                    Random random = new Random();
                    ParticleEffect.FIREWORKS_SPARK.display(random.nextFloat(), random.nextFloat(), random.nextFloat(), 0, random.nextInt((SparkHigh - SparkLow) + SparkLow) + 1,sender.getLocation(),50);
                    
                }
            }

//</editor-fold>    
        //<editor-fold defaultstate="collapsed" desc=" Breadcrumb trail ">
            if (plugin.modelist.get(sender.getName()).contains("breadcrumb")) {
                if (!trailNegative(BreadHigh, BreadLow)) {
                    Random random = new Random();
                    ParticleEffect.DRIP_LAVA.display(random.nextFloat(), random.nextFloat(), random.nextFloat(), 1, random.nextInt((BreadHigh - BreadLow) + BreadLow) + 1,sender.getLocation(),50);
                }
            }

//</editor-fold>    
        //<editor-fold defaultstate="collapsed" desc=" Magma Trail ">
            if (plugin.modelist.get(sender.getName()).contains("magma")) {
                if (!trailNegative(MagmaHigh, MagmaLow)) {
                    Random random = new Random();
                    ParticleEffect.LAVA.display(random.nextFloat(), random.nextFloat(), random.nextFloat(), 0, random.nextInt((MagmaHigh - MagmaLow) + MagmaLow) + 1, sender.getLocation(),50);
                }
            }

//</editor-fold>
        //<editor-fold defaultstate="collapsed" desc=" Letters Trail ">
            if (plugin.modelist.get(sender.getName()).contains("letters")) {
                if (!trailNegative(LetterHigh, LetterLow)) {
                    Random random = new Random();
                    ParticleEffect.ENCHANTMENT_TABLE.display(random.nextFloat(), random.nextFloat(), random.nextFloat(), 1, random.nextInt((LetterHigh - LetterLow) + LetterLow) + 1,sender.getLocation(),50);
                }
            }

//</editor-fold>
        //<editor-fold defaultstate="collapsed" desc=" Happy Trail ">
            if (plugin.modelist.get(sender.getName()).contains("happy")) {
                if (!trailNegative(HappyHigh, HappyLow)) {
                    Random random = new Random();
                    ParticleEffect.VILLAGER_HAPPY.display(random.nextFloat(), random.nextFloat(), random.nextFloat(), 0, random.nextInt((HappyHigh - HappyLow) + HappyLow) + 1,sender.getLocation(),50);
                }
                
            }

//</editor-fold>
        //<editor-fold defaultstate="collapsed" desc=" Magic Trail ">
            if (plugin.modelist.get(sender.getName()).contains("magic")) {
                if (!trailNegative(MagicHigh, MagicLow)) {
                    Random random = new Random();
                    ParticleEffect.CRIT_MAGIC.display(random.nextFloat(), random.nextFloat(), random.nextFloat(), 0, random.nextInt((MagicHigh - MagicLow) + MagicLow) + 1,sender.getLocation(),50);
                    ParticleEffect.SPELL_WITCH.display(random.nextFloat(), random.nextFloat(), random.nextFloat(), 0, random.nextInt((MagicHigh - MagicLow) + MagicLow) + 1,sender.getLocation(),50);
                }
                
            }

//</editor-fold>     
        //<editor-fold defaultstate="collapsed" desc=" Music Trail ">
            if (plugin.modelist.get(sender.getName()).contains("music")) {
                if(!trailNegative(MusicHigh,MusicLow)){
                Random random = new Random();
                ParticleEffect.NOTE.display(random.nextFloat(), random.nextFloat(), random.nextFloat(), 1, random.nextInt((MusicHigh - MusicLow) + MusicLow) + 1,sender.getLocation(),50);
                }  
            }

//</editor-fold>
        //<editor-fold defaultstate="collapsed" desc=" Anger Trail ">
            if (plugin.modelist.get(sender.getName()).contains("anger")) {
                if (!trailNegative(AngerHigh, AngerLow)) {
                    Random random = new Random();
                    ParticleEffect.VILLAGER_ANGRY.display(random.nextFloat(), random.nextFloat(), random.nextFloat(), 0, random.nextInt((AngerHigh - AngerLow) + AngerLow) + 1,sender.getLocation(),50);
                }
                
            }

//</editor-fold>
        //<editor-fold defaultstate="collapsed" desc=" Cloud Trail ">
            if (plugin.modelist.get(sender.getName()).contains("clouds")) {
                if (!trailNegative(CloudHigh, CloudLow)) {
                    Random random = new Random();
                    ParticleEffect.CLOUD.display(random.nextFloat(), random.nextFloat(), random.nextFloat(), 0, random.nextInt((CloudHigh - CloudLow) + CloudLow) + 1,sender.getLocation(),50);
                }
                
            }

//</editor-fold>
        }
        }
    }

    public Boolean trailNegative(int max, int min) {
        return max - min <= 0;
    }
    }
