package lingogo.logic.commands;

import static lingogo.logic.commands.CommandTestUtil.VALID_ENGLISH_PHRASE_AFTERNOON;
import static lingogo.logic.commands.CommandTestUtil.VALID_ENGLISH_PHRASE_GOOD_MORNING;
import static lingogo.logic.commands.CommandTestUtil.assertCommandFailure;
import static lingogo.logic.commands.CommandTestUtil.assertCommandSuccess;
import static lingogo.logic.commands.CommandTestUtil.showFlashcardAtIndex;
import static lingogo.testutil.TypicalFlashcards.getTypicalFlashcardApp;
import static lingogo.testutil.TypicalIndexes.INDEX_FIRST_FLASHCARD;
import static lingogo.testutil.TypicalIndexes.INDEX_SECOND_FLASHCARD;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import lingogo.commons.core.Messages;
import lingogo.commons.core.index.Index;
import lingogo.model.Model;
import lingogo.model.ModelManager;
import lingogo.model.UserPrefs;
import lingogo.model.flashcard.Flashcard;
import lingogo.model.flashcard.Phrase;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code TestCommand}.
 */
public class TestCommandTest {

    private Model model = new ModelManager(getTypicalFlashcardApp(), new UserPrefs());
    private Phrase validPhraseAfternoon = new Phrase(VALID_ENGLISH_PHRASE_AFTERNOON);
    private Phrase validPhraseGoodMorning = new Phrase(VALID_ENGLISH_PHRASE_GOOD_MORNING);

    @Test
    public void execute_validIndexUnfilteredListCorrectGivenPhrase_success() {
        Flashcard flashcardToTest = model.getFilteredFlashcardList().get(INDEX_FIRST_FLASHCARD.getZeroBased());
        TestCommand testCommand = new TestCommand(INDEX_FIRST_FLASHCARD, validPhraseAfternoon);

        String expectedMessage = String.format(TestCommand.MESSAGE_TEST_FLASHCARD_SUCCESS_CORRECT,
            flashcardToTest.getForeignPhrase(), flashcardToTest.getEnglishPhrase(), VALID_ENGLISH_PHRASE_AFTERNOON);

        ModelManager expectedModel = new ModelManager(model.getFlashcardApp(), new UserPrefs());

        assertCommandSuccess(testCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndexUnfilteredListWrongGivenPhrase_success() {
        Flashcard flashcardToTest = model.getFilteredFlashcardList().get(INDEX_FIRST_FLASHCARD.getZeroBased());
        TestCommand testCommand = new TestCommand(INDEX_FIRST_FLASHCARD, validPhraseGoodMorning);

        String expectedMessage = String.format(TestCommand.MESSAGE_TEST_FLASHCARD_SUCCESS_WRONG,
            flashcardToTest.getForeignPhrase(), flashcardToTest.getEnglishPhrase(), VALID_ENGLISH_PHRASE_GOOD_MORNING);

        ModelManager expectedModel = new ModelManager(model.getFlashcardApp(), new UserPrefs());

        assertCommandSuccess(testCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_testFlippedFlashcard_throwsCommandException() {
        Flashcard flashcardToTest = model.getFilteredFlashcardList().get(INDEX_FIRST_FLASHCARD.getZeroBased());
        model.setFlashcard(flashcardToTest, flashcardToTest.getFlippedFlashcard());

        TestCommand testCommand = new TestCommand(INDEX_FIRST_FLASHCARD, validPhraseGoodMorning);

        String expectedMessage =
            String.format(TestCommand.MESSAGE_FLASHCARD_NOT_FLIPPED_DOWN, flashcardToTest.getForeignPhrase());

        assertCommandFailure(testCommand, model, expectedMessage);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredFlashcardList().size() + 1);
        TestCommand testCommand = new TestCommand(outOfBoundIndex, validPhraseAfternoon);

        assertCommandFailure(testCommand, model, Messages.MESSAGE_INVALID_FLASHCARD_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredListCorrectGivenPhrase_success() {
        showFlashcardAtIndex(model, INDEX_FIRST_FLASHCARD);

        Flashcard flashcardToTest = model.getFilteredFlashcardList().get(INDEX_FIRST_FLASHCARD.getZeroBased());
        TestCommand testCommand = new TestCommand(INDEX_FIRST_FLASHCARD, validPhraseAfternoon);

        String expectedMessage =
            String.format(TestCommand.MESSAGE_TEST_FLASHCARD_SUCCESS_CORRECT, flashcardToTest.getForeignPhrase(),
                flashcardToTest.getEnglishPhrase(), VALID_ENGLISH_PHRASE_AFTERNOON);

        Model expectedModel = new ModelManager(model.getFlashcardApp(), new UserPrefs());
        showFlashcardAtIndex(expectedModel, INDEX_FIRST_FLASHCARD);


        assertCommandSuccess(testCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndexFilteredListWrongGivenPhrase_success() {
        showFlashcardAtIndex(model, INDEX_SECOND_FLASHCARD);

        Flashcard flashcardToTest = model.getFilteredFlashcardList().get(INDEX_FIRST_FLASHCARD.getZeroBased());
        TestCommand testCommand = new TestCommand(INDEX_FIRST_FLASHCARD, validPhraseAfternoon);

        String expectedMessage =
            String.format(TestCommand.MESSAGE_TEST_FLASHCARD_SUCCESS_WRONG, flashcardToTest.getForeignPhrase(),
                flashcardToTest.getEnglishPhrase(), VALID_ENGLISH_PHRASE_AFTERNOON);

        Model expectedModel = new ModelManager(model.getFlashcardApp(), new UserPrefs());
        showFlashcardAtIndex(expectedModel, INDEX_SECOND_FLASHCARD);

        System.out.println(model.getFlashcardApp().getFlashcardList());


        assertCommandSuccess(testCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showFlashcardAtIndex(model, INDEX_FIRST_FLASHCARD);

        Index outOfBoundIndex = INDEX_SECOND_FLASHCARD;
        // ensures that outOfBoundIndex is still in bounds of flashcard app list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getFlashcardApp().getFlashcardList().size());

        TestCommand testCommand = new TestCommand(outOfBoundIndex, validPhraseAfternoon);

        assertCommandFailure(testCommand, model, Messages.MESSAGE_INVALID_FLASHCARD_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        TestCommand testFirstCommand = new TestCommand(INDEX_FIRST_FLASHCARD, validPhraseAfternoon);
        TestCommand testSecondCommand = new TestCommand(INDEX_SECOND_FLASHCARD, validPhraseAfternoon);

        // same object -> returns true
        assertTrue(testFirstCommand.equals(testFirstCommand));

        // same values -> returns true
        TestCommand testFirstCommandCopy = new TestCommand(INDEX_FIRST_FLASHCARD, validPhraseAfternoon);
        assertTrue(testFirstCommand.equals(testFirstCommandCopy));

        // different types -> returns false
        assertFalse(testFirstCommand.equals(1));

        // null -> returns false
        assertFalse(testFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(testFirstCommand.equals(testSecondCommand));
    }
}
