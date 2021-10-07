package seedu.address.testutil;

import seedu.address.model.flashcard.Flashcard;
import seedu.address.model.flashcard.Phrase;

/**
 * A utility class to help with building Flashcard objects.
 */
public class FlashcardBuilder {
    public static final String DEFAULT_ENGLISH_PHRASE = "Good Morning";
    public static final String DEFAULT_FOREIGN_PHRASE = "早安";

    private Phrase englishPhrase;
    private Phrase foreignPhrase;

    /**
     * Creates a {@code FlashcardBuilder} with the default details.
     */
    public FlashcardBuilder() {
        englishPhrase = new Phrase(DEFAULT_ENGLISH_PHRASE);
        foreignPhrase = new Phrase(DEFAULT_FOREIGN_PHRASE);
    }

    /**
     * Initializes the FlashcardBuilder with the data of {@code flashcardToCopy}.
     */
    public FlashcardBuilder(Flashcard flashcardToCopy) {
        englishPhrase = flashcardToCopy.getEnglishPhrase();
        foreignPhrase = flashcardToCopy.getForeignPhrase();
    }

    /**
     * Sets the {@code englishPhrase} of the {@code Flashcard} that we are building.
     */
    public FlashcardBuilder withEnglishPhrase(String englishPhrase) {
        this.englishPhrase = new Phrase(englishPhrase);
        return this;
    }

    /**
     * Sets the {@code foreignPhrase} of the {@code Flashcard} that we are building.
     */
    public FlashcardBuilder withForeignPhrase(String foreignPhrase) {
        this.foreignPhrase = new Phrase(foreignPhrase);
        return this;
    }

    public Flashcard build() {
        return new Flashcard(englishPhrase, foreignPhrase);
    }
}
