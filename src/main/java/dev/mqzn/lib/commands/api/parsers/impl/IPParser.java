package dev.mqzn.lib.commands.api.parsers.impl;

import dev.mqzn.lib.commands.api.parsers.IP;
import dev.mqzn.lib.commands.api.parsers.impl.ArgumentParser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IPParser implements ArgumentParser<IP> {


    @Override
    public boolean matches(String arg) {

        String range = "(\\d{1,2}|([01])\\" + "d{2}|2[0-4]\\d|25[0-5])";

        String regex = range + "\\." + range + "\\." + range + "\\." + range;

        Pattern p = Pattern.compile(regex);

        // If the IP address is empty
        // return false
        if (arg == null) return false;

        Matcher m = p.matcher(arg);
        return m.matches();

    }

    @Override
    public IP parse(String arg) {

        if(!matches(arg)) {
            return null;
        }

        return IP.from(arg);
    }



}
