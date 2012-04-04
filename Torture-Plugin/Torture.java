package me.IronCrystal.Torture;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class test extends JavaPlugin
{
    
    public final Logger logger = Logger.getLogger("Minecraft");
    
    public static test plugin;
    
    public static Permission permission = null;
    
    @Override
    public void onDisable()
    {
	
	PluginDescriptionFile pdfFile = this.getDescription();
	this.logger.info(pdfFile.getName() + " is now disabled!");
    }
    
    @Override
    public void onEnable()
    {
	
	PluginDescriptionFile pdfFile = this.getDescription();
	this.logger.info(pdfFile.getName() + " version " + pdfFile.getVersion() + " has been enabled.");
    }
    
    enum AllTortureTypes
    {
	// Main Tortures
	STARVE, HURT, IGNITE, DIE, PAIN, EXPLODE,
	// Potion Tortures
	POISON, SLOW, CONFUSE, BLIND, HUNGRY, ILL, TORTURE,
	// Complex Tortures
	FALL, SUFFOCATE, RAINOFFIRE, TSTOP,
	// Mobs Spawning
	CREEPER, ANNOY, RABID, ZOMBIE, SKELETON, SPIDER, AGGRO;
    }
    
    EntityType mob = EntityType.EGG;
    
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
	
	// Common variables.
	Player targetPlayer = Bukkit.getServer().getPlayer(args[1]);
	Player player = (sender instanceof Player) ? (Player) sender : null;
	String tortureType = args[0];
	
	// Torture commands
	if (!cmd.getName().equalsIgnoreCase("tt"))
	{
	    return true;
	}
	
	if (args.length == 0)
	{
	    sender.sendMessage(ChatColor.RED + "Type /tt help for help");
	    return true;
	}
	
	// Abort if the player has no permission
	if (player != null && (!player.hasPermission("torture." + tortureType) || !player.isOp()))
	{
	    player.sendMessage(ChatColor.RED + "You do not have permission to use this command");
	    return true;
	}
	
	// Help command doesn't have a target
	if (tortureType.equalsIgnoreCase("help"))
	{
	    int page = 1;
	    if (args.length > 1)
	    {
		page = Integer.parseInt(args[1]);
	    }
	    doHelp(page, sender);
	    return true;
	}
	
	// Check if command doesn't have enough arguments.
	if (args.length <= 1)
	{
	    sender.sendMessage(ChatColor.RED + "Please type player name (/tt " + tortureType + " <player>)");
	    return true;
	}
	
	// Check if targeted player is online.
	if (targetPlayer == null)
	{
	    sender.sendMessage(ChatColor.RED + args[1] + " is not online.");
	    return true;
	}
	
	String[] tortureArgs = Arrays.copyOfRange(args, 2, args.length);
	
	switch (AllTortureTypes.valueOf(tortureType))
	{
	
	// Main Tortures
	    case STARVE:
		doStarve(sender, targetPlayer, tortureArgs);
		return true;
	    case HURT:
		doHurt(sender, targetPlayer, tortureArgs);
		return true;
	    case IGNITE:
		doIgnite(sender, targetPlayer, tortureArgs);
		return true;
	    case DIE:
		doDie(sender, targetPlayer, tortureArgs);
		return true;
	    case PAIN:
		doPain(sender, targetPlayer, tortureArgs);
		return true;
	    case EXPLODE:
		doExplode(sender, targetPlayer, tortureArgs);
		return true;
		
		// Potion Tortures
	    case POISON:
		doPoison(sender, targetPlayer, tortureArgs);
		return true;
	    case SLOW:
		doSlow(sender, targetPlayer, tortureArgs);
		return true;
	    case CONFUSE:
		doConfuse(sender, targetPlayer, tortureArgs);
		return true;
	    case BLIND:
		doBlind(sender, targetPlayer, tortureArgs);
		return true;
	    case HUNGRY:
		doHungry(sender, targetPlayer, tortureArgs);
		return true;
	    case ILL:
		doDie(sender, targetPlayer, tortureArgs);
		return true;
	    case TORTURE:
		doTorture(sender, targetPlayer, tortureArgs);
		return true;
		
		// Complex Tortures
	    case FALL:
		doFall(sender, targetPlayer, tortureArgs);
		return true;
	    case SUFFOCATE:
		doSuffocate(sender, targetPlayer, tortureArgs);
		return true;
	    case RAINOFFIRE:
		doRainoffire(sender, targetPlayer, tortureArgs);
		return true;
	    case TSTOP:
		doTstop(sender, targetPlayer, tortureArgs);
		return true;
		
		// Mob Spawning
	    case CREEPER:
		mob = EntityType.CREEPER;
		doMobs(sender, targetPlayer, tortureArgs);
		return true;
	    case ANNOY:
		mob = EntityType.VILLAGER;
		doMobs(sender, targetPlayer, tortureArgs);
		return true;
	    case RABID:
		mob = EntityType.WOLF;
		doMobs(sender, targetPlayer, tortureArgs);
		return true;
	    case ZOMBIE:
		mob = EntityType.ZOMBIE;
		doMobs(sender, targetPlayer, tortureArgs);
		return true;
	    case SKELETON:
		mob = EntityType.SKELETON;
		doMobs(sender, targetPlayer, tortureArgs);
		return true;
	    case SPIDER:
		mob = EntityType.SPIDER;
		doMobs(sender, targetPlayer, tortureArgs);
		return true;
	    case AGGRO:
		doAggro(sender, targetPlayer, tortureArgs);
		return true;
	}
	sender.sendMessage(ChatColor.RED + "Unknown torture command: " + tortureType);
	return true;
    }
    
    // Main Tortures
    public void doStarve(CommandSender sender, Player targetPlayer, String[] tortureArgs)
    {
	
	int newFoodLevel = tortureArgs.length == 1 ? Integer.parseInt(tortureArgs[0]) : 1000;
	
	targetPlayer.setFoodLevel(targetPlayer.getFoodLevel() - newFoodLevel);
	
	sender.sendMessage(ChatColor.RED + "You starved " + targetPlayer.getDisplayName() + "!");
    }
    
    public void doHurt(CommandSender sender, Player targetPlayer, String[] tortureArgs)
    {
	
	int newHealth = tortureArgs.length == 1 ? Integer.parseInt(tortureArgs[0]) : 19;
	
	targetPlayer.setHealth(targetPlayer.getHealth() - newHealth);
	
	sender.sendMessage(ChatColor.RED + "You hurt " + targetPlayer.getDisplayName() + "!");
    }
    
    public void doIgnite(CommandSender sender, Player targetPlayer, String[] tortureArgs)
    {
	
	int time = tortureArgs.length == 1 ? Integer.parseInt(tortureArgs[0]) : 500;
	
	targetPlayer.setFireTicks(time);
	
	sender.sendMessage(ChatColor.RED + "You set " + targetPlayer.getDisplayName() + " on fire!");
    }
    
    public void doDie(CommandSender sender, Player targetPlayer, String[] tortureArgs)
    {
	
	targetPlayer.performCommand("kill");
	
	sender.sendMessage(ChatColor.RED + "You killed " + targetPlayer.getDisplayName() + "!");
    }
    
    public void doPain(CommandSender sender, Player targetPlayer, String[] tortureArgs)
    {
	
	targetPlayer.setFoodLevel(0);
	targetPlayer.setHealth(1);
	
	sender.sendMessage(ChatColor.RED + "You put " + targetPlayer.getDisplayName() + " in pain!");
    }
    
    public void doExplode(CommandSender sender, Player targetPlayer, String[] tortureArgs)
    {
	
	float explosionPower = tortureArgs.length <= 2 ? Integer.parseInt(tortureArgs[0]) : 0F;
	
	targetPlayer.getWorld().createExplosion(targetPlayer.getLocation(), explosionPower);
	targetPlayer.setHealth(0);
	
	sender.sendMessage(ChatColor.RED + "You Exploded " + targetPlayer.getDisplayName() + "!");
    }
    
    // Potion Tortures
    public void doPoison(CommandSender sender, Player targetPlayer, String[] tortureArgs)
    {
	
	int time = tortureArgs.length <= 2 ? Integer.parseInt(tortureArgs[0]) : 500;
	int strength = tortureArgs.length == 2 ? Integer.parseInt(tortureArgs[0]) : 0;
	
	targetPlayer.addPotionEffect(new PotionEffect(PotionEffectType.POISON, time, strength));
	
	sender.sendMessage(ChatColor.RED + "You poisoned " + targetPlayer.getDisplayName() + "!");
    }
    
    public void doSlow(CommandSender sender, Player targetPlayer, String[] tortureArgs)
    {
	
	int time = tortureArgs.length <= 2 ? Integer.parseInt(tortureArgs[0]) : 500;
	int strength = tortureArgs.length == 2 ? Integer.parseInt(tortureArgs[0]) : 0;
	
	targetPlayer.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, time, strength));
	targetPlayer.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, time, strength));
	
	sender.sendMessage(ChatColor.RED + "You slowed down " + targetPlayer.getDisplayName() + "!");
    }
    
    public void doConfuse(CommandSender sender, Player targetPlayer, String[] tortureArgs)
    {
	
	int time = tortureArgs.length <= 2 ? Integer.parseInt(tortureArgs[0]) : 500;
	int strength = tortureArgs.length == 2 ? Integer.parseInt(tortureArgs[0]) : 15;
	
	targetPlayer.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, time, strength));
	
	sender.sendMessage(ChatColor.RED + "You confused " + targetPlayer.getDisplayName() + "!");
    }
    
    public void doBlind(CommandSender sender, Player targetPlayer, String[] tortureArgs)
    {
	
	int time = tortureArgs.length <= 2 ? Integer.parseInt(tortureArgs[0]) : 500;
	int strength = tortureArgs.length == 2 ? Integer.parseInt(tortureArgs[0]) : 0;
	
	targetPlayer.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, time, strength));
	
	sender.sendMessage(ChatColor.RED + "You blinded " + targetPlayer.getDisplayName() + "!");
    }
    
    public void doHungry(CommandSender sender, Player targetPlayer, String[] tortureArgs)
    {
	
	int time = tortureArgs.length <= 2 ? Integer.parseInt(tortureArgs[0]) : 500;
	int strength = tortureArgs.length == 2 ? Integer.parseInt(tortureArgs[0]) : 0;
	
	targetPlayer.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, time, strength));
	
	sender.sendMessage(ChatColor.RED + "You made " + targetPlayer.getDisplayName() + "hungry!");
    }
    
    public void doIll(CommandSender sender, Player targetPlayer, String[] tortureArgs)
    {
	
	int time = tortureArgs.length <= 2 ? Integer.parseInt(tortureArgs[0]) : 500;
	int strength = tortureArgs.length == 2 ? Integer.parseInt(tortureArgs[0]) : 0;
	
	targetPlayer.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, time, strength));
	
	sender.sendMessage(ChatColor.RED + "You confused " + targetPlayer.getDisplayName() + "!");
    }
    
    public void doTorture(CommandSender sender, Player targetPlayer, String[] tortureArgs)
    {
	
	int time = tortureArgs.length <= 2 ? Integer.parseInt(tortureArgs[0]) : 500;
	int strength = tortureArgs.length == 2 ? Integer.parseInt(tortureArgs[0]) : 0;
	
	targetPlayer.addPotionEffect(new PotionEffect(PotionEffectType.POISON, time, strength));
	targetPlayer.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, time, strength));
	targetPlayer.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, time, strength));
	targetPlayer.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, time, strength));
	
	sender.sendMessage(ChatColor.RED + "You tortured " + targetPlayer.getDisplayName() + "!");
    }
    
    // Complex Tortures
    public void doFall(CommandSender sender, Player targetPlayer, String[] tortureArgs)
    {
	
	int distance = tortureArgs.length <= 2 ? Integer.parseInt(tortureArgs[0]) : 13;
	Location targetPlayerLocation = targetPlayer.getLocation();
	World currentTargetWorld = targetPlayer.getWorld();
	double y = targetPlayerLocation.getBlockY();
	double x = targetPlayerLocation.getBlockX();
	double z = targetPlayerLocation.getBlockZ();
	Location newTargetPlayerLocation = new Location(currentTargetWorld, x, y + distance, z);
	
	targetPlayer.teleport(newTargetPlayerLocation);
	targetPlayer.setHealth(0);
	
	sender.sendMessage(ChatColor.RED + "You Exploded " + targetPlayer.getDisplayName() + "!");
    }
    
    public void doSuffocate(CommandSender sender, Player targetPlayer, String[] tortureArgs)
    {
	
	sender.sendMessage(ChatColor.RED + "You suffocated " + targetPlayer.getDisplayName() + "!");
    }
    
    public void doRainoffire(CommandSender sender, Player targetPlayer, String[] tortureArgs)
    {
	
	final Random rg = new Random();
	Location targetPlayerLocation = targetPlayer.getLocation();
	World currentTargetWorld = targetPlayer.getWorld();
	double y = targetPlayerLocation.getBlockY();
	double x = targetPlayerLocation.getBlockX();
	double z = targetPlayerLocation.getBlockZ();
	Location loc = new Location(currentTargetWorld, x, y, z);
	loc.setY(loc.getY() + 15);
	loc.setX(loc.getX() + (rg.nextInt((2 * 5) + 1) - 5));
	loc.setZ(loc.getZ() + (rg.nextInt((2 * 5) + 1) - 5));
	loc.setPitch(-90);
	Vector vec = new Vector(0, -1, 0);
	Arrow arrow = targetPlayer.getWorld().spawnArrow(loc, vec, 0.6f, 12f);
	arrow.setFireTicks(500);
	sender.sendMessage(ChatColor.RED + "You rained fire down upon " + targetPlayer.getDisplayName() + "!");
    }
    
    public void doTstop(CommandSender sender, Player targetPlayer, String[] tortureArgs)
    {
	
	targetPlayer.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 0, 0), true);
	targetPlayer.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 0, 0), true);
	targetPlayer.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 0, 0), true);
	targetPlayer.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 0, 0), true);
	targetPlayer.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 0, 0), true);
	targetPlayer.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 0, 0), true);
	
	if (targetPlayer.getHealth() < 20)
	{
	    targetPlayer.setHealth(20);
	}
	else if (targetPlayer.getFoodLevel() < 20)
	{
	    targetPlayer.setFoodLevel(20);
	}
	
	sender.sendMessage(ChatColor.GREEN + "You cured " + targetPlayer.getDisplayName() + "!");
    }
    
    // Mob Spawning
    public void doMobs(CommandSender sender, Player targetPlayer, String[] tortureArgs)
    {
	
	Location targetPlayerLocation = targetPlayer.getLocation();
	World currentTargetWorld = targetPlayer.getWorld();
	double y = targetPlayerLocation.getBlockY();
	double x = targetPlayerLocation.getBlockX();
	double z = targetPlayerLocation.getBlockZ();
	
	Location mloc1 = new Location(currentTargetWorld, x + 3, y, z);
	Location mloc2 = new Location(currentTargetWorld, x - 3, y, z);
	Location mloc3 = new Location(currentTargetWorld, x, y, z + 3);
	Location mloc4 = new Location(currentTargetWorld, x, y, z - 3);
	
	Creature m1 = (Creature) targetPlayer.getWorld().spawnCreature(mloc1, mob);
	Creature m2 = (Creature) targetPlayer.getWorld().spawnCreature(mloc2, mob);
	Creature m3 = (Creature) targetPlayer.getWorld().spawnCreature(mloc3, mob);
	Creature m4 = (Creature) targetPlayer.getWorld().spawnCreature(mloc4, mob);
	
	((Wolf) m1).setAngry(true);
	((Wolf) m2).setAngry(true);
	((Wolf) m3).setAngry(true);
	((Wolf) m4).setAngry(true);
	
	m1.setTarget(targetPlayer);
	m2.setTarget(targetPlayer);
	m3.setTarget(targetPlayer);
	m4.setTarget(targetPlayer);
	
	sender.sendMessage(ChatColor.GREEN + "You " + mob + "'ed " + targetPlayer.getDisplayName() + "!");
    }
    
    public void doAggro(CommandSender sender, Player targetPlayer, String[] tortureArgs)
    {
	double distance = tortureArgs.length > 1 ? Integer.parseInt(tortureArgs[1]) : 20;
	final Random rg = new Random();
	Location targetPlayerLocation = targetPlayer.getLocation();
	World currentTargetWorld = targetPlayer.getWorld();
	double x = targetPlayerLocation.getBlockX();
	double y = targetPlayerLocation.getBlockY();
	double z = targetPlayerLocation.getBlockZ();
	Location loc = new Location(currentTargetWorld, x, y, z);
	
	loc.setX(loc.getX() + (rg.nextInt((2 * 10) + 1) - 10));
	loc.setY(loc.getY());
	loc.setZ(loc.getZ() + (rg.nextInt((2 * 10) + 1) - 10));
	
	List<Entity> list = targetPlayer.getNearbyEntities(distance, distance, distance);
	
	if (list.isEmpty())
	{
	    sender.sendMessage(ChatColor.RED + "There are no nearby entities");
	}
	else
	{
	    for (Entity ent : targetPlayer.getNearbyEntities(distance, distance, distance))
	    {
		if (ent instanceof Creature && !(ent instanceof Creeper))
		{
		    Creature mob = (Creature) ent;
		    mob.teleport(loc);
		    mob.setTarget(targetPlayer);
		}
	    }
	    sender.sendMessage(ChatColor.RED + "You turned all nearby mobs against " + targetPlayer.getDisplayName());
	}
    }
    
    // Random Torture
    public void doRandom(CommandSender sender, Player targetPlayer, String[] tortureArgs)
    {
	
	String tcmd = "torture";
	final Random rg = new Random();
	int command = rg.nextInt(24) + 1;
	switch (command)
	{
	// Main Tortures
	    case 1:
		tcmd = "starve";
	    case 2:
		tcmd = "hurt";
	    case 3:
		tcmd = "ignite";
	    case 4:
		tcmd = "die";
	    case 5:
		tcmd = "pain";
	    case 6:
		tcmd = "explode";
		
		// Potion Tortures
	    case 7:
		tcmd = "poison";
	    case 8:
		tcmd = "slow";
	    case 9:
		tcmd = "confuse";
	    case 10:
		tcmd = "blind";
	    case 11:
		tcmd = "hungry";
	    case 12:
		tcmd = "ill";
	    case 13:
		tcmd = "torture";
		
		// Complex Tortures
	    case 14:
		tcmd = "fall";
	    case 15:
		tcmd = "suffocate";
	    case 16:
		tcmd = "rainoffire";
		
		// Mob Spawning
	    case 17:
		tcmd = "creeper";
	    case 18:
		tcmd = "annoy";
	    case 19:
		tcmd = "rapid";
	    case 20:
		tcmd = "zombie";
	    case 21:
		tcmd = "skeleton";
	    case 22:
		tcmd = "spider";
	    case 23:
		tcmd = "aggro";
	}
	targetPlayer.performCommand("tt " + targetPlayer + " " + tcmd);
	sender.sendMessage(ChatColor.GREEN + "You set a random torture on " + targetPlayer.getDisplayName() + "!");
    }
    
    public void doHelp(int page, CommandSender sender)
    {
	
	switch (page)
	{
	    case 1:
		sender.sendMessage(ChatColor.GREEN + "/tt help -shows this page");
		sender.sendMessage(ChatColor.GREEN + "/tt starve <player> <amount left> -depletes player's hunger bar");
		sender.sendMessage(ChatColor.GREEN + "/tt hurt <player> <damage> -hurts the player");
		sender.sendMessage(ChatColor.GREEN + "/tt ignite <player> -ignites the player on fire, painful death");
		sender.sendMessage(ChatColor.GREEN + "/tt die <player> -kills the player");
		sender.sendMessage(ChatColor.GREEN + "");
		sender.sendMessage(ChatColor.GREEN + "Type /tt help 2 to see the next page.");
		break;
	    case 2:
		sender.sendMessage(ChatColor.GREEN + "/tt painful -leaves player with 1 health and empties hunger bar");
		sender.sendMessage(ChatColor.GREEN + "/tt blind <player> <time> -leaves the player blind");
		sender.sendMessage(ChatColor.GREEN + "/tt ill <player> <time> -makes them feel sick");
		sender.sendMessage(ChatColor.GREEN + "/tt hungry <player> <time> <1 - 4> -leaves the player terribly hungry");
		sender.sendMessage(ChatColor.GREEN + "/tt poison <player> <time> <1 - 4> -leaves the player poisoned");
		sender.sendMessage(ChatColor.GREEN + "");
		sender.sendMessage(ChatColor.GREEN + "Type /tt help 3 to see the next page.");
		break;
	    case 3:
		sender.sendMessage(ChatColor.GREEN + "/slow <player> <time> <1 - 4> -slows the player down");
		sender.sendMessage(ChatColor.GREEN + "/upsidedown <player> <time> -turns the player's world upside down!");
		sender.sendMessage(ChatColor.GREEN + "/fall <player> <distance> -makes the player fall from the sky");
		sender.sendMessage(ChatColor.GREEN + "/explode <player> -explodes the player, automatic death");
		sender.sendMessage(ChatColor.GREEN + "/creeper <player> -spawns creepers all around the player");
		sender.sendMessage(ChatColor.GREEN + "");
		sender.sendMessage(ChatColor.GREEN + "Type /tt help 4 to see the next page.");
		break;
	    case 4:
		sender.sendMessage(ChatColor.GREEN + "/annoy <player> -annoys the player with villagers");
		sender.sendMessage(ChatColor.GREEN + "/rabid <player> -sends rabid wolves after the player");
		sender.sendMessage(ChatColor.GREEN + "/suffocate <player> -suffocates the player in a tower of sand");
		sender.sendMessage(ChatColor.GREEN + "/rainoffire <player> <number> -Drops firey arrows over the player");
		sender.sendMessage(ChatColor.GREEN + "/tt <player> <time> <1-4> -terrible things will come to pass");
		sender.sendMessage(ChatColor.GREEN + "");
		sender.sendMessage(ChatColor.GREEN + "Type /tt help 5 to see the next page.");
		break;
	    case 5:
		sender.sendMessage(ChatColor.GREEN + "/tt spider <player> -spawns spiders all around the player");
		sender.sendMessage(ChatColor.GREEN + "/tt zombie <player> -spawns zombies all around the player");
		sender.sendMessage(ChatColor.GREEN + "/tt skeleton <player> -spawns skeletons all around the player");
		sender.sendMessage(ChatColor.GREEN + "/tt stop <player> -stops all effects currently on the player");
		/*
		 * sender.sendMessage(ChatColor.GREEN + "");
		 * sender.sendMessage(ChatColor.GREEN + "");
		 * sender.sendMessage(ChatColor.GREEN +
		 * "Type /help 6 to see the next page.");
		 */
		break;
	}
    }
}
