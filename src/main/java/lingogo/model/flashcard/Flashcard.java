package lingogo.model.flashcard;

import static lingogo.commons.util.CollectionUtil.requireAllNonNull;
import static lingogo.logic.LogicManager.INCORRECT_CSV_FORMAT_ERROR_MESSAGE;

import java.util.Objects;

import lingogo.logic.commands.exceptions.CommandException;

/**
 * Represents a Flashcard in LingoGO!.
 * Guarantees: details are present and not null; field values are validated; immutable.
 */
public class Flashcard {

    // Data fields
    private final Phrase englishPhrase;
    private final Phrase foreignPhrase;

    /**
     * Every field must be present and not null.
     */
    public Flashcard(Phrase englishPhrase, Phrase foreignPhrase) {
        requireAllNonNull(englishPhrase, foreignPhrase);
        this.englishPhrase = englishPhrase;
        this.foreignPhrase = foreignPhrase;
    }

    /**
     * Constructs a Flashcard from a line in a CSV file.
     */
    public Flashcard(String csvLine) throws CommandException {
        requireAllNonNull(csvLine);
        String[] tokens = csvLine.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
        //System.out.println(csvLine);
        if (tokens.length != 2) {
            throw new CommandException(INCORRECT_CSV_FORMAT_ERROR_MESSAGE);
        }
        this.englishPhrase = new Phrase(tokens[1]);
        this.foreignPhrase = new Phrase(tokens[0]);
    }

    public Phrase getEnglishPhrase() {
        return englishPhrase;
    }

    public Phrase getForeignPhrase() {
        return foreignPhrase;
    }

    /**
     * Returns true if both flashcards have the same English phrase and foreign language.
     * This defines a weaker notion of equality between two flashcards.
     */
    public boolean isSameFlashcard(Flashcard otherFlashcard) {
        if (otherFlashcard == this) {
            return true;
        }
        return otherFlashcard != null
                && otherFlashcard.getEnglishPhrase().equals(getEnglishPhrase());
    }

    /**
     * Returns a String representation for each flashcard to be inserted into a CSV file.
     * This works like a toString() method to output a CSV line.
     */
    public String toCsvString() {
        String foreignPhrase = excludeSpecialChars(getForeignPhrase().value);
        String englishPhrase = excludeSpecialChars(getEnglishPhrase().value);
        return foreignPhrase + "," + englishPhrase + "\n";
    }

    /**
     * Handles special characters within the phrases in each flashcard.
     */
    private static String excludeSpecialChars(String entry) {
        String processed = entry.replaceAll("\\R", " ").replaceAll("\\t", " ");
        if (entry.contains(",") || entry.contains("\"") || entry.contains("'")
                || entry.contains(";") || entry.contains("\t")) {
            entry = entry.replace("\"", "\"\"");
            processed = "\"" + entry + "\"";
        }
        return processed;
    }

    /**
     * Returns true if both flashcards have the same foreign language, English phrase,
     * and foreign phrase.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Flashcard)) {
            return false;
        }

        Flashcard otherFlashcard = (Flashcard) other;
        return otherFlashcard.getEnglishPhrase().equals(getEnglishPhrase())
                && otherFlashcard.getForeignPhrase().equals(getForeignPhrase());
    }

    @Override
    public int hashCode() {
        return Objects.hash(englishPhrase, foreignPhrase);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("English phrase: ")
                .append(getEnglishPhrase())
                .append("\n")
                .append("Answer: ")
                .append(getForeignPhrase());
        return builder.toString();
    }
}
