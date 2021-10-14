package lingogo.logic.parser;

import static lingogo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static lingogo.logic.commands.CommandTestUtil.ENGLISH_PHRASE_DESC_GOOD_MORNING;
import static lingogo.logic.commands.CommandTestUtil.INVALID_ENGLISH_PHRASE_DESC;
import static lingogo.logic.commands.CommandTestUtil.VALID_ENGLISH_PHRASE_GOOD_MORNING;
import static lingogo.logic.parser.CommandParserTestUtil.assertParseFailure;
import static lingogo.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static lingogo.testutil.TypicalIndexes.INDEX_SECOND_FLASHCARD;

import org.junit.jupiter.api.Test;

import lingogo.commons.core.index.Index;
import lingogo.logic.commands.TestCommand;
import lingogo.model.flashcard.Phrase;

public class TestCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
        String.format(MESSAGE_INVALID_COMMAND_FORMAT, TestCommand.MESSAGE_USAGE);

    private TestCommandParser parser = new TestCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, ENGLISH_PHRASE_DESC_GOOD_MORNING, MESSAGE_INVALID_FORMAT);

        // no English phrase specified
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);

        // no index and no phrase specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);

        // no prefix for English phrase specified
        assertParseFailure(parser, "1 " + VALID_ENGLISH_PHRASE_GOOD_MORNING, MESSAGE_INVALID_FORMAT);

    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + ENGLISH_PHRASE_DESC_GOOD_MORNING, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + ENGLISH_PHRASE_DESC_GOOD_MORNING, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_ENGLISH_PHRASE_DESC,
            Phrase.MESSAGE_CONSTRAINTS); // invalid English phrase
    }


    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_FLASHCARD;
        String userInput = targetIndex.getOneBased() + ENGLISH_PHRASE_DESC_GOOD_MORNING;
        TestCommand expectedCommand = new TestCommand(targetIndex, new Phrase(VALID_ENGLISH_PHRASE_GOOD_MORNING));
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
