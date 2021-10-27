package lingogo.logic.commands;

import static lingogo.logic.commands.CommandTestUtil.assertCommandFailure;
import static lingogo.logic.commands.CommandTestUtil.assertCommandSuccess;
import static lingogo.testutil.TypicalFlashcards.getTypicalFlashcardApp;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import lingogo.commons.core.Messages;
import lingogo.model.Model;
import lingogo.model.ModelManager;
import lingogo.model.UserPrefs;

public class ExportCommandTest {

    private Model model = new ModelManager(getTypicalFlashcardApp(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalFlashcardApp(), new UserPrefs());

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
        String fileName = "exportTest.csv";
        String expectedMessage = String.format(ExportCommand.MESSAGE_SUCCESS, fileName);
        ExportCommand command = new ExportCommand(fileName);
        try {
            expectedModel.exportFlashCards(fileName);
        } catch (Exception e) {
            fail("Exception not expected");
        }
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        try {
            byte[] generatedCsv = Files.readAllBytes(Path.of("data/" + fileName));
            byte[] expectedCsv = Files.readAllBytes(Path.of("src/test/data/SampleCsvFiles/" + fileName));
            assertEquals(new String(generatedCsv), new String(expectedCsv));
        } catch (Exception e) {
            fail("Exception not expected");
        }
    }
}
