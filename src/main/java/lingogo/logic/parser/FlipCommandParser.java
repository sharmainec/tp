package lingogo.logic.parser;

import static lingogo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import lingogo.commons.core.index.Index;
import lingogo.logic.commands.FlipCommand;
import lingogo.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new FlipCommand object
 */
public class FlipCommandParser implements Parser<FlipCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the FlipCommand
     * and returns a FlipCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FlipCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new FlipCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FlipCommand.MESSAGE_USAGE), pe);
        }
    }
}
