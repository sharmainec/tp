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
    public static final String COMMAND_DESCRIPTION = "Imports flashcards from a CSV file into LingoGO!";

    public static final String[] COMMAND_PARAMETERS = new String[] {
            Parameter.CSV_FILE_PATH.withCondition("must have a valid file name with .csv extension")
    };
    public static final String[] COMMAND_EXAMPLES = new String[] {
            COMMAND_WORD + " data/dictionary.csv"
    };

    public static final String MESSAGE_USAGE = getUsageMessage();

    public static final String MESSAGE_SUCCESS = "LingoGO! has been updated with all the flashcards from %1$s";

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

    private static String getUsageMessage() {
        StringBuilder sb = new StringBuilder();
        sb.append(COMMAND_WORD).append(": ");
        sb.append(COMMAND_DESCRIPTION);
        sb.append("\n");
        sb.append("Parameters:");
        for (String parameter : COMMAND_PARAMETERS) {
            sb.append(" ").append(parameter);
        }
        sb.append("\n");
        sb.append("Examples:");
        for (String example : COMMAND_EXAMPLES) {
            sb.append(" ").append(example);
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ImportCommand // instanceof handles nulls
                && filePath.equals(((ImportCommand) other).filePath)); // state check
    }
}
