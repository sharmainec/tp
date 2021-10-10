package lingogo.testutil;

import lingogo.model.FlashcardApp;
import lingogo.model.flashcard.Flashcard;

/**
 * A utility class to help with building FlashcardApp objects.
 * Example usage: <br>
 *     {@code FlashcardApp fa = new FlashcardAppBuilder().withFlashcard(flashcard).build();}
 */
public class FlashcardAppBuilder {
    private FlashcardApp flashcardApp;

    public FlashcardAppBuilder() {
        flashcardApp = new FlashcardApp();
    }

    public FlashcardAppBuilder(FlashcardApp flashcardApp) {
        this.flashcardApp = flashcardApp;
    }

    /**
     * Adds a new {@code Flashcard} to the {@code FlashcardApp} that we are building.
     */
    public FlashcardAppBuilder withFlashcard(Flashcard flashcard) {
        flashcardApp.addFlashcard(flashcard);
        return this;
    }

    public FlashcardApp build() {
        return flashcardApp;
    }
}
