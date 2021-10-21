package lingogo.logic.commands;

import static lingogo.commons.core.Messages.MESSAGE_FLASHCARDS_LISTED_OVERVIEW;
import static lingogo.logic.commands.CommandTestUtil.assertCommandFailure;
import static lingogo.logic.commands.CommandTestUtil.assertCommandSuccess;
import static lingogo.testutil.TypicalFlashcards.SUNRISE_TAMIL_FLASHCARD;
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
import lingogo.model.flashcard.LanguageTypeMatchesGivenPhrasePredicate;
import lingogo.model.flashcard.Phrase;

/**
 * Contains integration tests (interaction with the Model) for {@code FilterCommand}.
 */
public class FilterCommandTest {

    private Model model = new ModelManager(getTypicalFlashcardApp(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalFlashcardApp(), new UserPrefs());

    @Test
    public void equals() {
        LanguageTypeMatchesGivenPhrasePredicate firstPredicate =
                new LanguageTypeMatchesGivenPhrasePredicate(new Phrase("Chinese"));
        LanguageTypeMatchesGivenPhrasePredicate secondPredicate =
                new LanguageTypeMatchesGivenPhrasePredicate(new Phrase("Tamil"));

        FilterCommand findFirstCommand = new FilterCommand(firstPredicate);
        FilterCommand findSecondCommand = new FilterCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FilterCommand findFirstCommandCopy = new FilterCommand(firstPredicate);
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
        LanguageTypeMatchesGivenPhrasePredicate predicate = preparePredicate("Invalid Language");
        FilterCommand command = new FilterCommand(predicate);
        expectedModel.updateFilteredFlashcardList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredFlashcardList());
    }

    @Test
    public void execute_keywordTamil_tamilFlashcardsFound() {
        String expectedMessage = String.format(MESSAGE_FLASHCARDS_LISTED_OVERVIEW, 1);
        LanguageTypeMatchesGivenPhrasePredicate predicate = preparePredicate("Tamil");
        FilterCommand command = new FilterCommand(predicate);
        expectedModel.updateFilteredFlashcardList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(SUNRISE_TAMIL_FLASHCARD), model.getFilteredFlashcardList());
    }

    @Test
    public void execute_slideshowActive_throwsCommandException() {
        model.startSlideshow();

        LanguageTypeMatchesGivenPhrasePredicate predicate = preparePredicate("Tamil");
        FilterCommand command = new FilterCommand(predicate);
        String expectedMessage = String.format(Messages.MESSAGE_IN_SLIDESHOW_MODE);

        assertCommandFailure(command, model, expectedMessage);
    }

    /**
     * Parses {@code userInput} into a {@code LanguageTypeMatchesGivenPhrasePredicate}.
     */
    private LanguageTypeMatchesGivenPhrasePredicate preparePredicate(String userInput) {
        return new LanguageTypeMatchesGivenPhrasePredicate(new Phrase(userInput));
    }
}
