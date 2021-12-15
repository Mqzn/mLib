package dev.mqzn.lib.commands.exceptions;

import dev.mqzn.lib.commands.CommandSyntax;
import dev.mqzn.lib.commands.MCommand;

public class IllegalSyntaxProvidedException extends Exception {

    /**
     * Constructs a new exception with the specified detail message.  The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     *
     * @param syntax the syntax trying to provide inside the command
     * @param command the command to add the syntax to
     * @see MCommand
     * @see CommandSyntax
     */

    public IllegalSyntaxProvidedException(MCommand command, String syntax) {
        super("Error occurred while trying to add the Syntax: " + syntax + "\n" +
                "The syntax MUSTN'T START WITH '/' OR '/" + command.getName() + "' " +
                "OR '" + command.getName() + "'");
    }
}
