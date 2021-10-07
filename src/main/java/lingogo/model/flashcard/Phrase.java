package lingogo.model.flashcard;

import static java.util.Objects.requireNonNull;
import static lingogo.commons.util.AppUtil.checkArgument;

/**
 * Represents a phrase in a Flashcard in LingoGO!.
 * Guarantees: immutable; is valid as declared in {@link #isValidPhrase(String)}
 */
public class Phrase {
    public static final String MESSAGE_CONSTRAINTS = "Phrases can take Unicode alphabet characters,"
        + "and should not be blank";

    /**
     * The first character of the phrase must not be a whitespace.
     * This prevents " " (a blank string) from becoming a valid input.
     * (?U) allows us to match Unicode characters, which are common in foreign languages.
     */
    public static final String VALIDATION_REGEX = "[^\\s](?U)[\\p{Alpha}', ]*";

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
        return test.matches(VALIDATION_REGEX);
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
