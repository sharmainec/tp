package lingogo.storage;

import static lingogo.testutil.Assert.assertThrows;
import static lingogo.testutil.TypicalFlashcards.AFTERNOON_CHINESE_FLASHCARD;
import static lingogo.testutil.TypicalFlashcards.HAPPY_BIRTHDAY;
import static lingogo.testutil.TypicalFlashcards.THANK_YOU_CHINESE_FLASHCARD;
import static lingogo.testutil.TypicalFlashcards.getTypicalFlashcardApp;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import lingogo.commons.exceptions.DataConversionException;
import lingogo.model.FlashcardApp;
import lingogo.model.ReadOnlyFlashcardApp;

public class JsonFlashcardAppStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonFlashcardAppStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readFlashcardApp_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readFlashcardApp(null));
    }

    private java.util.Optional<ReadOnlyFlashcardApp> readFlashcardApp(String filePath) throws Exception {
        return new JsonFlashcardAppStorage(Paths.get(filePath)).readFlashcardApp(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
            ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
            : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readFlashcardApp("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readFlashcardApp("notJsonFormatFlashcardApp.json"));
    }

    @Test
    public void readFlashcardApp_invalidFlashcardFlashcardApp_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readFlashcardApp("invalidFlashcardFlashcardApp.json"));
    }

    @Test
    public void readFlashcardApp_invalidAndValidFlashcardFlashcardApp_throwDataConversionException() {
        assertThrows(DataConversionException.class, () -> readFlashcardApp("invalidAndValidFlashcardFlashcardApp"
            + ".json"));
    }

    @Test
    public void readAndSaveFlashcardApp_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempFlashcardApp.json");
        FlashcardApp original = getTypicalFlashcardApp();
        JsonFlashcardAppStorage jsonFlashcardAppStorage = new JsonFlashcardAppStorage(filePath);

        // Save in new file and read back
        jsonFlashcardAppStorage.saveFlashcardApp(original, filePath);
        ReadOnlyFlashcardApp readBack = jsonFlashcardAppStorage.readFlashcardApp(filePath).get();
        assertEquals(original, new FlashcardApp(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addFlashcard(THANK_YOU_CHINESE_FLASHCARD);
        original.removeFlashcard(AFTERNOON_CHINESE_FLASHCARD);
        jsonFlashcardAppStorage.saveFlashcardApp(original, filePath);
        readBack = jsonFlashcardAppStorage.readFlashcardApp(filePath).get();
        assertEquals(original, new FlashcardApp(readBack));

        // Save and read without specifying file path
        original.addFlashcard(HAPPY_BIRTHDAY);
        jsonFlashcardAppStorage.saveFlashcardApp(original); // file path not specified
        readBack = jsonFlashcardAppStorage.readFlashcardApp().get(); // file path not specified
        assertEquals(original, new FlashcardApp(readBack));

    }

    @Test
    public void saveFlashcardApp_nullFlashcardApp_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveFlashcardApp(null, "SomeFile.json"));
    }

    /**
     * Saves {@code flashcardApp} at the specified {@code filePath}.
     */
    private void saveFlashcardApp(ReadOnlyFlashcardApp flashcardApp, String filePath) {
        try {
            new JsonFlashcardAppStorage(Paths.get(filePath))
                .saveFlashcardApp(flashcardApp, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveFlashcardApp_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveFlashcardApp(new FlashcardApp(), null));
    }
}
