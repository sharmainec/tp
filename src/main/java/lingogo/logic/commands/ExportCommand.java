package lingogo.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

import com.opencsv.CSVWriter;

import lingogo.commons.core.Messages;
import lingogo.logic.LogicManager;
import lingogo.logic.commands.exceptions.CommandException;
import lingogo.model.Model;
import lingogo.model.flashcard.Flashcard;

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

    public static final String MESSAGE_USAGE =
            getMessageUsage(COMMAND_WORD, COMMAND_DESCRIPTION, COMMAND_PARAMETERS, COMMAND_EXAMPLES);

    public static final String MESSAGE_SUCCESS = "All existing flashcards have been saved in %1$s";

    private static final String[] csvHeaders = {"Language", "Foreign", "English"};
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

        if (model.isSlideshowActive()) {
            throw new CommandException(Messages.MESSAGE_IN_SLIDESHOW_MODE);
        }

        try {
            exportHelper(model);
        } catch (Exception ioe) {
            throw new CommandException(String.format(LogicManager.EXPORT_IOEXCEPTION, fileName));
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, fileName));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ExportCommand // instanceof handles nulls
                && fileName.equals(((ExportCommand) other).fileName)); // state check
    }

    /**
     * Uses CSVWriter to export the contents of internalList to {@code fileName}.
     * @param model allows access to the current list of flashcards
     * @throws Exception for IOException involved when writing a new file
     */
    public void exportHelper(Model model) throws Exception {
        String file = "data/" + fileName;
        CSVWriter writer = new CSVWriter(
                new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8));
        writer.writeNext(csvHeaders);
        String[] line;
        for (Flashcard card : model.getFilteredFlashcardList()) {
            line = new String[]{card.getLanguageType().value,
                    card.getForeignPhrase().value, card.getEnglishPhrase().value};
            writer.writeNext(line);
        }
        writer.close();
    }
}
