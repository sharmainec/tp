package lingogo.logic.parser;

import static lingogo.commons.core.Messages.MESSAGE_INVALID_CSV_FILE_NAME;

import lingogo.logic.commands.ImportCommand;
import lingogo.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ImportCommand object
 */
public class ImportCommandParser implements Parser<ImportCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ImportCommand
     * and returns a ImportCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public ImportCommand parse(String args) throws ParseException {
        String fileName = args.trim();

        if (!fileName.endsWith(".csv") || fileName.contains("\\") || fileName.contains("/")) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_CSV_FILE_NAME, fileName));
        }

        return new ImportCommand(fileName);
    }
}
