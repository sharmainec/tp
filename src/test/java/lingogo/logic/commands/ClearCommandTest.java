package lingogo.logic.commands;

import static lingogo.logic.commands.CommandTestUtil.assertCommandFailure;
import static lingogo.logic.commands.CommandTestUtil.assertCommandSuccess;
import static lingogo.testutil.TypicalFlashcards.getTypicalFlashcardApp;

import org.junit.jupiter.api.Test;

import lingogo.commons.core.Messages;
import lingogo.model.FlashcardApp;
import lingogo.model.Model;
import lingogo.model.ModelManager;
import lingogo.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyFlashcardApp_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyFlashcardApp_success() {
        Model model = new ModelManager(getTypicalFlashcardApp(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalFlashcardApp(), new UserPrefs());
        expectedModel.setFlashcardApp(new FlashcardApp());

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_slideshowActive_throwsCommandException() {
        Model model = new ModelManager(getTypicalFlashcardApp(), new UserPrefs());
        model.startSlideshow();

        String expectedMessage = String.format(Messages.MESSAGE_IN_SLIDESHOW_MODE);

        assertCommandFailure(new ClearCommand(), model, expectedMessage);
    }

}
