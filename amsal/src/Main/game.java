package Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.WorldBorder;
import org.bukkit.block.Block;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import net.md_5.bungee.api.ChatColor;

public class game
{
	public static ArrayList<Player> GamePlayer = new ArrayList<Player>();
	public static ArrayList<Player> LivingPlayer = new ArrayList<Player>();
	public static boolean isOnGame = false;
	public static HashMap<Player, Integer> Target1 = new HashMap<Player, Integer>();
	public static HashMap<Integer, Player> Target2 = new HashMap<Integer, Player>();
	public static HashMap<Player, Integer> kill = new HashMap<Player, Integer>();
	public static int WorldborderSize = 400;
	
	public static void addPlayer(Player p)
	{
		if(isOnGame)
		{
			p.sendMessage(main.label + "게임중에는 참가할 수 없습니다");
			return;
		}
		
		if(GamePlayer.contains(p))
		{
			p.sendMessage(main.label + "당신은 이미 게임에 참가했습니다");
		}
		else
		{
			GamePlayer.add(p);
			Bukkit.getServer().broadcastMessage(main.label + p.getName() + "님이 게임에 참가했습니다");
		}
	}
	
	public static void removePlayer(Player p)
	{
		if(isOnGame)
		{
			p.sendMessage(main.label + "게임중에는 나갈 수 없습니다");
			return;
		}
		
		if(GamePlayer.contains(p))
		{
			GamePlayer.remove(p);
			Bukkit.getServer().broadcastMessage(main.label + p.getName() + "님이 게임을 나갔습니다");
		}
		else
		{
			p.sendMessage(main.label + "당신은 이미 게임을 나갔습니다");
		}
	}
	
