package vort3x;

import java.util.ArrayList;

public class ModuleManager {
	
	/* Used to manage enabled modules and handle
	 * callbacks scattered around the original code */
	
	public static void addModules()
	{
		
	}
	
	// Used for iterating through all the modules
	private static ArrayList<Module> modules = new ArrayList<Module>();
	
	public void setState(String name, boolean state)
	{
		for(Module module : modules)
			if(module.getName().equals(name))
				module.setEnabled(state);
	}
	
	public boolean getState(String name)
	{
		for(Module module : modules)
			if(module.getName().equals(name))
				return module.getEnabled();
		return false;
	}
}
