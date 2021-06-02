package com.jacksonjude.ScoreboardExport;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ScoreboardExportCommand implements CommandExecutor
{
	private final ScoreboardExportPlugin plugin;
	
	public ScoreboardExportCommand(ScoreboardExportPlugin plugin)
	{
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		if (!sender.hasPermission(ScoreboardExportPlugin.ADMIN_PERMISSION))
		{
			sender.sendMessage(ChatColor.RED + "You cannot use ScoreboardExport");
			return true;
		}
		
		if (args.length == 0)
		{
			sender.sendMessage(ChatColor.GOLD + "/scexport export <filename>" + ChatColor.GRAY + " - exports scoreboards" + "\n" + ChatColor.GOLD + "/scimport import <filename>" + ChatColor.GRAY + " - imports scoreboards");
			return true;
		}
		
		switch (args[0].toLowerCase())
		{
		case "export":
			String filename = ScoreboardExporter.DEFAULT_SCOREBOARD_TABLE_FILE;
			
			if (args.length >= 2 && args[1] != "")
			{
				filename = args[1];
			}
			
			String exportedFilename = ScoreboardExporter.exportScoreboardToCSV(plugin.getServer().getScoreboardManager().getMainScoreboard(), plugin.getDataFolder(), filename);
			if (exportedFilename != null)
			{
				sender.sendMessage(ChatColor.GREEN + "Exported scoreboards to " + exportedFilename);
			}
			else
			{
				sender.sendMessage(ChatColor.RED + "An error occured when exporting scoreboards");
			}
			
			return true;
		}
		
		return false;
	}
}
