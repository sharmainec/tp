package lingogo.logic.commands;

import static lingogo.commons.core.Messages.MESSAGE_FLASHCARDS_LISTED_OVERVIEW;
import static lingogo.logic.commands.CommandTestUtil.assertCommandFailure;
import static lingogo.logic.commands.CommandTestUtil.assertCommandSuccess;
import static lingogo.testutil.TypicalFlashcards.BYE_CHINESE_FLASHCARD;
import static lingogo.testutil.TypicalFlashcards.NIGHT_CHINESE_FLASHCARD;
import static lingogo.testutil.TypicalFlashcards.SORRY_CHINESE_FLASHCARD;
import static lingogo.testutil.TypicalFlashcards.getTypicalFlashcardApp;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import lingogo.commons.core.Messages;
import lingogo.model.Model;
import lingogo.model.ModelManager;
import lingogo.model.UserPrefs;
import lingogo.model.flashcard.EnglishPhraseContainsKeywordsPredicate;
import lingogo.model.flashcard.ForeignPhraseContainsKeywordsPredicate;
import lingogo.model.flashcard.PhraseContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalFlashcardApp(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalFlashcardApp(), new UserPrefs());

    @Test
    public void equals() {
        PhraseContainsKeywordsPredicate firstPredicate =
                new PhraseContainsKeywordsPredicate(Collections.singletonList("first"));
        PhraseContainsKeywordsPredicate secondPredicate =
                new PhraseContainsKeywordsPredicate(Collections.singletonList("second"));

        FindCommand findFirstCommand = new FindCommand(firstPredicate);
        FindCommand findSecondCommand = new FindCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different flashcard -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noFlashcardFound() {
        String expectedMessage = String.format(MESSAGE_FLASHCARDS_LISTED_OVERVIEW, 0);
        PhraseContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredFlashcardList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredFlashcardList());
    }

    @Test
    public void execute_multipleEnglishKeywords_multipleFlashcardsFound() {
        String expectedMessage = String.format(MESSAGE_FLASHCARDS_LISTED_OVERVIEW, 3);
        EnglishPhraseContainsKeywordsPredicate predicate =
                new EnglishPhraseContainsKeywordsPredicate(Arrays.asList("Bye", "Night", "Sorry"));
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredFlashcardList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(NIGHT_CHINESE_FLASHCARD, BYE_CHINESE_FLASHCARD, SORRY_CHINESE_FLASHCARD),
                model.getFilteredFlashcardList());
    }

    @Test
    public void execute_multipleForeignKeywords_multipleFlashcardsFound() {
        String expectedMessage = String.format(MESSAGE_FLASHCARDS_LISTED_OVERVIEW, 3);
        ForeignPhraseContainsKeywordsPredicate predicate =
                new ForeignPhraseContainsKeywordsPredicate(Arrays.asList("再见", "晚上", "对"));
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredFlashcardList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(NIGHT_CHINESE_FLASHCARD, BYE_CHINESE_FLASHCARD, SORRY_CHINESE_FLASHCARD),
                model.getFilteredFlashcardList());
    }

    @Test
    public void execute_multipleMixedKeywords_multipleFlashcardsFound() {
        String expectedMessage = String.format(MESSAGE_FLASHCARDS_LISTED_OVERVIEW, 3);
        PhraseContainsKeywordsPredicate predicate =
                new PhraseContainsKeywordsPredicate(Arrays.asList("Bye", "晚上", "Sorry"));
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredFlashcardList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(NIGHT_CHINESE_FLASHCARD, BYE_CHINESE_FLASHCARD, SORRY_CHINESE_FLASHCARD),
                model.getFilteredFlashcardList());
    }

    @Test
    public void execute_slideshowActive_throwsCommandException() {
        model.startSlideshow();

        PhraseContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindCommand command = new FindCommand(predicate);
        String expectedMessage = String.format(Messages.MESSAGE_IN_SLIDESHOW_MODE);

        assertCommandFailure(command, model, expectedMessage);
    }

    /**
     * Parses {@code userInput} into a {@code PhraseContainsKeywordsPredicate}.
     */
    private PhraseContainsKeywordsPredicate preparePredicate(String userInput) {
        return new PhraseContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
