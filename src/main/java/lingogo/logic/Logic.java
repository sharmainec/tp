package lingogo.logic;

import java.nio.file.Path;

import javafx.collections.ObservableList;
import lingogo.commons.core.GuiSettings;
import lingogo.logic.commands.CommandResult;
import lingogo.logic.commands.exceptions.CommandException;
import lingogo.logic.parser.exceptions.ParseException;
import lingogo.model.ReadOnlyFlashcardApp;
import lingogo.model.ReadOnlySlideshowApp;
import lingogo.model.flashcard.Flashcard;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns the FlashcardApp.
     *
     * @see lingogo.model.Model#getFlashcardApp()
     */
    ReadOnlyFlashcardApp getFlashcardApp();

    /** Returns an unmodifiable view of the filtered list of flashcards */
    ObservableList<Flashcard> getFilteredFlashcardList();

    /**
     * Returns the SlideshowApp.
     *
     * @see lingogo.model.Model#getSlideshowApp()
     */
    ReadOnlySlideshowApp getSlideshowApp();

    /**
     * Returns the user prefs' flashcard app file path.
     */
    Path getFlashcardAppFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Set the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);
}
