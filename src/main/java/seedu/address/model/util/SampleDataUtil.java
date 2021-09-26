package seedu.address.model.util;

import seedu.address.model.FlashcardApp;
import seedu.address.model.ReadOnlyFlashcardApp;
import seedu.address.model.flashcard.Flashcard;
import seedu.address.model.flashcard.Phrase;

/**
 * Contains utility methods for populating {@code FlashcardApp} with sample data.
 */
public class SampleDataUtil {
    public static Flashcard[] getSampleFlashcards() {
        return new Flashcard[] {
            new Flashcard(new Phrase("Hello"), new Phrase("你好")),
            new Flashcard(new Phrase("Good Morning"), new Phrase("早安")),
            new Flashcard(new Phrase("Good Afternoon"), new Phrase("下午好")),
            new Flashcard(new Phrase("Good Night"), new Phrase("晚安"))
        };
    }

    public static ReadOnlyFlashcardApp getSampleFlashcardApp() {
        FlashcardApp sampleFa = new FlashcardApp();
        for (Flashcard sampleFlashcard : getSampleFlashcards()) {
            sampleFa.addFlashcard(sampleFlashcard);
        }
        return sampleFa;
    }
}
