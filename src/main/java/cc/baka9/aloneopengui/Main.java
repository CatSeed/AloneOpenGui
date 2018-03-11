package cc.baka9.aloneopengui;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import cc.baka9.aloneopengui.Check;
import cc.baka9.aloneopengui.Config;

public class Main extends JavaPlugin {
	
	public void onEnable() {
		Config.init(this);
		System.out.println("禁止的方块id或方块名: " + Config.getId());
		String command = YamlConfiguration
				.loadConfiguration(this.getClass()
						.getResourceAsStream("/plugin.yml"))
				.getConfigurationSection("commands")
				.getKeys(false).toString()
				.replaceAll("[\\[\\]]", "");
		
		getCommand(command).setExecutor(new Commands());
		getServer().getPluginManager().registerEvents(new Check(), this);
		
	}

}
