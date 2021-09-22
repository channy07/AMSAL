package Main;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class commands implements CommandExecutor
{
	static Location center;
	
	@Override
	public boolean onCommand(CommandSender sender, Command c, String label, String[] args) 
	{
		Player p = (Player) sender;
		
		if(p instanceof Player)
		{
			if(c.getName().equalsIgnoreCase("amsal"))
			{
				if(args[0].equalsIgnoreCase("join"))
				{
					game.addPlayer(p);
				}
				else if(args[0].equalsIgnoreCase("quit"))
				{
					game.removePlayer(p);
				}
				else if(args[0].equalsIgnoreCase("start"))
				{
					game.startGame(p);
				}
				else if(args[0].equalsIgnoreCase("shop"))
				{
					p.openInventory(Items.getShopInv());
				}
				else if(args[0].equalsIgnoreCase("end"))
				{
					game.endGame(p);
				}
				else if(args[0].equalsIgnoreCase("center"))
				{
					center = p.getLocation();
					p.sendMessage(main.label + "자기장 중심이 설정되었습니다");
				}
				else if(args[0].equalsIgnoreCase("setworldbordersize"))
				{
					if(args.length == 2)
					{
						try 
						{
							game.WorldborderSize = Integer.parseInt(args[1]);
							p.sendMessage(main.label + "월드보더의 크기가 " + Integer.parseInt(args[1]) + "(으)로 설정되었습니다");
						} 
						catch (Exception e) 
						{
							p.sendMessage(main.label + "숫자를 입력해 주세요");
						}
					}
					else
					{
						p.sendMessage(main.label + "숫자를 입력해 주세요");
					}
				}
			}
		}
		
		return false;
	}
}