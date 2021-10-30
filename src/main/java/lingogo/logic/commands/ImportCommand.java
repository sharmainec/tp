package lingogo.logic.commands;

import static java.util.Objects.requireNonNull;
import static lingogo.commons.core.Messages.MESSAGE_INVALID_CSV_FORMAT;
import static lingogo.logic.LogicManager.FILE_OPS_ERROR_MESSAGE;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;

import lingogo.commons.core.Messages;
import lingogo.logic.commands.exceptions.CommandException;
import lingogo.model.Model;
import lingogo.model.flashcard.Flashcard;
import lingogo.model.flashcard.Phrase;

/**
 * Imports the cards in the CSV file to the flashcard app.
 */
public class ImportCommand extends Command {

    public static final String COMMAND_WORD = "import";
    public static final String COMMAND_DESCRIPTION = "Imports flashcards from a CSV file into LingoGO!";

    public static final String[] COMMAND_PARAMETERS = new String[] {
        Parameter.CSV_FILE_NAME.withCondition("must exist in the data folder and have .csv extension")
    };
    public static final String[] COMMAND_EXAMPLES = new String[] {
        COMMAND_WORD + " dictionary.csv"
    };

    public static final String MESSAGE_USAGE =
            getMessageUsage(COMMAND_WORD, COMMAND_DESCRIPTION, COMMAND_PARAMETERS, COMMAND_EXAMPLES);

    public static final String MESSAGE_SUCCESS = "LingoGO! has been updated with all the flashcards from %1$s";

    private static final String[] csvHeaders = {"Language", "Foreign", "English"};
    private final String fileName;

    /**
     * @param fileName of the CSV file to be imported to the flashcard app
     */
    public ImportCommand(String fileName) {
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
            importHelper(model);
        } catch (CsvValidationException e) {
            throw new CommandException(String.format(MESSAGE_INVALID_CSV_FORMAT, fileName));
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, fileName));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ImportCommand // instanceof handles nulls
                && fileName.equals(((ImportCommand) other).fileName)); // state check
    }

    /**
     * Uses CSVReader to import the contents of the {@code fileName} to {@code model}.
     * @param model allows access to the current list of flashcards
     * @throws CommandException to indicate that CSV file content is not of the correct format
     * @throws CsvValidationException when CSV file is not valid
     * @throws IOException involved when reading from an external file
     */
    public void importHelper(Model model) throws CommandException, CsvValidationException, IOException {
        String file = "data/" + fileName;
        CSVReader reader = new CSVReaderBuilder(
                new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8)).build();
        String[] line = reader.readNext();
        if (!Arrays.toString(line).equals(Arrays.toString(csvHeaders))) {
            throw new CommandException(String.format(MESSAGE_INVALID_CSV_FORMAT, fileName));
        }
        while ((line = reader.readNext()) != null) {
            if (line.length != 3 || line[0].isBlank() || line[1].isBlank() || line[2].isBlank()) {
                throw new CommandException(String.format(MESSAGE_INVALID_CSV_FORMAT, fileName));
            }
            Flashcard card = new Flashcard(new Phrase(line[0]), new Phrase(line[2]), new Phrase(line[1]));
            if (!model.hasFlashcard(card)) {
                model.addFlashcard(card);
            }
        }
    }
}
