package com.jacksonjude.ScoreboardExport;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import javax.swing.JTable;
import javax.swing.table.TableModel;

import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class ScoreboardExporter
{
	public static final String DEFAULT_SCOREBOARD_TABLE_FILE = "scoreboard-export";
	public static final String ENTITY_ID_COLUMN = "entityID";
	
	public static String exportScoreboardToCSV(Scoreboard scoreboard, File folderToExportTo, String filename)
	{
		ArrayList<String> objectiveNames = new ArrayList<String>();
		objectiveNames.add(ENTITY_ID_COLUMN);
		
		ArrayList<ArrayList<Object>> allEntityScores = new ArrayList<ArrayList<Object>>();
		Set<Objective> objectives = scoreboard.getObjectives();
		
		for (String entityID : scoreboard.getEntries())
		{
			ArrayList<Object> entityScores = new ArrayList<Object>();
			entityScores.add(entityID);
			
			for (Objective objective : objectives)
			{
				if (!objectiveNames.contains(objective.getName())) objectiveNames.add(objective.getName());
				
				int entityScore = (Integer)objective.getScore(entityID).getScore();
				entityScores.add(entityScore);
			}
			
			allEntityScores.add(entityScores);			
		}
		
		Object[][] allEntityScoresArray = new Object[allEntityScores.size()][];
		for (int i=0; i < allEntityScores.size(); i++)
		{
			Object[] entityScoresArray = new Object[allEntityScores.get(i).size()];
			for (int j=0; j < allEntityScores.get(i).size(); j++)
			{
				entityScoresArray[j] = allEntityScores.get(i).get(j);
			}
			allEntityScoresArray[i] = entityScoresArray;
		}
		
		String[] objectiveNamesArray = new String[objectiveNames.size()];
		for (int i=0; i < objectiveNames.size(); i++)
		{
			objectiveNamesArray[i] = objectiveNames.get(i);
		}
		
		JTable scoreboardTable = new JTable(allEntityScoresArray, objectiveNamesArray);
		return exportToCSV(scoreboardTable, folderToExportTo + "/" + getNewScoreboardFilename(filename, folderToExportTo));
	}
	
	public static String getNewScoreboardFilename(String filename, File folder)
	{
		folder.mkdir();
		
		ArrayList<String> filenames;
		int filenameSuffixOn = 1;
		String filenameToUse = new String(filename);
		do
		{
			filenames = listFilenamesForFolder(folder);
			filenameToUse = new String(filename) + "-" + filenameSuffixOn + ".csv";
			filenameSuffixOn += 1;
		}
		while (filenames.contains(filenameToUse));
		
		return filenameToUse;
	}
	
	public static ArrayList<String> listFilenamesForFolder(final File folder)
	{
		ArrayList<String> filenames = new ArrayList<String>();
	    for (final File fileEntry : folder.listFiles())
	    {
	        if (!fileEntry.isDirectory())
	        {
	        	filenames.add(fileEntry.getName());
	        }
	    }
	    return filenames;
	}
	
	public static String exportToCSV(JTable tableToExport, String pathToExportTo)
	{
	    try
	    {
	        TableModel model = tableToExport.getModel();
	        FileWriter csv = new FileWriter(new File(pathToExportTo));

	        for (int i = 0; i < model.getColumnCount(); i++)
	        {
	            csv.write(model.getColumnName(i) + ",");
	        }

	        csv.write("\n");

	        for (int i = 0; i < model.getRowCount(); i++)
	        {
	            for (int j = 0; j < model.getColumnCount(); j++)
	            {
	                csv.write(model.getValueAt(i, j).toString() + ",");
	            }
	            csv.write("\n");
	        }

	        csv.close();
	        return pathToExportTo;
	    }
	    catch (IOException e)
	    {
	        e.printStackTrace();
	    }
	    return null;
	}
}
