package lingogo.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
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
     * Returns the currentSlideNumber property of the slideshow app.
     */
    IntegerProperty currentSlideNumberProperty();
}
