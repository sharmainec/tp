package lingogo.logic.parser;

import static lingogo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static lingogo.logic.commands.CommandTestUtil.ENGLISH_PHRASE_DESC_GOOD_MORNING;
import static lingogo.logic.commands.CommandTestUtil.INVALID_ENGLISH_PHRASE_DESC;
import static lingogo.logic.commands.CommandTestUtil.VALID_ENGLISH_PHRASE_GOOD_MORNING;
import static lingogo.logic.parser.CommandParserTestUtil.assertParseFailure;
import static lingogo.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import lingogo.logic.commands.AnswerCommand;
import lingogo.model.flashcard.Phrase;

public class AnswerCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
        String.format(MESSAGE_INVALID_COMMAND_FORMAT, AnswerCommand.MESSAGE_USAGE);

    private AnswerCommandParser parser = new AnswerCommandParser();

    @Test
    public void parse_missingParts_failure() {

        // no English phrase specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);

        // no prefix for English phrase specified
        assertParseFailure(parser, VALID_ENGLISH_PHRASE_GOOD_MORNING, MESSAGE_INVALID_FORMAT);

    }

    @Test
    public void parse_invalidPreamble_failure() {

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, INVALID_ENGLISH_PHRASE_DESC, Phrase.MESSAGE_CONSTRAINTS); // invalid English phrase
    }


    @Test
    public void parse_allFieldsSpecified_success() {
        String userInput = ENGLISH_PHRASE_DESC_GOOD_MORNING;
        AnswerCommand expectedCommand = new AnswerCommand(new Phrase(VALID_ENGLISH_PHRASE_GOOD_MORNING));
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
