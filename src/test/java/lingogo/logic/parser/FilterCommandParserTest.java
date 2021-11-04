package lingogo.logic.parser;

import static lingogo.commons.core.Messages.MESSAGE_INDEX_IS_NOT_NON_ZERO_UNSIGNED_INT;
import static lingogo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static lingogo.commons.core.Messages.MESSAGE_INVALID_INDEX_RANGE;
import static lingogo.logic.commands.CommandTestUtil.INDICES_DESC_DESC_DUPLICATE_ONES;
import static lingogo.logic.commands.CommandTestUtil.INDICES_DESC_DESC_ONE_TWO;
import static lingogo.logic.commands.CommandTestUtil.INVALID_INDEX_RANGE;
import static lingogo.logic.commands.CommandTestUtil.INVALID_INDICES_DESC;
import static lingogo.logic.commands.CommandTestUtil.INVALID_LANGUAGE_TYPE_DESC;
import static lingogo.logic.commands.CommandTestUtil.INVALID_NEGATIVE_INDEX_DESC;
import static lingogo.logic.commands.CommandTestUtil.INVALID_NON_PAIR_RANGE;
import static lingogo.logic.commands.CommandTestUtil.INVALID_REVERSED_RANGE;
import static lingogo.logic.commands.CommandTestUtil.INVALID_VERY_LARGE_INDICES_DESC;
import static lingogo.logic.commands.CommandTestUtil.LANGUAGE_TYPE_DESC_CHINESE;
import static lingogo.logic.commands.CommandTestUtil.RANGE_DESC_TWO_FOUR;
import static lingogo.logic.commands.CommandTestUtil.VALID_LANGUAGE_TYPE_CHINESE;
import static lingogo.logic.parser.CommandParserTestUtil.assertParseFailure;
import static lingogo.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import lingogo.logic.commands.FilterCommand;
import lingogo.model.flashcard.LanguageType;
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
    public void parse_invalidLanguageField_failure() {
        // invalid Language type
        assertParseFailure(parser, INVALID_LANGUAGE_TYPE_DESC, LanguageType.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidReversedRangeField_failure() {
        assertParseFailure(parser, INVALID_REVERSED_RANGE, MESSAGE_INVALID_INDEX_RANGE);
    }

    @Test
    public void parse_invalidNonPairRangeField_failure() {
        assertParseFailure(parser, INVALID_NON_PAIR_RANGE, MESSAGE_INVALID_INDEX_RANGE);
    }

    @Test
    public void parse_invalidIndexRangeField_failure() {
        assertParseFailure(parser, INVALID_INDEX_RANGE, MESSAGE_INDEX_IS_NOT_NON_ZERO_UNSIGNED_INT);
    }

    @Test
    public void parse_indexListContainsNegativeIndex_failure() {
        assertParseFailure(parser, INVALID_NEGATIVE_INDEX_DESC, MESSAGE_INDEX_IS_NOT_NON_ZERO_UNSIGNED_INT);
    }

    @Test
    public void parse_indexListContainsNonInteger_failure() {
        assertParseFailure(parser, INVALID_INDICES_DESC, MESSAGE_INDEX_IS_NOT_NON_ZERO_UNSIGNED_INT);
    }

    @Test
    public void parse_indexListContainsVeryLargeNumber_failure() {
        assertParseFailure(parser, INVALID_VERY_LARGE_INDICES_DESC, MESSAGE_INDEX_IS_NOT_NON_ZERO_UNSIGNED_INT);
    }

    @Test
    public void parse_validLanguageFieldButIndexListDoesNotContainOnlyUnsignedInteger_failure() {
        assertParseFailure(parser, LANGUAGE_TYPE_DESC_CHINESE + INVALID_INDICES_DESC,
                MESSAGE_INDEX_IS_NOT_NON_ZERO_UNSIGNED_INT);
    }

    @Test
    public void parse_validIndexButInvalidLanguageField_failure() {
        assertParseFailure(parser, INVALID_LANGUAGE_TYPE_DESC + INDICES_DESC_DESC_ONE_TWO,
                LanguageType.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_validLanguageButInvalidRangeField_failure() {
        assertParseFailure(parser, LANGUAGE_TYPE_DESC_CHINESE + INVALID_REVERSED_RANGE,
                MESSAGE_INVALID_INDEX_RANGE);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        String userInput = LANGUAGE_TYPE_DESC_CHINESE + INDICES_DESC_DESC_ONE_TWO + RANGE_DESC_TWO_FOUR;
        LanguageType givenLanguageType = new LanguageType(VALID_LANGUAGE_TYPE_CHINESE);
        FilterCommand expectedCommand = new FilterCommand(new FilterBuilderBuilder()
                .withLanguageType(givenLanguageType).withIndexList(1, 2).withRange(2, 4).build());
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_languageFieldSpecified_success() {
        String userInput = LANGUAGE_TYPE_DESC_CHINESE;
        LanguageType givenLanguageType = new LanguageType(VALID_LANGUAGE_TYPE_CHINESE);
        FilterCommand expectedCommand =
                new FilterCommand(new FilterBuilderBuilder().withLanguageType(givenLanguageType).build());
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_indexListFieldSpecified_success() {
        String userInput = INDICES_DESC_DESC_ONE_TWO;
        FilterCommand expectedCommand =
                new FilterCommand(new FilterBuilderBuilder().withIndexList(1, 2).build());
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_rangeFieldSpecified_success() {
        String userInput = RANGE_DESC_TWO_FOUR;
        FilterCommand expectedCommand =
                new FilterCommand(new FilterBuilderBuilder().withRange(2, 4).build());
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_duplicateIndexListFieldSpecified_success() {
        String userInput = INDICES_DESC_DESC_DUPLICATE_ONES;
        FilterCommand expectedCommand =
                new FilterCommand(new FilterBuilderBuilder().withIndexList(1, 1).build());
        assertParseSuccess(parser, userInput, expectedCommand);
    }


    @Test
    public void parse_unevenWhiteSpaceSeperatedIndexListFieldSpecified_success() {
        String userInput = " i/1   4 \n 5";
        FilterCommand expectedCommand =
            new FilterCommand(new FilterBuilderBuilder().withIndexList(1, 4, 5).build());
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_whiteSpaceSeperatedRangeFieldSpecified_success() {
        String userInput = " r/1           \n 5";
        FilterCommand expectedCommand =
            new FilterCommand(new FilterBuilderBuilder().withRange(1, 5).build());
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
