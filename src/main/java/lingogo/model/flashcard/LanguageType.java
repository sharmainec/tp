package lingogo.model.flashcard;

import static java.util.Objects.requireNonNull;
import static lingogo.commons.util.AppUtil.checkArgument;

/**
 * Represents a language type in a Flashcard in LingoGO!.
 * Guarantees: immutable; is valid as declared in {@link #isValidLanguageType(String)}
 */
public class LanguageType {
    public static final String MESSAGE_CONSTRAINTS = "Language type can take only alphabet characters,"
            + " and should not be blank";

    /**
     * {@code \\S} The first character of the phrase must not be a whitespace.
     * This prevents " " (a blank string) from becoming a valid input.
     * {@code \\[a-zA-Z ]*} Allows us to match any alphabet characters 0 or more times.
     */
    public static final String VALIDATION_REGEX = "\\S[a-zA-Z ]*";

    public final String value;

    /**
     * Constructs an {@code Phrase}.
     *
     * @param languageType A valid language type.
     */
    public LanguageType(String languageType) {
        requireNonNull(languageType);
        checkArgument(isValidLanguageType(languageType), MESSAGE_CONSTRAINTS);
        value = formatLanguageType(languageType);
    }

    /**
     * Returns true if a given language type is a valid language type.
     */
    public static boolean isValidLanguageType(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns the formatted string for language type, where only the first letter is capitalized.
     */
    private static String formatLanguageType(String input) {
        String[] words = input.split("\\s+");
        StringBuilder languageType = new StringBuilder("");
        for (String word : words) {
            languageType.append(word.substring(0, 1).toUpperCase())
                    .append(word.substring(1).toLowerCase()).append(" ");
        }
        return languageType.substring(0, languageType.length() - 1);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof LanguageType
                && value.equals(((LanguageType) other).value));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
