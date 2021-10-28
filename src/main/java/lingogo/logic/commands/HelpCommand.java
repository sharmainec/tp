package lingogo.logic.commands;

import lingogo.model.Model;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";
    public static final String COMMAND_DESCRIPTION =
            "Shows a link to the help page and explanations for each command.";
    public static final String[] COMMAND_PARAMETERS = new String[0];
    public static final String[] COMMAND_EXAMPLES = new String[] { COMMAND_WORD };

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Use this command to access the help page, "
            + "which shows details about each command in LingoGO!";

    public static final String SHOWING_HELP_MESSAGE = "Opened help window.";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(SHOWING_HELP_MESSAGE, true, false);
    }
}
