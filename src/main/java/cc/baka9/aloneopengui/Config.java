package cc.baka9.aloneopengui;

import java.util.List;

import cc.baka9.aloneopengui.Main;

public class Config{
	
	private static List<String> id;
	private static Main main;
	  
	public static void init(Main main){
		Config.main = main;
		main.saveDefaultConfig();
	    id = main.getConfig().getStringList("id");
	}
	  
	public static void reload() {
		main.reloadConfig();
		main.saveDefaultConfig();
		id = main.getConfig().getStringList("id");
	}
	public static boolean add(String TypeOrId) {
		if(id.contains(TypeOrId)) {
			return false;
		}
		id.add(TypeOrId);
		main.getConfig().set("id", id);
		main.saveConfig();
		return true;
	}
	
	public static boolean del(String TypeOrId) {
		if(!id.contains(TypeOrId)) {
			return false;
		}
		id.remove(TypeOrId);
		main.getConfig().set("id", id);
		main.saveConfig();
		return true;
	}
	  
	public static List<String> getId() {
		return id;
	}

}
