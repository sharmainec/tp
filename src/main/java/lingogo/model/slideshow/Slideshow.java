package lingogo.model.slideshow;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import lingogo.model.flashcard.Flashcard;
import lingogo.model.slideshow.exceptions.EmptySlideshowException;
import lingogo.model.slideshow.exceptions.InvalidSlideshowIndexException;

public class Slideshow {
    private final ObservableList<Flashcard> flashcards;
    private int currentIdx; // index of the current slide in the flashcard list

    public Slideshow(FilteredList<Flashcard> flashcards) {
        this.flashcards = flashcards;
        currentIdx = 0;
    }

    /**
     * Returns the next {@code Flashcard} in the flashcard list. Throws {@code InvalidSlideshowIndexException}
     * if the end of the list is reached.
     */
    public Flashcard nextFlashcard() {
        // TODO: Boundary tests for this (incl empty list)

        if (currentIdx + 1 >= flashcards.size()) {
            throw new InvalidSlideshowIndexException();
        }
        currentIdx++;
        return flashcards.get(currentIdx);
    }

    /**
     * Returns the previous {@code Flashcard} in the flashcard list. Throws {@code InvalidSlideshowIndexException}
     * if there is no previous flashcard in the list.
     * @return
     */
    public Flashcard previousFlashcard() {
        // TODO: Boundary tests for this (incl empty list)

        if (currentIdx - 1 < 0) {
            throw new InvalidSlideshowIndexException();
        }
        currentIdx--;
        return flashcards.get(currentIdx);
    }

    /**
     * Starts the slideshow from the first card.
     */
    public Flashcard start() {
        currentIdx = 0; // reset index

        if (flashcards.size() == 0) {
            throw new EmptySlideshowException();
        }
        return flashcards.get(currentIdx);
    }

    /**
     * Stops the current slideshow.
     */
    public void stop() {
        currentIdx = 0; // reset index
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Slideshow)) {
            return false;
        }

        Slideshow s = (Slideshow) other;

        return flashcards.equals(s.flashcards)
                && currentIdx == s.currentIdx;
    }
}
