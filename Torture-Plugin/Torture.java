
package me.IronCrystal.Torture;

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
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class test2 extends JavaPlugin
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
		// To add a new /tt type, add it here, put a case block below, and write
		// the function.
		STARVE, HURT, IGNITE, DIE;
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

		// if (player == null) {
		// sender.sendMessage("Quit running from the console! You're going to screw up the backup again");
		// }

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

		String[] tortureArgs = { args[0], (args[1] + args[2] + args[3])};

		switch (AllTortureTypes.valueOf(tortureType))
		{
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
		}

		sender.sendMessage(ChatColor.RED + "Unknown torture command: " + tortureType);

		return true;
	}

	// Variable Defaults
	int time = 500;

	int strength = 0;

	public void doStarve(CommandSender sender, Player targetPlayer, String[] tortureArgs)
	{

		int newFoodLevel = 1000;

		if (tortureArgs.length == 1)
		{
			newFoodLevel = Integer.parseInt(tortureArgs[0]);
		}

		targetPlayer.setFoodLevel(targetPlayer.getFoodLevel() - newFoodLevel);

		sender.sendMessage(ChatColor.RED + "You starved " + targetPlayer.getDisplayName() + "!");
	}

	public void doHurt(CommandSender sender, Player targetPlayer, String[] tortureArgs)
	{

		int newHealth = 19;

		if (tortureArgs.length == 1)
		{
			newHealth = Integer.parseInt(tortureArgs[0]);
		}

		targetPlayer.setHealth(targetPlayer.getHealth() - newHealth);

		sender.sendMessage(ChatColor.RED + "You hurt " + targetPlayer.getDisplayName() + "!");
	}

	public void doIgnite(CommandSender sender, Player targetPlayer, String[] tortureArgs)
	{

		if (tortureArgs.length == 1)
		{
			time = Integer.parseInt(tortureArgs[0]);
		}

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

	public void doPoison(CommandSender sender, Player targetPlayer, String[] tortureArgs)
	{

		if (tortureArgs.length >= 2)
		{
			time = Integer.parseInt(tortureArgs[0]);
			if (tortureArgs.length == 2)
			{
				strength = Integer.parseInt(tortureArgs[1]);
			}
		}

		targetPlayer.addPotionEffect(new PotionEffect(PotionEffectType.POISON, time, strength));

		sender.sendMessage(ChatColor.RED + "You poisoned " + targetPlayer.getDisplayName() + "!");
	}

	public void doSlow(CommandSender sender, Player targetPlayer, String[] tortureArgs)
	{

		targetPlayer.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, time, strength));
		targetPlayer.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, time, strength));

		sender.sendMessage(ChatColor.RED + "You slowed down " + targetPlayer.getDisplayName() + "!");
	}

	public void doConfuse(CommandSender sender, Player targetPlayer, String[] tortureArgs)
	{

		targetPlayer.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, time, 15));

		sender.sendMessage(ChatColor.RED + "You confused " + targetPlayer.getDisplayName() + "!");
	}

	public void doBlind(CommandSender sender, Player targetPlayer, String[] tortureArgs)
	{

		targetPlayer.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, time, 0));

		sender.sendMessage(ChatColor.RED + "You blinded " + targetPlayer.getDisplayName() + "!");
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
