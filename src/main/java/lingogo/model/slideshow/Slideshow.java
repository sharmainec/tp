package lingogo.model.slideshow;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.transformation.FilteredList;
import lingogo.model.flashcard.Flashcard;
import lingogo.model.slideshow.exceptions.EmptySlideshowException;
import lingogo.model.slideshow.exceptions.InvalidSlideshowIndexException;

public class Slideshow {
    private final FilteredList<Flashcard> filteredFlashcards;
    private List<Slide> slides;
    private int currentIdx; // index of the current slide in the filteredlist

    public Slideshow(FilteredList<Flashcard> filteredFlashcards) {
        this.filteredFlashcards = filteredFlashcards;
        this.slides = Collections.emptyList();
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
        // Create slides for each flashcard
        slides = filteredFlashcards.stream().map(flashcard -> new Slide(flashcard)).collect(Collectors.toList());
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
        slides = Collections.emptyList(); // Removes all slides
        currentIdx = 0; // reset index
    }

    /**
     * Answers the current slide in the slideshow.
     */
    public void answerCurrentSlide() {
        slides.get(currentIdx).answer();
    }

    /**
     * Returns the current slide number.
     */
    public int getCurrentSlideNumber() {
        return currentIdx + 1;
    }

    /**
     * Checks whether the current slide in the slideshow has been answered.
     */
    public boolean isCurrentSlideAnswered() {
        return slides.get(currentIdx).isAnswered();
    }

    /**
     * Returns the total number of slides in the slideshow.
     */
    public int getTotalNumberOfSlides() {
        return filteredFlashcards.size();
    }

    /**
     * Returns the total number of flashcards answered so far in the slideshow.
     */
    public int getNumberOfAnsweredFlashcards() {
        return (int) slides.stream().filter(Slide::isAnswered).count();
    }

    public String getProgress() {
        return String.format("%d out of %d", getNumberOfAnsweredFlashcards(), getTotalNumberOfSlides());
    }
}
