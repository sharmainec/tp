package lingogo.logic.commands;

import static lingogo.logic.commands.CommandTestUtil.assertCommandFailure;
import static lingogo.logic.commands.CommandTestUtil.assertCommandSuccess;
import static lingogo.logic.commands.CommandTestUtil.showFlashcardAtIndex;
import static lingogo.testutil.TypicalFlashcards.getTypicalFlashcardApp;
import static lingogo.testutil.TypicalIndexes.INDEX_FIRST_FLASHCARD;

import org.junit.jupiter.api.Test;

import lingogo.commons.core.Messages;
import lingogo.model.Model;
import lingogo.model.ModelManager;
import lingogo.model.UserPrefs;

public class PreviousSlideCommandTest {

    private Model model = new ModelManager(getTypicalFlashcardApp(), new UserPrefs());

    @Test
    public void execute_success() {
        model.startSlideshow();
        model.slideshowNextFlashcard();

        Model expectedModel = new ModelManager(getTypicalFlashcardApp(), new UserPrefs());
        expectedModel.startSlideshow();

        assertCommandSuccess(new PreviousSlideCommand(), model, PreviousSlideCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_noPreviousFlashcard_throwsCommandException() {
        showFlashcardAtIndex(model, INDEX_FIRST_FLASHCARD);
        model.startSlideshow();

        assertCommandFailure(new PreviousSlideCommand(), model, PreviousSlideCommand.MESSAGE_NO_PREVIOUS_SLIDE);
    }

    @Test
    public void execute_slideshowInactive_throwsCommandException() {
        assertCommandFailure(new NextSlideCommand(), model, Messages.MESSAGE_NOT_IN_SLIDESHOW_MODE);
    }

}
