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
import lingogo.model.flashcard.Flashcard;
import lingogo.testutil.FlashcardBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalFlashcardApp(), new UserPrefs());
    }

    @Test
    public void execute_newFlashcard_success() {
        Flashcard validFlashcard = new FlashcardBuilder().build();

        Model expectedModel = new ModelManager(model.getFlashcardApp(), new UserPrefs());
        expectedModel.addFlashcard(validFlashcard);

        assertCommandSuccess(new AddCommand(validFlashcard), model,
                String.format(AddCommand.MESSAGE_SUCCESS, validFlashcard), expectedModel);
    }

    @Test
    public void execute_duplicateFlashcard_throwsCommandException() {
        Flashcard flashcardInList = model.getFlashcardApp().getFlashcardList().get(0);
        assertCommandFailure(new AddCommand(flashcardInList), model, AddCommand.MESSAGE_DUPLICATE_FLASHCARD);
    }

    @Test
    public void execute_slideshowActive_throwsCommandException() {
        model.startSlideshow();
        Flashcard validFlashcard = new FlashcardBuilder().build();
        assertCommandFailure(new AddCommand(validFlashcard), model, Messages.MESSAGE_IN_SLIDESHOW_MODE);
    }

}
