package dev.mqzn.lib.commands;

public class CommandParameter {

    private final String parameter;
    private final int index;
    private final ParameterType parameterType;

    public CommandParameter(String parameter, int index, ParameterType parameterType) {
        this.parameter = parameter;
        this.index = index;
        this.parameterType = parameterType;
    }

    public ParameterType getParameterType() {
        return parameterType;
    }


    public String getParameter() {
        return parameter;
    }

    public int getIndex() {
        return index;
    }



}
