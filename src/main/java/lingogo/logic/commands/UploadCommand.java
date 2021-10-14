package lingogo.logic.commands;

import static java.util.Objects.requireNonNull;

import lingogo.logic.commands.exceptions.CommandException;
import lingogo.model.Model;

/**
 * Imports the cards in the CSV file to the flashcard app.
 */
public class UploadCommand extends Command {

    public static final String COMMAND_WORD = "upload";
    public static final String COMMAND_DESCRIPTION = "Imports flashcards to a CSV file";
    public static final String COMMAND_USAGE = "upload FILE_PATH";
    public static final String COMMAND_EXAMPLES = "upload ./dictionary.csv";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Imports cards from a CSV file to LingoGO! "
            + "Parameters: FILE_PATH (must be a valid file path with .csv extension)\n"
            + "Example: " + COMMAND_WORD + " ./dictionary.csv";

    public static final String MESSAGE_SUCCESS = "LingoGO! now have been updated with all the cards from %1$s";

    private final String filePath;

    /**
     * @param filePath of the CSV file to be uploaded to the flashcard app
     */
    public UploadCommand(String filePath) {
        requireNonNull(filePath);

        this.filePath = filePath;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.uploadFlashCards(filePath);
        return new CommandResult(String.format(MESSAGE_SUCCESS, filePath));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UploadCommand // instanceof handles nulls
                && filePath.equals(((UploadCommand) other).filePath)); // state check
    }
}
