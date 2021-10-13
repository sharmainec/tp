package lingogo.logic.parser;

import static lingogo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.io.File;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;

import lingogo.logic.commands.UploadCommand;
import lingogo.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new UploadCommand object
 */
public class UploadCommandParser implements Parser<UploadCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the UploadCommand
     * and returns a UploadCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public UploadCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        File f = new File(trimmedArgs);
        if (!f.exists()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UploadCommand.MESSAGE_USAGE));
        }

        try {
            Paths.get(trimmedArgs);
        } catch (InvalidPathException | NullPointerException e) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UploadCommand.MESSAGE_USAGE));
        }
        return new UploadCommand(trimmedArgs);
    }
}
