package lingogo.logic.commands;

import static lingogo.logic.commands.CommandTestUtil.assertCommandFailure;
import static lingogo.logic.commands.CommandTestUtil.assertCommandSuccess;
import static lingogo.testutil.TypicalFlashcards.getTypicalFlashcardApp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import lingogo.commons.core.Messages;
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
    public void execute_listWithNoIndex_showsSameList() {
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listWithInvalidIndex_throwsCommandException() {
        String expectedMessage = String.format(Messages.MESSAGE_INVALID_N);

        assertCommandFailure(new ListCommand(0), model, expectedMessage);
        assertCommandFailure(new ListCommand(-1), model, expectedMessage);
    }

    @Test
    public void execute_slideshowActive_throwsCommandException() {
        model.startSlideshow();
        String expectedMessage = String.format(Messages.MESSAGE_IN_SLIDESHOW_MODE);

        assertCommandFailure(new ListCommand(), model, expectedMessage);
    }
}
