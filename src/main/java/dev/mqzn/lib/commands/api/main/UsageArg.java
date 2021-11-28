package dev.mqzn.lib.commands.api.main;

import com.google.common.base.Objects;

public class UsageArg implements Comparable<UsageArg>{

    private final String name;
    private final int position;
    private final Class<?> typeClass;
    private final ArgumentType argumentType;

    public UsageArg(String name, int position, Class<?> typeClass, ArgumentType argumentType) {
        this.name = name;
        this.position = position;
        this.typeClass = typeClass;
        this.argumentType = argumentType;
    }

    public ArgumentType getArgumentType() {
        return argumentType;
    }

    public String fullName() {
        return argumentType.getPrefix() + name + argumentType.getSuffix();
    }

    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }

    public Class<?> getTypeClass() {
        return typeClass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UsageArg)) return false;
        UsageArg usageArg = (UsageArg) o;
        return getPosition() == usageArg.getPosition() &&
                Objects.equal(getName(), usageArg.getName()) &&
                Objects.equal(getTypeClass(), usageArg.getTypeClass());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getName(), getPosition(), getTypeClass());
    }

    @Override
    public String toString() {
        return "<" + this.getName().toUpperCase() + "> ";
    }

    @Override
    public int compareTo(UsageArg o) {
        return this.getPosition()-o.getPosition();
    }


    public enum ArgumentType {

        OPTIONAL("[", "]"),
        REQUIRED("<", ">"),
        NONE("", "");

        private final String prefix, suffix;

        ArgumentType(String prefix, String suffix) {
            this.prefix = prefix;
            this.suffix = suffix;
        }

        public String getPrefix() {
            return prefix;
        }

        public String getSuffix() {
            return suffix;
        }

    }
}
