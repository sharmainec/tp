package lingogo.model.flashcard;

import static lingogo.logic.commands.CommandTestUtil.VALID_CHINESE_PHRASE_HELLO;
import static lingogo.logic.commands.CommandTestUtil.VALID_ENGLISH_PHRASE_GOOD_MORNING;
import static lingogo.logic.commands.CommandTestUtil.VALID_ENGLISH_PHRASE_HELLO;
import static lingogo.testutil.TypicalFlashcards.AFTERNOON_CHINESE_FLASHCARD;
import static lingogo.testutil.TypicalFlashcards.GOOD_MORNING_CHINESE_FLASHCARD;
import static lingogo.testutil.TypicalFlashcards.HELLO_CHINESE_FLASHCARD;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import lingogo.logic.commands.exceptions.CommandException;
import lingogo.testutil.FlashcardBuilder;

public class FlashcardTest {
    @Test
    public void isSameFlashcard() {
        // same object -> returns true
        assertTrue(GOOD_MORNING_CHINESE_FLASHCARD.isSameFlashcard(GOOD_MORNING_CHINESE_FLASHCARD));

        // null -> returns false
        assertFalse(GOOD_MORNING_CHINESE_FLASHCARD.isSameFlashcard(null));

        // same english phrase, all other attributes different -> returns true
        Flashcard editedGoodMorning = new FlashcardBuilder(GOOD_MORNING_CHINESE_FLASHCARD)
                .withForeignPhrase(VALID_CHINESE_PHRASE_HELLO).build();
        assertTrue(GOOD_MORNING_CHINESE_FLASHCARD.isSameFlashcard(editedGoodMorning));

        // different english phrase, all other attributes same -> returns false
        editedGoodMorning = new FlashcardBuilder(GOOD_MORNING_CHINESE_FLASHCARD)
                .withEnglishPhrase(VALID_ENGLISH_PHRASE_HELLO).build();
        assertFalse(GOOD_MORNING_CHINESE_FLASHCARD.isSameFlashcard(editedGoodMorning));

        // English phrase differs in case, all other attributes same -> returns false
        editedGoodMorning = new FlashcardBuilder(GOOD_MORNING_CHINESE_FLASHCARD)
                .withEnglishPhrase(VALID_ENGLISH_PHRASE_GOOD_MORNING.toLowerCase()).build();
        assertFalse(GOOD_MORNING_CHINESE_FLASHCARD.isSameFlashcard(editedGoodMorning));

        // name has trailing spaces, all other attributes same -> returns false
        String englishPhraseWithTrailingSpaces = VALID_ENGLISH_PHRASE_GOOD_MORNING + " ";
        editedGoodMorning = new FlashcardBuilder(GOOD_MORNING_CHINESE_FLASHCARD)
                .withEnglishPhrase(englishPhraseWithTrailingSpaces).build();
        assertFalse(GOOD_MORNING_CHINESE_FLASHCARD.isSameFlashcard(editedGoodMorning));

        // correct output to CSV string -> return true
        String csvLine = "下午,Afternoon\n";
        assertEquals(csvLine, AFTERNOON_CHINESE_FLASHCARD.toCsvString());

        // correct input from the CSV -> return true
        csvLine = "下午,Afternoon";
        try {
            assertTrue(AFTERNOON_CHINESE_FLASHCARD.isSameFlashcard(new Flashcard(csvLine)));
        } catch (CommandException ignored) {
            ;
        }
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

        // different English phrase -> returns false
        Flashcard editedGoodMorning = new FlashcardBuilder(GOOD_MORNING_CHINESE_FLASHCARD)
                .withEnglishPhrase(VALID_ENGLISH_PHRASE_HELLO).build();
        assertFalse(GOOD_MORNING_CHINESE_FLASHCARD.equals(editedGoodMorning));

        // different foreign phrase -> returns false
        editedGoodMorning = new FlashcardBuilder(GOOD_MORNING_CHINESE_FLASHCARD)
                .withForeignPhrase(VALID_CHINESE_PHRASE_HELLO).build();
        assertFalse(GOOD_MORNING_CHINESE_FLASHCARD.equals(editedGoodMorning));
    }
}
