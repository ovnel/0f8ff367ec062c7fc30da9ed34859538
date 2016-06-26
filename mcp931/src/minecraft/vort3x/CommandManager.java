package vort3x;

import java.util.ArrayList;

import vort3x.commands.*;

public class CommandManager{
	
	/* Handles all the client side commands
	 * and parses chat entries */
	
	// Used for iterating through all the commands
	private static ArrayList<Command> commands = new ArrayList<Command>();
	private static String commandPrefix = "!";
	
	public static void addCommands()
	{
		commands.add(new Toggle());
	}
	
	public static boolean onChatMessage(String message)
	{
		if(commandOption(0, message).startsWith(commandPrefix))
		{
			boolean success = false;
			for(Command command : commands)
			{
				if(commandOption(0, message).substring(commandPrefix.length()).equals(command.getName()))
				{
					success = true;
					command.call(message.substring(optionCharIndex(1, message)));
				}
			}
			if(!success)
			{
				Client.chatError("Invalid command: " + commandOption(0, message).substring(commandPrefix.length()));
				Client.chatPrint("Hint: Use " + commandPrefix + "help to list all the commands");
			}
			return false;
		}
		return true;
	}
	
	public static String commandOption(int index, String command)
	{
		boolean space = true;
		int currentIndex = 0;
		String option = "";
		for(int i = 0; i < command.length(); i++)
		{
			switch(command.charAt(i))
			{
			case 32:
				if(space == false)
					currentIndex++;
				space = true;
				break;
			default:
				space = false;
			}
			if(currentIndex == index && space == false)
				option += command.charAt(i);
		}
		return option;
	}
	
	public static int optionCharIndex(int option, String command)
	{
		boolean space = true;
		int currentIndex = 0;
		for(int i = 0; i < command.length(); i++)
		{
			switch(command.charAt(i))
			{
			case 32:
				if(space == false)
					currentIndex++;
				space = true;
				break;
			default:
				space = false;
			}
			if(currentIndex == option && space == false)
				return i;
		}
		return command.length();
	}
}
