package lingogo.model.flashcard;

import static lingogo.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class LanguageTypeTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Phrase(null));
    }

    @Test
    public void constructor_invalidLanguageType_throwsIllegalArgumentException() {
        String invalidLanguageType = "";
        assertThrows(IllegalArgumentException.class, () -> new LanguageType(invalidLanguageType));
    }

    @Test
    public void constructor_capitalize_success() {
        LanguageType l = new LanguageType("bahasa melayu");
        assertEquals(l.value, "Bahasa Melayu");
    }

    @Test
    public void isValidLanguageType() {
        // null language type
        assertThrows(NullPointerException.class, () -> LanguageType.isValidLanguageType(null));

        // invalid language type
        assertFalse(LanguageType.isValidLanguageType("")); // empty string
        assertFalse(LanguageType.isValidLanguageType(" ")); // spaces only
        assertFalse(LanguageType.isValidLanguageType("English\n")); // non alphabet not allowed
        assertFalse(LanguageType.isValidLanguageType(" English")); // preceding whitespace
        assertFalse(LanguageType.isValidLanguageType(":English")); // preceding non-alphabet
        assertFalse(LanguageType.isValidLanguageType(
                "eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee")); // longer than 50 characters

        // valid language type
        assertTrue(LanguageType.isValidLanguageType("English"));
        assertTrue(LanguageType.isValidLanguageType("ENGLISH"));
        assertTrue(Phrase.isValidPhrase("Scottish Gaelic")); // more than one word
    }

    @Test
    public void equals() {
        String value = "English";
        LanguageType languageType = new LanguageType(value);

        // same object -> returns true
        assertTrue(languageType.equals(languageType));

        // same value -> returns true
        assertTrue(languageType.equals(new LanguageType("English")));

        // different casing -> return true
        assertTrue(languageType.equals(new LanguageType("english")));

        // null -> returns false
        assertFalse(languageType.equals(null));

        // different type -> returns false
        assertFalse(languageType.equals(5));

        // different value -> returns false
        assertFalse(languageType.equals(new LanguageType("Chinese")));
    }
}
