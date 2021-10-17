package lingogo.storage;

import static lingogo.storage.JsonAdaptedFlashcard.MISSING_FIELD_MESSAGE_FORMAT;
import static lingogo.testutil.Assert.assertThrows;
import static lingogo.testutil.TypicalFlashcards.NIGHT_CHINESE_FLASHCARD;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import lingogo.commons.exceptions.IllegalValueException;
import lingogo.model.flashcard.Phrase;

public class JsonAdaptedFlashcardTest {
    private static final String INVALID_LANGUAGE_TYPE = "";
    private static final String INVALID_ENGLISH_PHRASE = "";
    private static final String INVALID_FOREIGN_PHRASE = "";

    private static final String VALID_LANGUAGE_TYPE = NIGHT_CHINESE_FLASHCARD.getLanguageType().toString();
    private static final String VALID_ENGLISH_PHRASE = NIGHT_CHINESE_FLASHCARD.getEnglishPhrase().toString();
    private static final String VALID_FOREIGN_PHRASE = NIGHT_CHINESE_FLASHCARD.getForeignPhrase().toString();

    @Test
    public void toModelType_validFlashcardDetails_returnsFlashcard() throws Exception {
        JsonAdaptedFlashcard flashcard = new JsonAdaptedFlashcard(NIGHT_CHINESE_FLASHCARD);
        assertEquals(NIGHT_CHINESE_FLASHCARD, flashcard.toModelType());
    }

    @Test
    public void toModelType_invalidLanguageType_throwsIllegalValueException() {
        JsonAdaptedFlashcard flashcard =
                new JsonAdaptedFlashcard(INVALID_LANGUAGE_TYPE, VALID_ENGLISH_PHRASE, VALID_FOREIGN_PHRASE);
        String expectedMessage = Phrase.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, flashcard::toModelType);
    }

    @Test
    public void toModelType_nullLanguageType_throwsIllegalValueException() {
        JsonAdaptedFlashcard flashcard = new JsonAdaptedFlashcard(null, VALID_ENGLISH_PHRASE, VALID_FOREIGN_PHRASE);
        String expectedMessage =
                String.format(MISSING_FIELD_MESSAGE_FORMAT, "Language " + Phrase.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, flashcard::toModelType);
    }

    @Test
    public void toModelType_invalidEnglishPhrase_throwsIllegalValueException() {
        JsonAdaptedFlashcard flashcard =
            new JsonAdaptedFlashcard(VALID_LANGUAGE_TYPE, INVALID_ENGLISH_PHRASE, VALID_FOREIGN_PHRASE);
        String expectedMessage = Phrase.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, flashcard::toModelType);
    }

    @Test
    public void toModelType_nullEnglishPhrase_throwsIllegalValueException() {
        JsonAdaptedFlashcard flashcard = new JsonAdaptedFlashcard(VALID_LANGUAGE_TYPE, null, VALID_FOREIGN_PHRASE);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "English " + Phrase.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, flashcard::toModelType);
    }

    @Test
    public void toModelType_invalidForeignPhrase_throwsIllegalValueException() {
        JsonAdaptedFlashcard flashcard =
            new JsonAdaptedFlashcard(VALID_LANGUAGE_TYPE, VALID_ENGLISH_PHRASE, INVALID_FOREIGN_PHRASE);
        String expectedMessage = Phrase.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, flashcard::toModelType);
    }

    @Test
    public void toModelType_nullForeignPhrase_throwsIllegalValueException() {
        JsonAdaptedFlashcard flashcard = new JsonAdaptedFlashcard(VALID_LANGUAGE_TYPE, VALID_ENGLISH_PHRASE, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "Foreign " + Phrase.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, flashcard::toModelType);
    }

}
