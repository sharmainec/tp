package lingogo.logic.parser;

import static lingogo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static lingogo.logic.parser.CommandParserTestUtil.assertParseFailure;
import static lingogo.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import lingogo.logic.commands.FindCommand;
import lingogo.model.flashcard.EnglishPhraseContainsKeywordsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new EnglishPhraseContainsKeywordsPredicate(Arrays.asList("Hello", "Good")));
        assertParseSuccess(parser, "Hello Good", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Hello \n \t Good  \t", expectedFindCommand);
    }

}
