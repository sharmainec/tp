package lingogo.model.util;

import lingogo.model.FlashcardApp;
import lingogo.model.ReadOnlyFlashcardApp;
import lingogo.model.flashcard.Flashcard;
import lingogo.model.flashcard.LanguageType;
import lingogo.model.flashcard.Phrase;

/**
 * Contains utility methods for populating {@code FlashcardApp} with sample data.
 */
public class SampleDataUtil {
    public static Flashcard[] getSampleFlashcards() {
        return new Flashcard[] {
            new Flashcard(new LanguageType("Chinese"), new Phrase("Hello"), new Phrase("你好")),
            new Flashcard(new LanguageType("Chinese"), new Phrase("Good Morning"), new Phrase("早安")),
            new Flashcard(new LanguageType("Chinese"), new Phrase("Good Afternoon"), new Phrase("午安")),
            new Flashcard(new LanguageType("Chinese"), new Phrase("Good Night"), new Phrase("晚安"))
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
