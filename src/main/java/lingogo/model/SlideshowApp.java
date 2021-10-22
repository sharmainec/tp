package lingogo.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import lingogo.model.flashcard.Flashcard;
import lingogo.model.slideshow.Slideshow;
import lingogo.model.slideshow.exceptions.InvalidSlideshowStartException;
import lingogo.model.slideshow.exceptions.InvalidSlideshowStopException;

public class SlideshowApp implements ReadOnlySlideshowApp {
    private ObjectProperty<Flashcard> currentFlashcard;
    private Slideshow slideshow;
    private BooleanProperty isActive;
    private BooleanProperty isAnswerDisplayed;

    /**
     * Creates a SlideshowApp using the flashcards in {@code flashcards}.
     */
    public SlideshowApp(ObservableList<Flashcard> flashcards) {
        this.currentFlashcard = new SimpleObjectProperty<>(Flashcard.EMPTY_FLASHCARD);
        this.slideshow = new Slideshow(flashcards);
        this.isActive = new SimpleBooleanProperty(false);
        this.isAnswerDisplayed = new SimpleBooleanProperty(false);
    }

    /**
     * Toggles to the next flashcard in the SlideshowApp.
     */
    public void nextFlashcard() {
        currentFlashcard.set(slideshow.nextFlashcard());
        isAnswerDisplayed.set(slideshow.isCurrentSlideAnswered());
    }

    /**
     * Toggles to the previous flashcard in the SlideshowApp.
     */
    public void previousFlashcard() {
        currentFlashcard.set(slideshow.previousFlashcard());
        isAnswerDisplayed.set(slideshow.isCurrentSlideAnswered());
    }

    /**
     * Starts the slideshow in the SlideshowApp. Throws {@code InvalidSlideshowStartException}
     * if the SlideshowApp is already started.
     */
    public void start() {
        if (isActive.get()) {
            throw new InvalidSlideshowStartException();
        }
        currentFlashcard.set(slideshow.start());
        isActive.set(true);
        isAnswerDisplayed.set(false);
    }

    /**
     * Stops the slideshow in the SlideshowApp. Throws {@code InvalidSlideshowStopException}
     * if the SlideshowApp is already stopped.
     */
    public void stop() {
        if (!isActive.get()) {
            throw new InvalidSlideshowStopException();
        }
        slideshow.stop();
        isActive.set(false);
        isAnswerDisplayed.set(false);
        currentFlashcard.set(Flashcard.EMPTY_FLASHCARD);
    }

    /**
     * Display the answer to the current flashcard in the SlideshowApp.
     */
    public void displayCurrentAnswer() {
        isAnswerDisplayed.set(true);
    }

    public void answerCurrentSlide() {
        slideshow.answerCurrentSlide();
    }

    @Override
    public Flashcard getCurrentSlide() {
        return currentFlashcard.get();
    }

    @Override
    public ObjectProperty<Flashcard> currentSlideProperty() {
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

    @Override
    public int getCurrentSlideNumber() {
        return slideshow.getCurrentSlideNumber();
    }

    @Override
    public boolean isCurrentSlideAnswered() {
        return slideshow.isCurrentSlideAnswered();
    }

    @Override
    public String getProgress() {
        return slideshow.getProgress();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof SlideshowApp)) {
            return false;
        }

        SlideshowApp s = (SlideshowApp) other;

        return currentFlashcard.getValue().equals(s.currentFlashcard.getValue())
                && slideshow.equals(s.slideshow)
                && isActive.getValue().equals(s.isActive.getValue())
                && isAnswerDisplayed.getValue().equals(s.isAnswerDisplayed.getValue());
    }
}
