package lingogo.storage;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lingogo.commons.exceptions.IllegalValueException;
import lingogo.model.flashcard.Flashcard;
import lingogo.model.flashcard.LanguageType;
import lingogo.model.flashcard.Phrase;


/**
 * Jackson-friendly version of {@link Flashcard}.
 */
class JsonAdaptedFlashcard {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Flashcard's %s field is missing!";

    private final String languageType;
    private final String englishPhrase;
    private final String foreignPhrase;

    /**
     * Constructs a {@code JsonAdaptedFlashcard} with the given flashcard details.
     */
    @JsonCreator
    public JsonAdaptedFlashcard(@JsonProperty("languageType") String languageType,
                                @JsonProperty("englishPhrase") String englishPhrase,
                                @JsonProperty("foreignPhrase") String foreignPhrase) {
        this.languageType = languageType;
        this.englishPhrase = englishPhrase;
        this.foreignPhrase = foreignPhrase;

    }

    /**
     * Converts a given {@code Flashcard} into this class for Jackson use.
     */
    public JsonAdaptedFlashcard(Flashcard source) {
        languageType = source.getLanguageType().value;
        englishPhrase = source.getEnglishPhrase().value;
        foreignPhrase = source.getForeignPhrase().value;
    }

    /**
     * Converts this Jackson-friendly adapted flashcard object into the model's {@code Flashcard} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted flashcard.
     */
    public Flashcard toModelType() throws IllegalValueException {

        if (languageType == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    "Language " + Phrase.class.getSimpleName()));
        }
        if (!LanguageType.isValidLanguageType(languageType)) {
            throw new IllegalValueException(Phrase.MESSAGE_CONSTRAINTS);
        }

        final LanguageType modelLanguageType = new LanguageType(languageType);

        if (englishPhrase == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                "English " + Phrase.class.getSimpleName()));
        }
        if (!Phrase.isValidPhrase(englishPhrase)) {
            throw new IllegalValueException(Phrase.MESSAGE_CONSTRAINTS);
        }

        final Phrase modelEnglishPhrase = new Phrase(englishPhrase);

        if (foreignPhrase == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                "Foreign " + Phrase.class.getSimpleName()));
        }
        if (!Phrase.isValidPhrase(foreignPhrase)) {
            throw new IllegalValueException(Phrase.MESSAGE_CONSTRAINTS);
        }

        final Phrase modelForeignPhrase = new Phrase(foreignPhrase);

        return new Flashcard(modelLanguageType, modelEnglishPhrase, modelForeignPhrase);
    }

}
