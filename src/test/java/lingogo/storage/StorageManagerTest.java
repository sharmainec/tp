package lingogo.storage;

import static lingogo.testutil.TypicalFlashcards.getTypicalFlashcardApp;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import lingogo.commons.core.GuiSettings;
import lingogo.model.FlashcardApp;
import lingogo.model.ReadOnlyFlashcardApp;
import lingogo.model.UserPrefs;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        JsonFlashcardAppStorage flashcardAppStorage = new JsonFlashcardAppStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(flashcardAppStorage, userPrefsStorage);
    }

    private Path getTempFilePath(String fileName) {
        return testFolder.resolve(fileName);
    }

    @Test
    public void prefsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonUserPrefsStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
         */
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(new GuiSettings(300, 600, 4, 6));
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void flashcardAppReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonFlashcardAppStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonFlashcardAppStorageTest} class.
         */
        FlashcardApp original = getTypicalFlashcardApp();
        storageManager.saveFlashcardApp(original);
        ReadOnlyFlashcardApp retrieved = storageManager.readFlashcardApp().get();
        assertEquals(original, new FlashcardApp(retrieved));
    }

    @Test
    public void getFlashcardAppFilePath() {
        assertNotNull(storageManager.getFlashcardAppFilePath());
    }

}
