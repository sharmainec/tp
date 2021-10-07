package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_FLASHCARDS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalFlashcards.BYE_CHINESE_FLASHCARD;
import static seedu.address.testutil.TypicalFlashcards.NIGHT_CHINESE_FLASHCARD;
import static seedu.address.testutil.TypicalFlashcards.SORRY_CHINESE_FLASHCARD;
import static seedu.address.testutil.TypicalFlashcards.getTypicalFlashcardApp;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.flashcard.EnglishPhraseContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalFlashcardApp(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalFlashcardApp(), new UserPrefs());

    @Test
    public void equals() {
        EnglishPhraseContainsKeywordsPredicate firstPredicate =
                new EnglishPhraseContainsKeywordsPredicate(Collections.singletonList("first"));
                EnglishPhraseContainsKeywordsPredicate secondPredicate =
                new EnglishPhraseContainsKeywordsPredicate(Collections.singletonList("second"));

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
        EnglishPhraseContainsKeywordsPredicate predicate = preparePredicate(" ");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredFlashcardList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredFlashcardList());
    }

    @Test
    public void execute_multipleKeywords_multipleFlashcardsFound() {
        String expectedMessage = String.format(MESSAGE_FLASHCARDS_LISTED_OVERVIEW, 3);
        EnglishPhraseContainsKeywordsPredicate predicate = preparePredicate("Bye Night Sorry");
        FindCommand command = new FindCommand(predicate);
        expectedModel.updateFilteredFlashcardList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(NIGHT_CHINESE_FLASHCARD, BYE_CHINESE_FLASHCARD, SORRY_CHINESE_FLASHCARD),
                model.getFilteredFlashcardList());
    }

    /**
     * Parses {@code userInput} into a {@code EnglishPhraseContainsKeywordsPredicate}.
     */
    private EnglishPhraseContainsKeywordsPredicate preparePredicate(String userInput) {
        return new EnglishPhraseContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }
}
