package lingogo.logic.commands;

import lingogo.model.Model;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";
    public static final String COMMAND_DESCRIPTION = "Shows a link to the help page and brief explanations for each"
            + " command";
    public static final String COMMAND_USAGE = "help";
    public static final String COMMAND_EXAMPLES = "help";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows program usage instructions.\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_HELP_MESSAGE = "Opened help window.";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(SHOWING_HELP_MESSAGE, true, false);
    }
}
