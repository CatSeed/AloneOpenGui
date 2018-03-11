package cc.baka9.aloneopengui;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class Check implements Listener {
	
	private Map<Location, String> openGui = new HashMap<Location, String>();
	private Map<String, Location> rightBlock = new HashMap<String, Location>();
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerInteractBlock(PlayerInteractEvent e) {
	  
		if (e.getClickedBlock() == null 
				|| e.getAction()!=Action.RIGHT_CLICK_BLOCK) {
			return;
		}
		String id = e.getClickedBlock().getTypeId()+"";
		String type = String.valueOf(e.getClickedBlock().getType());
		
		if (Config.getId().contains(id) || Config.getId().contains(type)) {
    	  
			rightBlock.put(e.getPlayer().getName(), e.getClickedBlock().getLocation());
		}
      
	}
  
	@EventHandler
	public void onPlayerOpenInventory(InventoryOpenEvent e){
		
		String name = e.getPlayer().getName();
		Location loc = rightBlock.get(name);
		if(loc == null) {
			return;
		}
		
		if (!openGui.containsKey(loc)){
			openGui.put(loc, e.getPlayer().getName());
		}
		
		if(openGui.get(loc).equalsIgnoreCase(name)) {
			return;
		}
		if(!updateMap(openGui.get(loc))) {
			e.setCancelled(true);
			((Player) e.getPlayer()).sendMessage("§8这个方块正在被别人使用");
			rightBlock.remove(e.getPlayer().getName());	
		}
	}

  @EventHandler
  public void onPlayerBreakBlock(BlockBreakEvent e){
	  
      Location loc = e.getBlock().getLocation();
      
      if (!openGui.containsKey(loc) || loc == null){
    	  return;
      }

      String name = e.getPlayer().getName();
      if(openGui.get(loc).equalsIgnoreCase(name)) {
		  openGui.remove(loc);
		  rightBlock.remove(name);
    	  return;
      }
      if(!updateMap(openGui.get(loc))) {
    	  e.setCancelled(true);
    	  ((Player) e.getPlayer()).sendMessage("§8这个方块正在被别人使用你不能破坏");
      }
  }
  
	@EventHandler
	public void onPlayerCloseInventory(InventoryCloseEvent e) {
		delOneDateInMap((Player)e.getPlayer());
	}
	
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent e) {
		delOneDateInMap(e.getPlayer());
	}
	/**
	 * 尝试删除 rightBlock,和openGui 对应游戏名的数据
	 * @param name 游戏名字
	 * @return 更新成功返回true
	 */
	private boolean updateMap(String name) {
	      Player player = Bukkit.getPlayer(name);
	      if(player == null || !player.isOnline()) {
	    	  delOneDateInMap(player);
	    	  return true;
	      }
		return false;
	}
	/**
	 * 删除 rightBlock,和openGui 对应玩家的数据
	 * @param player
	 */
	private void delOneDateInMap(Player player) {
		
		if(player == null) {
			return;
		}
		
		String name = player.getName();
		Location loc = rightBlock.get(name);
		if (openGui.get(loc) == name && loc != null) {
			openGui.remove(loc);
			rightBlock.remove(name);
		}
	}
}
