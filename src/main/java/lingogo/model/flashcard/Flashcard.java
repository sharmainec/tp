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
    private final Boolean isFlipped;

    /**
     * Every field must be present and not null. {@code isFlipped} set to false by default.
     */
    public Flashcard(Phrase englishPhrase, Phrase foreignPhrase) {
        requireAllNonNull(englishPhrase, foreignPhrase);
        this.englishPhrase = englishPhrase;
        this.foreignPhrase = foreignPhrase;
        this.isFlipped = false;
    }

    /**
     * Every field must be present and not null.
     */
    public Flashcard(Phrase englishPhrase, Phrase foreignPhrase, Boolean isFlipped) {
        requireAllNonNull(englishPhrase, foreignPhrase, isFlipped);
        this.englishPhrase = englishPhrase;
        this.foreignPhrase = foreignPhrase;
        this.isFlipped = isFlipped;
    }

    public Phrase getEnglishPhrase() {
        return englishPhrase;
    }

    public Phrase getForeignPhrase() {
        return foreignPhrase;
    }

    public Boolean getFlipStatus() {
        return isFlipped;
    }

    /**
     * Returns a flipped version of this flashcard.
     *
     * @return Flipped flashcard
     */
    public Flashcard getFlippedFlashcard() {
        return new Flashcard(englishPhrase, foreignPhrase, !isFlipped);
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
            && otherFlashcard.getForeignPhrase().equals(getForeignPhrase()) && otherFlashcard.getFlipStatus()
            .equals(getFlipStatus());
    }

    @Override
    public int hashCode() {
        return Objects.hash(englishPhrase, foreignPhrase);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Foreign phrase: ")
            .append(getForeignPhrase())
            .append("\n")
            .append("English phrase: ")
            .append(getEnglishPhrase())
            .append("\n")
            .append("isFlipped: ")
            .append(getFlipStatus());

        return builder.toString();
    }
}
