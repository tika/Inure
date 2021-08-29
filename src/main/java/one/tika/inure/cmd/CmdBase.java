package one.tika.inure.cmd;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class CmdBase implements CommandExecutor, TabExecutor {

    public abstract List<SubcmdBase> getSubcommands();
    public abstract void perform(CommandSender sender, org.bukkit.command.Command command, String label, String[] args);
    public abstract List<String> getTabArgs(CommandSender sender, String[] args);

    @Override
    public final boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        // If subcommands exist && args exist
        if (getSubcommands() != null && getSubcommands().size() > 0 && args.length > 0) {
            for (SubcmdBase subcommand : getSubcommands()) {
                if (args[0].equalsIgnoreCase(subcommand.getName()) ||
                        Arrays.stream(subcommand.getAliases()).anyMatch(name -> args[0].equalsIgnoreCase(name)))
                    subcommand.perform(sender, args);
            }
        } else perform(sender, command, label, args);

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 0) return null;
        if (getSubcommands() == null) return getTabArgs(sender, args);
        if (args.length == 1)
            return getSubcommands().stream().map(SubcmdBase::getName).collect(Collectors.toList());

        for (SubcmdBase subcommand : getSubcommands()) {
            if (args[0].equalsIgnoreCase(subcommand.getName()))
                return subcommand.getTabArgs(sender, args);
        }

        return null;
    }

}
