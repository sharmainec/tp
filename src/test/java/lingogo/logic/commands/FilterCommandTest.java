package lingogo.logic.commands;

import static lingogo.commons.core.Messages.MESSAGE_FLASHCARDS_LISTED_OVERVIEW;
import static lingogo.commons.core.Messages.MESSAGE_INVALID_FLASHCARD_DISPLAYED_INDEX;
import static lingogo.logic.commands.CommandTestUtil.VALID_LANGUAGE_TYPE_CHINESE;
import static lingogo.logic.commands.CommandTestUtil.VALID_LANGUAGE_TYPE_TAMIL;
import static lingogo.logic.commands.CommandTestUtil.assertCommandFailure;
import static lingogo.logic.commands.CommandTestUtil.assertCommandSuccess;
import static lingogo.logic.commands.FilterCommand.FilterBuilder;
import static lingogo.testutil.TypicalFlashcards.AFTERNOON_CHINESE_FLASHCARD;
import static lingogo.testutil.TypicalFlashcards.NIGHT_CHINESE_FLASHCARD;
import static lingogo.testutil.TypicalFlashcards.SUNRISE_TAMIL_FLASHCARD;
import static lingogo.testutil.TypicalFlashcards.getTypicalFlashcardApp;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import lingogo.commons.core.Messages;
import lingogo.model.Model;
import lingogo.model.ModelManager;
import lingogo.model.UserPrefs;
import lingogo.testutil.FilterBuilderBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code FilterCommand}.
 */
public class FilterCommandTest {

    private Model model = new ModelManager(getTypicalFlashcardApp(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalFlashcardApp(), new UserPrefs());

    @Test
    public void equals() {

        FilterBuilder firstFilterBuilder =
            new FilterBuilderBuilder().withIndexList(1, 2).withLanguagePhrase(VALID_LANGUAGE_TYPE_CHINESE).build();
        FilterBuilder firstFilterBuilderAgain =
            new FilterBuilderBuilder().withIndexList(1, 2).withLanguagePhrase(VALID_LANGUAGE_TYPE_CHINESE).build();
        FilterBuilder secondFilterBuilder =
            new FilterBuilderBuilder().withIndexList(1).withLanguagePhrase(VALID_LANGUAGE_TYPE_TAMIL).build();
        FilterBuilder thirdFilterBuilder =
            new FilterBuilderBuilder().withIndexList(1, 2).withLanguagePhrase(VALID_LANGUAGE_TYPE_TAMIL).build();
        FilterBuilder fourthFilterBuilder =
            new FilterBuilderBuilder().withIndexList(1).withLanguagePhrase(VALID_LANGUAGE_TYPE_CHINESE).build();

        FilterCommand firstFilterCommand = new FilterCommand(firstFilterBuilder);
        FilterCommand secondFilterCommand = new FilterCommand(secondFilterBuilder);
        FilterCommand thirdFilterCommand = new FilterCommand(thirdFilterBuilder);
        FilterCommand fourthFilterCommand = new FilterCommand(fourthFilterBuilder);

        // same object -> returns true
        assertTrue(firstFilterCommand.equals(firstFilterCommand));

        // same values -> returns true
        FilterCommand firstFilterCommandCopy = new FilterCommand(firstFilterBuilderAgain);
        assertTrue(firstFilterCommand.equals(firstFilterCommandCopy));

        // all fields different-> returns false
        assertFalse(firstFilterCommand.equals(secondFilterCommand));

        // language field different -> returns false
        assertFalse(firstFilterCommand.equals(thirdFilterCommand));

        // index list field different -> returns false
        assertFalse(firstFilterCommand.equals(fourthFilterCommand));

        // null -> returns false
        assertFalse(firstFilterCommand.equals(null));

        // different flashcard -> returns false
        assertFalse(firstFilterCommand.equals(secondFilterCommand));
    }

    @Test
    public void execute_unknownLanguage_noFlashcardFound() {
        String expectedMessage = String.format(MESSAGE_FLASHCARDS_LISTED_OVERVIEW, 0);
        FilterBuilder filterBuilder = new FilterBuilderBuilder().withLanguagePhrase("Unknown language").build();
        FilterCommand command = new FilterCommand(filterBuilder);
        try {
            expectedModel.updateFilteredFlashcardList(filterBuilder.buildFilter(model));
        } catch (Exception e) {
            fail("Exception not expected");
        }
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredFlashcardList());
    }

    @Test
    public void execute_invalidIndex_failure() {
        FilterBuilder filterBuilder = new FilterBuilderBuilder().withIndexList(6).build();
        FilterCommand command = new FilterCommand(filterBuilder);
        assertCommandFailure(command, model, MESSAGE_INVALID_FLASHCARD_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidIndexKeywordTamil_failure() {
        FilterBuilder filterBuilder =
                new FilterBuilderBuilder().withIndexList(1000).withLanguagePhrase("Tamil").build();
        FilterCommand command = new FilterCommand(filterBuilder);
        assertCommandFailure(command, model, MESSAGE_INVALID_FLASHCARD_DISPLAYED_INDEX);
    }


    @Test
    public void execute_keywordTamil_tamilFlashcardsFound() {
        String expectedMessage = String.format(MESSAGE_FLASHCARDS_LISTED_OVERVIEW, 1);
        FilterBuilder filterBuilder = new FilterBuilderBuilder().withLanguagePhrase("Tamil").build();
        FilterCommand command = new FilterCommand(filterBuilder);
        try {
            expectedModel.updateFilteredFlashcardList(filterBuilder.buildFilter(model));
        } catch (Exception e) {
            fail("Exception not expected");
        }
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(SUNRISE_TAMIL_FLASHCARD), model.getFilteredFlashcardList());
    }

    @Test
    public void execute_slideshowActive_throwsCommandException() {
        model.startSlideshow();

        FilterBuilder filterBuilder = new FilterBuilderBuilder().withLanguagePhrase("Tamil").build();
        FilterCommand command = new FilterCommand(filterBuilder);
        String expectedMessage = String.format(Messages.MESSAGE_IN_SLIDESHOW_MODE);

        assertCommandFailure(command, model, expectedMessage);
    }

    @Test
    public void execute_selectedIndices_flashcardsFound() {
        String expectedMessage = String.format(MESSAGE_FLASHCARDS_LISTED_OVERVIEW, 2);
        FilterBuilder filterBuilder = new FilterBuilderBuilder().withIndexList(1, 2).build();
        FilterCommand command = new FilterCommand(filterBuilder);
        try {
            expectedModel.updateFilteredFlashcardList(filterBuilder.buildFilter(model));
        } catch (Exception e) {
            fail("Exception not expected");
        }
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(AFTERNOON_CHINESE_FLASHCARD, NIGHT_CHINESE_FLASHCARD),
                model.getFilteredFlashcardList());
    }

    @Test
    public void execute_selectedIndicesAndLanguage_flashcardsFound() {
        String expectedMessage = String.format(MESSAGE_FLASHCARDS_LISTED_OVERVIEW, 1);
        FilterBuilder filterBuilder =
            new FilterBuilderBuilder().withIndexList(1, 5).withLanguagePhrase("Chinese").build();
        FilterCommand command = new FilterCommand(filterBuilder);
        try {
            expectedModel.updateFilteredFlashcardList(filterBuilder.buildFilter(model));
        } catch (Exception e) {
            fail("Exception not expected");
        }
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(AFTERNOON_CHINESE_FLASHCARD), model.getFilteredFlashcardList());
    }
}
