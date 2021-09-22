package Main;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

public class commands_tap implements TabCompleter 
{

	@Override
	public List<String> onTabComplete(CommandSender sender, Command c, String label, String[] args) 
	{
		if(c.getName().equalsIgnoreCase("amsal"))
		{
			if(args.length == 1)
			{
				List<String> commands = new ArrayList<String>();
				commands.add("join");
				commands.add("quit");
				commands.add("center");
				commands.add("start");
				commands.add("setworldbordersize");
				commands.add("end");
				
				return commands;
			}
		}
		
		return null;
	}
	
}
