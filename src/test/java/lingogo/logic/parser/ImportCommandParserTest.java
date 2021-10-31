package lingogo.logic.parser;

import static lingogo.commons.core.Messages.MESSAGE_FILE_NOT_FOUND;
import static lingogo.commons.core.Messages.MESSAGE_INVALID_CSV_FILE_NAME;
import static lingogo.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

public class ImportCommandParserTest {

    private final ImportCommandParser parser = new ImportCommandParser();

    @Test
    public void parse_invalidArgs_throwsParseException() {
        String userInput = "abc";
        assertParseFailure(parser, userInput,
                String.format(MESSAGE_INVALID_CSV_FILE_NAME, userInput));
    }

    @Test
    public void parse_nonExistentFile_throwsParseException() {
        String userInput = "x.csv";
        assertParseFailure(parser, userInput,
                String.format(MESSAGE_FILE_NOT_FOUND, userInput));
    }
}
