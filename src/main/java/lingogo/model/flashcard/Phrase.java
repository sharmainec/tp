package lingogo.model.flashcard;

import static java.util.Objects.requireNonNull;
import static lingogo.commons.util.AppUtil.checkArgument;

/**
 * Represents a phrase in a Flashcard in LingoGO!.
 * Guarantees: immutable; is valid as declared in {@link #isValidPhrase(String)}
 */
public class Phrase {
    /**
     * {@code \\S} The first character of the phrase must not be a whitespace.
     * This prevents " " (a blank string) from becoming a valid input.
     * {@code \\[^\n]*} Allows us to match any character that is not a new line character 0 or more times.
     */
    public static final String VALIDATION_REGEX = "\\S[^\n]*";
    public static final int MAX_NUMBER_OF_CHARACTERS = 100;

    public static final String MESSAGE_CONSTRAINTS = "Phrases can take Unicode alphabet characters,"
            + " should not be blank, and must be no longer than " + MAX_NUMBER_OF_CHARACTERS + " characters.";

    public final String value;

    /**
     * Constructs an {@code Phrase}.
     *
     * @param phrase A valid phrase.
     */
    public Phrase(String phrase) {
        requireNonNull(phrase);
        checkArgument(isValidPhrase(phrase), MESSAGE_CONSTRAINTS);
        value = phrase;
    }

    /**
     * Returns true if a given phrase is a valid phrase.
     */
    public static boolean isValidPhrase(String test) {
        return test.matches(VALIDATION_REGEX) && test.length() <= MAX_NUMBER_OF_CHARACTERS;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof Phrase
                && value.equals(((Phrase) other).value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
