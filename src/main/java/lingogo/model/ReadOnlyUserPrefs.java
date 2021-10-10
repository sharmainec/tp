package lingogo.model;

import java.nio.file.Path;

import lingogo.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    Path getFlashcardAppFilePath();

}
