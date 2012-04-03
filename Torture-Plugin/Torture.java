
package me.IronCrystal.Torture;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Random;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class test2 extends JavaPlugin
{

	// Variable Defaults
	int newFoodLevel = 1000;

	int newHealth = 19;

	int time = 500;

	int strength = 0;

	float explosionPower = 0F;

	Player targetPlayer = Bukkit.getServer().getPlayer(args[1]);

	Location targetPlayerLocation = Bukkit.getServer().getPlayer(args[1]).getLocation();

	double y = targetPlayerLocation.getBlockY();

	double x = targetPlayerLocation.getBlockX();

	double z = targetPlayerLocation.getBlockZ();

	World currentTargetWorld = targetPlayer.getWorld();

	int distance = Integer.parseInt(args[1]);

	Location NewTargetPlayerLocation = new Location(currentTargetWorld, x, y + distance, z);

	EntityType mob = EntityType.EGG;

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
		// To add a new /tt type, add it here, put a case block below, and write
		// the function.
		// Main Tortures
		STARVE, HURT, IGNITE, DIE, PAIN, FALL, EXPLODE,
		// Potion Tortures
		POISON, SLOW, CONFUSE, BLIND, HUNGRY, ILL, TORTURE,
		// Mobs Spawning
		CREEPER, ANNOY, RABID, ZOMBIE, SKELETON;
	}

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
		case FALL:
			doFall(sender, targetPlayer, tortureArgs);
			return true;

			// Potion Tortures
		case EXPLODE:
			doExplode(sender, targetPlayer, tortureArgs);
			return true;
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

		}

		sender.sendMessage(ChatColor.RED + "Unknown torture command: " + tortureType);

		return true;
	}

	// Main Tortures
	public void doStarve(CommandSender sender, Player targetPlayer, String[] tortureArgs)
	{

		newFoodLevel = tortureArgs.length == 1 ? Integer.parseInt(tortureArgs[0]) : 1000;

		targetPlayer.setFoodLevel(targetPlayer.getFoodLevel() - newFoodLevel);

		sender.sendMessage(ChatColor.RED + "You starved " + targetPlayer.getDisplayName() + "!");
	}

	public void doHurt(CommandSender sender, Player targetPlayer, String[] tortureArgs)
	{

		newHealth = tortureArgs.length == 1 ? Integer.parseInt(tortureArgs[0]) : 19;

		targetPlayer.setHealth(targetPlayer.getHealth() - newHealth);

		sender.sendMessage(ChatColor.RED + "You hurt " + targetPlayer.getDisplayName() + "!");
	}

	public void doIgnite(CommandSender sender, Player targetPlayer, String[] tortureArgs)
	{

		time = tortureArgs.length == 1 ? Integer.parseInt(tortureArgs[0]) : 500;

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

	public void doFall(CommandSender sender, Player targetPlayer, String[] tortureArgs)
	{

		explosionPower = tortureArgs.length <= 2 ? Integer.parseInt(tortureArgs[0]) : 0F;

		targetPlayer.teleport(NewTargetPlayerLocation);
		targetPlayer.setHealth(0);

		sender.sendMessage(ChatColor.RED + "You Exploded " + targetPlayer.getDisplayName() + "!");
	}

	public void doExplode(CommandSender sender, Player targetPlayer, String[] tortureArgs)
	{

		explosionPower = tortureArgs.length <= 2 ? Integer.parseInt(tortureArgs[0]) : 0F;

		targetPlayer.getWorld().createExplosion(targetPlayer.getLocation(), explosionPower);
		targetPlayer.setHealth(0);

		sender.sendMessage(ChatColor.RED + "You Exploded " + targetPlayer.getDisplayName() + "!");
	}

	// Potion Tortures

	public void doPoison(CommandSender sender, Player targetPlayer, String[] tortureArgs)
	{

		time = tortureArgs.length <= 2 ? Integer.parseInt(tortureArgs[0]) : 500;
		strength = tortureArgs.length == 2 ? Integer.parseInt(tortureArgs[0]) : 0;

		targetPlayer.addPotionEffect(new PotionEffect(PotionEffectType.POISON, time, strength));

		sender.sendMessage(ChatColor.RED + "You poisoned " + targetPlayer.getDisplayName() + "!");
	}

	public void doSlow(CommandSender sender, Player targetPlayer, String[] tortureArgs)
	{

		time = tortureArgs.length <= 2 ? Integer.parseInt(tortureArgs[0]) : 500;
		strength = tortureArgs.length == 2 ? Integer.parseInt(tortureArgs[0]) : 0;

		targetPlayer.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, time, strength));
		targetPlayer.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, time, strength));

		sender.sendMessage(ChatColor.RED + "You slowed down " + targetPlayer.getDisplayName() + "!");
	}

	public void doConfuse(CommandSender sender, Player targetPlayer, String[] tortureArgs)
	{

		time = tortureArgs.length <= 2 ? Integer.parseInt(tortureArgs[0]) : 500;
		strength = tortureArgs.length == 2 ? Integer.parseInt(tortureArgs[0]) : 0;

		targetPlayer.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, time, 15));

		sender.sendMessage(ChatColor.RED + "You confused " + targetPlayer.getDisplayName() + "!");
	}

	public void doBlind(CommandSender sender, Player targetPlayer, String[] tortureArgs)
	{

		time = tortureArgs.length <= 2 ? Integer.parseInt(tortureArgs[0]) : 500;
		strength = tortureArgs.length == 2 ? Integer.parseInt(tortureArgs[0]) : 0;

		targetPlayer.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, time, 0));

		sender.sendMessage(ChatColor.RED + "You blinded " + targetPlayer.getDisplayName() + "!");
	}

	public void doHungry(CommandSender sender, Player targetPlayer, String[] tortureArgs)
	{

		time = tortureArgs.length <= 2 ? Integer.parseInt(tortureArgs[0]) : 500;
		strength = tortureArgs.length == 2 ? Integer.parseInt(tortureArgs[0]) : 0;

		targetPlayer.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, time, strength));

		sender.sendMessage(ChatColor.RED + "You made " + targetPlayer.getDisplayName() + "hungry!");
	}

	public void doIll(CommandSender sender, Player targetPlayer, String[] tortureArgs)
	{

		time = tortureArgs.length <= 2 ? Integer.parseInt(tortureArgs[0]) : 500;
		strength = tortureArgs.length == 2 ? Integer.parseInt(tortureArgs[0]) : 0;

		targetPlayer.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, time, strength));

		sender.sendMessage(ChatColor.RED + "You confused " + targetPlayer.getDisplayName() + "!");
	}

	public void doTorture(CommandSender sender, Player targetPlayer, String[] tortureArgs)
	{

		time = tortureArgs.length <= 2 ? Integer.parseInt(tortureArgs[0]) : 500;
		strength = tortureArgs.length == 2 ? Integer.parseInt(tortureArgs[0]) : 0;

		targetPlayer.addPotionEffect(new PotionEffect(PotionEffectType.POISON, time, strength));
		targetPlayer.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, time, strength));
		targetPlayer.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, time, strength));
		targetPlayer.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, time, strength));

		sender.sendMessage(ChatColor.RED + "You tortured " + targetPlayer.getDisplayName() + "!");
	}

	// Mob Spawning

	public void doMobs(CommandSender sender, Player targetPlayer, String[] tortureArgs)
	{

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
	}

	// next is to add: SUFFOCATE, RAINOFFIRE, TSTOP, and the newer features like
	// RANDOM

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
