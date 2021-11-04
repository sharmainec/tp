package lingogo.logic.parser;

import static lingogo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static lingogo.logic.parser.CliSyntax.PREFIX_ENGLISH_PHRASE;
import static lingogo.logic.parser.CliSyntax.PREFIX_FOREIGN_PHRASE;
import static lingogo.logic.parser.CliSyntax.PREFIX_LANGUAGE_TYPE;

import java.util.stream.Stream;

import lingogo.logic.commands.AddCommand;
import lingogo.logic.parser.exceptions.ParseException;
import lingogo.model.flashcard.Flashcard;
import lingogo.model.flashcard.LanguageType;
import lingogo.model.flashcard.Phrase;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_LANGUAGE_TYPE, PREFIX_ENGLISH_PHRASE, PREFIX_FOREIGN_PHRASE);

        if (!arePrefixesPresent(argMultimap, PREFIX_LANGUAGE_TYPE, PREFIX_ENGLISH_PHRASE, PREFIX_FOREIGN_PHRASE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        LanguageType languageType = ParserUtil.parseLanguageType(argMultimap.getValue(PREFIX_LANGUAGE_TYPE).get());
        Phrase englishPhrase = ParserUtil.parsePhrase(argMultimap.getValue(PREFIX_ENGLISH_PHRASE).get());
        Phrase foreignPhrase = ParserUtil.parsePhrase(argMultimap.getValue(PREFIX_FOREIGN_PHRASE).get());

        Flashcard flashcard = new Flashcard(languageType, englishPhrase, foreignPhrase);

        return new AddCommand(flashcard);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
