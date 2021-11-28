package dev.mqzn.lib.commands.api.parsers.impl;

public class BooleanParser implements ArgumentParser<Boolean> {


    @Override
    public boolean matches(String arg) {
        return arg.equalsIgnoreCase("true") || arg.equalsIgnoreCase("false");
    }

    @Override
    public Boolean parse(String arg) {
        return Boolean.parseBoolean(arg);
    }


}
