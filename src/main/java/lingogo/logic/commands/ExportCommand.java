package lingogo.logic.commands;

import static java.util.Objects.requireNonNull;

import lingogo.logic.commands.exceptions.CommandException;
import lingogo.model.Model;

/**
 * Exports the existing cards in the flashcard app to a CSV file.
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";
    public static final String COMMAND_DESCRIPTION = "Exports flashcards to a CSV file";
    public static final String COMMAND_USAGE = "export FILE_NAME";
    public static final String COMMAND_EXAMPLES = "export myCards.csv";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Exports cards from LingoGO! to a CSV file. "
            + "Parameters: FILE_NAME (must have a valid file name with .csv extension)\n"
            + "Example: " + COMMAND_WORD + " myCards.csv";

    public static final String MESSAGE_SUCCESS = "All existing flashcards have been saved in %1$s";

    private final String fileName;

    /**
     * @param fileName of the CSV file to be exported from the flashcard app
     */
    public ExportCommand(String fileName) {
        requireNonNull(fileName);

        this.fileName = fileName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.exportFlashCards(fileName);
        return new CommandResult(String.format(MESSAGE_SUCCESS, fileName));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ExportCommand // instanceof handles nulls
                && fileName.equals(((ExportCommand) other).fileName)); // state check
    }
}
