package cc.baka9.aloneopengui;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import cc.baka9.aloneopengui.Check;
import cc.baka9.aloneopengui.Config;

public class Main extends JavaPlugin {
	
	public void onEnable() {
		Config.init(this);
		getLogger().info("禁止的方块id或方块名: " + Config.getId());
		getLogger().info("禁止的菜单id或菜单物品名: " + Config.getMenu());	
		String command = YamlConfiguration
				.loadConfiguration(this.getClass().getResourceAsStream("/plugin.yml"))
				.getConfigurationSection("commands")
				.getKeys(false).toString()
				.replaceAll("[\\[\\]]", "");
		
		getCommand(command).setExecutor(new Commands());
		getServer().getPluginManager().registerEvents(new Check(), this);
		
	}

}
