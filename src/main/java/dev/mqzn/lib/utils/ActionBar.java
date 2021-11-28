package dev.mqzn.lib.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Objects;

public final class ActionBar {

    private Class<?> nmsChatSerializer;
    private Class<?> nmsPacketChat;
    private Class<?> nmsChatBaseComponent;

    private final JavaPlugin plugin;
    public ActionBar(JavaPlugin plugin) {
        this.plugin = plugin;
    }


    private void loadClasses() {
        nmsPacketChat = getNMSClass("PacketPlayOutChat");

        nmsChatBaseComponent = getNMSClass("IChatBaseComponent");
        getNMSClass("PacketPlayOutTitle$EnumTitleAction");

        if (getVersion().contains("1_8")) {
            if (getVersion().contains("R1")) {
                nmsChatSerializer = getNMSClass("ChatSerializer");
                getNMSClass("EnumTitleAction");
            } else if (getVersion().contains("R2") || getVersion().contains("R3")) {
                nmsChatSerializer = getNMSClass("IChatBaseComponent$ChatSerializer");
            }
        } else if (getVersion().contains("1_9") || getVersion().contains("1_10") || getVersion().contains("1_11")) {
            nmsChatSerializer = getNMSClass("IChatBaseComponent$ChatSerializer");
        }
    }

    public void sendActionBar(Player player, String message) {
        loadClasses();
        try {
            String handle1 = "getHandle";
            Object handle = Objects.requireNonNull(getMethod(player.getClass(), handle1)).invoke(player);
            String playerConnection1 = "playerConnection";
            Object playerConnection = Objects.requireNonNull(getField(handle.getClass(), playerConnection1)).get(handle);
            Object serializedMessage = Objects.requireNonNull(getMethod(this.nmsChatSerializer, "a", String.class)).invoke(this.nmsChatSerializer, "{\"text\":\"" + ChatColor.translateAlternateColorCodes('&', message) + "\"}");
            Object packet = this.nmsPacketChat.getConstructor(this.nmsChatBaseComponent, byte.class).newInstance(serializedMessage, (byte) 2);

//    		if (getVersion().contains("1_8") || getVersion().contains("1_9") || getVersion().contains("1_10") || getVersion().contains("1_11")) {
//    			packet = this.nmsPacketChat.getConstructor(this.nmsChatBaseComponent, byte.class).newInstance(serializedMessage, (byte) 2);
//    		}

            String sendPacket = "sendPacket";
            Objects.requireNonNull(getMethod(playerConnection.getClass(), sendPacket)).invoke(playerConnection, packet);
        } catch (Exception localException) {
            localException.printStackTrace();
        }
    }

    public void sendActionBar(final Player player, final String message, int duration) {
        sendActionBar(player, message);

        if (duration >= 0) {
            // Sends empty message at the end of the duration. Allows messages shorter than 3 seconds, ensures precision.
            new BukkitRunnable() {
                @Override
                public void run() {
                    sendActionBar(player, "");
                }
            }.runTaskLater(plugin, duration + 1);
        }

        // Re-sends the messages every 3 seconds so it doesn't go away from the player's screen.
        while (duration > 60) {
            duration -= 60;
            new BukkitRunnable() {
                @Override
                public void run() {
                    sendActionBar(player, message);
                }
            }.runTaskLater(plugin, duration%60);
        }
    }

    public void sendActionBarToAllPlayers(String message) {
        sendActionBarToAllPlayers(message, -1);
    }

    public void sendActionBarToAllPlayers(String message, int duration) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            sendActionBar(p, message, duration);
        }
    }

    public static String getVersion() {
        String name = Bukkit.getServer().getClass().getPackage().getName();
        return name.substring(name.lastIndexOf('.') + 1) + ".";
    }

    private Class<?> getNMSClass(String className) {
        String fullName = "net.minecraft.server." + getVersion() + className;
        Class<?> clazz = null;
        try {
            clazz = Class.forName(fullName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clazz;
    }

    private Field getField(Class<?> clazz, String name) {
        try {
            Field field = clazz.getDeclaredField(name);
            field.setAccessible(true);
            return field;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private Method getMethod(Class<?> clazz, String name, Class<?>... args) {
        for (Method m : clazz.getMethods())
            if (m.getName().equals(name) && (args.length == 0 || classListEqual(args, m.getParameterTypes()))) {
                m.setAccessible(true);
                return m;
            }
        return null;
    }

    private boolean classListEqual(Class<?>[] l1, Class<?>[] l2) {
        boolean equal = true;
        if (l1.length != l2.length)
            return false;
        for (int i = 0; i < l1.length; i++)
            if (l1[i] != l2[i]) {
                equal = false;
                break;
            }
        return equal;
    }


}