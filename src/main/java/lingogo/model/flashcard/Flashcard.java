package lingogo.model.flashcard;

import static lingogo.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents a Flashcard in LingoGO!.
 * Guarantees: details are present and not null; field values are validated; immutable.
 */
public class Flashcard {

    // Data fields
    private final Phrase englishPhrase;
    private final Phrase foreignPhrase;
    private boolean isFlipped;

    /**
     * Every field must be present and not null.
     */
    public Flashcard(Phrase englishPhrase, Phrase foreignPhrase) {
        requireAllNonNull(englishPhrase, foreignPhrase);
        this.englishPhrase = englishPhrase;
        this.foreignPhrase = foreignPhrase;
        this.isFlipped = false;
    }

    public Phrase getEnglishPhrase() {
        return englishPhrase;
    }

    public Phrase getForeignPhrase() {
        return foreignPhrase;
    }

    public boolean getFlipStatus() {
        return isFlipped;
    }

    public void setFlipStatus(boolean status) {
        this.isFlipped = status;
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
