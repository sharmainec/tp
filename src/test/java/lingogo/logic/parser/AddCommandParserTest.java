package lingogo.logic.parser;

import static lingogo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static lingogo.logic.commands.CommandTestUtil.CHINESE_PHRASE_DESC_GOOD_MORNING;
import static lingogo.logic.commands.CommandTestUtil.CHINESE_PHRASE_DESC_HELLO;
import static lingogo.logic.commands.CommandTestUtil.ENGLISH_PHRASE_DESC_GOOD_MORNING;
import static lingogo.logic.commands.CommandTestUtil.ENGLISH_PHRASE_DESC_HELLO;
import static lingogo.logic.commands.CommandTestUtil.INVALID_ENGLISH_PHRASE_DESC;
import static lingogo.logic.commands.CommandTestUtil.INVALID_FOREIGN_PHRASE_DESC;
import static lingogo.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static lingogo.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static lingogo.logic.commands.CommandTestUtil.VALID_CHINESE_PHRASE_HELLO;
import static lingogo.logic.commands.CommandTestUtil.VALID_ENGLISH_PHRASE_HELLO;
import static lingogo.logic.parser.CommandParserTestUtil.assertParseFailure;
import static lingogo.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static lingogo.testutil.TypicalFlashcards.HELLO_CHINESE_FLASHCARD;

import org.junit.jupiter.api.Test;

import lingogo.logic.commands.AddCommand;
import lingogo.model.flashcard.Flashcard;
import lingogo.model.flashcard.Phrase;
import lingogo.testutil.FlashcardBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Flashcard expectedFlashcard = new FlashcardBuilder(HELLO_CHINESE_FLASHCARD).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + ENGLISH_PHRASE_DESC_HELLO
                + CHINESE_PHRASE_DESC_HELLO, new AddCommand(expectedFlashcard));

        // multiple English phrases - last English phrase accepted
        assertParseSuccess(parser, ENGLISH_PHRASE_DESC_GOOD_MORNING + ENGLISH_PHRASE_DESC_HELLO
                + CHINESE_PHRASE_DESC_HELLO, new AddCommand(expectedFlashcard));

        // multiple foreign phrases - last foreign phrase accepted
        assertParseSuccess(parser, ENGLISH_PHRASE_DESC_HELLO + CHINESE_PHRASE_DESC_GOOD_MORNING
                + CHINESE_PHRASE_DESC_HELLO, new AddCommand(expectedFlashcard));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing English phrase prefix
        assertParseFailure(parser, VALID_ENGLISH_PHRASE_HELLO + CHINESE_PHRASE_DESC_HELLO, expectedMessage);

        // missing foreign phrase prefix
        assertParseFailure(parser, ENGLISH_PHRASE_DESC_HELLO + VALID_CHINESE_PHRASE_HELLO, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_ENGLISH_PHRASE_HELLO + VALID_CHINESE_PHRASE_HELLO, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid English phrase
        assertParseFailure(parser, INVALID_ENGLISH_PHRASE_DESC + CHINESE_PHRASE_DESC_HELLO, Phrase.MESSAGE_CONSTRAINTS);

        // invalid foreign phrase
        assertParseFailure(parser, ENGLISH_PHRASE_DESC_HELLO + INVALID_FOREIGN_PHRASE_DESC, Phrase.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_ENGLISH_PHRASE_DESC + INVALID_FOREIGN_PHRASE_DESC,
                Phrase.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + ENGLISH_PHRASE_DESC_HELLO + CHINESE_PHRASE_DESC_HELLO,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
