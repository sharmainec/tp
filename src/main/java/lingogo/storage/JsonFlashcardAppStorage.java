package lingogo.storage;

import static java.nio.file.Files.isDirectory;
import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import lingogo.commons.core.LogsCenter;
import lingogo.commons.exceptions.DataConversionException;
import lingogo.commons.exceptions.DataFileAsDirectoryException;
import lingogo.commons.exceptions.IllegalValueException;
import lingogo.commons.util.FileUtil;
import lingogo.commons.util.JsonUtil;
import lingogo.model.ReadOnlyFlashcardApp;

/**
 * A class to access FlashcardApp data stored as a json file on the hard disk.
 */
public class JsonFlashcardAppStorage implements FlashcardAppStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonFlashcardAppStorage.class);

    private Path filePath;

    public JsonFlashcardAppStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getFlashcardAppFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyFlashcardApp> readFlashcardApp() throws DataConversionException,
            DataFileAsDirectoryException {
        return readFlashcardApp(filePath);
    }

    /**
     * Similar to {@link #readFlashcardApp()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyFlashcardApp> readFlashcardApp(Path filePath) throws DataConversionException,
            DataFileAsDirectoryException {
        requireNonNull(filePath);

        if (isDirectory(filePath)) {
            throw new DataFileAsDirectoryException("data/flashcardapp.json should be a json file, not a directory.");
        }

        Optional<JsonSerializableFlashcardApp> jsonFlashcardApp = JsonUtil.readJsonFile(
            filePath, JsonSerializableFlashcardApp.class);
        if (!jsonFlashcardApp.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonFlashcardApp.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveFlashcardApp(ReadOnlyFlashcardApp flashcardApp) throws IOException {
        saveFlashcardApp(flashcardApp, filePath);
    }

    /**
     * Similar to {@link #saveFlashcardApp(ReadOnlyFlashcardApp)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveFlashcardApp(ReadOnlyFlashcardApp flashcardApp, Path filePath) throws IOException {
        requireNonNull(flashcardApp);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableFlashcardApp(flashcardApp), filePath);
    }

}
