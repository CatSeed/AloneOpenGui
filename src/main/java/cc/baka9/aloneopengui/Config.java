package cc.baka9.aloneopengui;

import java.util.List;

import cc.baka9.aloneopengui.Main;

public class Config{
	
	private static List<String> id;
	private static List<String> menu;
	private static Main main;
	  
	public static void init(Main main){
		Config.main = main;
		main.saveDefaultConfig();
		load();
	}
	  
	public static void reload() {
		main.reloadConfig();
		main.saveDefaultConfig();
		load();
	}
	
	public static boolean add(String key, String TypeOrId) {
		if(key.equals("id")) {
			if(id.contains(TypeOrId)) {
				return false;
			}
			id.add(TypeOrId);
			main.getConfig().set("id", id);
			main.saveConfig();
			return true;	
		}
		if(key.equals("menu")) {
			if(menu.contains(TypeOrId)) {
				return false;
			}
			menu.add(TypeOrId);
			main.getConfig().set("menu", menu);
			main.saveConfig();
			return true;	
		}
		return false;
	}
	
	public static boolean del(String key, String TypeOrId) {
		if(key.equals("id")) {
			if(!id.contains(TypeOrId)) {
				return false;
			}
			id.remove(TypeOrId);
			main.getConfig().set("id", id);
			main.saveConfig();
			return true;
		}
		if(key.equals("menu")) {
			if(!menu.contains(TypeOrId)) {
				return false;
			}
			menu.remove(TypeOrId);
			main.getConfig().set("menu", menu);
			main.saveConfig();
			return true;
		}
		return false;
	}
	  
	public static List<String> getId() {
		return id;
	}
	public static List<String> getMenu() {
		return menu;
	}
	private static void load() {
	    id = main.getConfig().getStringList("id");
	    menu = main.getConfig().getStringList("menu");
	}

}
