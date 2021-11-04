package lingogo.model.flashcard;

import static lingogo.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class PhraseTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Phrase(null));
    }

    @Test
    public void constructor_invalidPhrase_throwsIllegalArgumentException() {
        String invalidPhrase = "";
        assertThrows(IllegalArgumentException.class, () -> new Phrase(invalidPhrase));
    }

    @Test
    public void constructor_phraseTooLong_throwsIllegalArgumentException() {
        String longPhrase = "aaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaa";
        assertThrows(IllegalArgumentException.class, () -> new Phrase(longPhrase));
    }

    @Test
    public void isValidPhrase() {
        // null phrase
        assertThrows(NullPointerException.class, () -> Phrase.isValidPhrase(null));

        // invalid phrases
        assertFalse(Phrase.isValidPhrase("")); // empty string
        assertFalse(Phrase.isValidPhrase(" ")); // spaces only
        assertFalse(Phrase.isValidPhrase("Good\nMorning")); // new line not allowed
        assertFalse(Phrase.isValidPhrase(" Good Morning")); // preceding whitespace
        assertFalse(Phrase.isValidPhrase("aaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaa"
                + "aaaaaaaaaaaaaaaaaaaaa")); // phrase too long

        // valid English phrases
        assertTrue(Phrase.isValidPhrase("Good Morning"));
        assertTrue(Phrase.isValidPhrase("GOOD MORNING"));
        assertTrue(Phrase.isValidPhrase("shouldn't have")); // punctuation: apostrophe
        assertTrue(Phrase.isValidPhrase("Good, morning")); // punctuation: comma
        assertTrue(Phrase.isValidPhrase("Good Morning.")); // punctuation: fullstop
        assertTrue(Phrase.isValidPhrase("Good Morning!")); // punctuation: exclamation mark
        assertTrue(Phrase.isValidPhrase("Good Morning?")); // punctuation: question mark
        assertTrue(Phrase.isValidPhrase("Good_Morning")); // punctuation: underscore
        assertTrue(Phrase.isValidPhrase("Non-stop")); // punctuation: dash
        assertTrue(Phrase.isValidPhrase("This valid string is exactly 50 characters long :D")); // long phrase

        // valid Unicode character phrases
        assertTrue(Phrase.isValidPhrase("你好")); // Chinese, Simplified
        assertTrue(Phrase.isValidPhrase("Привет")); // Russian
        assertTrue(Phrase.isValidPhrase("أهلا")); // Arabic
        assertTrue(Phrase.isValidPhrase("안녕")); // Korean
        assertTrue(Phrase.isValidPhrase("szczęśliwy")); // Polish
        assertTrue(Phrase.isValidPhrase("légère")); //French
        assertTrue(Phrase.isValidPhrase("शौचालय")); //Hindi
        assertTrue(Phrase.isValidPhrase("தண்ணீர்")); //Tamil
    }

    @Test
    public void equals() {
        String value = "Good Morning";

        // same object -> returns true
        assertTrue(value.equals(value));

        // same value -> returns true
        assertTrue(value.equals("Good Morning"));

        // null -> returns false
        assertFalse(value.equals(null));

        // different type -> returns false
        assertFalse(value.equals(5));

        // different value -> returns false
        assertFalse(value.equals("Afternoon"));
    }
}
