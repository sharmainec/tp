package lingogo.logic.commands;

import static lingogo.logic.parser.CliSyntax.PREFIX_ENGLISH_PHRASE;
import static lingogo.logic.parser.CliSyntax.PREFIX_FOREIGN_PHRASE;
import static lingogo.logic.parser.CliSyntax.PREFIX_INDEX_LIST;
import static lingogo.logic.parser.CliSyntax.PREFIX_INDEX_RANGE;
import static lingogo.logic.parser.CliSyntax.PREFIX_LANGUAGE_TYPE;
import static lingogo.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lingogo.commons.core.index.Index;
import lingogo.logic.commands.exceptions.CommandException;
import lingogo.model.FlashcardApp;
import lingogo.model.Model;
import lingogo.model.flashcard.Flashcard;
import lingogo.model.flashcard.PhraseContainsKeywordsPredicate;
import lingogo.testutil.EditFlashcardDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_LANGUAGE_TYPE_CHINESE = "Chinese";
    public static final String VALID_LANGUAGE_TYPE_TAMIL = "Tamil";
    public static final String VALID_ENGLISH_PHRASE_HELLO = "Hello";
    public static final String VALID_CHINESE_PHRASE_HELLO = "你好";
    public static final String VALID_ENGLISH_PHRASE_GOOD_MORNING = "Good Morning";
    public static final String VALID_CHINESE_PHRASE_GOOD_MORNING = "早安";
    public static final String VALID_ENGLISH_PHRASE_AFTERNOON = "Afternoon";
    public static final String VALID_ENGLISH_PHRASE_SUNRISE = "Sunrise";
    public static final String VALID_TAMIL_PHRASE_SUNRISE = "சூரிய உதயம்";
    public static final String VALID_INDICES_ONE_TWO = "1 2";
    public static final String VALID_INDICES_DUPLICATE_ONES = "1 1";
    public static final String VALID_RANGE_TWO_FOUR = "2 4";



    public static final String LANGUAGE_TYPE_DESC_CHINESE = " " + PREFIX_LANGUAGE_TYPE
            + VALID_LANGUAGE_TYPE_CHINESE;
    public static final String LANGUAGE_TYPE_DESC_TAMIL = " " + PREFIX_LANGUAGE_TYPE
            + VALID_LANGUAGE_TYPE_TAMIL;
    public static final String CHINESE_PHRASE_DESC_GOOD_MORNING = " " + PREFIX_FOREIGN_PHRASE
            + VALID_CHINESE_PHRASE_GOOD_MORNING;
    public static final String CHINESE_PHRASE_DESC_HELLO = " " + PREFIX_FOREIGN_PHRASE
            + VALID_CHINESE_PHRASE_HELLO;
    public static final String TAMIL_PHRASE_DESC_SUNRISE = " " + PREFIX_FOREIGN_PHRASE
            + VALID_TAMIL_PHRASE_SUNRISE;
    public static final String ENGLISH_PHRASE_DESC_GOOD_MORNING = " " + PREFIX_ENGLISH_PHRASE
            + VALID_ENGLISH_PHRASE_GOOD_MORNING;
    public static final String ENGLISH_PHRASE_DESC_HELLO = " " + PREFIX_ENGLISH_PHRASE
            + VALID_ENGLISH_PHRASE_HELLO;
    public static final String ENGLISH_PHRASE_DESC_SUNRISE = " " + PREFIX_ENGLISH_PHRASE
            + VALID_ENGLISH_PHRASE_SUNRISE;
    public static final String INDICES_DESC_DESC_ONE_TWO = " " + PREFIX_INDEX_LIST + VALID_INDICES_ONE_TWO;
    public static final String INDICES_DESC_DESC_DUPLICATE_ONES = " " + PREFIX_INDEX_LIST
            + VALID_INDICES_DUPLICATE_ONES;
    public static final String RANGE_DESC_TWO_FOUR = " " + PREFIX_INDEX_RANGE + VALID_RANGE_TWO_FOUR;




    public static final String INVALID_LANGUAGE_TYPE_DESC = " " + PREFIX_LANGUAGE_TYPE
            + "    "; // Phrases cannot be empty
    public static final String INVALID_FOREIGN_PHRASE_DESC = " " + PREFIX_FOREIGN_PHRASE
            + "    "; // Phrases cannot be empty
    public static final String INVALID_ENGLISH_PHRASE_DESC = " " + PREFIX_ENGLISH_PHRASE
            + "    "; // Phrases cannot be empty
    public static final String INVALID_LONG_ENGLISH_PHRASE_DESC = " " + PREFIX_ENGLISH_PHRASE
            + "aaaaaaaaaaaaaaaaaaaa"
            + "aaaaaaaaaaaaaaaaaaaa"
            + "aaaaaaaaaaaaaaaaaaaa"
            + "aaaaaaaaaaaaaaaaaaaa"
            + "aaaaaaaaaaaaaaaaaaaaa"; // 101 character English phrase
    public static final String INVALID_LONG_FOREIGN_PHRASE_DESC = " " + PREFIX_FOREIGN_PHRASE
            + "哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈"
            + "哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈"
            + "哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈"
            + "哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈"
            + "哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈"; // 101 character foreign phrase

    public static final String INVALID_NEGATIVE_INDEX_DESC = " " + PREFIX_INDEX_LIST + "-1";

    public static final String INVALID_INDICES_DESC = " " + PREFIX_INDEX_LIST + "1 1 C ; , ?";

    public static final String INVALID_VERY_LARGE_INDICES_DESC = " " + PREFIX_INDEX_LIST
        + "1000000000000000000000000000000000000000000000 999999999999999999999999999999999999999999";
    public static final String INVALID_NON_PAIR_RANGE = " " + PREFIX_INDEX_RANGE + "2";
    public static final String INVALID_REVERSED_RANGE = " " + PREFIX_INDEX_RANGE + "4 2";
    public static final String INVALID_INDEX_RANGE = " " + PREFIX_INDEX_RANGE + "-1 1";




    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditFlashcardDescriptor DESC_HELLO;
    public static final EditCommand.EditFlashcardDescriptor DESC_GOOD_MORNING;

    static {
        DESC_GOOD_MORNING = new EditFlashcardDescriptorBuilder().withLanguageType(VALID_LANGUAGE_TYPE_CHINESE)
                .withEnglishPhrase(VALID_ENGLISH_PHRASE_GOOD_MORNING)
                .withForeignPhrase(VALID_CHINESE_PHRASE_GOOD_MORNING).build();
        DESC_HELLO = new EditFlashcardDescriptorBuilder().withLanguageType(VALID_LANGUAGE_TYPE_CHINESE)
                .withEnglishPhrase(VALID_ENGLISH_PHRASE_HELLO)
                .withForeignPhrase(VALID_CHINESE_PHRASE_HELLO).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the flashcard app, filtered flashcard list and selected flashcard in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        FlashcardApp expectedFlashcardApp = new FlashcardApp(actualModel.getFlashcardApp());
        List<Flashcard> expectedFilteredList = new ArrayList<>(actualModel.getFilteredFlashcardList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedFlashcardApp, actualModel.getFlashcardApp());
        assertEquals(expectedFilteredList, actualModel.getFilteredFlashcardList());
    }
    /**
     * Updates {@code model}'s filtered list to show only the flashcard at the given {@code targetIndex} in the
     * {@code model}'s flashcard app.
     */
    public static void showFlashcardAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredFlashcardList().size());

        Flashcard flashcard = model.getFilteredFlashcardList().get(targetIndex.getZeroBased());
        final String[] splitEnglishPhrase = flashcard.getEnglishPhrase().value.split("\\s+");
        model.updateFilteredFlashcardList(new PhraseContainsKeywordsPredicate(
                Arrays.asList(splitEnglishPhrase[0])));

        assertEquals(1, model.getFilteredFlashcardList().size());
    }

}
