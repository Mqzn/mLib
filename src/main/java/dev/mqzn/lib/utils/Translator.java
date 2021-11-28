package dev.mqzn.lib.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.logging.Level;
import java.util.stream.Collectors;

public final class Translator {

    private Translator() {}

    public static void log(String msg) {
        Bukkit.getLogger().log(Level.INFO, Translator.color(msg));
    }

    public static String color(String msg) {
        if(msg == null) return "NULL";
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    public static List<String> colorList(List<String> list) {
        return list.stream().map(Translator::color)
                .collect(Collectors.toList());
    }

    public static String formatCase(String txt) {
        String firstChar = String.valueOf(txt.charAt(0)).toUpperCase();

        StringBuilder restOfText = new StringBuilder();
        for (int i = 1; i < txt.length(); i++) {
            restOfText.append(String.valueOf(txt.charAt(i)).toLowerCase());
        }

        return Translator.color(firstChar + restOfText);
    }

    public static String formatMoney(int money) {
        return NumberFormat.getCurrencyInstance(Locale.US).format(money);
    }


    public static String formatMoney(double money) {
        return NumberFormat.getCurrencyInstance(Locale.US).format(money);
    }

    public static String formatBoolean(boolean value) {
        return  value ? "&atrue" : "&cfalse";
    }

    public static String reverseString(String str) {

        StringBuilder text = new StringBuilder();
        char[] chars = text.toString().toCharArray();
        for(int i = chars.length-1; i >=0; i--) {
            text.append(chars[i]);
        }
        return text.toString();
    }




    public static boolean canBeNumber(String str) {
        try {
            Integer.parseInt(str);
            return true;
        }catch (NumberFormatException ex) {
            return false;
        }
    }


    public static String genId(int idLength) {
        return UUID.randomUUID().toString()
                .substring(0, idLength).toUpperCase();
    }



}
