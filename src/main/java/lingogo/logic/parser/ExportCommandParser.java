package lingogo.logic.parser;

import static lingogo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import lingogo.commons.util.FileUtil;
import lingogo.logic.commands.ExportCommand;
import lingogo.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ExportCommand object
 */
public class ExportCommandParser implements Parser<ExportCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ExportCommand
     * and returns a ExportCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public ExportCommand parse(String args) throws ParseException {
        String fileName = args.trim();
        if (!FileUtil.isValidCsvFileName(fileName)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportCommand.MESSAGE_USAGE));
        }
        return new ExportCommand(fileName);
    }
}
