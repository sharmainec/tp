package seedu.address.model.flashcard;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

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
    public void isValidPhrase() {
        // null address
        assertThrows(NullPointerException.class, () -> Phrase.isValidPhrase(null));

        // invalid phrases
        assertFalse(Phrase.isValidPhrase("")); // empty string
        assertFalse(Phrase.isValidPhrase(" ")); // spaces only
        assertFalse(Phrase.isValidPhrase(" Good Morning")); // preceding whitespace
        assertFalse(Phrase.isValidPhrase("Good Morning.")); // punctuation: fullstop
        assertFalse(Phrase.isValidPhrase("Good Morning!")); // punctuation: exclaimation mark
        assertFalse(Phrase.isValidPhrase("Good Morning?")); // punctuation: question mark
        assertFalse(Phrase.isValidPhrase("Good_Morning")); // punctuation: underscore
        assertFalse(Phrase.isValidPhrase("Good-Morning")); // punctuation: dash

        // valid English phrases
        assertTrue(Phrase.isValidPhrase("Good Morning"));
        assertTrue(Phrase.isValidPhrase("GOOD MORNING"));
        assertTrue(Phrase.isValidPhrase("shouldn't have")); // punctuation: apostrophe
        assertTrue(Phrase.isValidPhrase("Good, morning")); // punctuation: comma
        assertTrue(Phrase.isValidPhrase("sheesh, i can't believe we are back in lockdown again")); // long phrase
       
        // valid Unicode character phrases
        assertTrue(Phrase.isValidPhrase("你好")); // Chinese, Simplified
        assertTrue(Phrase.isValidPhrase("Привет")); // Russian
        assertTrue(Phrase.isValidPhrase("أهلا")); // Arabic
        assertTrue(Phrase.isValidPhrase("안녕")); // Korean
        assertTrue(Phrase.isValidPhrase("szczęśliwy")); // Polish
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
