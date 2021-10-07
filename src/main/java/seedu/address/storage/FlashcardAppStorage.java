package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyFlashcardApp;

/**
 * Represents a storage for {@link seedu.address.model.FlashcardApp}.
 */
public interface FlashcardAppStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getFlashcardAppFilePath();

    /**
     * Returns Flashcard data as a {@link ReadOnlyFlashcardApp}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException             if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyFlashcardApp> readFlashcardApp() throws DataConversionException, IOException;

    /**
     * @see #getFlashcardAppFilePath()
     */
    Optional<ReadOnlyFlashcardApp> readFlashcardApp(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyFlashcardApp} to the storage.
     *
     * @param flashcardApp cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveFlashcardApp(ReadOnlyFlashcardApp flashcardApp) throws IOException;

    /**
     * @see #saveFlashcardApp(ReadOnlyFlashcardApp)
     */
    void saveFlashcardApp(ReadOnlyFlashcardApp flashcardApp, Path filePath) throws IOException;

}
