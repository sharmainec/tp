package lingogo.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import lingogo.model.flashcard.Flashcard;

/**
 * Unmodifiable view of a slideshow
 */
public interface ReadOnlySlideshowApp {
    /**
     * Returns the current flashcard to be displayed in the slideshow app.
     */
    Flashcard getCurrentSlide();

    /**
     * Returns the currentSlide property of the slideshow app.
     */
    ObjectProperty<Flashcard> currentSlideProperty();

    /**
     * Returns the isActive boolean property of the slideshow app.
     */
    BooleanProperty isActiveProperty();

    /**
     * Returns the isAnswerDisplayedProperty of the slideshow app.
     */
    BooleanProperty isAnswerDisplayedProperty();

    /**
     * Returns the current slide number of the slideshow app.
     */
    int getCurrentSlideNumber();

    /**
     * Returns if the current slide has been answered by the user.
     */
    boolean isCurrentSlideAnswered();

    /**
     * Returns the progress property of the slideshow app.
     */
    String getProgress();
}
