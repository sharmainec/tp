package lingogo.logic.parser;

import static lingogo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import lingogo.commons.util.FileUtil;
import lingogo.logic.commands.DownloadCommand;
import lingogo.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DownloadCommand object
 */
public class DownloadCommandParser implements Parser<DownloadCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DownloadCommand
     * and returns a DownloadCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public DownloadCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (!FileUtil.isValidCSVFileName(trimmedArgs)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DownloadCommand.MESSAGE_USAGE));
        }
        return new DownloadCommand(trimmedArgs);
    }
}
