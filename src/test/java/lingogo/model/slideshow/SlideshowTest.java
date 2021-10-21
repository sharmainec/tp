package lingogo.model.slideshow;

import static lingogo.testutil.TypicalFlashcards.getTypicalFlashcardApp;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import lingogo.model.FlashcardApp;
import lingogo.model.slideshow.exceptions.EmptySlideshowException;
import lingogo.model.slideshow.exceptions.InvalidSlideshowIndexException;

public class SlideshowTest {
    private final FlashcardApp flashcardApp = getTypicalFlashcardApp();
    private Slideshow slideshow = new Slideshow(flashcardApp.getFlashcardList());

    @Test
    public void nextFlashcard_success() {
        assertEquals(slideshow.getCurrentIndex(), 0);
        slideshow.nextFlashcard();
        assertEquals(slideshow.getCurrentIndex(), 1);
    }

    @Test
    public void nextFlashcard_exceedListSize_throwsInvalidSlideshowIndexException() {
        slideshow = new Slideshow(new FlashcardApp().getFlashcardList());
        assertThrows(InvalidSlideshowIndexException.class, () -> slideshow.nextFlashcard());
    }

    @Test
    public void previousFlashcard_success() {
        slideshow.nextFlashcard();
        assertEquals(slideshow.getCurrentIndex(), 1);
        slideshow.previousFlashcard();
        assertEquals(slideshow.getCurrentIndex(), 0);
    }

    @Test
    public void previousFlashcard_exceedListBounds_throwsInvalidSlideshowIndexException() {
        slideshow = new Slideshow(new FlashcardApp().getFlashcardList());
        assertThrows(InvalidSlideshowIndexException.class, () -> slideshow.previousFlashcard());
    }

    @Test
    public void start_resetsCurrentIndex_success() {
        slideshow.nextFlashcard(); // makes currentIdx != 0
        assertNotEquals(slideshow.getCurrentIndex(), 0);

        slideshow.start();
        assertEquals(slideshow.getCurrentIndex(), 0);
    }

    @Test
    public void start_emptySlideshow_throwsEmptySlideshowException() {
        slideshow = new Slideshow(new FlashcardApp().getFlashcardList());
        assertThrows(EmptySlideshowException.class, () -> slideshow.start());
    }

    @Test
    public void stop_resetsCurrentIndex_success() {
        slideshow.nextFlashcard(); // makes currentIdx != 0
        assertNotEquals(slideshow.getCurrentIndex(), 0);

        slideshow.stop();
        assertEquals(slideshow.getCurrentIndex(), 0);
    }

    @Test
    public void equals_success() {
        assertEquals(slideshow, new Slideshow(getTypicalFlashcardApp().getFlashcardList()));
    }

    @Test
    public void equals_notEqual_success() {
        // different flashcards
        assertNotEquals(slideshow, new Slideshow(new FlashcardApp().getFlashcardList()));

        // different current index
        slideshow.nextFlashcard();
        assertNotEquals(slideshow, new Slideshow(getTypicalFlashcardApp().getFlashcardList()));
    }
}
