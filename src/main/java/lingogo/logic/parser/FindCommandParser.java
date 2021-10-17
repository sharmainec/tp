package lingogo.logic.parser;

import static java.util.Objects.requireNonNull;
import static lingogo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static lingogo.logic.parser.CliSyntax.PREFIX_ENGLISH_PHRASE;
import static lingogo.logic.parser.CliSyntax.PREFIX_FOREIGN_PHRASE;

import java.util.Arrays;

import org.apache.commons.lang3.ArrayUtils;

import lingogo.logic.commands.FindCommand;
import lingogo.logic.parser.exceptions.ParseException;
import lingogo.model.flashcard.EnglishPhraseContainsKeywordsPredicate;
import lingogo.model.flashcard.ForeignPhraseContainsKeywordsPredicate;
import lingogo.model.flashcard.PhraseContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_ENGLISH_PHRASE, PREFIX_FOREIGN_PHRASE);

        if (argMultimap.getValue(PREFIX_ENGLISH_PHRASE).isPresent()
                && argMultimap.getValue(PREFIX_FOREIGN_PHRASE).isPresent()) {
            String[] englishPhraseKeywords = argMultimap.getValue(PREFIX_ENGLISH_PHRASE).get().split("\\s+");
            String[] foreignPhraseKeywords = argMultimap.getValue(PREFIX_FOREIGN_PHRASE).get().split("\\s+");
            String[] phraseKeywords = ArrayUtils.addAll(englishPhraseKeywords, foreignPhraseKeywords);
            return new FindCommand(new PhraseContainsKeywordsPredicate(Arrays.asList(phraseKeywords)));
        } else if (argMultimap.getValue(PREFIX_ENGLISH_PHRASE).isPresent()) {
            String[] englishPhraseKeywords = argMultimap.getValue(PREFIX_ENGLISH_PHRASE).get().split("\\s+");
            return new FindCommand(new EnglishPhraseContainsKeywordsPredicate(Arrays.asList(englishPhraseKeywords)));
        } else if (argMultimap.getValue(PREFIX_FOREIGN_PHRASE).isPresent()) {
            String[] foreignPhraseKeywords = argMultimap.getValue(PREFIX_FOREIGN_PHRASE).get().split("\\s+");
            return new FindCommand(new ForeignPhraseContainsKeywordsPredicate(Arrays.asList(foreignPhraseKeywords)));
        } else {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
    }

}
