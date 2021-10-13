package lingogo.logic.parser;

import static lingogo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static lingogo.logic.parser.CommandParserTestUtil.assertParseFailure;
import static lingogo.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import lingogo.logic.commands.UploadCommand;

public class UploadCommandParserTest {

    private final UploadCommandParser parser = new UploadCommandParser();

    @Test
    public void parse_validArgs_returnsUploadCommand() {
        String userInput = "./data/myCards.csv";
        assertParseSuccess(parser, userInput, new UploadCommand(userInput));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        String userInput = "abc";
        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, UploadCommand.MESSAGE_USAGE));
    }
}
