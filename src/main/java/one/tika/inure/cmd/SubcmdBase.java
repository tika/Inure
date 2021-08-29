package one.tika.inure.cmd;

import org.bukkit.command.CommandSender;

import java.util.List;

public abstract class SubcmdBase {

    public abstract String getName();
    public abstract String[] getAliases();
    public abstract void perform(CommandSender sender, String[] args);
    public abstract List<String> getTabArgs(CommandSender sender, String[] args);

}
