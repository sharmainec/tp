package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalFlashcards.getTypicalFlashcardApp;

import org.junit.jupiter.api.Test;

import seedu.address.model.FlashcardApp;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

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

}
