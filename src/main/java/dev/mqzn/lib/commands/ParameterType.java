package dev.mqzn.lib.commands;

public enum ParameterType {

    REQUIRED("<", ">"),
    OPTIONAL("[", "]"),
    NONE("", "");


    String prefix, suffix;
    ParameterType(String prefix, String suffix) {
        this.prefix = prefix;
        this.suffix = suffix;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getSuffix() {
        return suffix;
    }


    static ParameterType fromArg(String arg) {

        for (ParameterType types : ParameterType.values()) {
            if(arg.startsWith(types.getPrefix()) && arg.endsWith(types.suffix)) {
                return types;
            }
        }

        return ParameterType.NONE;
    }


}
