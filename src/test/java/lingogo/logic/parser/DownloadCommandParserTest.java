package lingogo.logic.parser;

import static lingogo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static lingogo.logic.parser.CommandParserTestUtil.assertParseFailure;
import static lingogo.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import lingogo.logic.commands.DownloadCommand;

public class DownloadCommandParserTest {

    private final DownloadCommandParser parser = new DownloadCommandParser();

    @Test
    public void parse_validArgs_returnsDownloadCommand() {
        String userInput = "test.csv";
        assertParseSuccess(parser, userInput, new DownloadCommand(userInput));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        String userInput = "abc";
        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DownloadCommand.MESSAGE_USAGE));
    }
}
