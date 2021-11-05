package lingogo.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import lingogo.commons.core.LogsCenter;
import lingogo.commons.exceptions.DataConversionException;
import lingogo.commons.exceptions.DataFileAsDirectoryException;
import lingogo.model.ReadOnlyFlashcardApp;
import lingogo.model.ReadOnlyUserPrefs;
import lingogo.model.UserPrefs;

/**
 * Manages storage of FlashcardApp data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private FlashcardAppStorage flashcardAppStorage;
    private UserPrefsStorage userPrefsStorage;

    /**
     * Creates a {@code StorageManager} with the given {@code FlashcardAppStorage} and {@code UserPrefStorage}.
     */
    public StorageManager(FlashcardAppStorage flashcardAppStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.flashcardAppStorage = flashcardAppStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ FlashcardApp methods ==============================

    @Override
    public Path getFlashcardAppFilePath() {
        return flashcardAppStorage.getFlashcardAppFilePath();
    }

    @Override
    public Optional<ReadOnlyFlashcardApp> readFlashcardApp() throws DataConversionException, IOException,
            DataFileAsDirectoryException {
        return readFlashcardApp(flashcardAppStorage.getFlashcardAppFilePath());
    }

    @Override
    public Optional<ReadOnlyFlashcardApp> readFlashcardApp(Path filePath) throws DataConversionException, IOException,
            DataFileAsDirectoryException {
        logger.fine("Attempting to read data from file: " + filePath);
        return flashcardAppStorage.readFlashcardApp(filePath);
    }

    @Override
    public void saveFlashcardApp(ReadOnlyFlashcardApp flashcardApp) throws IOException {
        saveFlashcardApp(flashcardApp, flashcardAppStorage.getFlashcardAppFilePath());
    }

    @Override
    public void saveFlashcardApp(ReadOnlyFlashcardApp flashcardApp, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        flashcardAppStorage.saveFlashcardApp(flashcardApp, filePath);
    }

}
