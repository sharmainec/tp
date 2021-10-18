package lingogo.logic.commands;

import static lingogo.logic.commands.CommandTestUtil.assertCommandSuccess;
import static lingogo.logic.commands.CommandTestUtil.showFlashcardAtIndex;
import static lingogo.testutil.TypicalFlashcards.getTypicalFlashcardApp;
import static lingogo.testutil.TypicalIndexes.INDEX_FIRST_FLASHCARD;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import lingogo.model.Model;
import lingogo.model.ModelManager;
import lingogo.model.UserPrefs;

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
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
        assertCommandSuccess(new ListCommand(""), model, ListCommand.MESSAGE_SUCCESS, expectedModel);

        // when n > total number of flashcards, list is not filtered
        String size = Integer.toString(model.getFilteredFlashcardList().size() + 100);
        assertCommandSuccess(new ListCommand(size), model, ListCommand.MESSAGE_SUCCESS, expectedModel);

        // when n is not an integer, ignore user input and display list which is not filtered
        assertCommandSuccess(new ListCommand("abc"), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showFlashcardAtIndex(model, INDEX_FIRST_FLASHCARD);
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
