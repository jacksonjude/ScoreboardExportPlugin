package com.jacksonjude.ScoreboardExport;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class ScoreboardExportPlugin extends JavaPlugin implements Listener
{
	public static final String ADMIN_PERMISSION = "scexport.admin";
	public static final String ADMIN_COMMAND = "scexport";
	
	@Override
    public void onEnable()
	{
		getCommand(ADMIN_COMMAND).setExecutor(new ScoreboardExportCommand(this));
		//getCommand(ADMIN_COMMAND).setTabCompleter(new DisableCraftCompleter(this));
		this.getServer().getPluginManager().registerEvents(this, this);
    }
}
