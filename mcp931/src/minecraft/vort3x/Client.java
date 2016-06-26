package vort3x;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;

public class Client {
	
	/*This class includes miscellaneous methods and variables*/
	
	// Used as a shorthand in the hack's code
	public static Minecraft mc = Minecraft.getMinecraft();
	
	public static void startClient()
	{
		CommandManager.addCommands();
		ModuleManager.addModules();
	}
	
	public static void chatPrint(String message)
	{
		mc.ingameGUI.getChatGUI().printChatMessage(new TextComponentString("§r§0[§5§lV§r§0]§7 " + message));
	}
	
	public static void chatError(String message)
	{
		chatPrint("§4Error: §c" + message);
	}
}
