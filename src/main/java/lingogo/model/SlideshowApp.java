package lingogo.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
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
    private IntegerProperty currentSlideNumber;

    public SlideshowApp(FilteredList<Flashcard> filteredFlashcards) {
        this.currentFlashcard = new SimpleObjectProperty<>(Flashcard.EMPTY_FLASHCARD);
        this.slideshow = new Slideshow(filteredFlashcards);
        this.isActive = new SimpleBooleanProperty(false);
        this.isAnswerDisplayed = new SimpleBooleanProperty(false);
        this.currentSlideNumber = new SimpleIntegerProperty(0);
    }

    public void nextFlashcard() {
        currentFlashcard.set(slideshow.nextFlashcard());
        isAnswerDisplayed.set(false);
        currentSlideNumber.set(currentSlideNumber.get() + 1);
    }

    public void previousFlashcard() {
        currentFlashcard.set(slideshow.previousFlashcard());
        isAnswerDisplayed.set(false);
        currentSlideNumber.set(currentSlideNumber.get() - 1);
    }

    public void start() {
        if (isActive.get()) {
            throw new InvalidSlideshowStartException();
        }
        currentFlashcard.set(slideshow.start());
        isActive.set(true);
        isAnswerDisplayed.set(false);
        currentSlideNumber.set(1);
    }

    public void stop() {
        if (!isActive.get()) {
            throw new InvalidSlideshowStopException();
        }
        slideshow.stop();
        isActive.set(false);
        isAnswerDisplayed.set(false);
        currentSlideNumber.set(0);
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
    public IntegerProperty currentSlideNumberProperty() {
        return currentSlideNumber;
    }
}
