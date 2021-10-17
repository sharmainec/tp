package lingogo.testutil;

import lingogo.model.flashcard.Flashcard;
import lingogo.model.flashcard.Phrase;

/**
 * A utility class to help with building Flashcard objects.
 */
public class FlashcardBuilder {
    public static final String DEFAULT_LANGUAGE_TYPE = "Chinese";
    public static final String DEFAULT_ENGLISH_PHRASE = "Good Morning";
    public static final String DEFAULT_FOREIGN_PHRASE = "早安";

    private Phrase languageType;
    private Phrase englishPhrase;
    private Phrase foreignPhrase;

    /**
     * Creates a {@code FlashcardBuilder} with the default details.
     */
    public FlashcardBuilder() {
        languageType = new Phrase(DEFAULT_LANGUAGE_TYPE);
        englishPhrase = new Phrase(DEFAULT_ENGLISH_PHRASE);
        foreignPhrase = new Phrase(DEFAULT_FOREIGN_PHRASE);
    }

    /**
     * Initializes the FlashcardBuilder with the data of {@code flashcardToCopy}.
     */
    public FlashcardBuilder(Flashcard flashcardToCopy) {
        languageType = flashcardToCopy.getLanguageType();
        englishPhrase = flashcardToCopy.getEnglishPhrase();
        foreignPhrase = flashcardToCopy.getForeignPhrase();
    }

    /**
     * Sets the {@code languageType} of the {@code Flashcard} that we are building.
     */
    public FlashcardBuilder withLanguageType(String languageType) {
        this.languageType = new Phrase(languageType);
        return this;
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
        return new Flashcard(languageType, englishPhrase, foreignPhrase);
    }
}
