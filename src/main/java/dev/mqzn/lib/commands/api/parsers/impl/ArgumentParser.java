package dev.mqzn.lib.commands.api.parsers.impl;

public interface ArgumentParser<T> {



    boolean matches(String arg);

    /**
     *
     * parse the object
     *
     * @param arg the argument in it's string form
     * @return the arg object
     */


    T parse(String arg);

}
