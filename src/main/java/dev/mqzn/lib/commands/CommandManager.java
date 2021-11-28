package dev.mqzn.lib.commands;

import dev.mqzn.lib.commands.api.main.Requirement;
import dev.mqzn.lib.commands.api.parsers.impl.IPParser;
import dev.mqzn.lib.commands.api.parsers.impl.ArgumentParser;
import dev.mqzn.lib.commands.api.main.MCommand;
import dev.mqzn.lib.commands.api.parsers.IP;
import dev.mqzn.lib.commands.api.parsers.impl.BooleanParser;
import dev.mqzn.lib.commands.api.parsers.impl.DoubleParser;
import dev.mqzn.lib.commands.api.parsers.impl.IntegerParser;
import dev.mqzn.lib.commands.resolvers.ArgsResolver;
import dev.mqzn.lib.commands.resolvers.impl.DefaultResolver;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class CommandManager {

    private static CommandManager INSTANCE;

    public static CommandManager getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new CommandManager();
        }
        return INSTANCE;
    }

    private CommandMap commandMap;
    private final Map<Class<?>, ArgumentParser<?>> parsers;

    private static final ArgsResolver<Requirement> DEFAULT_Resolver = new DefaultResolver<>();
    private static final ArgumentParser<String>DEFAULT_PARSER = new ArgumentParser<String>() {

        @Override
        public boolean matches(String arg) {
            return true;
        }

        @Override
        public String parse(String arg) {
            return arg;
        }
    };

    private CommandManager() {
        try {
            Field field = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            field.setAccessible(true);
            commandMap = (CommandMap) field.get(Bukkit.getServer());

        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        parsers = new LinkedHashMap<>();

        DoubleParser doubleParser = new DoubleParser();
        parsers.put(double.class, doubleParser);
        parsers.put(Double.class, doubleParser);

        IntegerParser integerParser = new IntegerParser();
        parsers.put(int.class, integerParser);
        parsers.put(Integer.class, integerParser);

        BooleanParser booleanParser = new BooleanParser();
        parsers.put(boolean.class, booleanParser);
        parsers.put(Boolean.class, booleanParser);

        parsers.put(IP.class, new IPParser());
        parsers.put(String.class, DEFAULT_PARSER);

    }


    public Collection<? extends ArgumentParser<?>> getParsers() {
        return parsers.values();
    }

    public void registerCommand(MCommand mCommand) {
        commandMap.register(mCommand.getName(), mCommand);
    }


    public boolean hasArgumentParser (Class<?> type){
        return parsers.containsKey(type);
    }

    public ArgumentParser<?> getArgumentParser (Class<?> type){
        if(!hasArgumentParser(type)) throw new IllegalArgumentException("Argument Parser for type " + type.getName() + " isn't registered!");
        return parsers.getOrDefault(type, DEFAULT_PARSER);
    }


    public Class<?> getClazzType(String arg) {

        for(ArgumentParser<?> parser : this.getParsers()) {
            if(parser.matches(arg)) {
                return parser.parse(arg).getClass();
            }
        }

        return String.class;
    }

    public static ArgsResolver<Requirement> getDefaultResolver() {
        return DEFAULT_Resolver;
    }

}
