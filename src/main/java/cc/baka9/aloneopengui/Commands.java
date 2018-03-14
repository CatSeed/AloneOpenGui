package cc.baka9.aloneopengui;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Commands implements CommandExecutor{
	
	private String getTypeNmae(String key) {
		if(key.equals("id")) {
			return "§a物品";
		}
		if(key.equals("menu")) {
			return "§c菜单";
		}
		return "";
		
	}
	
	private void reload(CommandSender sender) {
		
		Config.reload();
		sender.sendMessage("禁止的方块id或方块名: " + Config.getId());
		sender.sendMessage("禁止的菜单id或菜单物品名: " + Config.getMenu());	
	}
	private void add(String key ,CommandSender sender) {
		
		if(!(sender instanceof Player)) {
			sender.sendMessage("控制台不能使用这个指令");
			return;
		}
    	Player player = (Player) sender;
    	ItemStack item = player.getItemInHand();
    	if(item == null || item.getType() == Material.AIR) {
    		player.sendMessage("手中请拿上你要添加的" + getTypeNmae(key));
    		return;
    	}
    	if(Config.add(key ,item.getType().name())) {
    		player.sendMessage(getTypeNmae(key) + "添加成功!");
    		return;
    	}
    	player.sendMessage(getTypeNmae(key) + "添加失败,已经存在,不要重复添加");
    	return;
	}
	@SuppressWarnings("deprecation")
	private void del(String key ,CommandSender sender) {
		if(!(sender instanceof Player)) {
			sender.sendMessage("控制台不能使用这个指令");
			return;
		}
	    Player player = (Player) sender;
	    ItemStack item = player.getItemInHand();
		if(item == null || item.getType() == Material.AIR) {
			player.sendMessage("手中请拿上你要删除的" + getTypeNmae(key));
			return;
		}
		if(Config.del(key ,item.getType().name()) | Config.del(key ,item.getTypeId()+"")) {
			player.sendMessage(getTypeNmae(key) + "删除成功!");
			return;
		}
		player.sendMessage(getTypeNmae(key) + "删除失败,需要删除的不存在,不需要再删除");
	}
	
	@Override
	public boolean onCommand(CommandSender sender,Command cmd, String label, String[] args) {
		
		if (!sender.isOp()) return true;
		
		if(args.length == 1) {
			
			if(args[0].equalsIgnoreCase("reload")) {
				reload(sender);
				return true;
			}
			
			if(args[0].equalsIgnoreCase("add")) {
				add("id" , sender);
				return true;
			} 
			
			if(args[0].equalsIgnoreCase("del")) {
				del("id" , sender);
				return true;
			}
			if(args[0].equalsIgnoreCase("addmenu")) {
				add("menu" , sender);
				return true;
			} 
			if(args[0].equalsIgnoreCase("delmenu")) {
				del("menu" , sender);
				return true;
			}
		}

		return false;   
	}

}
