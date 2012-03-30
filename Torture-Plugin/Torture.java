
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

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{

		Player player = null;
		if (sender instanceof Player)
		{
			player = (Player) sender;
		}
		if (cmd.getName().equalsIgnoreCase(cmd.getName()))
		{
			if (player != null && !player.hasPermission("torture." + cmd.getName()))
			{
				player.sendMessage(ChatColor.RED + "You do not have the proper permissions to use this command");
				return true;
			}
			else if (cmd.getName().equalsIgnoreCase("thelp"))
			{
				if (args.length == 0)
				{
					sender.sendMessage(ChatColor.GREEN + "/thelp -shows this page");
					sender.sendMessage(ChatColor.GREEN + "/starve <player> <amount left> -depletes player's hunger bar");
					sender.sendMessage(ChatColor.GREEN + "/hurt <player> <damage> -hurts the player");
					sender.sendMessage(ChatColor.GREEN + "/ignite <player> -ignites the player on fire, painful death");
					sender.sendMessage(ChatColor.GREEN + "/die <player> -kills the player");
					sender.sendMessage(ChatColor.GREEN + "");
					sender.sendMessage(ChatColor.GREEN + "Type /thelp 2 to see the next page.");
					return true;
				}
				else if (args.length == 1)
				{
					int page = Integer.parseInt(args[0]);
					if (page == 2)
					{
						sender.sendMessage(ChatColor.GREEN + "/painful -leaves player with 1 health and empties hunger bar");
						sender.sendMessage(ChatColor.GREEN + "/blind <player> <time> -leaves the player blind");
						sender.sendMessage(ChatColor.GREEN + "/ill <player> <time> -makes them feel sick");
						sender.sendMessage(ChatColor.GREEN + "/hungry <player> <time> <1 - 4> -leaves the player terribly hungry");
						sender.sendMessage(ChatColor.GREEN + "/poison <player> <time> <1 - 4> -leaves the player poisoned");
						sender.sendMessage(ChatColor.GREEN + "");
						sender.sendMessage(ChatColor.GREEN + "Type /thelp 3 to see the next page.");
						return true;
					}
					else if (page == 3)
					{
						sender.sendMessage(ChatColor.GREEN + "/slow <player> <time> <1 - 4> -slows the player down");
						sender.sendMessage(ChatColor.GREEN + "/upsidedown <player> <time> -turns the player's world upside down!");
						sender.sendMessage(ChatColor.GREEN + "/fall <player> <distance> -makes the player fall from the sky");
						sender.sendMessage(ChatColor.GREEN + "/explode <player> -explodes the player, automatic death");
						sender.sendMessage(ChatColor.GREEN + "/creeper <player> -spawns creepers all around the player");
						sender.sendMessage(ChatColor.GREEN + "");
						sender.sendMessage(ChatColor.GREEN + "Type /help 4 to see the next page.");
					}
					else if (page == 4)
					{
						sender.sendMessage(ChatColor.GREEN + "/annoy <player> -annoys the player with villagers");
						sender.sendMessage(ChatColor.GREEN + "/rabid <player> -sends rabid wolves after the player");
						sender.sendMessage(ChatColor.GREEN + "/suffocate <player> -suffocates the player in a tower of sand");
						sender.sendMessage(ChatColor.GREEN + "/rainoffire <player> <number> -Drops firey arrows over the player");
						sender.sendMessage(ChatColor.GREEN + "/torture <player> <time> <1-4> -terrible things will come to pass");
						sender.sendMessage(ChatColor.GREEN + "");
						sender.sendMessage(ChatColor.GREEN + "Type /help 5 to see the next page.");
					}
					else if (page == 5)
					{
						sender.sendMessage(ChatColor.GREEN + "/spider <player> -spawns spiders all around the player");
						sender.sendMessage(ChatColor.GREEN + "/zombie <player> -spawns zombies all around the player");
						sender.sendMessage(ChatColor.GREEN + "/skeleton <player> -spawns skeletons all around the player");
						sender.sendMessage(ChatColor.GREEN + "/tstop <player> -stops all effects currently on the player");
						/*
						 * sender.sendMessage(ChatColor.GREEN + "");
						 * sender.sendMessage(ChatColor.GREEN + "");
						 * sender.sendMessage(ChatColor.GREEN +
						 * "Type /help 6 to see the next page.");
						 */
					}
					return true;
				}
			}
			Player tplayer = player;
			int time = 500;
			int strength = 0;
			int strength2 = 0;
			if (args.length > 0 && args.length < 4)
			{
				tplayer = Bukkit.getServer().getPlayer(args[0]);
				if (args.length == 2)
				{
					time = Integer.parseInt(args[1]) * 20;
				}
				if (args.length == 3)
				{
					strength = Integer.parseInt(args[2]) - 1;
					strength2 = Integer.parseInt(args[2]);
				}
			}
			Location playerLocation = tplayer.getLocation();
			World currentWorld = tplayer.getWorld();
			double y = playerLocation.getBlockY();
			double x = playerLocation.getBlockX();
			double z = playerLocation.getBlockZ();
			float explosionPower = 0F;
			int distance = Integer.parseInt(args[1]);
			Location NewPlayerLocation = new Location(currentWorld, x, y + 13, z);
			Location NewPlayerLocation1 = new Location(currentWorld, x, y + distance, z);
			/*
			 * Location targetPlayerLocation = targetPlayer.getLocation();8?
			 * World currentTargetWorld = targetPlayer.getWorld(); double ty =
			 * targetPlayerLocation.getBlockY(); double tx =
			 * targetPlayerLocation.getBlockX(); double tz =
			 * targetPlayerLocation.getBlockZ(); Location
			 * NewTargetPlayerLocation = new Location(currentTargetWorld, x, y +
			 * 13, z); Location NewTargetPlayerLocation1 = new
			 * Location(currentTargetWorld, x, y + distance, z);
			 */
			if (player == null && args.length == 0)
			{
				sender.sendMessage(ChatColor.RED + "Please type player name (/" + cmd.getName() + " <player>)");
				return true;
			}
			else if (Bukkit.getServer().getPlayer(args[0]) == null)
			{
				sender.sendMessage(ChatColor.RED + args[0] + " is not online.");
				return true;
			}
			else if (cmd.getName().equalsIgnoreCase("starve"))
			{
				if (args.length < 2)
				{
					tplayer.setFoodLevel(0);
					sender.sendMessage(ChatColor.RED + "You starved " + args[0] + "!");
					return true;
				}
				else if (args.length == 2)
				{
					tplayer.setFoodLevel(Integer.parseInt(args[1]));
					sender.sendMessage(ChatColor.RED + "You starved " + args[0] + "!");
					return true;
				}
			}
			else if (cmd.getName().equalsIgnoreCase("hurt"))
			{
				if (args.length < 2)
				{
					tplayer.setHealth(1);
					sender.sendMessage(ChatColor.RED + "You hurt " + args[0] + "!");
					return true;
				}
				else if (args.length == 2)
				{
					tplayer.setHealth(tplayer.getHealth() - Integer.parseInt(args[1]));
					sender.sendMessage(ChatColor.RED + "You hurt " + args[0] + "!");
					return true;
				}
			}
			else if (cmd.getName().equalsIgnoreCase("ignite"))
			{
				if (args.length < 2)
				{
					tplayer.setFireTicks(10000);
					sender.sendMessage(ChatColor.RED + "You set " + args[0] + " on fire!");
					return true;
				}
			}
			else if (cmd.getName().equalsIgnoreCase("die"))
			{
				if (args.length < 2)
				{
					tplayer.setHealth(0);
					sender.sendMessage(ChatColor.RED + "You killed " + args[0] + "!");
					return true;
				}
			}
			else if (cmd.getName().equalsIgnoreCase("painful"))
			{
				if (args.length < 2)
				{
					tplayer.setFoodLevel(0);
					tplayer.setHealth(1);
					sender.sendMessage(ChatColor.RED + "You put " + args[0] + " in pain!");
					return true;
				}
			}
			else if (cmd.getName().equalsIgnoreCase("poison"))
			{
				if (args.length < 4)
				{
					tplayer.addPotionEffect(new PotionEffect(PotionEffectType.POISON, time, strength));
					sender.sendMessage(ChatColor.RED + "You poisoned " + args[0] + ".");
					return true;
				}
			}
			else if (cmd.getName().equalsIgnoreCase("slow"))
			{
				if (args.length < 4)
				{
					tplayer.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, time, strength2));
					tplayer.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, time, strength2));
					sender.sendMessage(ChatColor.RED + "You slowed " + args[0] + " down.");
					return true;
				}
			}
			else if (cmd.getName().equalsIgnoreCase("upsidedown"))
			{
				if (args.length < 2)
				{
					tplayer.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, time, 15));
					sender.sendMessage(ChatColor.RED + "You turned " + args[0] + "'s world upside down!");
					return true;
				}
			}
			else if (cmd.getName().equalsIgnoreCase("blind"))
			{
				if (args.length < 3)
				{
					tplayer.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, time, 0));
					sender.sendMessage(ChatColor.RED + "You made " + args[0] + " blind.");
					return true;
				}
			}
			else if (cmd.getName().equalsIgnoreCase("hungry"))
			{
				if (args.length < 4)
				{
					tplayer.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, time, strength));
					sender.sendMessage(ChatColor.RED + "You made " + args[0] + " hungry.");
					return true;
				}
			}
			else if (cmd.getName().equalsIgnoreCase("ill"))
			{
				if (args.length < 3)
				{
					tplayer.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, time, 0));
					sender.sendMessage(ChatColor.RED + "You made " + args[0] + " ill.");
					return true;
				}
			}
			else if (cmd.getName().equalsIgnoreCase("torture"))
			{
				if (args.length < 4)
				{
					tplayer.addPotionEffect(new PotionEffect(PotionEffectType.POISON, time, strength));
					tplayer.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, time, strength));
					tplayer.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, time, strength));
					tplayer.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, time, strength));
					sender.sendMessage(ChatColor.RED + "You tortured " + args[0] + ".");
					return true;
				}
			}
			else if (cmd.getName().equalsIgnoreCase("explode"))
			{
				if (args.length < 2)
				{
					tplayer.getWorld().createExplosion(tplayer.getLocation(), explosionPower);
					tplayer.setHealth(0);
					sender.sendMessage(ChatColor.RED + "You exploded " + args[0] + ".");
					return true;
				}
			}
			else if (cmd.getName().equalsIgnoreCase("fall"))
			{
				if (args.length < 2)
				{
					tplayer.teleport(NewPlayerLocation);
					sender.sendMessage(ChatColor.RED + "You made " + args[0] + " fall!");
					return true;
				}
				else if (args.length == 2)
				{
					tplayer.teleport(NewPlayerLocation1);
					sender.sendMessage(ChatColor.RED + "You made " + args[0] + " fall!");
					return true;
				}
			}
			else if (cmd.getName().equalsIgnoreCase("suffocate"))
			{
				int count1 = 0;
				int count2 = 0;
				int count3 = 0;
				int count4 = 0;
				int count5 = 0;
				int count6 = 0;
				int count7 = 0;
				int count8 = 0;
				int count9 = 0;

				if (args.length < 2)
				{
					y = y - 1;
					x = x + 1;
					while (count2 <= 2)
					{
						y++;
						Location Sand = new Location(currentWorld, x, y, z);
						Sand.getBlock().setType(Material.DIRT);
						count2++;
					}
					y = y - count2;
					x = x - 2;
					while (count3 <= 2)
					{
						y++;
						Location Sand = new Location(currentWorld, x, y, z);
						Sand.getBlock().setType(Material.DIRT);
						count3++;
					}
					y = y - count3;
					x = x + 1;
					z = z + 1;
					while (count4 <= 2)
					{
						y++;
						Location Sand = new Location(currentWorld, x, y, z);
						Sand.getBlock().setType(Material.DIRT);
						count4++;
					}
					y = y - count4;
					z = z - 2;
					while (count5 <= 2)
					{
						y++;
						Location Sand = new Location(currentWorld, x, y, z);
						Sand.getBlock().setType(Material.DIRT);
						count5++;
					}
					y = y - count5;
					x = x + 1;
					while (count6 <= 2)
					{
						y++;
						Location Sand = new Location(currentWorld, x, y, z);
						Sand.getBlock().setType(Material.DIRT);
						count6++;
					}
					y = y - count6;
					x = x - 2;
					while (count7 <= 2)
					{
						y++;
						Location Sand = new Location(currentWorld, x, y, z);
						Sand.getBlock().setType(Material.DIRT);
						count7++;
					}
					y = y - count7;
					z = z + 2;
					while (count8 <= 2)
					{
						y++;
						Location Sand = new Location(currentWorld, x, y, z);
						Sand.getBlock().setType(Material.DIRT);
						count8++;
					}
					y = y - count8;
					x = x + 2;
					while (count9 <= 2)
					{
						y++;
						Location Sand = new Location(currentWorld, x, y, z);
						Sand.getBlock().setType(Material.DIRT);
						count9++;
					}
					x = x - 1;
					z = z - 1;
					y = y - count9 + 3;
					while (count1 <= 10)
					{
						y++;
						Location Sand = new Location(currentWorld, x, y, z);
						Sand.getBlock().setType(Material.SAND);
						count1++;
					}
					sender.sendMessage(ChatColor.RED + "You suffocated " + args[0] + "!");
					return true;
				}
			}
			else if (cmd.getName().equalsIgnoreCase("rainoffire"))
			{
				final Random rg = new Random();
				if (args.length < 2)
				{
					Location loc = new Location(currentWorld, x, y, z);
					loc.setY(loc.getY() + 15);
					loc.setX(loc.getX() + (rg.nextInt((2 * 5) + 1) - 5));
					loc.setZ(loc.getZ() + (rg.nextInt((2 * 5) + 1) - 5));
					loc.setPitch(-90);
					Vector vec = new Vector(0, -1, 0);
					Arrow arrow = tplayer.getWorld().spawnArrow(loc, vec, 0.6f, 12f);
					arrow.setFireTicks(500);
					sender.sendMessage(ChatColor.RED + "You rained fire down upon " + args[0]);
				}
				else if (args.length == 2)
				{
					int number = Integer.parseInt(args[1]);
					if (number > 121)
					{
						sender.sendMessage(ChatColor.RED + "Too many arrows!");
						sender.sendMessage(ChatColor.RED + "You can not use more than 121 arrows!");
						return true;
					}
					for (int i = 0; i < number; i++)
					{
						Location loc = new Location(currentWorld, x, y, z);
						loc.setY(loc.getY() + 15);
						loc.setX(loc.getX() + (rg.nextInt((2 * 5) + 1) - 5));
						loc.setZ(loc.getZ() + (rg.nextInt((2 * 5) + 1) - 5));
						loc.setPitch(-90);
						Vector vec = new Vector(0, -1, 0);
						Arrow arrow = tplayer.getWorld().spawnArrow(loc, vec, 0.6f, 12f);
						arrow.setFireTicks(500);
					}
					sender.sendMessage(ChatColor.RED + "You rained fire down upon " + args[0]);
				}
			}
			else if (cmd.getName().equalsIgnoreCase("tstop"))
			{
				if (args.length < 2)
				{
					if (tplayer.hasPotionEffect(PotionEffectType.POISON) == true)
					{
						tplayer.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 0, 0), true);
					}
					else if (tplayer.hasPotionEffect(PotionEffectType.BLINDNESS))
					{
						tplayer.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 0, 0), true);
					}
					else if (tplayer.hasPotionEffect(PotionEffectType.SLOW))
					{
						tplayer.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 0, 0), true);
					}
					else if (tplayer.hasPotionEffect(PotionEffectType.HUNGER))
					{
						tplayer.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 0, 0), true);
					}
					else if (tplayer.hasPotionEffect(PotionEffectType.CONFUSION))
					{
						tplayer.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 0, 0), true);
					}
					else if (tplayer.hasPotionEffect(PotionEffectType.SLOW_DIGGING))
					{
						tplayer.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 0, 0), true);
					}
					tplayer.setHealth(20);
					tplayer.setFoodLevel(20);
					return true;
				}
			}
			else if ((cmd.getName().equalsIgnoreCase("creeper")) || (cmd.getName().equalsIgnoreCase("villager")) || (cmd.getName().equalsIgnoreCase("wolf")) || (cmd.getName().equalsIgnoreCase("spider")) || (cmd.getName().equalsIgnoreCase("skeleton")) || (cmd.getName().equalsIgnoreCase("zombie")))
			{
				EntityType mob = EntityType.LIGHTNING;

				if (cmd.getName().equalsIgnoreCase("creeper"))
				{
					mob = EntityType.CREEPER;
				}
				else if (cmd.getName().equalsIgnoreCase("villager"))
				{
					mob = EntityType.VILLAGER;
				}

				else if (cmd.getName().equalsIgnoreCase("wolf"))
				{
					mob = EntityType.WOLF;
				}

				else if (cmd.getName().equalsIgnoreCase("spider"))
				{
					mob = EntityType.SPIDER;
				}

				else if (cmd.getName().equalsIgnoreCase("skeleton"))
				{
					mob = EntityType.SKELETON;
				}

				else if (cmd.getName().equalsIgnoreCase("zombie"))
				{
					mob = EntityType.ZOMBIE;
				}
				if (args.length < 2)
				{
					Location mloc1 = new Location(currentWorld, x + 3, y, z);
					Location mloc2 = new Location(currentWorld, x - 3, y, z);
					Location mloc3 = new Location(currentWorld, x, y, z + 3);
					Location mloc4 = new Location(currentWorld, x, y, z - 3);

					Creature m1 = (Creature) tplayer.getWorld().spawnCreature(mloc1, mob);
					Creature m2 = (Creature) tplayer.getWorld().spawnCreature(mloc2, mob);
					Creature m3 = (Creature) tplayer.getWorld().spawnCreature(mloc3, mob);
					Creature m4 = (Creature) tplayer.getWorld().spawnCreature(mloc4, mob);

					m1.setTarget(tplayer);
					m2.setTarget(tplayer);
					m3.setTarget(tplayer);
					m4.setTarget(tplayer);

					sender.sendMessage(ChatColor.RED + "You spawned " + mob.getName() + " near " + args[0] + "!");
					return true;
				}
			}
		}
		return false;
	}
}
