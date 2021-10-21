package lingogo.model.slideshow;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import lingogo.model.flashcard.Flashcard;
import lingogo.model.slideshow.exceptions.EmptySlideshowException;
import lingogo.model.slideshow.exceptions.InvalidSlideshowIndexException;
import lingogo.model.slideshow.exceptions.SlideAlreadyAnsweredException;

public class Slideshow {
    private final ObservableList<Flashcard> flashcards;
    private List<Flashcard> slides;
    private int currentIdx; // index of the current slide in the flashcard list

    /**
     * Creates a Slideshow using the flashcards in {@code flashcards}.
     */
    public Slideshow(ObservableList<Flashcard> flashcards) {
        this.flashcards = flashcards;
        this.slides = Collections.emptyList();
        currentIdx = 0;
    }

    /**
     * Returns the next {@code Flashcard} in the flashcard list. Throws {@code InvalidSlideshowIndexException}
     * if the end of the list is reached.
     */
    public Flashcard nextFlashcard() {
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
        // Create slides for each flashcard
        slides = flashcards.stream().map(flashcard -> new Flashcard(flashcard)).collect(Collectors.toList());
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
        slides = Collections.emptyList(); // Removes all slides
        currentIdx = 0; // reset index
    }

    /**
     * Answers the current slide in the slideshow.
     */
    public void answerCurrentSlide() {
        if (isCurrentSlideAnswered()) {
            throw new SlideAlreadyAnsweredException();
        }
        Flashcard flippedFlashcard = slides.get(currentIdx).getFlippedFlashcard();
        slides.set(currentIdx, flippedFlashcard);
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
        return slides.get(currentIdx).getFlipStatus();
    }

    /**
     * Returns the total number of slides in the slideshow.
     */
    public int getTotalNumberOfSlides() {
        return flashcards.size();
    }

    /**
     * Returns the total number of flashcards answered so far in the slideshow.
     */
    public int getNumberOfAnsweredFlashcards() {
        return (int) slides.stream().filter(Flashcard::getFlipStatus).count();
    }

    /**
     * Returns a string showing how many flashcards out of the
     * total number of flashcards in the slideshow have been answered.
     */
    public String getProgress() {
        return String.format("%d out of %d", getNumberOfAnsweredFlashcards(), getTotalNumberOfSlides());
    }

    /**
     * Returns the current index of the slideshow. This method should only be used for testing purposes.
     */
    public int getCurrentIndex() {
        return currentIdx;
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
                && slides.equals(s.slides)
                && currentIdx == s.currentIdx;
    }

    @Override
    public int hashCode() {
        return flashcards.hashCode();
    }
}
