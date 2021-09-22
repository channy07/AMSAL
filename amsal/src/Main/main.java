package Main;

import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;

public class main extends JavaPlugin
{
	public static String label = ChatColor.RED + "[" + ChatColor.WHITE + "AMSAL" + ChatColor.RED + "] " + ChatColor.RESET;
	
	@Override
	public void onEnable()
	{
		System.out.println("----------------------------------------------");
		System.out.println("  P r o j e c t   A M S A L ");
		System.out.println(" bug report : channy070123@gmail.com");
		System.out.println("----------------------------------------------");
		
		this.getCommand("amsal").setExecutor(new commands());
		getServer().getPluginManager().registerEvents(new events(), this);
		this.getCommand("amsal").setTabCompleter(new commands_tap());
	}
	
	@Override
	public void onDisable()
	{
		System.out.println("----------------------------------------------");
		System.out.println("  P r o j e c t   A M S A L ");
		System.out.println(" bug report : channy070123@gmail.com");
		System.out.println("----------------------------------------------");
	}
}
