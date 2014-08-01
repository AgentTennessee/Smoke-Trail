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
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
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
import org.bukkit.inventory.meta.FireworkMeta;

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
        int FireLow = plugin.getConfig().getInt("TrailValues.FireLow");
        int FireHigh = plugin.getConfig().getInt("TrailValues.FireHigh");

        int SmokeLow = plugin.getConfig().getInt("TrailValues.SmokeLow");
        int SmokeHigh = plugin.getConfig().getInt("TrailValues.SmokeHigh");

        int HeartLow = plugin.getConfig().getInt("TrailValues.HeartLow");
        int HeartHigh = plugin.getConfig().getInt("TrailValues.HeartsHigh");

        int EnderHigh = plugin.getConfig().getInt("TrailValues.EnderHigh");
        int EnderLow = plugin.getConfig().getInt("TrailValues.EnderLow");

        int CritHigh = plugin.getConfig().getInt("TrailValues.CritHigh");
        int CritLow = plugin.getConfig().getInt("TrailValues.CritLow");

        int SweatHigh = plugin.getConfig().getInt("TrailValues.SweatHigh");
        int SweatLow = plugin.getConfig().getInt("TrailValues.SweatLow");

        int DiscoHigh = plugin.getConfig().getInt("TrailValues.DiscoHigh");
        int DiscoLow = plugin.getConfig().getInt("TrailValues.DiscoLow");

        int MagmaHigh = plugin.getConfig().getInt("TrailValues.MagmaHigh");
        int MagmaLow = plugin.getConfig().getInt("TrailValues.MagmaLow");

        int LetterHigh = plugin.getConfig().getInt("TrailValues.LetterHigh");
        int LetterLow = plugin.getConfig().getInt("TrailValues.LetterLow");

        int SparkHigh = plugin.getConfig().getInt("TrailValues.SparkHigh");
        int SparkLow = plugin.getConfig().getInt("TrailValues.SparkLow");

        int BreadHigh = plugin.getConfig().getInt("TrailValues.BreadHigh");
        int BreadLow = plugin.getConfig().getInt("TrailValues.BreadLow");

        int BloodHigh = plugin.getConfig().getInt("TrailValues.BloodHigh");
        int BloodLow = plugin.getConfig().getInt("TrailValues.BloodLow");

        int MagicHigh = plugin.getConfig().getInt("TrailValues.MagicHigh");
        int MagicLow = plugin.getConfig().getInt("TrailValues.MagicLow");

        int SnowHigh = plugin.getConfig().getInt("TrailValues.SnowHigh");
        int SnowLow = plugin.getConfig().getInt("TrailValues.SnowLow");

        int HappyHigh = plugin.getConfig().getInt("TrailValues.HappyHigh");
        int HappyLow = plugin.getConfig().getInt("TrailValues.HappyLow");
        if(plugin.modelist.containsKey(event.getPlayer().getName())){
        if ((plugin.modelist.get(event.getPlayer().getName())).contains("fire")) {
            if (!trailNegative(FireHigh, FireLow)) {
                Random random = new Random();
                ParticleEffect.FLAME.display(event.getPlayer().getLocation(), random.nextFloat(), random.nextFloat(), random.nextFloat(), 0, random.nextInt((FireHigh - FireLow) + FireLow) + 1);
            }
        }
        if (plugin.modelist.get(event.getPlayer().getName()).contains("disco")) {
            if (!trailNegative(DiscoHigh, DiscoLow)) {
                Random random = new Random();
                ParticleEffect.RED_DUST.display(event.getPlayer().getLocation(), random.nextFloat(), random.nextFloat(), random.nextFloat(), 1, random.nextInt((DiscoHigh - DiscoLow) + DiscoLow) + 1);
                ParticleEffect.MOB_SPELL.display(event.getPlayer().getLocation(), random.nextFloat(), random.nextFloat(), random.nextFloat(), 1, random.nextInt((DiscoHigh - DiscoLow) + DiscoLow) + 1);
            }
        }
        if (plugin.modelist.get(event.getPlayer().getName()).contains("music")) {

            Random random = new Random();
            ParticleEffect.NOTE.display(event.getPlayer().getLocation(), random.nextFloat(), random.nextFloat(), random.nextFloat(), 1, random.nextInt((10 - 4) + 4) + 1);

        }

        if (plugin.modelist.get(event.getPlayer().getName()).contains("ender")) {
            if (!trailNegative(EnderHigh, EnderLow)) {
                Random random = new Random();
                ParticleEffect.PORTAL.display(event.getPlayer().getLocation(), random.nextFloat(), random.nextFloat(), random.nextFloat(), 1, random.nextInt((EnderHigh - EnderLow) + EnderLow) + 1);
            }
        }
        if (plugin.modelist.get(event.getPlayer().getName()).contains("smoke")) {
            if (!trailNegative(SmokeHigh, SmokeLow)) {
                Random random = new Random();
                ParticleEffect.LARGE_SMOKE.display(event.getPlayer().getLocation(), random.nextFloat(), random.nextFloat(), random.nextFloat(), 0, random.nextInt((SmokeHigh - SmokeLow) + SmokeLow) + 1);
            }
        }
        if (plugin.modelist.get(event.getPlayer().getName()).contains("hearts")) {
            if (!trailNegative(HeartHigh, HeartLow)) {
                Random random = new Random();
                ParticleEffect.HEART.display(event.getPlayer().getLocation(), random.nextFloat(), random.nextFloat(), random.nextFloat(), 3, random.nextInt((HeartHigh - HeartLow) + HeartLow) + 1);
            }
        }

        if (plugin.modelist.get(event.getPlayer().getName()).contains("sweat")) {
            if (!trailNegative(SweatHigh, SweatLow)) {
                Random random = new Random();
                ParticleEffect.SPLASH.display(event.getPlayer().getLocation(), random.nextFloat(), random.nextFloat(), random.nextFloat(), 2, random.nextInt((SweatHigh - SweatLow) + SweatLow) + 1);
            }
        }
        if (plugin.modelist.get(event.getPlayer().getName()).contains("crit")) {
            if (!trailNegative(CritHigh, CritLow)) {
                Random random = new Random();
                ParticleEffect.CRIT.display(event.getPlayer().getLocation(), random.nextFloat(), random.nextFloat(), random.nextFloat(), 1, random.nextInt((CritHigh - CritLow) + CritLow) + 1);
            }

        }

        if (plugin.modelist.get(event.getPlayer().getName()).contains("flowers")) {

            if (flower.containsKey(sender)) {

                //Spawn flowers when player walks based off what number is generated a 50% chance to pick either one
                //Also add each item spawned to the player's personal list of items that has been spawned and add it to the global list of items spawned
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
        //Drops loot when the players moves
        if (plugin.modelist.get(event.getPlayer().getName()).contains("loot")) {
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
        if (plugin.modelist.get(event.getPlayer().getName()).contains("letters")) {
            if (!trailNegative(LetterHigh, LetterLow)) {
                Random random = new Random();
                ParticleEffect.ENCHANTMENT_TABLE.display(event.getPlayer().getLocation(), random.nextFloat(), random.nextFloat(), random.nextFloat(), 1, random.nextInt((LetterHigh - LetterLow) + LetterLow) + 1);
            }
        }
        if (plugin.modelist.get(event.getPlayer().getName()).contains("blood")) {
            if (!trailNegative(BloodHigh, BloodLow)) {
                Random random = new Random();
                ParticleEffect.RED_DUST.display(event.getPlayer().getLocation(), random.nextFloat(), random.nextFloat(), random.nextFloat(), 0, random.nextInt((BloodHigh - BloodLow) + BloodLow) + 1);
            }
        }
        if (plugin.modelist.get(event.getPlayer().getName()).contains("breadcrumbs")) {
            if (!trailNegative(BreadHigh, BreadLow)) {
                Random random = new Random();
                ParticleEffect.DRIP_LAVA.display(event.getPlayer().getLocation(), random.nextFloat(), random.nextFloat(), random.nextFloat(), 1, random.nextInt((BreadHigh - BreadLow) + BreadLow) + 1);
            }
        }
        if (plugin.modelist.get(event.getPlayer().getName()).contains("magma")) {
            if (!trailNegative(MagmaHigh, MagmaLow)) {
                Random random = new Random();
                ParticleEffect.LAVA.display(event.getPlayer().getLocation(), random.nextFloat(), random.nextFloat(), random.nextFloat(), 0, random.nextInt((MagmaHigh - MagmaLow) + MagmaLow) + 1);
            }
        }
        if (plugin.modelist.get(event.getPlayer().getName()).contains("magic")) {
            if (!trailNegative(MagicHigh, MagicLow)) {
                Random random = new Random();
                ParticleEffect.MAGIC_CRIT.display(event.getPlayer().getLocation(), random.nextFloat(), random.nextFloat(), random.nextFloat(), 0, random.nextInt((MagicHigh - MagicLow) + MagicLow) + 1);
                ParticleEffect.WITCH_MAGIC.display(event.getPlayer().getLocation(), random.nextFloat(), random.nextFloat(), random.nextFloat(), 0, random.nextInt((MagicHigh - MagicLow) + MagicLow) + 1);
            }

        }
        if (plugin.modelist.get(event.getPlayer().getName()).contains("happy")) {
            if (!trailNegative(HappyHigh, HappyLow)) {
                Random random = new Random();
                ParticleEffect.HAPPY_VILLAGER.display(event.getPlayer().getLocation(), random.nextFloat(), random.nextFloat(), random.nextFloat(), 0, random.nextInt((HappyHigh - HappyLow) + HappyLow) + 1);
            }

        }
        if ((plugin.modelist.get(event.getPlayer().getName())).contains("snow")) {
            if (!trailNegative(SnowHigh, SnowLow)) {
                Random random = new Random();
                ParticleEffect.displayBlockDust(event.getPlayer().getLocation(), 80, (byte) 0, random.nextFloat(), random.nextFloat(), random.nextFloat(), 0, random.nextInt((SnowHigh - SnowLow) + SnowLow) + 1);
                ParticleEffect.SNOW_SHOVEL.display(event.getPlayer().getLocation(), random.nextFloat(), random.nextFloat(), random.nextFloat(), 0, random.nextInt((SnowHigh - SnowLow) + SnowLow) + 1);
                ParticleEffect.SNOWBALL_POOF.display(event.getPlayer().getLocation(), random.nextFloat(), random.nextFloat(), random.nextFloat(), 0, random.nextInt((SnowHigh - SnowLow) + SnowLow) + 1);

            }

        }
        if (plugin.modelist.get(event.getPlayer().getName()).contains("sparks")) {
            if (!trailNegative(SparkHigh, SparkLow)) {
                Random random = new Random();
                ParticleEffect.FIREWORKS_SPARK.display(event.getPlayer().getLocation(), random.nextFloat(), random.nextFloat(), random.nextFloat(), 0, random.nextInt((SparkHigh - SparkLow) + SparkLow) + 1);

            }
        }
        /*if (plugin.modelist.get(event.getPlayer().getName()).contains("skulls")) {
         //Picks what will drop based off of a randomly generated number
         Player player = event.getPlayer();
         if (skulls.containsKey(sender)) {
         ItemStack witherskull = new ItemStack(Material.SKULL_ITEM, 1, (short) 1);
         ItemStack creeperskull = new ItemStack(Material.SKULL_ITEM, 1, (short) 4);
         ItemStack zombieskull = new ItemStack(Material.SKULL_ITEM, 1, (short) 2);
         ItemStack skeletonskull = new ItemStack(Material.SKULL_ITEM, 1);
         ItemStack steveskull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
         ArrayList<ItemStack> skulllist = new ArrayList<ItemStack>();

         if (plugin.skulllist.get(player.getPlayerListName()).contains("wither")) {
         skulllist.add(witherskull);
         }


         if (plugin.skulllist.get(player.getPlayerListName()).contains("creeper")) {
         skulllist.add(creeperskull);
         }


         if (plugin.skulllist.get(player.getPlayerListName()).contains("zombie")) {
         }
         skulllist.add(zombieskull);


         if (plugin.skulllist.get(player.getPlayerListName()).contains("skeleton")) {

         skulllist.add(skeletonskull);
         }


         if (plugin.skulllist.get(player.getPlayerListName()).contains("steve")) {

         skulllist.add(steveskull);
         }
         if(skulllist.size()>1){
         Random dropchance = new Random();
         int dropped = dropchance.nextInt((skulllist.size()));
         Entity diamonddrop = world.dropItem(event.getFrom(), skulllist.get(dropped));
         skulls.get(sender).add(diamonddrop);
         allitems.add(diamonddrop);
         }
         //Removes said loot after a short period of time
         Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
         public void run() {
         if (!skulls.get(sender).isEmpty()) {
         Iterator alpha = traillistener.this.skulls.get(sender).iterator();
         Entity next1 = (Entity) alpha.next();
         skulls.get(sender).remove(next1);
         allitems.remove(next1);
         next1.remove();
         } else {
         Bukkit.getScheduler().cancelTasks(traillistener.plugin);
         }
         }
         }, 80L);

         } else {
         skulls.put(sender, new ArrayList<Entity>());

         }
         }
         */
        //Drops nether stars when the player walks
        if (plugin.modelist.get(event.getPlayer().getName()).contains("stars")) {

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

        if (plugin.modelist.get(event.getPlayer().getName()).contains("fireworks")) {
            Firework fw = (Firework) sender.getWorld().spawnEntity(sender.getLocation(), EntityType.FIREWORK);
            FireworkMeta fwm = fw.getFireworkMeta();

            //Our random generator
            Random r = new Random();

            //Get the type
            int rt = r.nextInt(4) + 1;
            Type type = Type.BALL;
            if (rt == 1) {
                type = Type.BALL;
            }
            if (rt == 2) {
                type = Type.BALL_LARGE;
            }
            if (rt == 3) {
                type = Type.BURST;
            }
            if (rt == 4) {
                type = Type.CREEPER;
            }
            if (rt == 5) {
                type = Type.STAR;
            }

            //Get our random colours 
            int r1i = r.nextInt(17) + 1;
            int r2i = r.nextInt(17) + 1;
            Color c1 = getColor(r1i);
            Color c2 = getColor(r2i);

            //Create our effect with this
            FireworkEffect effect = FireworkEffect.builder().flicker(r.nextBoolean()).withColor(c1).withFade(c2).with(type).trail(r.nextBoolean()).build();

            //Then apply the effect to the meta
            fwm.addEffect(effect);

            //Generate some random power and set it
            int rp = 1;
            fwm.setPower(rp);

            //Then apply this to our rocket
            fw.setFireworkMeta(fwm);
            fw.detonate();
        }
        }
    }

    public Boolean trailNegative(int max, int min) {
        return max - min <= 0;
    }

    private Color getColor(int i) {
        Color c = null;
        if (i == 1) {
            c = Color.AQUA;
        }
        if (i == 2) {
            c = Color.BLACK;
        }
        if (i == 3) {
            c = Color.BLUE;
        }
        if (i == 4) {
            c = Color.FUCHSIA;
        }
        if (i == 5) {
            c = Color.GRAY;
        }
        if (i == 6) {
            c = Color.GREEN;
        }
        if (i == 7) {
            c = Color.LIME;
        }
        if (i == 8) {
            c = Color.MAROON;
        }
        if (i == 9) {
            c = Color.NAVY;
        }
        if (i == 10) {
            c = Color.OLIVE;
        }
        if (i == 11) {
            c = Color.ORANGE;
        }
        if (i == 12) {
            c = Color.PURPLE;
        }
        if (i == 13) {
            c = Color.RED;
        }
        if (i == 14) {
            c = Color.SILVER;
        }
        if (i == 15) {
            c = Color.TEAL;
        }
        if (i == 16) {
            c = Color.WHITE;
        }
        if (i == 17) {
            c = Color.YELLOW;
        }

        return c;
    }
}
