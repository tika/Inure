package one.tika.inure.util;

import org.bukkit.ChatColor;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.bukkit.ChatColor.COLOR_CHAR;

public class Txt {

    public static String color(final String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static String flatten(List<String> list) {
        return String.join("\n", list);
    }

    public static String stringifyList(List<String> list) {
        return String.join(", ", list);
    }

    public static String hex(String message) {
        final Pattern hexPattern = Pattern.compile("&#([A-Fa-f0-9]{6})");
        Matcher matcher = hexPattern.matcher(message);
        StringBuffer buffer = new StringBuffer(message.length() + 4 * 8);
        while (matcher.find())
        {
            String group = matcher.group(1);
            matcher.appendReplacement(buffer, COLOR_CHAR + "x"
                    + COLOR_CHAR + group.charAt(0) + COLOR_CHAR + group.charAt(1)
                    + COLOR_CHAR + group.charAt(2) + COLOR_CHAR + group.charAt(3)
                    + COLOR_CHAR + group.charAt(4) + COLOR_CHAR + group.charAt(5)
            );
        }
        return matcher.appendTail(buffer).toString();
    }

    public static String getContrast(String initial) {
        Map<String, String> opposites = CollectionUtil.map(
                "&1", "&9",
                "&2", "&a",
                "&3", "&b",
                "&4", "&c",
                "&5", "&d",
                "&6", "&e",
                "&7", "&8",
                "&f", "&7",

                "&9", "&1",
                "&a", "&2",
                "&b", "&3",
                "&c", "&4",
                "&d", "&5",
                "&e", "&6",
                "&8", "&7",
                "&7", "&f"
        );

        return opposites.get(initial);
    }

    public static ChatColor getColor(String coloredRaw) {
        return ChatColor.getByChar(ChatColor.stripColor(coloredRaw).substring(0, 2));
    }

    private final static List<String> trueList = Arrays.asList(
            "yes",
            "y",
            "true",
            "on"
    );

    private final static List<String> falseList = Arrays.asList(
            "no",
            "n",
            "false",
            "off"
    );

    public static List<String> getBoolStrings() {
        final List<String> boolStrings = trueList;
        boolStrings.addAll(falseList);
        return boolStrings;
    }

    public static boolean isBoolString(String raw) {
        return falseList.stream().anyMatch(it -> it.equalsIgnoreCase(raw))
                || trueList.stream().anyMatch(it -> it.equalsIgnoreCase(raw));
    }

    public static boolean isTrueString(String raw) {
        return trueList.stream().anyMatch(it -> it.equalsIgnoreCase(raw));
    }

}
