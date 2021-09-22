package Main;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.bukkit.scheduler.BukkitRunnable;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class Items 
{
	public static Inventory getShopInv()
	{
		Inventory inv = Bukkit.createInventory(null, 54, "SHOP");
		
		ItemStack frame = new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1);
		ItemMeta frameMeta = frame.getItemMeta();
		frameMeta.setDisplayName("");
		frame.setItemMeta(frameMeta);
		
		ItemStack invisiblePotion = getInvisiblePotion();
		
		ItemStack jumpPotion = getJumpPotion();
		
		ItemStack glowing = getGlowing();
		
		ItemStack teleport = getEnderPearl();
		
		ItemStack jump = getJump();
		
		ItemStack grenade = getGrenade();
		
		ItemStack targetPaper = getTargetPaper();
		
		for(int i=0; i<=8; i++)
		{
			inv.setItem(i, frame);
			inv.setItem(i+45, frame);
		}
		
		for(int i=1; i<=5; i++)
		{
			inv.setItem(i*9, frame);
			inv.setItem(i*9+8, frame);
		}
		
		inv.setItem(20, invisiblePotion);
		inv.setItem(21, jumpPotion);
		inv.setItem(22, teleport);
		inv.setItem(23, glowing);
		inv.setItem(24, jump);
		inv.setItem(29, grenade);
		inv.setItem(30, targetPaper);
		
		return inv;
	}
	
	public static ItemStack getInvisiblePotion()
	{
		ItemStack invisiblePotion = new ItemStack(Material.POTION, 1);
		PotionMeta invisiblePotionMeta = (PotionMeta) invisiblePotion.getItemMeta();
		invisiblePotionMeta.setBasePotionData(new PotionData(PotionType.INVISIBILITY));
		invisiblePotionMeta.setDisplayName("Invisible Potion");
		List<String> invisiblePotionLore = new ArrayList<String>();
		invisiblePotionLore.add("");
		invisiblePotionLore.add(ChatColor.GREEN + "5 EMERALD");
		invisiblePotionLore.add("투명화 효과를 10초 부여합니다");
		invisiblePotionMeta.setLore(invisiblePotionLore);
		invisiblePotion.setItemMeta(invisiblePotionMeta);
		
		return invisiblePotion;
	}
	
	public static ItemStack getJumpPotion()
	{
		ItemStack jumpPotion = new ItemStack(Material.POTION, 1);
		PotionMeta jumpPotionMeta = (PotionMeta) jumpPotion.getItemMeta();
		jumpPotionMeta.setBasePotionData(new PotionData(PotionType.JUMP));
		jumpPotionMeta.setDisplayName("Jump Potion");
		List<String> jumpPotionLore = new ArrayList<String>();
		jumpPotionLore.add("");
		jumpPotionLore.add(ChatColor.GREEN + "1 EMERALD");
		jumpPotionLore.add("점프 강화와 신속효과를 30초 부여합니다");
		jumpPotionMeta.setLore(jumpPotionLore);
		jumpPotion.setItemMeta(jumpPotionMeta);
		
		return jumpPotion;
	}
	
	public static ItemStack getGlowing()
	{
		ItemStack glowing = new ItemStack(Material.DRAGON_BREATH, 1);
		ItemMeta glowingMeta = glowing.getItemMeta();
		glowingMeta.setDisplayName("Target Glowing");
		List<String> glowingLore = new ArrayList<String>();
		glowingLore.add("");
		glowingLore.add(ChatColor.GREEN + "3 EMERALD");
		glowingLore.add("자신의 타겟을 10초 발광시킵니다");
		glowingMeta.setLore(glowingLore);
		glowing.setItemMeta(glowingMeta);
		
		return glowing;
	}
	
	public static ItemStack getEnderPearl()
	{
		ItemStack teleport = new ItemStack(Material.ENDER_PEARL, 1);
		ItemMeta teleportMeta = teleport.getItemMeta();
		teleportMeta.setDisplayName("Ender Pearl");
		List<String> teleportLore = new ArrayList<String>();
		teleportLore.add("");
		teleportLore.add(ChatColor.GREEN + "2 EMERALD");
		teleportMeta.setLore(teleportLore);
		teleport.setItemMeta(teleportMeta);
		
		return teleport;
	}
	
	public static ItemStack getJump()
	{
		ItemStack jump = new ItemStack(Material.ENCHANTED_BOOK, 1);
		ItemMeta jumpMeta = jump.getItemMeta();
		jumpMeta.setDisplayName("Jump");
		List<String> jumpLore = new ArrayList<String>();
		jumpLore.add("");
		jumpLore.add(ChatColor.GREEN + "2 EMERALD");
		jumpLore.add("임의의 힘으로 전방을 향해 도약합니다");
		jumpMeta.setLore(jumpLore);
		jump.setItemMeta(jumpMeta);
		
		return jump;
	}
	
	public static ItemStack getGrenade()
	{
		ItemStack grenade = new ItemStack(Material.TNT, 1);
		ItemMeta grenadeMeta = grenade.getItemMeta();
		grenadeMeta.setDisplayName("Grenade");
		List<String> grenadeLore = new ArrayList<String>();
		grenadeLore.add("");
		grenadeLore.add(ChatColor.GREEN + "4 EMERALD");
		grenadeLore.add("전방을 향해 범위가 10인 수류탄을 던집니다");
		grenadeMeta.setLore(grenadeLore);
		grenade.setItemMeta(grenadeMeta);
		
		return grenade;
	}
	
	public static ItemStack getTargetPaper()
	{
		ItemStack TargetPaper = new ItemStack(Material.PAPER, 1);
		ItemMeta TargetPaperMeta = TargetPaper.getItemMeta();
		TargetPaperMeta.setDisplayName("Target Paper");
		List<String> TargetPaperLore = new ArrayList<String>();
		TargetPaperLore.add("");
		TargetPaperLore.add(ChatColor.GREEN + "2 EMERALD");
		TargetPaperLore.add("자신이 타겟인 사람의 거리를 30초간 알려줍니다");
		TargetPaperMeta.setLore(TargetPaperLore);
		TargetPaper.setItemMeta(TargetPaperMeta);
		
		return TargetPaper;
	}
	
	public static ItemStack getbow()
	{
		ItemStack bow = new ItemStack(Material.BOW, 1);
		ItemMeta bowMeta = bow.getItemMeta();
		bowMeta.setDisplayName("GUN");
		bowMeta.addEnchant(Enchantment.ARROW_INFINITE, 1, false);
		bow.setItemMeta(bowMeta);
		
		return bow;
	}
	
	public static ItemStack getshop()
	{
		ItemStack shop = new ItemStack(Material.CHEST, 1);
		ItemMeta shopMeta = shop.getItemMeta();
		shopMeta.setDisplayName("SHOP");
		shop.setItemMeta(shopMeta);
		
		return shop;
	}
	
	public static ItemStack gettoken(int i)
	{
		ItemStack shop = new ItemStack(Material.EMERALD);
		ItemMeta shopMeta = shop.getItemMeta();
		shopMeta.setDisplayName("TOKEN");
		shop.setAmount(i);
		shop.setItemMeta(shopMeta);
		
		return shop;
	}
	
	public static ItemStack getarrow()
	{
		ItemStack arrow = new ItemStack(Material.ARROW);
		ItemMeta arrowMeta = arrow.getItemMeta();
		arrowMeta.setDisplayName("BULLET");
		arrow.setAmount(64);
		arrow.setItemMeta(arrowMeta);
		
		return arrow;
	}
	
	public static ItemStack getcompass()
	{
		ItemStack compass = new ItemStack(Material.COMPASS, 1);
		ItemMeta compassMeta = compass.getItemMeta();
		compassMeta.setDisplayName("TRACKER");
		compass.setItemMeta(compassMeta);
		
		return compass;
	}
	
	public static boolean removeToken(Player p, int i)
	{
		int Count = 0;
		for(ItemStack item : p.getInventory().getContents())
		{
			if(item != null && item.getType() == Material.EMERALD)
	        {
				Count += item.getAmount();
				
	            if(Count >= i)
	            {
	                item.setAmount(item.getAmount() - (i));
	                return true;
	            }
	        }
		}
		return false;
	}
	
	public static int getTokenCount(Player p)
	{
		int Count = 0;
		for(ItemStack item : p.getInventory().getContents())
		{
			if(item != null && item.getType() == Material.EMERALD)
	        {
				Count += item.getAmount();
	        }
		}
		
		return Count;
	}
	
	public static void useInvisiblePotion(Player p)
	{
		p.getInventory().removeItem(getInvisiblePotion());
		
		p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 20*10, 1));
	}
	
	public static void useJumpPotion(Player p)
	{
		p.getInventory().removeItem(getJumpPotion());
		
		p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 20*30, 7));
		p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20*30, 3));
	}
	
	public static void useGlowing(Player p)
	{
		p.getInventory().removeItem(getGlowing());
		
		game.getTarget(p).addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 20*10, 1), true);
	}
	
	public static void useJump(Player p)
	{
		p.getInventory().removeItem(getJump());
		
		Random random = new Random();
		int level = random.nextInt(4);
		
		p.setVelocity(p.getLocation().getDirection().multiply(level + 6));
	}
	
	public static void useGrenade(Player p)
	{
		p.getInventory().removeItem(getGrenade());
		
		Entity grenade = p.getWorld().spawnEntity(p.getLocation(), EntityType.PRIMED_TNT);
		grenade.setVelocity(p.getLocation().getDirection().multiply(2));
		
		new BukkitRunnable() 
		{
			int i = 0;
			
			@Override
			public void run() 
			{
				i++;
				
				if(i == 4)
				{
					p.getWorld().playSound(grenade.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 5, 1);
					Location loc = grenade.getLocation();
					for(int i1 = -1; i1 <= 1; i1++)
					{
						for(int i2 = -1; i2 <= 1; i2++)
						{
							for(int i3 = -1; i3 <= 1; i3++)
							{
								p.getWorld().spawnParticle(Particle.EXPLOSION_HUGE, loc.clone().add(i1 * 6, i2 *6 , i3 * 6), 1);
							}
						}
					}
					
					grenade.remove();
					
					for(Entity e : p.getWorld().getNearbyEntities(p.getLocation(), 10, 10, 10))
					{
						if(e instanceof Player && e != p)
						{
							Player player = (Player) e;
							
							if(game.LivingPlayer.contains(p) && game.LivingPlayer.contains(player))
							{
								if(game.canDamaged(p, player))
								{
									game.killPlayer(p, player);
									this.cancel();
									break;
								}
							}
						}
					}
					
					this.cancel();
				}
				
				if(i < 4)
				{
					p.getWorld().playSound(grenade.getLocation(), Sound.BLOCK_ANVIL_PLACE, 3, 1);
				}
			}
		}.runTaskTimer(Bukkit.getServer().getPluginManager().getPlugin("amsal"), 0, 20);
	}
	
	public static void useTargetPaper(Player p)
	{
		p.getInventory().removeItem(getTargetPaper());
		
		new BukkitRunnable() 
		{
			int i = 0;
			Player killer = null;
			@Override
			public void run() 
			{
				for(Player player : game.LivingPlayer)
				{
					if(game.getTarget(player) == p)
					{
						killer = player;
						break;
					}
				}
				
				if(i == 30*20 || killer == null || !(game.LivingPlayer.contains(killer)) || !(game.LivingPlayer.contains(p)))
				{
					p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText (""));
					this.cancel();
				}
				
				int distance = (int) p.getLocation().distance(killer.getLocation());
				p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText (ChatColor.GREEN + "" + distance + "m"));
				
				i++;
				
				if(!game.isOnGame)
				{
					p.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText (""));
					this.cancel();
				}
			}
		}.runTaskTimer(Bukkit.getServer().getPluginManager().getPlugin("amsal"), 0, 1);
	}
}









