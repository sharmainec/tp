package lingogo.logic.parser;

import static java.util.Objects.requireNonNull;
import static lingogo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static lingogo.logic.parser.CliSyntax.PREFIX_ENGLISH_PHRASE;

import lingogo.commons.core.index.Index;
import lingogo.logic.commands.TestCommand;
import lingogo.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new TestCommand object
 */
public class TestCommandParser implements Parser<TestCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the TestCommand
     * and returns a TestCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public TestCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_ENGLISH_PHRASE);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TestCommand.MESSAGE_USAGE), pe);
        }

        if (!argMultimap.getValue(PREFIX_ENGLISH_PHRASE).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TestCommand.MESSAGE_USAGE));
        }

        return new TestCommand(index, ParserUtil.parsePhrase(argMultimap.getValue(PREFIX_ENGLISH_PHRASE).get()));
    }

}
