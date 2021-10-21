package lingogo.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.transformation.FilteredList;
import lingogo.model.flashcard.Flashcard;
import lingogo.model.slideshow.Slideshow;
import lingogo.model.slideshow.exceptions.InvalidSlideshowStartException;
import lingogo.model.slideshow.exceptions.InvalidSlideshowStopException;

public class SlideshowApp implements ReadOnlySlideshowApp {
    private ObjectProperty<Flashcard> currentFlashcard;
    private Slideshow slideshow;
    private BooleanProperty isActive;
    private BooleanProperty isAnswerDisplayed;

    public SlideshowApp(FilteredList<Flashcard> filteredFlashcards) {
        this.currentFlashcard = new SimpleObjectProperty<>(Flashcard.EMPTY_FLASHCARD);
        this.slideshow = new Slideshow(filteredFlashcards);
        this.isActive = new SimpleBooleanProperty(false);
        this.isAnswerDisplayed = new SimpleBooleanProperty(false);
    }

    public void nextFlashcard() {
        currentFlashcard.set(slideshow.nextFlashcard());
        isAnswerDisplayed.set(false);
    }

    public void previousFlashcard() {
        currentFlashcard.set(slideshow.previousFlashcard());
        isAnswerDisplayed.set(false);
    }

    public void start() {
        if (isActive.get()) {
            throw new InvalidSlideshowStartException();
        }
        currentFlashcard.set(slideshow.start());
        isActive.set(true);
        isAnswerDisplayed.set(false);
    }

    public void stop() {
        if (!isActive.get()) {
            throw new InvalidSlideshowStopException();
        }
        slideshow.stop();
        isActive.set(false);
        isAnswerDisplayed.set(false);

        currentFlashcard.set(Flashcard.EMPTY_FLASHCARD);
    }

    public void displayCurrentAnswer() {
        isAnswerDisplayed.set(true);
    }

    @Override
    public Flashcard getCurrentSlide() {
        return currentFlashcard.get();
    }

    @Override
    public ObjectProperty<Flashcard> getCurrentSlideProperty() {
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
