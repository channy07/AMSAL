package Main;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Particle;
import org.bukkit.craftbukkit.libs.jline.console.KillRing;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class events implements Listener 
{
	@EventHandler
	public void itemInteract(PlayerInteractEvent e)
	{
		if(!game.isOnGame)
		{
			return;
		}
		
		if(e.getHand() != EquipmentSlot.HAND)
		{
			return;
		}
		
		Player p = e.getPlayer();
		Action action = e.getAction();
		
		if(action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK)
		{
			if(p.getItemInHand() != null)
			{		
				if(p.getItemInHand().getItemMeta() == null)
				{
					return;
				}
				
				if(p.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("SHOP"))
				{
					p.openInventory(Items.getShopInv());
					e.setCancelled(true);
				}
				else if(p.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("Invisible Potion"))
				{
					e.setCancelled(true);
					Items.useInvisiblePotion(p);
				}
				else if(p.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("Jump Potion"))
				{
					e.setCancelled(true);
					Items.useJumpPotion(p);
				}
				else if(p.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("Target Glowing"))
				{
					e.setCancelled(true);
					Items.useGlowing(p);
				}
				else if(p.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("Jump"))
				{
					e.setCancelled(true);
					Items.useJump(p);
				}
				else if(p.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("Grenade"))
				{
					e.setCancelled(true);
					Items.useGrenade(p);
				}
				else if(p.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("Target Paper"))
				{
					e.setCancelled(true);
					Items.useTargetPaper(p);
				}
			}
		}
	}
	
	@EventHandler
	public void shootBow(EntityShootBowEvent e)
	{
		if(e.getEntity() instanceof Player && game.isOnGame)
		{
			Player p = (Player) e.getEntity();
			
			if(e.getForce() >= 0.9)
			{
				game.shootArrow(p);
				e.setCancelled(true);
			}
			else
			{
				e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void playerInventoryClick(InventoryClickEvent e)
	{
		if(!game.isOnGame || e.getCurrentItem() == null)
		{
			return;
		}
		
		if(e.getView().getTitle().equalsIgnoreCase("SHOP"))
		{
			Player p = (Player) e.getWhoClicked();
			
			if(e.getSlot() == 20)
			{
				if(Items.removeToken(p, 5))
				{
					p.getInventory().addItem(Items.getInvisiblePotion());
				}
				else
				{
					p.sendMessage(main.label + "토큰이 부족합니다");
				}
			}
			else if(e.getSlot() == 21)
			{
				if(Items.removeToken(p, 1))
				{
					p.getInventory().addItem(Items.getJumpPotion());
				}
				else
				{
					p.sendMessage(main.label + "토큰이 부족합니다");
				}
			}
			else if(e.getSlot() == 22)
			{
				if(Items.removeToken(p, 2))
				{
					p.getInventory().addItem(Items.getEnderPearl());
				}
				else
				{
					p.sendMessage(main.label + "토큰이 부족합니다");
				}
			}
			else if(e.getSlot() == 23)
			{
				if(Items.removeToken(p, 3))
				{
					p.getInventory().addItem(Items.getGlowing());
				}
				else
				{
					p.sendMessage(main.label + "토큰이 부족합니다");
				}
			}
			else if(e.getSlot() == 24)
			{
				if(Items.removeToken(p, 2))
				{
					p.getInventory().addItem(Items.getJump());
				}
				else
				{
					p.sendMessage(main.label + "토큰이 부족합니다");
				}
			}
			else if(e.getSlot() == 29)
			{
				if(Items.removeToken(p, 4))
				{
					p.getInventory().addItem(Items.getGrenade());
				}
				else
				{
					p.sendMessage(main.label + "토큰이 부족합니다");
				}
			}
			else if(e.getSlot() == 30)
			{
				if(Items.removeToken(p, 2))
				{
					p.getInventory().addItem(Items.getTargetPaper());
				}
				else
				{
					p.sendMessage(main.label + "토큰이 부족합니다");
				}
			}
			
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void breakBlock(BlockBreakEvent e)
	{
		if(game.checkBlock(e.getBlock()))
		{
			return;
		}
		
		e.setCancelled(true);
	}
	
	@EventHandler
	public void placeBlock(BlockPlaceEvent e)
	{
		e.setCancelled(true);
	}
	
	@EventHandler
	public void ThrowItems(PlayerDropItemEvent e)
	{
		if(game.isOnGame)
		{
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void EntityDamage(EntityDamageByEntityEvent e)
	{
		if(game.isOnGame)
		{
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void WorldBorderDead(EntityDeathEvent e)
	{
		if(game.isOnGame)
		{
			if(e.getEntity() instanceof Player)
			{
				Player p = (Player) e.getEntity();
				
				game.killPlayer(p, p);
			}
		}
	}
	
	@EventHandler
	public void PlayerQuit(PlayerQuitEvent e)
	{
		Player p = e.getPlayer();
		
		if(game.isOnGame)
		{
			if(game.LivingPlayer.contains(p))
			{
				game.killPlayer(p, p);
			}
		}
		else
		{
			if(game.GamePlayer.contains(p))
			{
				game.removePlayer(p);
			}
		}
	}
}




















