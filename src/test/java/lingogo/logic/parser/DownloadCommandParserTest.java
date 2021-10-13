package lingogo.logic.parser;

import lingogo.logic.commands.DownloadCommand;
import org.junit.jupiter.api.Test;

import static lingogo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static lingogo.logic.parser.CommandParserTestUtil.assertParseFailure;
import static lingogo.logic.parser.CommandParserTestUtil.assertParseSuccess;

public class DownloadCommandParserTest {

    private final DownloadCommandParser parser = new DownloadCommandParser();

    @Test
    public void parse_validArgs_returnsDownloadCommand() {
        String USER_INPUT = "test.csv";
        assertParseSuccess(parser, USER_INPUT, new DownloadCommand(USER_INPUT));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        String USER_INPUT = "abc";
        assertParseFailure(parser, USER_INPUT, String.format(MESSAGE_INVALID_COMMAND_FORMAT, DownloadCommand.MESSAGE_USAGE));
    }
}
