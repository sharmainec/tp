package lingogo.model.slideshow;

import javafx.collections.transformation.FilteredList;
import lingogo.model.flashcard.Flashcard;
import lingogo.model.slideshow.exceptions.EmptySlideshowException;
import lingogo.model.slideshow.exceptions.InvalidSlideshowIndexException;

public class Slideshow {
    private final FilteredList<Flashcard> filteredFlashcards;
    private int currentIdx; // index of the current slide in the filteredlist

    public Slideshow(FilteredList<Flashcard> filteredFlashcards) {
        this.filteredFlashcards = filteredFlashcards;
        currentIdx = 0;
    }

    /**
     * Returns the next {@code Flashcard} in the filtered list. Throws {@code InvalidSlideshowIndexException}
     * if the end of the list is reached.
     */
    public Flashcard nextFlashcard() {
        // TODO: Boundary tests for this (incl empty list)

        if (currentIdx + 1 >= filteredFlashcards.size()) {
            throw new InvalidSlideshowIndexException();
        }
        currentIdx++;
        return filteredFlashcards.get(currentIdx);
    }

    /**
     * Returns the previous {@code Flashcard} in the filtered list. Throws {@code InvalidSlideshowIndexException}
     * if there is no previous flashcard in the list.
     * @return
     */
    public Flashcard previousFlashcard() {
        // TODO: Boundary tests for this (incl empty list)

        if (currentIdx - 1 < 0) {
            throw new InvalidSlideshowIndexException();
        }
        currentIdx--;
        return filteredFlashcards.get(currentIdx);
    }

    /**
     * Starts the slideshow from the first card.
     */
    public Flashcard start() {
        currentIdx = 0; // reset index

        if (filteredFlashcards.size() == 0) {
            throw new EmptySlideshowException();
        }
        return filteredFlashcards.get(currentIdx);
    }

    /**
     * Stops the current slideshow.
     */
    public void stop() {
        currentIdx = 0; // reset index
    }

}
