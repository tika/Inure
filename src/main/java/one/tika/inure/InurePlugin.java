package one.tika.inure;

import one.tika.inure.cmd.CmdBase;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class InurePlugin extends JavaPlugin {

    public void registerEvents(Listener... listeners) {
        for (Listener listener : listeners)
            getServer().getPluginManager().registerEvents(listener, this);
    }

    public void registerCommands(CmdBase... commands) {
        for (CmdBase command : commands)
            getCommand(command.getName()).setExecutor(command);
    }


}
