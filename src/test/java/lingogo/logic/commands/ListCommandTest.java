package lingogo.logic.commands;

import static lingogo.logic.commands.CommandTestUtil.assertCommandFailure;
import static lingogo.logic.commands.CommandTestUtil.assertCommandSuccess;
import static lingogo.testutil.TypicalFlashcards.AFTERNOON_CHINESE_FLASHCARD;
import static lingogo.testutil.TypicalFlashcards.NIGHT_CHINESE_FLASHCARD;
import static lingogo.testutil.TypicalFlashcards.SORRY_CHINESE_FLASHCARD;
import static lingogo.testutil.TypicalFlashcards.SUNRISE_TAMIL_FLASHCARD;
import static lingogo.testutil.TypicalFlashcards.getTypicalFlashcardApp;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import lingogo.commons.core.Messages;
import lingogo.model.Model;
import lingogo.model.ModelManager;
import lingogo.model.UserPrefs;
import lingogo.model.flashcard.FlashcardInGivenFlashcardListPredicate;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalFlashcardApp(), new UserPrefs());
        expectedModel = new ModelManager(model.getFlashcardApp(), new UserPrefs());
    }

    @Test
    public void execute_listWithNoIndex_showsSameList() {
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listWithInvalidIndex_throwsCommandException() {
        String expectedMessage = String.format(Messages.MESSAGE_INVALID_N);

        assertCommandFailure(new ListCommand(0), model, expectedMessage);
        assertCommandFailure(new ListCommand(-1), model, expectedMessage);
        assertCommandFailure(new ListCommand(model.getFlashcardApp().getFlashcardList().size() + 1),
                model, expectedMessage);
    }

    @Test
    public void execute_listWithValidIndex_success() {
        int n = 3;

        String expectedMessage = String.format(ListCommand.MESSAGE_SUCCESS_SHUFFLED, n);

        int seed = 10;
        // With seed = 10, flashcards expected to be selected are:
        // AFTERNOON_CHINESE_FLASHCARD, NIGHT_CHINESE_FLASHCARD and SORRY_CHINESE_FLASHCARD
        expectedModel.updateFilteredFlashcardList(new FlashcardInGivenFlashcardListPredicate(
                List.of(AFTERNOON_CHINESE_FLASHCARD, NIGHT_CHINESE_FLASHCARD, SORRY_CHINESE_FLASHCARD)));
        assertCommandSuccess(new ListCommand(n, seed), model, expectedMessage, expectedModel);

        seed = 9;
        // With seed = 9, flashcards expected to be selected are:
        // NIGHT_CHINESE_FLASHCARD, SORRY_CHINESE_FLASHCARD and SUNRISE_TAMIL_FLASHCARD
        expectedModel.updateFilteredFlashcardList(new FlashcardInGivenFlashcardListPredicate(
                List.of(NIGHT_CHINESE_FLASHCARD, SORRY_CHINESE_FLASHCARD, SUNRISE_TAMIL_FLASHCARD)));
        assertCommandSuccess(new ListCommand(n, seed), model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_slideshowActive_throwsCommandException() {
        model.startSlideshow();
        String expectedMessage = String.format(Messages.MESSAGE_IN_SLIDESHOW_MODE);

        assertCommandFailure(new ListCommand(), model, expectedMessage);
    }

    @Test
    public void equals() {
        ListCommand listAllCommand = new ListCommand();
        ListCommand listRandomThreeCommand = new ListCommand(3);

        // same object -> returns true
        assertTrue(listAllCommand.equals(listAllCommand));

        // same values -> returns true
        ListCommand listRandomThreeCommandCopy = new ListCommand(3);
        assertTrue(listRandomThreeCommand.equals(listRandomThreeCommandCopy));
        assertEquals(listAllCommand, new ListCommand());

        // different types -> returns false
        assertFalse(listRandomThreeCommand.equals(1));

        // null -> returns false
        assertFalse(listRandomThreeCommand.equals(null));

        // different person -> returns false
        assertFalse(listAllCommand.equals(listRandomThreeCommand));
    }
}
