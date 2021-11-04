package lingogo.testutil;

import lingogo.model.flashcard.Flashcard;
import lingogo.model.flashcard.LanguageType;
import lingogo.model.flashcard.Phrase;

/**
 * A utility class to help with building Flashcard objects.
 */
public class FlashcardBuilder {
    public static final String DEFAULT_LANGUAGE_TYPE = "Chinese";
    public static final String DEFAULT_ENGLISH_PHRASE = "Good Morning";
    public static final String DEFAULT_FOREIGN_PHRASE = "早安";
    public static final Boolean DEFAULT_IS_FLIPPED = false;

    private LanguageType languageType;
    private Phrase englishPhrase;
    private Phrase foreignPhrase;
    private Boolean isFlipped;

    /**
     * Creates a {@code FlashcardBuilder} with the default details.
     */
    public FlashcardBuilder() {
        languageType = new LanguageType(DEFAULT_LANGUAGE_TYPE);
        englishPhrase = new Phrase(DEFAULT_ENGLISH_PHRASE);
        foreignPhrase = new Phrase(DEFAULT_FOREIGN_PHRASE);
        isFlipped = DEFAULT_IS_FLIPPED;
    }

    /**
     * Initializes the FlashcardBuilder with the data of {@code flashcardToCopy}.
     */
    public FlashcardBuilder(Flashcard flashcardToCopy) {
        languageType = flashcardToCopy.getLanguageType();
        englishPhrase = flashcardToCopy.getEnglishPhrase();
        foreignPhrase = flashcardToCopy.getForeignPhrase();
        isFlipped = flashcardToCopy.getFlipStatus();
    }

    /**
     * Sets the {@code languageType} of the {@code Flashcard} that we are building.
     */
    public FlashcardBuilder withLanguageType(String languageType) {
        this.languageType = new LanguageType(languageType);
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

    /**
     * Sets {@code isFlipped} of the {@code Flashcard} that we are building.
     */
    public FlashcardBuilder withFlipStatus(Boolean isFlipped) {
        this.isFlipped = isFlipped;
        return this;
    }

    public Flashcard build() {
        return new Flashcard(languageType, englishPhrase, foreignPhrase);
    }
}
