package cc.baka9.aloneopengui;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Commands implements CommandExecutor{

	private void reload(CommandSender sender,String[] args) {
		
		Config.reload();
		sender.sendMessage("禁止的方块id或方块名: " + Config.getId());	
	}
	private void add(CommandSender sender,String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage("控制台不能使用这个指令");
			return;
		}
    	Player player = (Player) sender;
    	ItemStack item = player.getItemInHand();
    	if(item == null || item.getType() == Material.AIR) {
    		player.sendMessage("手中请拿上你要添加的物品");
    		return;
    	}
    	if(Config.add(item.getType().name())) {
    		player.sendMessage("添加成功!");
    		return;
    	}
    	player.sendMessage("添加失败,已经存在,不要重复添加");
	}
	private void del(CommandSender sender,String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage("控制台不能使用这个指令");
			return;
		}
	    Player player = (Player) sender;
	    ItemStack item = player.getItemInHand();
		if(item == null || item.getType() == Material.AIR) {
			player.sendMessage("手中请拿上你要删除的物品");
			return;
		}
		if(Config.del(item.getType().name())) {
			player.sendMessage("删除成功!");
			return;
		}
		player.sendMessage("删除失败,需要删除的不存在,不需要再删除");
		return;
	}
	
	@Override
	public boolean onCommand(CommandSender sender,Command cmd, String label, String[] args) {
		
		if (!sender.isOp()) return true;
		
		if(args.length == 1) {
			
			if(args[0].equalsIgnoreCase("reload")) {
				reload(sender, args);
				return true;
			}
			
			if(args[0].equalsIgnoreCase("add")) {
				add(sender, args);
				return true;
			} 
			
			if(args[0].equalsIgnoreCase("del")) {
				del(sender, args);
				return true;
			}
		}

		return false;   
	}

}
