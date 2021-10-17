package lingogo.logic.parser;

import static java.util.Objects.requireNonNull;
import static lingogo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static lingogo.logic.parser.CliSyntax.PREFIX_LANGUAGE_TYPE;

import lingogo.logic.commands.FilterCommand;
import lingogo.logic.parser.exceptions.ParseException;
import lingogo.model.flashcard.LanguageTypeMatchesGivenPhrasePredicate;
import lingogo.model.flashcard.Phrase;

/**
 * Parses input arguments and creates a new FilterCommand object
 */
public class FilterCommandParser implements Parser<FilterCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FilterCommand
     * and returns a FilterCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public FilterCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_LANGUAGE_TYPE);

        if (!argMultimap.getValue(PREFIX_LANGUAGE_TYPE).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }

        Phrase languageType = ParserUtil.parsePhrase(argMultimap.getValue(PREFIX_LANGUAGE_TYPE).get());

        return new FilterCommand(new LanguageTypeMatchesGivenPhrasePredicate(languageType));
    }
}
