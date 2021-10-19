package lingogo.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.transformation.FilteredList;
import lingogo.model.flashcard.Flashcard;
import lingogo.model.slideshow.Slideshow;

public class SlideshowApp implements ReadOnlySlideshowApp {
    private Flashcard currentFlashcard;
    private Slideshow slideshow;
    private BooleanProperty isActive;
    private BooleanProperty isAnswerDisplayed;

    public SlideshowApp(FilteredList<Flashcard> filteredFlashcards) {
        this.currentFlashcard = Flashcard.EMPTY_FLASHCARD;
        this.slideshow = new Slideshow(filteredFlashcards);
        this.isActive = new SimpleBooleanProperty(false);
        this.isAnswerDisplayed = new SimpleBooleanProperty(false);
    }

    public void nextFlashcard() {
        currentFlashcard = slideshow.nextFlashcard();
        isAnswerDisplayed.set(false);
    }

    public void previousFlashcard() {
        currentFlashcard = slideshow.previousFlashcard();
        isAnswerDisplayed.set(false);
    }

    public void start() {
        // TODO: Throw error when slideshow is already running
        currentFlashcard = slideshow.start();
        isActive.set(true);
        isAnswerDisplayed.set(false);
    }

    public void stop() {
        // TODO: Throw error when slideshow is not already running
        slideshow.stop();
        isActive.set(false);
        isAnswerDisplayed.set(false);

        currentFlashcard = Flashcard.EMPTY_FLASHCARD;
    }

    public int numFlashcards() {
        return slideshow.numFlashcards();
    }

    public void displayCurrentAnswer() {
        isAnswerDisplayed.set(true);;
    }

    @Override
    public Flashcard getCurrentSlide() {
        return currentFlashcard;
    }

    @Override
    public BooleanProperty isActiveProperty() {
        return isActive;
    }

    @Override
    public BooleanProperty isAnswerDisplayedProperty() {
        return isAnswerDisplayed;
    }
}
