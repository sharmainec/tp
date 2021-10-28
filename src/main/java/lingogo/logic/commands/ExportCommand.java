package lingogo.logic.commands;

import static java.util.Objects.requireNonNull;

import lingogo.commons.core.Messages;
import lingogo.logic.commands.exceptions.CommandException;
import lingogo.model.Model;

/**
 * Exports the existing cards in the flashcard app to a CSV file.
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";
    public static final String COMMAND_DESCRIPTION =
            "Exports flashcards to a CSV file in where your data folder is located.";
    public static final String[] COMMAND_PARAMETERS = new String[] {
            Parameter.FILE_NAME.withCondition("must have a valid file name with .csv extension")
    };
    public static final String[] COMMAND_EXAMPLES = new String[] {
            COMMAND_WORD + " myCards.csv"
    };

    public static final String MESSAGE_USAGE = getUsageMessage();

    public static final String MESSAGE_SUCCESS = "All existing flashcards have been saved in %1$s";

    private final String fileName;

    /**
     * @param fileName of the CSV file to be exported from the flashcard app
     */
    public ExportCommand(String fileName) {
        requireNonNull(fileName);

        this.fileName = fileName;
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
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.isSlideshowActive()) {
            throw new CommandException(Messages.MESSAGE_IN_SLIDESHOW_MODE);
        }

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
