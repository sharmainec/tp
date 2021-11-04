package lingogo.logic.commands;

import static lingogo.logic.commands.CommandTestUtil.assertCommandFailure;
import static lingogo.logic.commands.CommandTestUtil.assertCommandSuccess;
import static lingogo.testutil.TypicalFlashcards.getTypicalFlashcardApp;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;

import org.junit.jupiter.api.Test;

import lingogo.commons.core.Messages;
import lingogo.model.FlashcardApp;
import lingogo.model.Model;
import lingogo.model.ModelManager;
import lingogo.model.UserPrefs;
import lingogo.model.flashcard.Flashcard;
import lingogo.model.flashcard.LanguageType;
import lingogo.model.flashcard.Phrase;

public class ExportCommandTest {

    private Model model = new ModelManager(getTypicalFlashcardApp(), new UserPrefs());

    @Test
    public void equals() {

        ExportCommand firstExportCommand = new ExportCommand("same.csv");
        ExportCommand secondExportCommand = new ExportCommand("same.csv");
        ExportCommand thirdExportCommand = new ExportCommand("different.csv");

        // same object -> returns true
        assertTrue(firstExportCommand.equals(firstExportCommand));

        // same values -> returns true
        assertTrue(firstExportCommand.equals(secondExportCommand));

        // different file name -> return false
        assertFalse(firstExportCommand.equals(thirdExportCommand));

        // null -> returns false
        assertFalse(firstExportCommand.equals(null));
    }

    @Test
    public void execute_slideshowActive_throwsCommandException() {
        model.startSlideshow();

        ExportCommand command = new ExportCommand("myCards.csv");
        String expectedMessage = String.format(Messages.MESSAGE_IN_SLIDESHOW_MODE);

        assertCommandFailure(command, model, expectedMessage);
    }

    @Test
    public void execute_exportTypicalFlashcards_successfulExport() {
        createDataFolder();
        String fileName = "exportTest.csv";
        ExportCommand command = new ExportCommand(fileName);
        String expectedMessage = String.format(ExportCommand.MESSAGE_SUCCESS, fileName);
        Model expectedModel = new ModelManager(getTypicalFlashcardApp(), new UserPrefs());
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        Model emptyModel = new ModelManager(new FlashcardApp(), new UserPrefs());
        try {
            new ImportCommand(fileName).execute(emptyModel);
            assertEquals(model.getFilteredFlashcardList(), emptyModel.getFilteredFlashcardList());
        } catch (Exception e) {
            fail("Exception not expected");
        }
        try {
            deleteCopyInDataFolder(fileName);
        } catch (Exception e) {
            fail("Exception not expected");
        }
    }

    @Test
    public void execute_overwriteExportTestCsvFile_successfulExport() {
        createDataFolder();
        String fileName = "exportTest.csv";
        // create an exported file with just typical flashcards
        try {
            new ExportCommand(fileName).execute(model);
        } catch (Exception e) {
            fail("Exception not expected");
        }
        // add a new flashcard and export again to the existing CSV file
        Flashcard newlyAdded = new Flashcard(new LanguageType("Korean"), new Phrase("Hello"), new Phrase("안녕"));
        model.addFlashcard(newlyAdded);
        ExportCommand command = new ExportCommand(fileName);
        Model expectedModel = new ModelManager(getTypicalFlashcardApp(), new UserPrefs());
        expectedModel.addFlashcard(newlyAdded);
        String expectedMessage = String.format(ExportCommand.MESSAGE_SUCCESS, fileName);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        // check if overwriting successfully happened
        Model emptyModel = new ModelManager(new FlashcardApp(), new UserPrefs());
        try {
            new ImportCommand(fileName).execute(emptyModel);
            assertEquals(expectedModel.getFilteredFlashcardList(), emptyModel.getFilteredFlashcardList());
        } catch (Exception e) {
            fail("Exception not expected");
        }
        try {
            deleteCopyInDataFolder(fileName);
        } catch (Exception e) {
            fail("Exception not expected");
        }
    }

    public void deleteCopyInDataFolder(String fileName) throws Exception {
        File file = new File("data/" + fileName);
        file.delete();
    }

    public void createDataFolder() {
        File data = new File("data");
        if (!data.exists()) {
            data.mkdir();
        }
    }
}
