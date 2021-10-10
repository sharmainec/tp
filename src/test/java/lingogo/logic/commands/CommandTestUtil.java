package lingogo.logic.commands;

import static lingogo.logic.parser.CliSyntax.PREFIX_ENGLISH_PHRASE;
import static lingogo.logic.parser.CliSyntax.PREFIX_FOREIGN_PHRASE;
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
import lingogo.model.flashcard.EnglishPhraseContainsKeywordsPredicate;
import lingogo.model.flashcard.Flashcard;
import lingogo.testutil.EditFlashcardDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_ENGLISH_PHRASE_HELLO = "Hello";
    public static final String VALID_CHINESE_PHRASE_HELLO = "你好";
    public static final String VALID_ENGLISH_PHRASE_GOOD_MORNING = "Good Morning";
    public static final String VALID_CHINESE_PHRASE_GOOD_MORNING = "早安";

    public static final String CHINESE_PHRASE_DESC_GOOD_MORNING = " " + PREFIX_FOREIGN_PHRASE
            + VALID_CHINESE_PHRASE_GOOD_MORNING;
    public static final String CHINESE_PHRASE_DESC_HELLO = " " + PREFIX_FOREIGN_PHRASE
            + VALID_CHINESE_PHRASE_HELLO;
    public static final String ENGLISH_PHRASE_DESC_GOOD_MORNING = " " + PREFIX_ENGLISH_PHRASE
            + VALID_ENGLISH_PHRASE_GOOD_MORNING;
    public static final String ENGLISH_PHRASE_DESC_HELLO = " " + PREFIX_ENGLISH_PHRASE
            + VALID_ENGLISH_PHRASE_HELLO;

    public static final String INVALID_FOREIGN_PHRASE_DESC = " " + PREFIX_FOREIGN_PHRASE
            + "早安&"; // '&' not allowed in phrases
    public static final String INVALID_ENGLISH_PHRASE_DESC = " " + PREFIX_ENGLISH_PHRASE
            + "Good Morning\""; // '"' not allows in phrases

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditFlashcardDescriptor DESC_HELLO;
    public static final EditCommand.EditFlashcardDescriptor DESC_GOOD_MORNING;

    static {
        DESC_GOOD_MORNING = new EditFlashcardDescriptorBuilder().withEnglishPhrase(VALID_ENGLISH_PHRASE_GOOD_MORNING)
                .withForeignPhrase(VALID_CHINESE_PHRASE_GOOD_MORNING).build();
        DESC_HELLO = new EditFlashcardDescriptorBuilder().withEnglishPhrase(VALID_ENGLISH_PHRASE_HELLO)
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
        model.updateFilteredFlashcardList(new EnglishPhraseContainsKeywordsPredicate(
                Arrays.asList(splitEnglishPhrase[0])));

        assertEquals(1, model.getFilteredFlashcardList().size());
    }

}
