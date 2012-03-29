
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
import org.bukkit.entity.Creeper;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Spider;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Wolf;
import org.bukkit.entity.Zombie;
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
				return false;
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
			else if (cmd.getName().equalsIgnoreCase("creeper"))
			{
				Location mloc1 = new Location(currentWorld, x + 1, y, z);
				Location mloc2 = new Location(currentWorld, x - 1, y, z);
				Location mloc3 = new Location(currentWorld, x, y, z + 1);
				Location mloc4 = new Location(currentWorld, x, y, z - 1);

				EntityType mob = EntityType.CREEPER;

				Creeper m1 = (Creeper) tplayer.getWorld().spawnCreature(mloc1, mob);
				Creeper m2 = (Creeper) tplayer.getWorld().spawnCreature(mloc2, mob);
				Creeper m3 = (Creeper) tplayer.getWorld().spawnCreature(mloc3, mob);
				Creeper m4 = (Creeper) tplayer.getWorld().spawnCreature(mloc4, mob);

				if (args.length < 2)
				{
					m1.setTarget(tplayer);
					m2.setTarget(tplayer);
					m3.setTarget(tplayer);
					m4.setTarget(tplayer);

					sender.sendMessage(ChatColor.RED + "You spawned creepers near " + args[0] + "!");
					return true;
				}
			}
			else if (cmd.getName().equalsIgnoreCase("annoy"))
			{
				Location Villager1 = new Location(currentWorld, x + 1, y, z);
				Location Villager2 = new Location(currentWorld, x - 1, y, z);
				Location Villager3 = new Location(currentWorld, x, y, z + 1);
				Location Villager4 = new Location(currentWorld, x, y, z - 1);
				Location Villager5 = new Location(currentWorld, x + 2, y, z);
				Location Villager6 = new Location(currentWorld, x - 2, y, z);
				Location Villager7 = new Location(currentWorld, x, y, z + 2);
				Location Villager8 = new Location(currentWorld, x, y, z - 2);

				Villager v1 = (Villager) tplayer.getWorld().spawnCreature(Villager1, EntityType.VILLAGER);
				Villager v2 = (Villager) tplayer.getWorld().spawnCreature(Villager2, EntityType.VILLAGER);
				Villager v3 = (Villager) tplayer.getWorld().spawnCreature(Villager3, EntityType.VILLAGER);
				Villager v4 = (Villager) tplayer.getWorld().spawnCreature(Villager4, EntityType.VILLAGER);
				Villager v5 = (Villager) tplayer.getWorld().spawnCreature(Villager5, EntityType.VILLAGER);
				Villager v6 = (Villager) tplayer.getWorld().spawnCreature(Villager6, EntityType.VILLAGER);
				Villager v7 = (Villager) tplayer.getWorld().spawnCreature(Villager7, EntityType.VILLAGER);
				Villager v8 = (Villager) tplayer.getWorld().spawnCreature(Villager8, EntityType.VILLAGER);

				if (args.length < 2)
				{
					v1.setTarget(tplayer);
					v2.setTarget(tplayer);
					v3.setTarget(tplayer);
					v4.setTarget(tplayer);
					v5.setTarget(tplayer);
					v6.setTarget(tplayer);
					v7.setTarget(tplayer);
					v8.setTarget(tplayer);

					sender.sendMessage(ChatColor.RED + "You annoyed " + args[0] + " with villagers!");
					return true;
				}
			}
			else if (cmd.getName().equalsIgnoreCase("rabid"))
			{
				Location Wolf1 = new Location(currentWorld, x + 5, y, z);
				Location Wolf2 = new Location(currentWorld, x + 5, y, z - 1);
				Location Wolf3 = new Location(currentWorld, x + 5, y, z + 1);

				Wolf w1 = (Wolf) player.getWorld().spawnCreature(Wolf1, EntityType.WOLF);
				Wolf w2 = (Wolf) player.getWorld().spawnCreature(Wolf2, EntityType.WOLF);
				Wolf w3 = (Wolf) player.getWorld().spawnCreature(Wolf3, EntityType.WOLF);

				if (args.length < 2)
				{
					w1.setAngry(true);
					w2.setAngry(true);
					w3.setAngry(true);
					w1.setTarget(tplayer);
					w2.setTarget(tplayer);
					w3.setTarget(tplayer);

					sender.sendMessage(ChatColor.RED + "You spawned rabid wolves near " + args[0] + "!");
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
			else if (cmd.getName().equalsIgnoreCase("spider"))
			{
				Location Spider1 = new Location(currentWorld, x + 2, y, z);
				Location Spider2 = new Location(currentWorld, x - 2, y, z);
				Location Spider3 = new Location(currentWorld, x, y, z + 2);
				Location Spider4 = new Location(currentWorld, x, y, z - 2);

				Spider s1 = (Spider) player.getWorld().spawnCreature(Spider1, EntityType.SPIDER);
				Spider s2 = (Spider) player.getWorld().spawnCreature(Spider2, EntityType.SPIDER);
				Spider s3 = (Spider) player.getWorld().spawnCreature(Spider3, EntityType.SPIDER);
				Spider s4 = (Spider) player.getWorld().spawnCreature(Spider4, EntityType.SPIDER);
				if (args.length < 2)
				{
					s1.setTarget(tplayer);
					s2.setTarget(tplayer);
					s3.setTarget(tplayer);
					s4.setTarget(tplayer);

					sender.sendMessage(ChatColor.RED + "You spawned spiders near " + args[0] + "!");
					return true;
				}
			}
			else if (cmd.getName().equalsIgnoreCase("skeleton"))
			{
				Location Skeleton1 = new Location(currentWorld, x + 2, y, z);
				Location Skeleton2 = new Location(currentWorld, x - 2, y, z);
				Location Skeleton3 = new Location(currentWorld, x, y, z + 2);
				Location Skeleton4 = new Location(currentWorld, x, y, z - 2);

				Skeleton s1 = (Skeleton) player.getWorld().spawnCreature(Skeleton1, EntityType.SKELETON);
				Skeleton s2 = (Skeleton) player.getWorld().spawnCreature(Skeleton2, EntityType.SKELETON);
				Skeleton s3 = (Skeleton) player.getWorld().spawnCreature(Skeleton3, EntityType.SKELETON);
				Skeleton s4 = (Skeleton) player.getWorld().spawnCreature(Skeleton4, EntityType.SKELETON);

				if (args.length < 2)
				{
					s1.setTarget(tplayer);
					s2.setTarget(tplayer);
					s3.setTarget(tplayer);
					s4.setTarget(tplayer);

					sender.sendMessage(ChatColor.RED + "You spawned skeletons near " + args[0] + "!");
					return true;
				}
			}
			else if (cmd.getName().equalsIgnoreCase("zombie"))
			{
				Location Zombie1 = new Location(currentWorld, x + 2, y, z);
				Location Zombie2 = new Location(currentWorld, x - 2, y, z);
				Location Zombie3 = new Location(currentWorld, x, y, z + 2);
				Location Zombie4 = new Location(currentWorld, x, y, z - 2);

				Zombie s1 = (Zombie) player.getWorld().spawnCreature(Zombie1, EntityType.ZOMBIE);
				Zombie s2 = (Zombie) player.getWorld().spawnCreature(Zombie2, EntityType.ZOMBIE);
				Zombie s3 = (Zombie) player.getWorld().spawnCreature(Zombie3, EntityType.ZOMBIE);
				Zombie s4 = (Zombie) player.getWorld().spawnCreature(Zombie4, EntityType.ZOMBIE);

				if (args.length < 2)
				{
					s1.setTarget(tplayer);
					s2.setTarget(tplayer);
					s3.setTarget(tplayer);
					s4.setTarget(tplayer);

					sender.sendMessage(ChatColor.RED + "You spawned zombies near " + args[0] + "!");
					return true;
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
		}
		return false;
	}
}
