package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.flashcard.Flashcard;

/**
 * Unmodifiable view of a flashcard app
 */
public interface ReadOnlyFlashcardApp {
    /**
     * Returns an unmodifiable view of the flashcards list.
     * This list will not contain any duplicate flashcards.
     */
    ObservableList<Flashcard> getFlashcardList();
}
