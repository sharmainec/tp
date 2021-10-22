package lingogo.logic.parser;

import lingogo.logic.commands.ListCommand;
import lingogo.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ListCommand object
 */
public class ListCommandParser implements Parser<ListCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the ListCommand
     * and returns a ListCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.matches("^[0-9]+$")) {
            int n = Integer.parseInt(trimmedArgs);

            return new ListCommand(n);
        }
        return new ListCommand();
    }
}
