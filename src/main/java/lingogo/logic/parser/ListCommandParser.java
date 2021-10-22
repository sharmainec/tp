package lingogo.logic.parser;

import static lingogo.commons.util.StringUtil.isNonZeroUnsignedInteger;

import lingogo.logic.commands.ListCommand;

/**
 * Parses input arguments and creates a new ListCommand object
 */
public class ListCommandParser implements Parser<ListCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the ListCommand
     * and returns a ListCommand object for execution.
     * @throws NumberFormatException if the user input does not conform the expected format
     */
    public ListCommand parse(String args) {
        String trimmedArgs = args.trim();
        if (!isNonZeroUnsignedInteger(trimmedArgs)) {
            return new ListCommand();
        }
        int n = Integer.parseInt(trimmedArgs);
        return new ListCommand(n);
    }
}
