package lingogo.model.slideshow;

import static lingogo.testutil.TypicalFlashcards.getTypicalFlashcardApp;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import lingogo.model.FlashcardApp;
import lingogo.model.slideshow.exceptions.EmptySlideshowException;
import lingogo.model.slideshow.exceptions.InvalidSlideshowIndexException;
import lingogo.model.slideshow.exceptions.SlideAlreadyAnsweredException;

public class SlideshowTest {
    private final FlashcardApp flashcardApp = getTypicalFlashcardApp();
    private Slideshow slideshow = new Slideshow(flashcardApp.getFlashcardList());

    @Test
    public void nextFlashcard_success() {
        assertEquals(slideshow.getCurrentIndex(), 0);
        assertEquals(slideshow.getCurrentSlideNumber(), 1);
        slideshow.nextFlashcard();
        assertEquals(slideshow.getCurrentIndex(), 1);
        assertEquals(slideshow.getCurrentSlideNumber(), 2);
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
        assertEquals(slideshow.getCurrentSlideNumber(), 2);
        slideshow.previousFlashcard();
        assertEquals(slideshow.getCurrentIndex(), 0);
        assertEquals(slideshow.getCurrentSlideNumber(), 1);
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
        assertNotEquals(slideshow.getCurrentSlideNumber(), 1);

        slideshow.start();
        assertEquals(slideshow.getCurrentIndex(), 0);
        assertEquals(slideshow.getCurrentSlideNumber(), 1);
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
        assertNotEquals(slideshow.getCurrentSlideNumber(), 1);

        slideshow.stop();
        assertEquals(slideshow.getCurrentIndex(), 0);
        assertEquals(slideshow.getCurrentSlideNumber(), 1);
    }

    @Test
    public void answerCurrentSlide_success() {
        slideshow.start();

        slideshow.answerCurrentSlide();
        assertTrue(slideshow.isCurrentSlideAnswered());
        assertEquals(slideshow.getNumberOfAnsweredFlashcards(), 1);
        String expectedProgress = String.format("%d out of %d",
                slideshow.getNumberOfAnsweredFlashcards(), slideshow.getTotalNumberOfSlides());
        assertEquals(slideshow.getProgress(), expectedProgress);

        slideshow.stop();
    }

    @Test
    public void answerCurrentSlide_currentSlideAlreadyAnswered_throwsSlideAlreadyAnsweredException() {
        slideshow.start();

        slideshow.answerCurrentSlide();
        assertTrue(slideshow.isCurrentSlideAnswered());

        assertThrows(SlideAlreadyAnsweredException.class, () -> slideshow.answerCurrentSlide());

        slideshow.stop();
    }

    @Test
    public void getNumberOfSlides() {
        slideshow.start();

        assertEquals(slideshow.getTotalNumberOfSlides(), flashcardApp.getFlashcardList().size());

        slideshow.stop();
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
