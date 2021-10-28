package lingogo.logic.commands;

import static lingogo.logic.commands.CommandTestUtil.assertCommandFailure;
import static lingogo.logic.commands.CommandTestUtil.assertCommandSuccess;
import static lingogo.logic.commands.SlideshowCommand.MESSAGE_EMPTY_SLIDESHOW;
import static lingogo.testutil.TypicalFlashcards.getTypicalFlashcardApp;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import lingogo.commons.core.Messages;
import lingogo.model.Model;
import lingogo.model.ModelManager;
import lingogo.model.ReadOnlySlideshowApp;
import lingogo.model.UserPrefs;

public class SlideshowCommandTest {

    private Model model = new ModelManager(getTypicalFlashcardApp(), new UserPrefs());

    @Test
    public void execute_success() {
        ReadOnlySlideshowApp s = model.getSlideshowApp();
        assertFalse(s.isActiveProperty().get());

        Model expectedModel = new ModelManager(getTypicalFlashcardApp(), new UserPrefs());
        expectedModel.startSlideshow();

        assertCommandSuccess(new SlideshowCommand(), model, SlideshowCommand.MESSAGE_SUCCESS, expectedModel);

        assertTrue(s.isActiveProperty().get());
    }

    @Test
    public void execute_slideshowIsAlreadyActive_throwsCommandException() {
        model.startSlideshow();

        assertCommandFailure(new SlideshowCommand(), model, Messages.MESSAGE_IN_SLIDESHOW_MODE);
    }

    @Test
    public void execute_emptySlideshow_throwsCommandException() {
        model = new ModelManager();

        assertCommandFailure(new SlideshowCommand(), model, MESSAGE_EMPTY_SLIDESHOW);
    }

}