	public static void startGame(Player p)
	{
		if(GamePlayer.size() >= 1 && commands.center != null)
		{
			isOnGame = true;
			p.getWorld().playSound(commands.center, Sound.ENTITY_ENDER_DRAGON_GROWL, 1, 1);
			LivingPlayer = GamePlayer;
			targetController();
			
			setTarget();
			
			for(Player player : GamePlayer)
			{
				player.teleport(commands.center);
				player.sendTitle("Game Start", "", 10, 20, 10);
				player.getInventory().clear();
				
				Inventory inv = player.getInventory();
				inv.setItem(0, Items.getbow());
				inv.setItem(7, Items.getshop());
				
				player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 45*20, 7), true);
				player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 45*20, 1), false);
				
				new BukkitRunnable() 
				{
					@Override
					public void run() 
					{
						inv.setItem(9, Items.getarrow());
						inv.setItem(8, Items.getcompass());
						inv.setItem(6, Items.gettoken(4));
						player.sendMessage(main.label + "나침반이 지급되었습니다");
						
						if(!isOnGame)
						{
							this.cancel();
						}
					}
				}.runTaskLater(Bukkit.getServer().getPluginManager().getPlugin("amsal"), 45*20);
				
				player.setGameMode(GameMode.SURVIVAL);
			}
			
			Location centerLoc = commands.center;
			
			WorldBorder wb = p.getWorld().getWorldBorder();
			wb.setCenter(centerLoc);
			wb.setSize(WorldborderSize);
			wb.setSize(0, 25*20L);
			wb.setDamageAmount(4);
		}
		else
		{
			p.sendMessage(main.label + "인원이 부족하거나 자기장 중앙이 설정되지 않았습니다");
		}
	}
	
	public static void setTarget()
	{
		ArrayList<Integer> num = new ArrayList<Integer>();
		
		while(GamePlayer.size() != num.size())
		{
			Random random = new Random();
			int r = random.nextInt(GamePlayer.size());
			
			if(!num.contains(r))
			{
				num.add(r);
			}
		}
		
		for(int i = 0; i<=(GamePlayer.size() - 1); i++)
		{
			Target1.put(GamePlayer.get(i), num.get(i));
			Target2.put(num.get(i), GamePlayer.get(i));
		}
	}
	
	public static boolean canDamaged(Player p1, Player p2)
	{
		if(game.Target1.get(p1) + 1 == game.Target1.get(p2) || (game.Target1.get(p1) == game.LivingPlayer.size() - 1 && game.Target1.get(p2) == 0))
		{
			return true;
		}
		
		if(game.Target1.get(p1) - 1 == game.Target1.get(p2) || (game.Target1.get(p1) == 0 && game.Target1.get(p2) == game.LivingPlayer.size() - 1))
		{
			return true;
		}
		
		return false;
	}
	
	public static void removeTarget(int num)
	{
		Target1.remove(Target2.get(num));
		Target2.remove(num);
		
		HashMap<Player, Integer> Target1_clone = new HashMap<Player, Integer>();
		HashMap<Integer, Player> Target2_clone = new HashMap<Integer, Player>();
		
		for(int i=0; i <= Target2.size(); i++)
		{
			if(Target2.containsKey(i))
			{
				if(i < num)
				{
					Target1_clone.put(Target2.get(i), i);
					Target2_clone.put(i, Target2.get(i));
				}
				else if(i > num)
				{
					Target1_clone.put(Target2.get(i), i - 1);
					Target2_clone.put(i - 1, Target2.get(i));
				}
			}
		}
		
		Target1 = Target1_clone;
		Target2 = Target2_clone;
	}
	
	public static void killPlayer(Player killer, Player target)
	{
		target.getWorld().playSound(target.getLocation(), Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, 0.7f, 0.4f);
		
		if(killer == target)
		{
			LivingPlayer.remove(target);
			Bukkit.getServer().broadcastMessage(main.label + ChatColor.RED + ChatColor.BOLD + "어디선가 비명소리가 들립니다");
			removeTarget(Target1.get(target));
			
			killer.getWorld().playSound(killer.getLocation(), Sound.ENTITY_CAT_AMBIENT, 1f, 0.4f);
			target.setGameMode(GameMode.SPECTATOR);
			target.sendMessage(main.label + "바보");
			
			if(LivingPlayer.size() == 1)
			{
				endGame(LivingPlayer.get(0));
			}
			
			return;
		}
		
		addKill(killer);
		killer.getInventory().addItem(Items.gettoken(Items.getTokenCount(target) / 3 + 5));
		LivingPlayer.remove(target);
		Bukkit.getServer().broadcastMessage(main.label + ChatColor.RED + ChatColor.BOLD + "어디선가 비명소리가 들립니다");
		removeTarget(Target1.get(target));
		target.setGameMode(GameMode.SPECTATOR);
		target.sendMessage(main.label + ChatColor.RED + killer.getName() + "에게 살해당했습니다");
		killer.getWorld().playSound(killer.getLocation(), Sound.ENTITY_CAT_AMBIENT, 1f, 10f);
		target.getWorld().playSound(target.getLocation(), Sound.ENTITY_CAT_AMBIENT, 1f, 10f);
		
		if(LivingPlayer.size() == 1)
		{
			endGame(killer);
			return;
		}
	}
	
	public static Player getTarget(Player p)
	{
		if(Target2.containsKey(Target1.get(p) + 1))
		{
			return Target2.get(Target1.get(p) + 1);
		}
		else
		{
			return Target2.get(0);
		}
	}
	
	public static void targetController()
	{
		new BukkitRunnable() 
		{
			@Override
			public void run() 
			{
				if(Target2.isEmpty())
				{
					this.cancel();
				}
				
				for(Player p : LivingPlayer)
				{
					p.setCompassTarget(getTarget(p).getLocation());
				}
				
				if(!game.isOnGame)
				{
					this.cancel();
				}
			}
		}.runTaskTimer(Bukkit.getPluginManager().getPlugin("amsal"), 0L, 2L);
	}
	
	public static void shootArrow(Player p)
	{		
		Location loc = p.getEyeLocation();
		p.getWorld().playSound(p.getLocation(), Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, 0.7f, 4f);
		
		for(int i = 0; i < 5000; i++)
		{
			loc.add(loc.getDirection().multiply(0.5));
			Particle.DustOptions dust = new Particle.DustOptions(Color.fromRGB(0, 0, 0), 1);
			loc.getWorld().spawnParticle(Particle.REDSTONE, loc.getX(), loc.getY(), loc.getZ(), 0, 0, 0, 10, dust);
			
			if(!(loc.getBlock().getType() == Material.AIR || checkBlock(loc.getBlock()) || loc.getBlock().getType() == Material.LADDER || loc.getBlock().getType() == Material.GRASS || loc.getBlock().getType() == Material.TALL_GRASS))
			{
				break;
			}
			
			for(Entity e : p.getWorld().getNearbyEntities(loc, 0.3, 0.3, 0.3))
			{
				if(e instanceof Player && e != p)
				{
					Player p2 = (Player) e;
						
					if(game.LivingPlayer.contains(p) && game.LivingPlayer.contains(p2))
					{
						if(game.canDamaged(p, p2))
						{
							game.killPlayer(p, p2);
						}
						else
						{
							game.killPlayer(p, p);
						}
					}
				}
			}
		}
	}
	
	public static void endGame(Player p)
	{	
		p.getWorld().getWorldBorder().setSize(WorldborderSize);
		
		Bukkit.getServer().broadcastMessage(main.label + "-----------kill-----------");
		
		for(Player player : Bukkit.getServer().getOnlinePlayers())
		{
			if(kill.containsKey(player))
			{
				Bukkit.getServer().broadcastMessage(main.label + player.getName() + " : " + kill.get(player) + "kill");
			}

			player.sendTitle(ChatColor.GREEN + "GAME OVER", "winner : " + p.getName(), 10, 20, 10);
			player.getInventory().clear();
			player.removePotionEffect(PotionEffectType.JUMP);
			player.removePotionEffect(PotionEffectType.SPEED);
			player.removePotionEffect(PotionEffectType.INVISIBILITY);
			player.removePotionEffect(PotionEffectType.GLOWING);
		}
		
		Bukkit.getServer().broadcastMessage(main.label + "--------------------------");
		
		isOnGame = false;
		Target1.clear();
		Target2.clear();
		LivingPlayer.clear();
		kill.clear();
		
		p.setGameMode(GameMode.CREATIVE);
		
		p.getWorld().playSound(p.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 1, 1);
		
		new BukkitRunnable() 
		{
			int i = 0;
			@Override
			public void run() 
			{
				Location loc = p.getLocation();
				Firework fw = (Firework) loc.getWorld().spawnEntity(loc, EntityType.FIREWORK);
				FireworkMeta fwm = fw.getFireworkMeta();

				fwm.setPower(2);
				fwm.addEffect(FireworkEffect.builder().withColor(Color.LIME).flicker(true).build());

				fw.setFireworkMeta(fwm);
				fw.detonate();
				
				if(i == 10)
				{
					for(Player player : Bukkit.getServer().getOnlinePlayers())
					{
						player.teleport(commands.center);
						player.setGameMode(GameMode.SURVIVAL);
					}
					this.cancel();
				}
				
				i++;
			}
		}.runTaskTimer(Bukkit.getServer().getPluginManager().getPlugin("amsal"), 0, 5);
	}
	
	public static boolean checkBlock(Block b)
	{
		if(b.getType() == Material.GLASS || b.getType() == Material.GLASS_PANE || b.getType() == Material.BLACK_STAINED_GLASS || b.getType() == Material.BLACK_STAINED_GLASS_PANE ||
				b.getType() == Material.BLUE_STAINED_GLASS || b.getType() == Material.BLUE_STAINED_GLASS_PANE || b.getType() == Material.BROWN_STAINED_GLASS || b.getType() == Material.BROWN_STAINED_GLASS_PANE
				|| b.getType() == Material.CYAN_STAINED_GLASS || b.getType() == Material.CYAN_STAINED_GLASS_PANE || b.getType() == Material.GRAY_STAINED_GLASS || b.getType() == Material.GRAY_STAINED_GLASS_PANE
				|| b.getType() == Material.GREEN_STAINED_GLASS || b.getType() == Material.GREEN_STAINED_GLASS_PANE || b.getType() == Material.LIGHT_BLUE_STAINED_GLASS || b.getType() == Material.LIGHT_BLUE_STAINED_GLASS_PANE
				|| b.getType() == Material.LIGHT_GRAY_STAINED_GLASS || b.getType() == Material.LIGHT_GRAY_STAINED_GLASS_PANE || b.getType() == Material.LIME_STAINED_GLASS || b.getType() == Material.LIME_STAINED_GLASS_PANE
				|| b.getType() == Material.MAGENTA_STAINED_GLASS || b.getType() == Material.MAGENTA_STAINED_GLASS_PANE || b.getType() == Material.ORANGE_STAINED_GLASS || b.getType() == Material.ORANGE_STAINED_GLASS_PANE
				|| b.getType() == Material.PINK_STAINED_GLASS || b.getType() == Material.PINK_STAINED_GLASS_PANE || b.getType() == Material.PURPLE_STAINED_GLASS || b.getType() == Material.PURPLE_STAINED_GLASS_PANE
				|| b.getType() == Material.RED_STAINED_GLASS || b.getType() == Material.RED_STAINED_GLASS_PANE || b.getType() == Material.WHITE_STAINED_GLASS || b.getType() == Material.WHITE_STAINED_GLASS_PANE
				|| b.getType() == Material.YELLOW_STAINED_GLASS || b.getType() == Material.YELLOW_STAINED_GLASS_PANE || b.getType() == Material.WATER)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public static void addKill(Player p)
	{
		if(kill.containsKey(p))
		{
			kill.put(p, kill.get(p) + 1);
		}
		else
		{
			kill.put(p, 1);
		}
	}
}























