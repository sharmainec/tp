package lingogo.logic.parser;

import lingogo.logic.commands.UploadCommand;
import org.junit.jupiter.api.Test;

import static lingogo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static lingogo.logic.parser.CommandParserTestUtil.assertParseFailure;
import static lingogo.logic.parser.CommandParserTestUtil.assertParseSuccess;

public class UploadCommandParserTest {

    private final UploadCommandParser parser = new UploadCommandParser();

    @Test
    public void parse_validArgs_returnsUploadCommand() {
        String USER_INPUT = "./data/myCards.csv";
        assertParseSuccess(parser, USER_INPUT, new UploadCommand(USER_INPUT));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        String USER_INPUT = "abc";
        assertParseFailure(parser, USER_INPUT, String.format(MESSAGE_INVALID_COMMAND_FORMAT, UploadCommand.MESSAGE_USAGE));
    }
}
