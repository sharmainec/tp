package lingogo.logic.parser;

import static lingogo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static lingogo.logic.parser.CommandParserTestUtil.assertParseFailure;
import static lingogo.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import lingogo.logic.commands.FindCommand;
import lingogo.model.flashcard.EnglishPhraseContainsKeywordsPredicate;
import lingogo.model.flashcard.ForeignPhraseContainsKeywordsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validEnglishArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new EnglishPhraseContainsKeywordsPredicate(Arrays.asList("Hello", "Good")));
        assertParseSuccess(parser, " e/Hello Good", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " e/\n Hello \n \t Good  \t", expectedFindCommand);
    }

    @Test
    public void parse_validForeignArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new ForeignPhraseContainsKeywordsPredicate(Arrays.asList("你好", "早")));
        assertParseSuccess(parser, " f/你好 早", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " f/\n 你好 \n \t 早  \t", expectedFindCommand);
    }
}
