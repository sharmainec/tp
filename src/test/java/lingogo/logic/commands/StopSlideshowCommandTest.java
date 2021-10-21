package lingogo.logic.commands;

import static lingogo.logic.commands.CommandTestUtil.assertCommandFailure;
import static lingogo.logic.commands.CommandTestUtil.assertCommandSuccess;
import static lingogo.testutil.TypicalFlashcards.getTypicalFlashcardApp;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import lingogo.commons.core.Messages;
import lingogo.model.Model;
import lingogo.model.ModelManager;
import lingogo.model.ReadOnlySlideshowApp;
import lingogo.model.UserPrefs;

public class StopSlideshowCommandTest {

    private Model model = new ModelManager(getTypicalFlashcardApp(), new UserPrefs());

    @Test
    public void execute_success() {
        model.startSlideshow();
        ReadOnlySlideshowApp s = model.getSlideshowApp();
        assertTrue(s.isActiveProperty().get());

        Model expectedModel = new ModelManager(getTypicalFlashcardApp(), new UserPrefs());

        assertCommandSuccess(new StopSlideshowCommand(), model, StopSlideshowCommand.MESSAGE_SUCCESS, expectedModel);

        assertFalse(s.isActiveProperty().get());
    }

    @Test
    public void execute_slideshowIsAlreadyInactive_throwsCommandException() {
        assertCommandFailure(new StopSlideshowCommand(), model, Messages.MESSAGE_NOT_IN_SLIDESHOW_MODE);
    }

}
