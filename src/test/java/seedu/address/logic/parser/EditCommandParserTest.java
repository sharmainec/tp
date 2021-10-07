package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.CHINESE_PHRASE_DESC_GOOD_MORNING;
import static seedu.address.logic.commands.CommandTestUtil.CHINESE_PHRASE_DESC_HELLO;
import static seedu.address.logic.commands.CommandTestUtil.ENGLISH_PHRASE_DESC_GOOD_MORNING;
import static seedu.address.logic.commands.CommandTestUtil.ENGLISH_PHRASE_DESC_HELLO;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ENGLISH_PHRASE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_FOREIGN_PHRASE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CHINESE_PHRASE_GOOD_MORNING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CHINESE_PHRASE_HELLO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ENGLISH_PHRASE_GOOD_MORNING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ENGLISH_PHRASE_HELLO;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_FLASHCARD;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_FLASHCARD;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_FLASHCARD;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditFlashcardDescriptor;
import seedu.address.model.flashcard.Phrase;
import seedu.address.testutil.EditFlashcardDescriptorBuilder;

public class EditCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_ENGLISH_PHRASE_GOOD_MORNING, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
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
        assertParseFailure(parser, "1" + INVALID_ENGLISH_PHRASE_DESC, Phrase.MESSAGE_CONSTRAINTS); // invalid English phrase
        assertParseFailure(parser, "1" + INVALID_FOREIGN_PHRASE_DESC, Phrase.MESSAGE_CONSTRAINTS); // invalid foreign phrase

        // invalid English phrase followed by valid foreign phrase
        assertParseFailure(parser, "1" + INVALID_ENGLISH_PHRASE_DESC + CHINESE_PHRASE_DESC_GOOD_MORNING, Phrase.MESSAGE_CONSTRAINTS);

        // valid English phrase followed by invalid English phrase. The test case for invalid English phrase followed by valid English phrase
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + ENGLISH_PHRASE_DESC_GOOD_MORNING + INVALID_ENGLISH_PHRASE_DESC, Phrase.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_ENGLISH_PHRASE_DESC + INVALID_FOREIGN_PHRASE_DESC, Phrase.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_FLASHCARD;
        String userInput = targetIndex.getOneBased() + ENGLISH_PHRASE_DESC_GOOD_MORNING + CHINESE_PHRASE_DESC_GOOD_MORNING;

        EditFlashcardDescriptor descriptor = new EditFlashcardDescriptorBuilder().withEnglishPhrase(VALID_ENGLISH_PHRASE_GOOD_MORNING)
                .withForeignPhrase(VALID_CHINESE_PHRASE_GOOD_MORNING).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_FLASHCARD;
        String userInput = targetIndex.getOneBased() + ENGLISH_PHRASE_DESC_HELLO;

        EditFlashcardDescriptor descriptor = new EditFlashcardDescriptorBuilder().withEnglishPhrase(VALID_ENGLISH_PHRASE_HELLO).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // English phrase
        Index targetIndex = INDEX_THIRD_FLASHCARD;
        String userInput = targetIndex.getOneBased() + ENGLISH_PHRASE_DESC_GOOD_MORNING;
        EditFlashcardDescriptor descriptor = new EditFlashcardDescriptorBuilder().withEnglishPhrase(VALID_ENGLISH_PHRASE_GOOD_MORNING).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // foreign phrase
        userInput = targetIndex.getOneBased() + CHINESE_PHRASE_DESC_HELLO;
        descriptor = new EditFlashcardDescriptorBuilder().withForeignPhrase(VALID_CHINESE_PHRASE_HELLO).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_FLASHCARD;
        String userInput = targetIndex.getOneBased() + ENGLISH_PHRASE_DESC_GOOD_MORNING + CHINESE_PHRASE_DESC_GOOD_MORNING
                + ENGLISH_PHRASE_DESC_GOOD_MORNING + CHINESE_PHRASE_DESC_GOOD_MORNING + ENGLISH_PHRASE_DESC_HELLO + CHINESE_PHRASE_DESC_HELLO;

        EditFlashcardDescriptor descriptor = new EditFlashcardDescriptorBuilder().withEnglishPhrase(VALID_ENGLISH_PHRASE_HELLO)
                .withForeignPhrase(VALID_CHINESE_PHRASE_HELLO).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_FLASHCARD;
        String userInput = targetIndex.getOneBased() + INVALID_ENGLISH_PHRASE_DESC + ENGLISH_PHRASE_DESC_GOOD_MORNING;
        EditFlashcardDescriptor descriptor = new EditFlashcardDescriptorBuilder().withEnglishPhrase(VALID_ENGLISH_PHRASE_GOOD_MORNING).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + INVALID_ENGLISH_PHRASE_DESC + CHINESE_PHRASE_DESC_GOOD_MORNING + ENGLISH_PHRASE_DESC_GOOD_MORNING;
        descriptor = new EditFlashcardDescriptorBuilder().withEnglishPhrase(VALID_ENGLISH_PHRASE_GOOD_MORNING)
                .withForeignPhrase(VALID_CHINESE_PHRASE_GOOD_MORNING).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

}
