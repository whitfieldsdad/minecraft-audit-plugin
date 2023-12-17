package ca.tylerfisher.minecraft.auditor;

import org.bukkit.Server;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class App extends JavaPlugin {

  @Override
  public void onEnable() {
    Server server = getServer();
    PluginManager pluginManager = server.getPluginManager();
    EventListener listener = new EventListener();
    pluginManager.registerEvents(listener, this);
  }
}
