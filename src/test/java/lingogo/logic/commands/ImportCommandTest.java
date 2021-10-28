package lingogo.logic.commands;

import static lingogo.commons.core.Messages.MESSAGE_INVALID_CSV_FORMAT;
import static lingogo.logic.commands.CommandTestUtil.assertCommandFailure;
import static lingogo.logic.commands.CommandTestUtil.assertCommandSuccess;
import static lingogo.testutil.TypicalFlashcards.getTypicalFlashcardApp;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import lingogo.commons.core.Messages;
import lingogo.model.Model;
import lingogo.model.ModelManager;
import lingogo.model.UserPrefs;
import lingogo.model.flashcard.Flashcard;
import lingogo.model.flashcard.Phrase;

public class ImportCommandTest {

    private Model model = new ModelManager(getTypicalFlashcardApp(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalFlashcardApp(), new UserPrefs());

    @Test
    public void equals() {

        ImportCommand firstImportCommand = new ImportCommand("data/same.csv");
        ImportCommand secondImportCommand = new ImportCommand("data/same.csv");
        ImportCommand thirdImportCommand = new ImportCommand("data/different.csv");

        // same object -> returns true
        assertTrue(firstImportCommand.equals(firstImportCommand));

        // same values -> returns true
        assertTrue(firstImportCommand.equals(secondImportCommand));

        // different file name -> return false
        assertFalse(firstImportCommand.equals(thirdImportCommand));

        // null -> returns false
        assertFalse(firstImportCommand.equals(null));
    }

    @Test
    public void execute_slideshowActive_throwsCommandException() {
        model.startSlideshow();

        ImportCommand command = new ImportCommand("myCards.csv");
        String expectedMessage = String.format(Messages.MESSAGE_IN_SLIDESHOW_MODE);

        assertCommandFailure(command, model, expectedMessage);
    }

    @Test
    public void execute_importInvalidHeaders_throwsCommandException() {
        String filePath = "src/test/data/SampleCsvFiles/invalidHeaders.csv";
        String expectedMessage = String.format(MESSAGE_INVALID_CSV_FORMAT, filePath);
        ImportCommand command = new ImportCommand(filePath);
        assertCommandFailure(command, model, expectedMessage);
    }

    @Test
    public void execute_importInvalidContent_throwsCommandException() {
        String filePath = "src/test/data/SampleCsvFiles/invalidContent.csv";
        String expectedMessage = String.format(MESSAGE_INVALID_CSV_FORMAT, filePath);
        ImportCommand command = new ImportCommand(filePath);
        assertCommandFailure(command, model, expectedMessage);
    }

    @Test
    public void execute_importDuplicateContent_noNewFlashcards() {
        String filePath = "src/test/data/SampleCsvFiles/duplicateContent.csv";
        String expectedMessage = String.format(ImportCommand.MESSAGE_SUCCESS, filePath);
        ImportCommand command = new ImportCommand(filePath);
        try {
            expectedModel.importFlashCards(filePath);
        } catch (Exception e) {
            fail("Exception not expected");
        }
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(expectedModel.getFilteredFlashcardList(), model.getFilteredFlashcardList());
    }

    @Test
    public void execute_importNewContent_newFlashcard() {
        String filePath = "src/test/data/SampleCsvFiles/newContent.csv";
        String expectedMessage = String.format(ImportCommand.MESSAGE_SUCCESS, filePath);
        ImportCommand command = new ImportCommand(filePath);
        Flashcard newlyAdded = new Flashcard(new Phrase("Korean"), new Phrase("Hello"), new Phrase("안녕"));
        try {
            model.importFlashCards(filePath);
        } catch (Exception e) {
            fail("Exception not expected");
        }
        expectedModel.addFlashcard(newlyAdded);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(expectedModel.getFilteredFlashcardList(), model.getFilteredFlashcardList());
    }
}
