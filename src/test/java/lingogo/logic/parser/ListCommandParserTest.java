package lingogo.logic.parser;

import static lingogo.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import lingogo.commons.core.Messages;

public class ListCommandParserTest {

    private ListCommandParser parser = new ListCommandParser();

    @Test
    public void parse_invalidArg_throwsParseException() {
        String expectedMessage = Messages.MESSAGE_INVALID_N;
        assertParseFailure(parser, "0", expectedMessage);
        assertParseFailure(parser, "-1", expectedMessage);
        assertParseFailure(parser, "abc", expectedMessage);
    }
}
