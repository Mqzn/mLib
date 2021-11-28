package dev.mqzn.lib.commands.api.main;

import com.google.common.base.Objects;

public final class CommandArg {

    private final String arg;
    private final int index;
    private final Object parsedArg;

    public CommandArg(int index, Object parsedArg, String arg) {
        this.index = index;
        this.parsedArg = parsedArg;
        this.arg = arg;
    }

    public int getIndex() {
        return index;
    }

    public Object getParsedArg() {
        return parsedArg;
    }

    public String getArgument() {
        return arg;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CommandArg)) return false;
        CommandArg that = (CommandArg) o;
        return getIndex() == that.getIndex() &&
                Objects.equal(getArgument(), that.getArgument());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getArgument(), getIndex());
    }

}
