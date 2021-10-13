package lingogo.model;

import java.io.IOException;
import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import lingogo.commons.core.GuiSettings;
import lingogo.logic.commands.exceptions.CommandException;
import lingogo.model.flashcard.Flashcard;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Flashcard> PREDICATE_SHOW_ALL_FLASHCARDS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' flashcard app file path.
     */
    Path getFlashcardAppFilePath();

    /**
     * Sets the user prefs' flashcard app file path.
     */
    void setFlashcardAppFilePath(Path flashcardAppFilePath);

    /**
     * Replaces flashcard app data with the data in {@code flashcardApp}.
     */
    void setFlashcardApp(ReadOnlyFlashcardApp flashcardApp);

    /** Returns the FlashcardApp */
    ReadOnlyFlashcardApp getFlashcardApp();

    /**
     * Returns true if a flashcard with the same identity as {@code flashcard} exists in the flashcard app.
     */
    boolean hasFlashcard(Flashcard flashcard);

    /**
     * Deletes the given flashcard.
     * The flashcard must exist in the flashcard app.
     */
    void deleteFlashcard(Flashcard target);

    /**
     * Adds the given flashcard.
     * {@code flashcard} must not already exist in the flashcard app.
     */
    void addFlashcard(Flashcard flashcard);

    /**
     * Replaces the given flashcard {@code target} with {@code editedFlashcard}.
     * {@code target} must exist in the flashcard app.
     * The flashcard identity of {@code editedFlashcard} must not be the same as
     * another existing flashcard in the flashcard app.
     */
    void setFlashcard(Flashcard target, Flashcard editedFlashcard);

    /**
     * Creates the CSV file named {@code fileName} which contains all existing
     * cards in the flashcard app.
     * {@code fileName} must be a valid filename with .csv extension.
     */
    void downloadFlashCards(String fileName) throws CommandException;

    /**
     * Loads the CSV file named {@code filePath} which contains foreign words
     * in the first column and English meanings in the second column
     * {@code filePath} must be a valid file path with .csv extension.
     */
    void uploadFlashCards(String filePath) throws CommandException;

    /** Returns an unmodifiable view of the filtered flashcard list */
    ObservableList<Flashcard> getFilteredFlashcardList();

    /**
     * Updates the filter of the filtered flashcard list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredFlashcardList(Predicate<Flashcard> predicate);
}
