package lingogo.logic.commands;

import static java.util.Objects.requireNonNull;

import lingogo.commons.core.Messages;
import lingogo.logic.commands.exceptions.CommandException;
import lingogo.model.Model;

/**
 * Imports the cards in the CSV file to the flashcard app.
 */
public class ImportCommand extends Command {

    public static final String COMMAND_WORD = "import";
    public static final String COMMAND_DESCRIPTION = "Imports flashcards to a CSV file";
    public static final String COMMAND_USAGE = "import FILE_PATH";
    public static final String COMMAND_EXAMPLES = "import ./dictionary.csv";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Imports cards from a CSV file to LingoGO! "
            + "Parameters: FILE_PATH (must be a valid file path with .csv extension)\n"
            + "Example: " + COMMAND_WORD + " ./dictionary.csv";

    public static final String MESSAGE_SUCCESS = "LingoGO! now have been updated with all the cards from %1$s";

    private final String filePath;

    /**
     * @param filePath of the CSV file to be imported to the flashcard app
     */
    public ImportCommand(String filePath) {
        requireNonNull(filePath);

        this.filePath = filePath;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.isSlideshowActive()) {
            throw new CommandException(Messages.MESSAGE_IN_SLIDESHOW_MODE);
        }

        model.importFlashCards(filePath);
        return new CommandResult(String.format(MESSAGE_SUCCESS, filePath));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ImportCommand // instanceof handles nulls
                && filePath.equals(((ImportCommand) other).filePath)); // state check
    }
}
