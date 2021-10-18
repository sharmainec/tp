package lingogo.logic.parser;

import static lingogo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static lingogo.logic.commands.CommandTestUtil.INDICES_DESC_DESC_ONE_TWO;
import static lingogo.logic.commands.CommandTestUtil.INVALID_INDICES_DESC;
import static lingogo.logic.commands.CommandTestUtil.INVALID_LANGUAGE_TYPE_DESC;
import static lingogo.logic.commands.CommandTestUtil.LANGUAGE_TYPE_DESC_CHINESE;
import static lingogo.logic.commands.CommandTestUtil.VALID_LANGUAGE_TYPE_CHINESE;
import static lingogo.logic.parser.CommandParserTestUtil.assertParseFailure;
import static lingogo.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static lingogo.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;

import org.junit.jupiter.api.Test;

import lingogo.logic.commands.FilterCommand;
import lingogo.model.flashcard.Phrase;
import lingogo.testutil.FilterBuilderBuilder;

public class FilterCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE);

    private FilterCommandParser parser = new FilterCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // empty user input
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);

        // no prefix for Language type specified
        assertParseFailure(parser, VALID_LANGUAGE_TYPE_CHINESE, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "q/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidLanguagePhrase_failure() {
        assertParseFailure(parser, INVALID_LANGUAGE_TYPE_DESC, Phrase.MESSAGE_CONSTRAINTS); // invalid Language type
    }

    @Test
    public void parse_indexListDoesNotContainOnlyUnsignedInteger_failure() {
        assertParseFailure(parser, INVALID_INDICES_DESC, MESSAGE_INVALID_INDEX);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        String userInput = LANGUAGE_TYPE_DESC_CHINESE + INDICES_DESC_DESC_ONE_TWO;
        Phrase givenPhrase = new Phrase(VALID_LANGUAGE_TYPE_CHINESE);
        FilterCommand expectedCommand =
            new FilterCommand(new FilterBuilderBuilder().withLanguagePhrase(givenPhrase).withIndexList(1, 2).build());
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
