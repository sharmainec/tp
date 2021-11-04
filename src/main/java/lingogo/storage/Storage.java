package lingogo.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import lingogo.commons.exceptions.DataConversionException;
import lingogo.commons.exceptions.DataFileAsDirectoryException;
import lingogo.model.ReadOnlyFlashcardApp;
import lingogo.model.ReadOnlyUserPrefs;
import lingogo.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends FlashcardAppStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getFlashcardAppFilePath();

    @Override
    Optional<ReadOnlyFlashcardApp> readFlashcardApp() throws DataConversionException, IOException,
            DataFileAsDirectoryException;

    @Override
    void saveFlashcardApp(ReadOnlyFlashcardApp flashcardApp) throws IOException;

}
