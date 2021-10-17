package lingogo.logic.commands;

import lingogo.model.Model;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command {

    public static final String COMMAND_WORD = "exit";
    public static final String COMMAND_DESCRIPTION = "Exits LingoGO!";
    public static final String COMMAND_USAGE = "exit";
    public static final String COMMAND_EXAMPLES = "exit";

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting LingoGO! as requested ...";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, false, true);
    }

}