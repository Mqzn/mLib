package dev.mqzn.lib.commands.api.parsers.impl;

public class IntegerParser implements ArgumentParser<Integer> {


    @Override
    public boolean matches(String arg) {

        try {
            Integer.parseInt(arg);
            return true;
        }catch (NumberFormatException ex) {
            return false;
        }

    }

    @Override
    public Integer parse(String arg) {
        return Integer.parseInt(arg);
    }


}
