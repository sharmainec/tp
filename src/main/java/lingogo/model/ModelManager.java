package lingogo.model;

import static java.util.Objects.requireNonNull;
import static lingogo.commons.util.CollectionUtil.requireAllNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import lingogo.commons.core.GuiSettings;
import lingogo.commons.core.LogsCenter;
import lingogo.logic.commands.exceptions.CommandException;
import lingogo.model.flashcard.Flashcard;

/**
 * Represents the in-memory model of the flashcard app data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final FlashcardApp flashcardApp;
    private final UserPrefs userPrefs;
    private final FilteredList<Flashcard> filteredFlashcards;

    /**
     * Initializes a ModelManager with the given flashcardApp and userPrefs.
     */
    public ModelManager(ReadOnlyFlashcardApp flashcardApp, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(flashcardApp, userPrefs);

        logger.fine("Initializing with flashcard app: " + flashcardApp + " and user prefs " + userPrefs);

        this.flashcardApp = new FlashcardApp(flashcardApp);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredFlashcards = new FilteredList<>(this.flashcardApp.getFlashcardList());
    }

    public ModelManager() {
        this(new FlashcardApp(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getFlashcardAppFilePath() {
        return userPrefs.getFlashcardAppFilePath();
    }

    @Override
    public void setFlashcardAppFilePath(Path flashcardAppFilePath) {
        requireNonNull(flashcardAppFilePath);
        userPrefs.setFlashcardAppFilePath(flashcardAppFilePath);
    }

    //=========== FlashcardApp ================================================================================

    @Override
    public void setFlashcardApp(ReadOnlyFlashcardApp flashcardApp) {
        this.flashcardApp.resetData(flashcardApp);
    }

    @Override
    public ReadOnlyFlashcardApp getFlashcardApp() {
        return flashcardApp;
    }

    @Override
    public boolean hasFlashcard(Flashcard flashcard) {
        requireNonNull(flashcard);
        return flashcardApp.hasFlashcard(flashcard);
    }

    @Override
    public void deleteFlashcard(Flashcard target) {
        flashcardApp.removeFlashcard(target);
    }

    @Override
    public void addFlashcard(Flashcard flashcard) {
        flashcardApp.addFlashcard(flashcard);
        updateFilteredFlashcardList(PREDICATE_SHOW_ALL_FLASHCARDS);
    }

    @Override
    public void setFlashcard(Flashcard target, Flashcard editedFlashcard) {
        requireAllNonNull(target, editedFlashcard);

        flashcardApp.setFlashcard(target, editedFlashcard);
    }

    @Override
    public void downloadFlashCards(String fileName) throws CommandException {
        requireAllNonNull(fileName);
        flashcardApp.downloadFlashCards(fileName);
    }

    @Override
    public void uploadFlashCards(String filePath) throws CommandException {
        requireAllNonNull(filePath);
        flashcardApp.uploadFlashCards(filePath);
    }

    //=========== Filtered Flashcard List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Flashcard} backed by the internal list of
     * {@code versionedFlashcardApp}
     */
    @Override
    public ObservableList<Flashcard> getFilteredFlashcardList() {
        return filteredFlashcards;
    }

    @Override
    public void updateFilteredFlashcardList(Predicate<Flashcard> predicate) {
        requireNonNull(predicate);
        filteredFlashcards.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return flashcardApp.equals(other.flashcardApp)
                && userPrefs.equals(other.userPrefs)
                && filteredFlashcards.equals(other.filteredFlashcards);
    }

}
