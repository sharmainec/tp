package lingogo.model;

import static lingogo.testutil.TypicalFlashcards.AFTERNOON_CHINESE_FLASHCARD;
import static lingogo.testutil.TypicalFlashcards.NIGHT_CHINESE_FLASHCARD;
import static lingogo.testutil.TypicalFlashcards.getTypicalFlashcardApp;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import lingogo.model.flashcard.Flashcard;
import lingogo.model.slideshow.exceptions.InvalidSlideshowStartException;
import lingogo.model.slideshow.exceptions.InvalidSlideshowStopException;

public class SlideshowAppTest {
    private static final String PROGRESS_FORMAT_STRING = "%d out of %d";

    private final FlashcardApp flashcardApp = getTypicalFlashcardApp();
    private SlideshowApp slideshowApp = new SlideshowApp(flashcardApp.getFlashcardList());

    @Test
    public void start_success() {
        assertFalse(slideshowApp.isActiveProperty().get());

        slideshowApp.start();

        assertTrue(slideshowApp.isActiveProperty().get());
        assertEquals(slideshowApp.getCurrentSlide(), AFTERNOON_CHINESE_FLASHCARD);
        assertEquals(slideshowApp.getCurrentSlideNumber(), 1);
        assertFalse(slideshowApp.isAnswerDisplayedProperty().get());
        assertEquals(slideshowApp.getProgress(), String.format(PROGRESS_FORMAT_STRING,
                0, flashcardApp.getFlashcardList().size()));
    }

    @Test
    public void start_isAlreadyStarted_throwsInvalidSlideshowStartException() {
        slideshowApp.start();
        assertTrue(slideshowApp.isActiveProperty().get());

        assertThrows(InvalidSlideshowStartException.class, () -> slideshowApp.start());
    }

    @Test
    public void stop_success() {
        slideshowApp.start();
        assertTrue(slideshowApp.isActiveProperty().get());
        assertEquals(slideshowApp.getCurrentSlide(), AFTERNOON_CHINESE_FLASHCARD);
        assertEquals(slideshowApp.getCurrentSlideNumber(), 1);
        assertFalse(slideshowApp.isAnswerDisplayedProperty().get());
        assertEquals(slideshowApp.getProgress(), String.format(PROGRESS_FORMAT_STRING,
                0, flashcardApp.getFlashcardList().size()));

        slideshowApp.stop();

        assertFalse(slideshowApp.isActiveProperty().get());
        assertEquals(slideshowApp.getCurrentSlide(), Flashcard.EMPTY_FLASHCARD);
        assertFalse(slideshowApp.isAnswerDisplayedProperty().get());
    }

    @Test
    public void stop_isAlreadyStopped_throwsInvalidSlideshowStopException() {
        assertFalse(slideshowApp.isActiveProperty().get());
        assertThrows(InvalidSlideshowStopException.class, () -> slideshowApp.stop());
    }

    @Test
    public void nextFlashcard_success() {
        slideshowApp.start();
        assertEquals(slideshowApp.getCurrentSlide(), AFTERNOON_CHINESE_FLASHCARD);
        assertEquals(slideshowApp.getCurrentSlideNumber(), 1);
        assertEquals(slideshowApp.getProgress(), String.format(PROGRESS_FORMAT_STRING,
                0, flashcardApp.getFlashcardList().size()));

        slideshowApp.nextFlashcard();

        assertEquals(slideshowApp.getCurrentSlide(), NIGHT_CHINESE_FLASHCARD);
        assertEquals(slideshowApp.getCurrentSlideNumber(), 2);
        assertEquals(slideshowApp.getProgress(), String.format(PROGRESS_FORMAT_STRING,
                0, flashcardApp.getFlashcardList().size()));
    }

    @Test
    public void previousFlashcard_success() {
        slideshowApp.start();
        slideshowApp.nextFlashcard();
        assertEquals(slideshowApp.getCurrentSlide(), NIGHT_CHINESE_FLASHCARD);
        assertEquals(slideshowApp.getCurrentSlideNumber(), 2);
        assertEquals(slideshowApp.getProgress(), String.format(PROGRESS_FORMAT_STRING,
                0, flashcardApp.getFlashcardList().size()));

        slideshowApp.previousFlashcard();

        assertEquals(slideshowApp.getCurrentSlide(), AFTERNOON_CHINESE_FLASHCARD);
        assertEquals(slideshowApp.getCurrentSlideNumber(), 1);
        assertEquals(slideshowApp.getProgress(), String.format(PROGRESS_FORMAT_STRING,
                0, flashcardApp.getFlashcardList().size()));
    }

    @Test
    public void displayCurrentAnswer_success() {
        assertFalse(slideshowApp.isAnswerDisplayedProperty().get());
        slideshowApp.displayCurrentAnswer();
        assertTrue(slideshowApp.isAnswerDisplayedProperty().get());
    }

    @Test
    public void answerCurrentSlide_success() {
        slideshowApp.start();

        assertFalse(slideshowApp.isCurrentSlideAnswered());
        assertEquals(slideshowApp.getProgress(), String.format(PROGRESS_FORMAT_STRING,
                0, flashcardApp.getFlashcardList().size()));
        slideshowApp.answerCurrentSlide();
        assertTrue(slideshowApp.isCurrentSlideAnswered());
        assertEquals(slideshowApp.getProgress(), String.format(PROGRESS_FORMAT_STRING,
                1, flashcardApp.getFlashcardList().size()));

        slideshowApp.stop();
    }

    @Test
    public void equals_success() {
        assertEquals(slideshowApp, new SlideshowApp(flashcardApp.getFlashcardList()));
    }

    @Test
    public void equals_notEqual_success() {
        // different slideshow
        assertNotEquals(slideshowApp, new SlideshowApp(new FlashcardApp().getFlashcardList()));

        // different isActive attribute
        SlideshowApp other = new SlideshowApp(flashcardApp.getFlashcardList());
        other.start();
        assertNotEquals(slideshowApp, other);

        // different isAnswerDisplayed attribute
        other = new SlideshowApp(flashcardApp.getFlashcardList());
        other.displayCurrentAnswer();
        assertNotEquals(slideshowApp, other);

        // different currentFlashcard attribute
        other = new SlideshowApp(flashcardApp.getFlashcardList());
        slideshowApp.start();
        other.start();
        other.nextFlashcard();
        assertNotEquals(slideshowApp, other);
    }
}
