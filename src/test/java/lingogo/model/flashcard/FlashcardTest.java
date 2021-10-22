package lingogo.model.flashcard;

import static lingogo.logic.commands.CommandTestUtil.VALID_CHINESE_PHRASE_GOOD_MORNING;
import static lingogo.logic.commands.CommandTestUtil.VALID_CHINESE_PHRASE_HELLO;
import static lingogo.logic.commands.CommandTestUtil.VALID_ENGLISH_PHRASE_GOOD_MORNING;
import static lingogo.logic.commands.CommandTestUtil.VALID_ENGLISH_PHRASE_HELLO;
import static lingogo.logic.commands.CommandTestUtil.VALID_LANGUAGE_TYPE_CHINESE;
import static lingogo.logic.commands.CommandTestUtil.VALID_LANGUAGE_TYPE_TAMIL;
import static lingogo.testutil.TypicalFlashcards.GOOD_MORNING_CHINESE_FLASHCARD;
import static lingogo.testutil.TypicalFlashcards.HELLO_CHINESE_FLASHCARD;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import lingogo.testutil.FlashcardBuilder;

public class FlashcardTest {
    @Test
    public void isSameFlashcard() {
        // same object -> returns true
        assertTrue(GOOD_MORNING_CHINESE_FLASHCARD.isSameFlashcard(GOOD_MORNING_CHINESE_FLASHCARD));

        // null -> returns false
        assertFalse(GOOD_MORNING_CHINESE_FLASHCARD.isSameFlashcard(null));

        // all attributes same except isFlipped -> returns true
        Flashcard editedGoodMorning =
            new FlashcardBuilder(GOOD_MORNING_CHINESE_FLASHCARD).withFlipStatus(true).build();
        assertTrue(GOOD_MORNING_CHINESE_FLASHCARD.isSameFlashcard(editedGoodMorning));

        // different english phrase, all other attributes same -> returns false
        editedGoodMorning = new FlashcardBuilder(GOOD_MORNING_CHINESE_FLASHCARD)
            .withEnglishPhrase(VALID_ENGLISH_PHRASE_HELLO).build();
        assertFalse(GOOD_MORNING_CHINESE_FLASHCARD.isSameFlashcard(editedGoodMorning));

        // English phrase differs in case, all other attributes same -> returns true
        editedGoodMorning = new FlashcardBuilder(GOOD_MORNING_CHINESE_FLASHCARD)
            .withEnglishPhrase(VALID_ENGLISH_PHRASE_GOOD_MORNING.toLowerCase()).build();
        assertTrue(GOOD_MORNING_CHINESE_FLASHCARD.isSameFlashcard(editedGoodMorning));

        // English phrase has trailing spaces, all other attributes same -> returns true
        String englishPhraseWithTrailingSpaces = VALID_ENGLISH_PHRASE_GOOD_MORNING + " ";
        editedGoodMorning = new FlashcardBuilder(GOOD_MORNING_CHINESE_FLASHCARD)
            .withEnglishPhrase(englishPhraseWithTrailingSpaces).build();
        assertTrue(GOOD_MORNING_CHINESE_FLASHCARD.isSameFlashcard(editedGoodMorning));

        // different foreign phrase, all other attributes same -> returns false
        editedGoodMorning = new FlashcardBuilder(GOOD_MORNING_CHINESE_FLASHCARD)
            .withForeignPhrase(VALID_CHINESE_PHRASE_HELLO).build();
        assertFalse(GOOD_MORNING_CHINESE_FLASHCARD.isSameFlashcard(editedGoodMorning));

        // foreign phrase differs in case, all other attributes same -> returns false
        Flashcard testGoodMorning = new FlashcardBuilder(GOOD_MORNING_CHINESE_FLASHCARD)
            .withForeignPhrase(VALID_ENGLISH_PHRASE_GOOD_MORNING).build();
        editedGoodMorning = new FlashcardBuilder(GOOD_MORNING_CHINESE_FLASHCARD)
            .withEnglishPhrase(VALID_ENGLISH_PHRASE_GOOD_MORNING.toLowerCase()).build();
        assertFalse(testGoodMorning.isSameFlashcard(editedGoodMorning));

        // foreign phrase has trailing spaces, all other attributes same -> returns false
        String foreignPhraseWithTrailingSpaces = VALID_CHINESE_PHRASE_GOOD_MORNING + " ";
        editedGoodMorning = new FlashcardBuilder(GOOD_MORNING_CHINESE_FLASHCARD)
            .withForeignPhrase(foreignPhraseWithTrailingSpaces).build();
        assertFalse(GOOD_MORNING_CHINESE_FLASHCARD.isSameFlashcard(editedGoodMorning));

        // different language type, all other attributes same -> returns false
        editedGoodMorning = new FlashcardBuilder(GOOD_MORNING_CHINESE_FLASHCARD)
            .withLanguageType(VALID_LANGUAGE_TYPE_TAMIL).build();
        assertFalse(GOOD_MORNING_CHINESE_FLASHCARD.isSameFlashcard(editedGoodMorning));

        // language type differs in case, all other attributes same -> returns true
        editedGoodMorning = new FlashcardBuilder(GOOD_MORNING_CHINESE_FLASHCARD)
            .withLanguageType(VALID_LANGUAGE_TYPE_CHINESE.toLowerCase()).build();
        assertTrue(GOOD_MORNING_CHINESE_FLASHCARD.isSameFlashcard(editedGoodMorning));

        // language type has trailing spaces, all other attributes same -> returns true
        String languageTypeWithTrailingSpaces = VALID_LANGUAGE_TYPE_CHINESE + " ";
        editedGoodMorning = new FlashcardBuilder(GOOD_MORNING_CHINESE_FLASHCARD)
            .withLanguageType(languageTypeWithTrailingSpaces).build();
        assertTrue(GOOD_MORNING_CHINESE_FLASHCARD.isSameFlashcard(editedGoodMorning));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Flashcard goodMorningCopy = new FlashcardBuilder(GOOD_MORNING_CHINESE_FLASHCARD).build();
        assertTrue(GOOD_MORNING_CHINESE_FLASHCARD.equals(goodMorningCopy));

        // same object -> returns true
        assertTrue(GOOD_MORNING_CHINESE_FLASHCARD.equals(GOOD_MORNING_CHINESE_FLASHCARD));

        // null -> returns false
        assertFalse(GOOD_MORNING_CHINESE_FLASHCARD.equals(null));

        // different type -> returns false
        assertFalse(GOOD_MORNING_CHINESE_FLASHCARD.equals(5));

        // different flashcard -> returns false
        assertFalse(GOOD_MORNING_CHINESE_FLASHCARD.equals(HELLO_CHINESE_FLASHCARD));

        // different Language type -> returns false
        Flashcard editedGoodMorning = new FlashcardBuilder(GOOD_MORNING_CHINESE_FLASHCARD)
            .withLanguageType(VALID_LANGUAGE_TYPE_TAMIL).build();
        assertFalse(GOOD_MORNING_CHINESE_FLASHCARD.equals(editedGoodMorning));

        // different English phrase -> returns false
        editedGoodMorning = new FlashcardBuilder(GOOD_MORNING_CHINESE_FLASHCARD)
            .withEnglishPhrase(VALID_ENGLISH_PHRASE_HELLO).build();
        assertFalse(GOOD_MORNING_CHINESE_FLASHCARD.equals(editedGoodMorning));

        // different foreign phrase -> returns false
        editedGoodMorning = new FlashcardBuilder(GOOD_MORNING_CHINESE_FLASHCARD)
            .withForeignPhrase(VALID_CHINESE_PHRASE_HELLO).build();
        assertFalse(GOOD_MORNING_CHINESE_FLASHCARD.equals(editedGoodMorning));
    }
}
