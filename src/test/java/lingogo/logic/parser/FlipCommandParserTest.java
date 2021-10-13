package lingogo.logic.parser;

import lingogo.logic.commands.FlipCommand;
import org.junit.jupiter.api.Test;

import static lingogo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static lingogo.logic.parser.CommandParserTestUtil.assertParseFailure;
import static lingogo.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static lingogo.testutil.TypicalIndexes.INDEX_FIRST_FLASHCARD;
import static lingogo.testutil.TypicalIndexes.INDEX_SECOND_FLASHCARD;

public class FlipCommandParserTest {
    private FlipCommandParser parser = new FlipCommandParser();

    @Test
    public void parse_validArgs_returnsFlipCommand() {
        assertParseSuccess(parser, "1", new FlipCommand(INDEX_FIRST_FLASHCARD));
        assertParseSuccess(parser, "2", new FlipCommand(INDEX_SECOND_FLASHCARD));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FlipCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "-1", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FlipCommand.MESSAGE_USAGE));
    }
}
