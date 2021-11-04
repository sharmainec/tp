package lingogo.logic.parser;

import static java.util.Objects.requireNonNull;
import static lingogo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static lingogo.logic.parser.CliSyntax.PREFIX_ENGLISH_PHRASE;
import static lingogo.logic.parser.CliSyntax.PREFIX_FOREIGN_PHRASE;
import static lingogo.logic.parser.CliSyntax.PREFIX_LANGUAGE_TYPE;

import lingogo.commons.core.index.Index;
import lingogo.logic.commands.EditCommand;
import lingogo.logic.commands.EditCommand.EditFlashcardDescriptor;
import lingogo.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser implements Parser<EditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_LANGUAGE_TYPE, PREFIX_ENGLISH_PHRASE, PREFIX_FOREIGN_PHRASE);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE), pe);
        }

        EditFlashcardDescriptor editFlashcardDescriptor = new EditFlashcardDescriptor();

        if (argMultimap.getValue(PREFIX_LANGUAGE_TYPE).isPresent()) {
            editFlashcardDescriptor.setLanguageType(
                    ParserUtil.parseLanguageType(argMultimap.getValue(PREFIX_LANGUAGE_TYPE).get()));
        }
        if (argMultimap.getValue(PREFIX_ENGLISH_PHRASE).isPresent()) {
            editFlashcardDescriptor.setEnglishPhrase(
                    ParserUtil.parsePhrase(argMultimap.getValue(PREFIX_ENGLISH_PHRASE).get()));
        }
        if (argMultimap.getValue(PREFIX_FOREIGN_PHRASE).isPresent()) {
            editFlashcardDescriptor.setForeignPhrase(
                    ParserUtil.parsePhrase(argMultimap.getValue(PREFIX_FOREIGN_PHRASE).get()));
        }

        if (!editFlashcardDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(index, editFlashcardDescriptor);
    }

}
